package hello.word;

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

import hello.world.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class})
@Deployment(resources = "bpmn/hello-world.bpmn")
public class HelloWorldTest {

    @Autowired
    RuntimeService runtimeService;

    /**
     * Test to start a process and print a hello world message.
     */
    @Test
    public void helloWorld() {
        runtimeService.startProcessInstanceByKey("helloWorldProcess");
    }

}
