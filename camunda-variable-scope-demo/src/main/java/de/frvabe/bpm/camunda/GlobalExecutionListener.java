package de.frvabe.bpm.camunda;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * A listener that is registered to listen to all process activity events.
 */
public class GlobalExecutionListener implements ExecutionListener {

    public static final String ACTIVITY_EXECUTION_MAP_VAR_NAME = "activityExecutionMap";

    @Override
    public void notify(final DelegateExecution execution) throws Exception {
        if (execution.getCurrentActivityName() != null
                && "end".equals(execution.getEventName())) {
            System.out.println("activityName=" + execution.getCurrentActivityName()
                    + ", processInstanceId=" + execution.getProcessInstanceId() + ", executionId="
                    + execution.getId());
            addExecutionId(execution);
            addVariables(execution);
            printVariables(execution);
            System.out.println("");
        }
    }

    private void addExecutionId(final DelegateExecution execution) {
        if (!execution.hasVariable(ACTIVITY_EXECUTION_MAP_VAR_NAME)) {
            execution.setVariable(ACTIVITY_EXECUTION_MAP_VAR_NAME, new HashMap<String, String>());
        }
        @SuppressWarnings("unchecked")
        Map<String, String> activityExecutionMap =
                (Map<String, String>) execution.getVariable(ACTIVITY_EXECUTION_MAP_VAR_NAME);
        activityExecutionMap.put(execution.getCurrentActivityName(), execution.getId());
        execution.setVariable(ACTIVITY_EXECUTION_MAP_VAR_NAME, activityExecutionMap);
    }

    private void addVariables(final DelegateExecution execution) {
        execution.setVariable(execution.getCurrentActivityName(), "");
        execution.setVariableLocal(execution.getCurrentActivityName() + "Local", "");
    }

    private void printVariables(final DelegateExecution execution) {
        System.out.println("  VariableNames     : " + execution.getVariableNames().stream()
                .map(v -> "'" + v + "'" + " ").reduce("", String::concat));
        System.out.println("  VariableNamesLocal: " + execution.getVariableNamesLocal().stream()
                .map(v -> "'" + v + "'" + " ").reduce("", String::concat));
    }


}
