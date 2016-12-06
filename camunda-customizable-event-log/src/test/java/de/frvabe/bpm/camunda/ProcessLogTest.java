package de.frvabe.bpm.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class ProcessLogTest {

    @Autowired
    RuntimeService runtimeService;

    @Test
    public void runProcess() {
        runtimeService.startProcessInstanceByKey("demoProcess");
    }

}
