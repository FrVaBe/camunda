package de.frvabe.bpm.camunda;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
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
    public void fireUnblockingOutboundEvent() throws InterruptedException {

        // start a process; the follow up date of the Demo Task will be set to 3 seconds from now

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("demoProcess");
        assertThat(processInstance).isNotNull();

        Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
        assertEquals(1, variables.size());
        assertTrue(variables.containsKey("demoTask.followUpDate"));
       LOGGER.info("############################## " + (Date) variables.get("demoTask.followUpDate"));

        Thread.sleep(2000); // nothing should have changed

        LOGGER.info("############################## 1. check at " + new Date());
        variables = runtimeService.getVariables(processInstance.getId());
        assertEquals(1, variables.size());
        assertTrue(variables.containsKey("demoTask.followUpDate"));

        Thread.sleep(5000); // timer is expected to have been fired

        System.out.println("############################## 2. check at " + new Date());
        variables = runtimeService.getVariables(processInstance.getId());
        assertEquals(2, variables.size());
        assertTrue(variables.containsKey("demoTask.followUpDate"));
        assertTrue(variables.containsKey("demoTaskBoundaryEvent.fired"));

    }
}
