package de.frvabe.bpm.camunda;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.MessageCorrelationResultType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class MessageBoundaryCatchEventTest {

    private static final String PROCESS_KEY = "demoProcessWithMessageBoundaryCatchEvent";
    private static final String DEMO_TASK_CANCEL_MSG_NAME = "demoTaskCanceled";
    private static final String ALL_DONE_MSG_NAME = "allDone";

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Test
    public void catchCancel() throws InterruptedException {

        // given two demo process instances

        ProcessInstance pi1 = runtimeService.startProcessInstanceByKey(PROCESS_KEY);
        assertThat(pi1).isNotNull();

        ProcessInstance pi2 = runtimeService.startProcessInstanceByKey(PROCESS_KEY);
        assertThat(pi2).isNotNull();

        // send message to first process instance (and cancel UserTask)

        Task task = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
        assertNotNull(task);

        MessageCorrelationResult result =
                runtimeService.createMessageCorrelation(DEMO_TASK_CANCEL_MSG_NAME)
                        .processInstanceId(pi1.getId()).correlateWithResult();
        assertEquals(MessageCorrelationResultType.Execution, result.getResultType());

        task = taskService.createTaskQuery().processInstanceId(pi1.getId()).singleResult();
        assertNull(task);

        // check that 2nd process instance was not affected from message (still in UserTask)

        Task task2 = taskService.createTaskQuery().processInstanceId(pi2.getId()).singleResult();
        assertNotNull(task2);

        // cleanup

        runtimeService.deleteProcessInstance(pi1.getId(), "JUnit test");
        runtimeService.deleteProcessInstance(pi2.getId(), "JUnit test");

    }

    @Test
    public void eventSubscriptionQuery() throws InterruptedException {

        // create test process instance

        ProcessInstance pi = runtimeService.startProcessInstanceByKey(PROCESS_KEY);
        assertThat(pi).isNotNull();

        // check if expected message catch event exists

        EventSubscription es1 = runtimeService.createEventSubscriptionQuery()
                .processInstanceId(pi.getId()).singleResult();
        assertNotNull(es1);
        assertEquals(DEMO_TASK_CANCEL_MSG_NAME, es1.getEventName());

        // send message to catch event and force process continuation

        MessageCorrelationResult result =
                runtimeService.createMessageCorrelation(es1.getEventName())
                        .processInstanceId(pi.getId()).correlateWithResult();
        assertEquals(MessageCorrelationResultType.Execution, result.getResultType());

        // check if the next expected message catch event exists

        EventSubscription es2 = runtimeService.createEventSubscriptionQuery()
                .processInstanceId(pi.getId()).singleResult();
        assertNotNull(es2);
        assertEquals(ALL_DONE_MSG_NAME, es2.getEventName());

        // cleanup

        runtimeService.deleteProcessInstance(pi.getId(), "JUnit test");

    }

}
