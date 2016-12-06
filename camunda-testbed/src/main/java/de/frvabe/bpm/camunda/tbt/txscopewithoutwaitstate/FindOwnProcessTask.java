package de.frvabe.bpm.camunda.tbt.txscopewithoutwaitstate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;

/**
 * This is a simple task that tries to find its own process by using a ProcessInstanceQuery. This
 * probably will only work if the process was already persisted.
 */
public class FindOwnProcessTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String processInstanceId = execution.getProcessInstanceId();

        System.out.println("Entering FindOwnProcessTask.execute (processInstanceId="
                + processInstanceId + ")");

        ProcessInstanceQuery query = execution.getProcessEngineServices().getRuntimeService()
                .createProcessInstanceQuery().processInstanceId(processInstanceId);
        ProcessInstance pi = query.singleResult();

        if (pi == null) {
            System.out
                    .println("WARN - No process instance with id " + processInstanceId + " found!");
        } else {
            System.out.println("Hello World!");
        }

        System.out.println(
                "Exiting FindOwnProcessTask.execute (processInstanceId=" + processInstanceId + ")");

    }


}
