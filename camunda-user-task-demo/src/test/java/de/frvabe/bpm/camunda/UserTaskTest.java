package de.frvabe.bpm.camunda;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Deployment(resources = "bpmn/demoProcess.bpmn")
public class UserTaskTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTaskTest.class);

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    /**
     * Checks if an unblocking outpound event of a User Task gets fired.
     * 
     * @throws InterruptedException
     */
    @Test
    public void timeWithTimerDate() throws InterruptedException {

        // start a process; the follow up date of the Demo Task will be set to 3 seconds from now

        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("demoProcessWithTimerDate");
        assertThat(processInstance).isNotNull();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .singleResult();

        LOGGER.info("Process executionId        : " + processInstance.getId());
        LOGGER.info("Task executionId           : " + task.getExecutionId());
        LOGGER.info("Task getTaskDefinitionKey(): " + task.getTaskDefinitionKey());


        Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
        assertEquals(1, variables.size());
        assertTrue(variables.containsKey("demoTask.followUpDate"));

        Map<String, Object> variablesLocal =
                runtimeService.getVariablesLocal(task.getExecutionId());
        assertEquals(0, variablesLocal.size());

        LOGGER.info("##################### " + (Date) variables.get("demoTask.followUpDate"));

        Thread.sleep(2000); // nothing should have changed

        LOGGER.info("##################### 1. check at " + new Date());

        variables = runtimeService.getVariables(processInstance.getId());
        assertEquals(1, variables.size());
        assertTrue(variables.containsKey("demoTask.followUpDate"));

        variablesLocal = runtimeService.getVariablesLocal(task.getExecutionId());
        assertEquals(0, variablesLocal.size());

        Thread.sleep(10000); // timer is expected to have been fired after this sleep

        System.out.println("##################### 2. check at " + new Date());

        variables = runtimeService.getVariables(processInstance.getId());
        assertEquals(1, variables.size());
        assertTrue(variables.containsKey("demoTask.followUpDate"));

        variablesLocal = runtimeService.getVariablesLocal(task.getExecutionId());
        assertEquals(1, variablesLocal.size());
        assertTrue(variablesLocal.containsKey("demoTaskBoundaryEvent.fired"));

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

    }

}
