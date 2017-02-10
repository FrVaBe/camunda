package de.frvabe.bpm.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Deployment(resources = "bpmn/demoProcess.bpmn")
public class ExternalTaskTest {

    @Autowired
    RuntimeService runtimeService;

    /**
     * Test to start a process and print a hello world message.
     * 
     * @throws InterruptedException
     */
    @Test
    public void handleExternalTask() throws InterruptedException {
        runtimeService.startProcessInstanceByKey("demoProcess");
    }

}
