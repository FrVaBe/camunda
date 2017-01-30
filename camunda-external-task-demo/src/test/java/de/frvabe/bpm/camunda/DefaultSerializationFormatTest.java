package de.frvabe.bpm.camunda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test if the {@code camunda.bpm.defaultSerializationFormat} setting is handled as expected.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Deployment(resources = "bpmn/demoProcess.bpmn")
public class DefaultSerializationFormatTest {

    @Autowired
    RuntimeService runtimeService;

    /**
     * Start a process instance and check of the process instance variables are serialized with
     * "application/json" as configured in the {@code applications.properties} file.
     * 
     * @throws InterruptedException
     */
    @Test
    public void checkDefaultSerializationFormatHandling() throws InterruptedException {

        String businessKey = "JUnit " + System.currentTimeMillis();

        runtimeService.startProcessInstanceByKey("demoProcess", businessKey);
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey).singleResult();
        ObjectValue retrievedTypedObjectValue =
                runtimeService.getVariableTyped(pi.getId(), "complexOject");
        assertNotNull(retrievedTypedObjectValue);
        assertEquals(Variables.SerializationDataFormats.JSON.getName(),
                retrievedTypedObjectValue.getSerializationDataFormat());
    }

}
