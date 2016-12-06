package de.frvabe.bpm.camunda.tbt.variableScopeDemo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * This task will set variables into different variable scopes (by using
 * {@link DelegateExecution#setVariable(String, Object)} and
 * {@link DelegateExecution#setVariableLocal(String, Object)}.
 * 
 */
public class SetVariablesTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println();
        System.out.println("Entering '" + execution.getCurrentActivityName()
                + "' ... (variableScopeKey=" + execution.getVariableScopeKey() + ")");
        System.out.println("  executing 'execution.setVariable(\"myGlobal\", \"4711\") ...' ");
        execution.setVariable("myGlobal", "4711");
        System.out.println("  executing 'execution.setVariableLocal(\"myLocal\", \"4712\") ...'");
        execution.setVariableLocal("myLocal", "4712");
        System.out.println();
    }

}
