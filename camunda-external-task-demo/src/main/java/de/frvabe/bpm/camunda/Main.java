package de.frvabe.bpm.camunda;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * The main class which can be used to start the application.
 */
@SpringBootApplication()
@ProcessApplication
public class Main extends SpringBootProcessApplication {


    /**
     * Main method to start the application.
     * <p>
     * The application can be configured via external properties as supported by Spring Boot.
     * External properties might be properties specified in a property file, system property or
     * program arguments. Possible properties are documented in the {@code application.properties}
     * file that is located in this jar.
     * </p>
     * The application will be accessible here: {@code http://localhost:8080/index.html} - or the
     * REST endpoint here: {@code http://localhost:8080/rest/process-instance}
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
        ConfigurableApplicationContext ctx = app.run(args);
        
        ProcessEngineImpl pe = (ProcessEngineImpl) ctx.getBean(ProcessEngine.class);
        pe.getProcessEngineConfiguration().setDefaultSerializationFormat("application/json");
    }

}
