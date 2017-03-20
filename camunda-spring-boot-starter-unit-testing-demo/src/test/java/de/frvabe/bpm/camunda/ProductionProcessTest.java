package de.frvabe.bpm.camunda;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@TestPropertySource(locations = {"classpath:test.properties"})
public class ProductionProcessTest {

    private static final String PRODUCTION_PROCESS_KEY = "productionDemoProcess";

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Test
    public void startProductionProcess() throws InterruptedException {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(PRODUCTION_PROCESS_KEY);
        assertThat(pi).isNotNull();
        runtimeService.deleteProcessInstance(pi.getId(), "JUnit test");
    }

}
