package de.frvabe.bpm.camunda;

import org.camunda.bpm.application.impl.event.ProcessApplicationEventListenerPlugin;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The main class. It contains the {@link SpringBootApplication} annotation and thus is the root of
 * the spring configuration. It also has a {@link #main(String[]) method which can be used to start
 * the application.
 * <p>
 * {@link EnableProcessApplication} is not used here we want to register process application event
 * listener. This can be done by extending the {@link SpringBootProcessApplication} class and
 * overwriting {@link #getTaskListener()} and {@link #getExecutionListener()}.
 * </p>
 * 
 * @see <a href=
 *      "https://camunda.github.io/camunda-bpm-spring-boot-starter/docs/2.0.0/index.html#processapplication">Using
 *      the ProcessApplication</a>
 * @see <a href=
 *      "https://docs.camunda.org/manual/7.6/user-guide/process-applications/process-application-event-listeners/">Process
 *      Application Event Listeners</a>
 */
@SpringBootApplication()
public class Main extends SpringBootProcessApplication {

    /**
     * Bean creation method for the Camunda ProcessApplicationEventListenerPlugin. This is necessary
     * to make use of the global application listeners.
     * 
     * @return the ProcessApplicationEventListenerPlugin bean
     */
    @Bean
    ProcessApplicationEventListenerPlugin processApplicationEventListenerPlugin() {
        return new ProcessApplicationEventListenerPlugin();
    }

    @Override
    public TaskListener getTaskListener() {
        return new TaskListener() {
            public void notify(DelegateTask delegateTask) {
                System.out.println("GlobalTaskListener invocation from "
                        + delegateTask.getTaskDefinitionKey() + "." + delegateTask.getEventName());
            }
        };
    }

    @Override
    public ExecutionListener getExecutionListener() {
        return new ExecutionListener() {
            public void notify(DelegateExecution execution) throws Exception {
                System.out.println("GlobalExecutionListener invocation from "
                        + execution.getCurrentActivityName() + "." + execution.getEventName());
            }
        };
    }

    /**
     * Main method to start the application.
     * <p>
     * The application can be configured via external properties as supported by Spring Boot.
     * External properties might be properties specified in a property file, system property or
     * program arguments. Possible properties are documented in the {@code application.properties}
     * file that is located in this jar.
     * </p>
     * 
     * @param args application properties can be provided as arguments in Spring Boot notation (
     *        {@code --property=value}) but also as system properties or in an property file.
     *
     * @see <a href="http://projects.spring.io/spring-boot/">Spring-Boot (for detailed explanation
     *      of external configuration possibilities)</a>
     * 
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.setBannerMode(Banner.Mode.LOG);
        app.run(args);
    }

}
