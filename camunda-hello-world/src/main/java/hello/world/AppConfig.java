package hello.world;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The core Spring Application configuration.
 */
@SpringBootApplication
public class AppConfig {

    /**
     * Bean creation method for the helloWorld service task.
     * 
     * @param runtimeService the Camunda runtime service handle
     * @return the helloWorld service task bean
     */
    @Bean
    HelloWorldTask helloWorldTask(final RuntimeService runtimeService) {
        return new HelloWorldTask(runtimeService);
    }

}
