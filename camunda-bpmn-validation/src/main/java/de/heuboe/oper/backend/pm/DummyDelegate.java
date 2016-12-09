package de.heuboe.oper.backend.pm;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Dummy JavaDelegate to mock a service task action.
 */
public class DummyDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {}

}
