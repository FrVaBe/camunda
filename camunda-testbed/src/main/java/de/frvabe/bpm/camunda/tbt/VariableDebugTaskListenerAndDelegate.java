package de.frvabe.bpm.camunda.tbt;

import java.util.Map.Entry;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * DelegateExecution code that will output current 'variable' and 'variable local' content to
 * System.out.
 */
public class VariableDebugTaskListenerAndDelegate implements ExecutionListener, JavaDelegate {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        debug(execution);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        debug(execution);
    }

    private void debug(DelegateExecution execution) {
        System.out.println();

        System.out.println("Entering '" + execution.getCurrentActivityName() + "' Activity (event="
                + execution.getEventName() + ", variableScopeKey=" + execution.getVariableScopeKey()
                + ")");

        for (Entry<String, Object> entry : execution.getVariables().entrySet()) {
            System.out.println("  variable: " + entry.getKey() + "=" + entry.getValue());
        }

        for (Entry<String, Object> entry : execution.getVariablesLocal().entrySet()) {
            System.out.println("  variable local: " + entry.getKey() + "=" + entry.getValue());
        }

        System.out.println("Exiting " + execution.getCurrentActivityName() + " (event="
                + execution.getEventName() + ", variableScopeKey=" + execution.getVariableScopeKey()
                + ")");

        System.out.println();
    }

}
