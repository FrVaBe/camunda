package de.frvabe.bpm.camunda;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class UserTaskTest {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    /**
     * Checks if an unblocking outbound event of a User Task gets fired on the follow up date.
     * 
     * @throws InterruptedException
     */
    @Test
    public void timerWithTimerDate() throws InterruptedException {

        // start a process; the follow up date of the Demo Task will be set to 3 seconds from now

        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("demoProcessWithTimerDate");
        assertThat(processInstance).isNotNull();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .singleResult();

        Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
        assertTrue(variables.containsKey("demoTask.followUpDate"));

        Map<String, Object> variablesLocal =
                runtimeService.getVariablesLocal(task.getExecutionId());
        assertFalse(variablesLocal.containsKey("demoTaskBoundaryEvent.fired"));

        Thread.sleep(2500); // nothing should have changed

        variables = runtimeService.getVariables(processInstance.getId());
        assertTrue(variables.containsKey("demoTask.followUpDate"));

        variablesLocal = runtimeService.getVariablesLocal(task.getExecutionId());
        assertFalse(variablesLocal.containsKey("demoTaskBoundaryEvent.fired"));

        Thread.sleep(7500); // timer is expected to have been fired after this sleep

        variables = runtimeService.getVariables(processInstance.getId());
        assertTrue(variables.containsKey("demoTask.followUpDate"));

        variablesLocal = runtimeService.getVariablesLocal(task.getExecutionId());
        assertTrue(variablesLocal.containsKey("demoTaskBoundaryEvent.fired"));

        // cleanup after test
        runtimeService.deleteProcessInstance(processInstance.getId(), "JUnit test");

    }

    /**
     * Checks if a Timer with cycle detects the maturity of a User Task.
     * 
     * @throws InterruptedException
     */
    @Test
    public void timerWithTimeCycle() throws InterruptedException {

        // start a process; the follow up date of the Demo Task will be set to 3 seconds from now

        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("demoProcessWithTimerCycle");
        assertThat(processInstance).isNotNull();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .singleResult();

        assertNotNull(task.getFollowUpDate());
        assertTrue(task.getFollowUpDate().after(new Date()));

        Map<String, Object> variablesLocal =
                runtimeService.getVariablesLocal(task.getExecutionId());
        assertFalse(variablesLocal.containsKey("demoTaskBoundaryEvent.followUpDateReached"));

        Thread.sleep(2000); // nothing should have changed

        variablesLocal = runtimeService.getVariablesLocal(task.getExecutionId());
        assertFalse(variablesLocal.containsKey("demoTaskBoundaryEvent.followUpDateReached"));

        Thread.sleep(7500); // timer is expected to have been fired after this sleep

        variablesLocal = runtimeService.getVariablesLocal(task.getExecutionId());
        assertTrue(variablesLocal.containsKey("demoTaskBoundaryEvent.followUpDateReached"));

        // cleanup after test
        runtimeService.deleteProcessInstance(processInstance.getId(), "JUnit test");

    }

    /**
     * Test to update the followUp date of a given user task instance. Also check of task scope
     * variables are not changed.
     */
    @Test
    public void updateFollowUpDate() {

        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("demoProcessWithTimerCycle");
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .singleResult();
        runtimeService.setVariableLocal(task.getExecutionId(), "foo", "bar");

        String taskInstanceId = task.getId();
        String taskExecutionId = task.getExecutionId();

        // followUpDate is set in BPM diagram
        assertNotNull(task.getFollowUpDate());

        // now unset followUpDate
        task.setFollowUpDate(null);
        taskService.saveTask(task);

        // request task
        Task task1 = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .singleResult();

        // check task properties
        assertEquals(taskInstanceId, task1.getId());
        assertEquals(taskExecutionId, task1.getExecutionId());
        assertNull(task.getFollowUpDate());

        // check task scope variable
        assertEquals("bar", runtimeService.getVariableLocal(task.getExecutionId(), "foo"));

        // cleanup after test
        runtimeService.deleteProcessInstance(processInstance.getId(), "JUnit test");

    }

}
