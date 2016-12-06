package hello.world;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;

/**
 * Business Process Task that sends notifications about new incidents to a dedicated exchange.
 */
public class HelloWorldTask implements JavaDelegate {

    private final RuntimeService runtimeService;

    /**
     * Full value constructor.
     * 
     * @param runtimeService the camunda runtime service handle
     */
    public HelloWorldTask(final RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String processInstanceId = execution.getProcessInstanceId();

        System.out.println(
                "Entering HelloWorldTask.execute (processInstanceId=" + processInstanceId + ")");

        ProcessInstanceQuery query =
                runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId);
        ProcessInstance pi = query.singleResult();

        if (pi == null) {
            System.out
                    .println("WARN - No process instance with id " + processInstanceId + " found!");
        } else {
            System.out.println("Hello World!");
        }

        System.out.println(
                "Exiting HelloWorldTask.execute (processInstanceId=" + processInstanceId + ")");

    }


}
