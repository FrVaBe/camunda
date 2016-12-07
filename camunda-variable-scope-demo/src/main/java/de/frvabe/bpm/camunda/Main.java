package de.frvabe.bpm.camunda;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The core Spring Application configuration.
 */
@SpringBootApplication
@ProcessApplication
public class Main extends SpringBootProcessApplication {

    @Override
    public ExecutionListener getExecutionListener() {
        return new GlobalExecutionListener();
    }

    public static void main(final String... args) {
        SpringApplication.run(Main.class, args);
    }

}
