package de.frvabe.bpm.camunda.tbt.txtscopewithoutwaitstate;

/**
 * {@link HelloWorldTask} related JUnit test.
 */
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.frvabe.bpm.camunda.Main;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Deployment(resources = "bpmn/tx-scope-without-wait-state.bpmn")
public class TxScopeWithoutWaitStateTest {

    @Autowired
    RuntimeService runtimeService;

    /**
     * Test that will start the process without a wait state. Inside a Service Task the code tries
     * to find its own process by using a ProcessInstanceQuery. Monitor the output to see what
     * happens.
     */
    @Test
    public void helloWorld() {
        runtimeService.startProcessInstanceByKey("findOwnProcess");
    }

}
