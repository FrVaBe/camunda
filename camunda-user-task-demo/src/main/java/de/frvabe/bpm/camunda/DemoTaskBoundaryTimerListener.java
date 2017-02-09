package de.frvabe.bpm.camunda;

import java.util.Date;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A lister that will be invoked on DemoTask boundary events.
 */
public class DemoTaskBoundaryTimerListener implements ExecutionListener {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(DemoTaskBoundaryTimerListener.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> Timer fired at " + new Date());
        execution.setVariable("demoTaskBoundaryEvent.fired", true);
    }


}
