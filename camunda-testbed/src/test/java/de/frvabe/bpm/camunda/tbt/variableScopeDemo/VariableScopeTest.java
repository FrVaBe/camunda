package de.frvabe.bpm.camunda.tbt.variableScopeDemo;

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
@Deployment(resources = "bpmn/variable-scope.bpmn")
public class VariableScopeTest {

    @Autowired
    RuntimeService runtimeService;

    @Test
    public void varibaleScopeDemo() {
        runtimeService.startProcessInstanceByKey("variableScopeDemo");
    }
}
