package de.frvabe.bpm.camunda;

import java.io.InputStream;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

/**
 * A listener that is registered to listen to all process activity events..
 */
public class GlobalExecutionListener implements ExecutionListener {

    private final DmnEngine dmnEngine;
    final DmnDecision eventLogDecision;

    /**
     * Default NoArgs constructor.
     */
    public GlobalExecutionListener() {
        dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();
        InputStream inputStream =
                GlobalExecutionListener.class.getResourceAsStream("/dmn/event-log-config.dmn");
        eventLogDecision = dmnEngine.parseDecision("eventLog", inputStream);
    }

    @Override
    public void notify(final DelegateExecution execution) throws Exception {
        System.out.println("Entering activity (activitId=" + execution.getCurrentActivityId()
                + ", eventName=" + execution.getEventName() + ") ...");
        handleProcessLog(execution);
    }

    /**
     * Checks if the current execution matches the configuration. If {@code yes} performs desired
     * operation; here just prints a message that was specified in the DMN model.
     * 
     * @param execution the current execution
     */
    private void handleProcessLog(final DelegateExecution execution) {
        if (execution.getCurrentActivityId() != null && execution.getEventName() != null) {
            VariableMap variables =
                    Variables.putValue("activityId", execution.getCurrentActivityId())
                            .putValue("eventName", execution.getEventName());
            DmnDecisionTableResult result =
                    dmnEngine.evaluateDecisionTable(eventLogDecision, variables);
            if (!result.isEmpty()) {
                // the configuration matched the event
                System.out.println(">>> " + result.getSingleEntry().toString());
            }
        }
    }

}
