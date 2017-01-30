package de.frvabe.bpm.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Delegate that just puts some variables to the execution for further testing.
 */
public class PrepareStateTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        ComplexObject obj = new ComplexObject();
        obj.setTextValue("Hello World");
        obj.setIntegerValue(4711);
        obj.setBooleanValue(true);

        execution.setVariable("complexOject", obj);
        execution.setVariable("textValue", obj.getTextValue());
        execution.setVariable("integerValue", obj.getIntegerValue());
        execution.setVariable("booleanValue", obj.getBooleanValue());

        System.out.println("preparation of process state done");

    }

}
