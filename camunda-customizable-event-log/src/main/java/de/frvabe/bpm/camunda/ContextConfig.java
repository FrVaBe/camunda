package de.frvabe.bpm.camunda;

import org.camunda.bpm.application.impl.event.ProcessApplicationEventListenerPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Context Configuration.
 */
@Configuration
public class ContextConfig {

    /**
     * Bean creation method for the Camunda ProcessApplicationEventListenerPlugin.
     * 
     * @return the ProcessApplicationEventListenerPlugin bean
     * @see <a href=
     *      "https://docs.camunda.org/manual/7.6/user-guide/process-applications/process-application-event-listeners/">Process
     *      Application Event Listeners</a>
     */
    @Bean
    ProcessApplicationEventListenerPlugin processApplicationEventListenerPlugin() {
        return new ProcessApplicationEventListenerPlugin();
    }

}
