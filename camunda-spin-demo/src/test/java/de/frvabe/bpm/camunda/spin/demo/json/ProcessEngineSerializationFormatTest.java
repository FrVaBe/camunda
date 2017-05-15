package de.frvabe.bpm.camunda.spin.demo.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.frvabe.bpm.camunda.spin.demo.Main;
import de.frvabe.bpm.camunda.spin.demo.pojo.ImmutablePersons;
import de.frvabe.bpm.camunda.spin.demo.pojo.Persons;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class ProcessEngineSerializationFormatTest {

    private static final String TEST_PROCESS_KEY = "testDemoProcess";

    @Autowired
    ProcessEngine pe;

    @Autowired
    RuntimeService runtimeService;

    @Test
    public void readAndWritePersonsAsJson() {

        final String variableName = "json.persons";

        // use JSON as default serialization format
        ((ProcessEngineImpl) pe).getProcessEngineConfiguration()
                .setDefaultSerializationFormat(Variables.SerializationDataFormats.JSON.getName());

        // start a demo process instance
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(TEST_PROCESS_KEY);

        // store a variable
        Persons persons = PersonsFactory.createPersons();
        runtimeService.setVariable(pi.getProcessInstanceId(), variableName, persons);

        // read variable back and check if format is JSON
        ObjectValue retrievedTypedObjectValue =
                runtimeService.getVariableTyped(pi.getId(), variableName);
        assertNotNull(retrievedTypedObjectValue);
        assertEquals(Variables.SerializationDataFormats.JSON.getName(),
                retrievedTypedObjectValue.getSerializationDataFormat());

        // read all variables back
        Map<String, Object> vars = runtimeService.getVariables(pi.getId());
        assertTrue(vars.keySet().contains(variableName));

    }

    @Test
    public void readAndWritePersonsAsJava() {

        final String variableName = "java.persons";

        // use JAVA as default serialization format
        ((ProcessEngineImpl) pe).getProcessEngineConfiguration()
                .setDefaultSerializationFormat(Variables.SerializationDataFormats.JAVA.getName());

        // start a demo process instance
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(TEST_PROCESS_KEY);

        // store a variable
        Persons persons = PersonsFactory.createPersons();
        runtimeService.setVariable(pi.getProcessInstanceId(), variableName, persons);

        // read variable back and check if format is JSON
        ObjectValue retrievedTypedObjectValue =
                runtimeService.getVariableTyped(pi.getId(), variableName);
        assertNotNull(retrievedTypedObjectValue);
        assertEquals(Variables.SerializationDataFormats.JAVA.getName(),
                retrievedTypedObjectValue.getSerializationDataFormat());

        // read all variables back
        Map<String, Object> vars = runtimeService.getVariables(pi.getId());
        assertTrue(vars.keySet().contains(variableName));

    }

    @Test
    public void readAndWriteImmutablePersonsAsJson() {

        final String variableName = "json.persons";

        // use JSON as default serialization format
        ((ProcessEngineImpl) pe).getProcessEngineConfiguration()
                .setDefaultSerializationFormat(Variables.SerializationDataFormats.JSON.getName());

        // start a demo process instance
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(TEST_PROCESS_KEY);

        // store a variable
        ImmutablePersons persons = PersonsFactory.createImmutablePersons();
        runtimeService.setVariable(pi.getProcessInstanceId(), variableName, persons);

        // read variable back and check if format is JSON
        ObjectValue retrievedTypedObjectValue =
                runtimeService.getVariableTyped(pi.getId(), variableName);
        assertNotNull(retrievedTypedObjectValue);
        assertEquals(Variables.SerializationDataFormats.JSON.getName(),
                retrievedTypedObjectValue.getSerializationDataFormat());

        // read all variables back
        Map<String, Object> vars = runtimeService.getVariables(pi.getId());
        assertTrue(vars.keySet().contains(variableName));

    }

    @Test
    public void readAndWriteImmutablePersonsAsJava() {

        final String variableName = "java.persons";

        // use JAVA as default serialization format
        ((ProcessEngineImpl) pe).getProcessEngineConfiguration()
                .setDefaultSerializationFormat(Variables.SerializationDataFormats.JAVA.getName());

        // start a demo process instance
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(TEST_PROCESS_KEY);

        // store a variable
        ImmutablePersons persons = PersonsFactory.createImmutablePersons();
        runtimeService.setVariable(pi.getProcessInstanceId(), variableName, persons);

        // read variable back and check if format is JSON
        ObjectValue retrievedTypedObjectValue =
                runtimeService.getVariableTyped(pi.getId(), variableName);
        assertNotNull(retrievedTypedObjectValue);
        assertEquals(Variables.SerializationDataFormats.JAVA.getName(),
                retrievedTypedObjectValue.getSerializationDataFormat());

        // read all variables back
        Map<String, Object> vars = runtimeService.getVariables(pi.getId());
        assertTrue(vars.keySet().contains(variableName));

    }
}
