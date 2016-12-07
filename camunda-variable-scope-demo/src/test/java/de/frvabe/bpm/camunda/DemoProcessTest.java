package de.frvabe.bpm.camunda;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
public class DemoProcessTest {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    @Test
    public void runProcess() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("demoProcess");

        // given the execution ids of all activities

        String task11ExecId = getExecutionId("Task 1.1", pi.getId());
        String task12ExecId = getExecutionId("Task 1.2", pi.getId());
        String task13ExecId = getExecutionId("Task 1.3", pi.getId());
        String task14ExecId = getExecutionId("Task 1.4", pi.getId());
        String task15ExecId = getExecutionId("Task 1.5", pi.getId());
        String task16ExecId = getExecutionId("Task 1.6", pi.getId());
        String task21ExecId = getExecutionId("Task 2.1", pi.getId());
        String task22ExecId = getExecutionId("Task 2.2", pi.getId());
        String task31ExecId = getExecutionId("Task 3.1", pi.getId());

        // we expect some activities to have the same execution ID
        assertEqualIds(task11ExecId, task12ExecId, task14ExecId, task16ExecId);

        // and other to have different execution ids (thus have an own execution scope=
        assertDifferentIds(task11ExecId, task13ExecId, task14ExecId, task15ExecId, task21ExecId,
                task22ExecId, task31ExecId);

    }

    private String getExecutionId(final String taskName, final String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityName(taskName).singleResult()
                .getExecutionId();
    }

    private void assertEqualIds(final String... ids) {
        if (ids.length > 1) {
            for (int idx = 1; idx < ids.length; idx++) {
                assertEquals("Ids are not equal", ids[idx - 1], ids[idx]);
            }
        }
    }

    private void assertDifferentIds(final String... ids) {
        Set<String> idSet = new HashSet<>();
        for (String id : ids) {
            idSet.add(id);
        }
        assertEquals("Ids are not different! Number of ids differs from number of unique ids.",
                ids.length, idSet.size());
    }

}
