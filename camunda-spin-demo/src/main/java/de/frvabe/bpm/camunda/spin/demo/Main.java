package de.frvabe.bpm.camunda.spin.demo;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * A main class that runs the application.
 * <p>
 * As there is not much to run the class mainly serves as the entry configuration point for Spring
 * Boot and the Camunda Spring Boot Starter.
 */
@SpringBootApplication
@EnableProcessApplication
public class Main {
    
    /**
     * Bean creation method for the Camunda SpinProcessEnginePlugin.
     * 
     * @return the SpinProcessEnginePlugin bean
     * @see <a href=
     *      "https://docs.camunda.org/manual/latest/user-guide/data-formats/configuring-spin-integration/#configuring-the-spin-process-engine-plugin">Configuring
     *      the Spin Process Engine Plugin</a>
     */
    @Bean
    SpinProcessEnginePlugin spinProcessEnginePlugin() {
        return new SpinProcessEnginePlugin();
    }

    public static void main(String... args) {
        SpringApplication.run(Main.class, args);
    }
}
