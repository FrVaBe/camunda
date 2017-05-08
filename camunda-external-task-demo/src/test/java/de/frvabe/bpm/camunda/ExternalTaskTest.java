package de.frvabe.bpm.camunda;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Main.class})
@Deployment(resources = "bpmn/demoProcess.bpmn")
public class ExternalTaskTest {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ExternalTaskService externalTaskService;

    /**
     * Test to start a process and print a hello world message.
     * 
     * @throws InterruptedException
     */
    @Test
    public void handleExternalTask() throws InterruptedException {
        runtimeService.startProcessInstanceByKey("demoProcess");
    }

    /**
     * Simple tests that creates a bulk number of process instances which contain an externalTask.
     * The test will just measure how long it takes to create the process instances and complete the
     * external tasks. A small report will be printed out to the console after completing the test.
     * No assumptions are made.
     * 
     * @throws InterruptedException
     */
    @Test
    public void bulkCompleteTest() throws InterruptedException {

        // number of process instances that should be handled
        final int PROCESS_INSTANCE_VOLUME = 10_000;

        long start = System.currentTimeMillis();

        //
        // process instance creation
        //

        for (int i = 0; i < PROCESS_INSTANCE_VOLUME; i++) {
            runtimeService.startProcessInstanceByKey("demoProcess");
            if (i % 1000 == 0) {
                System.out.println("created " + i + " process instances");
            }
        }

        long afterInstanceCreation = System.currentTimeMillis();

        System.out.println("created " + PROCESS_INSTANCE_VOLUME + " process instances in "
                + (afterInstanceCreation - start) + " ms.");

        //
        // external task completion
        //

        List<LockedExternalTask> tasks = externalTaskService.fetchAndLock(100, "externalWorkerId")
                .topic("externalTaskTopic", 60L * 1000L).execute();

        int totalCompletionSize = 0;

        while (!tasks.isEmpty()) {
            for (LockedExternalTask task : tasks) {
                externalTaskService.complete(task.getId(), "externalWorkerId");
            }

            totalCompletionSize += tasks.size();
            System.out.println("completed externalTasks so far: " + totalCompletionSize);

            tasks = externalTaskService.fetchAndLock(100, "externalWorkerId")
                    .topic("externalTaskTopic", 60L * 1000L).execute();
        }

        //
        // report
        //

        long finished = System.currentTimeMillis();

        long processInstanceCreationTime = afterInstanceCreation - start;
        long externalTaskCompletionTime = finished - afterInstanceCreation;
        long totalTime = finished - start;

        System.out.println();

        System.out.println("processed " + PROCESS_INSTANCE_VOLUME + " process instances");
        System.out.println("  process instance creation: " + processInstanceCreationTime + " [ms]");
        System.out.println("  external task completion : " + externalTaskCompletionTime + " [ms]");
        System.out.println("  total                    : " + totalTime + "[ms]");

        System.out.println();

        System.out.println("Avg. duration to start a process            : "
                + (processInstanceCreationTime / PROCESS_INSTANCE_VOLUME) + " ms");
        System.out
                .println("Avg. number of started processes per second : " + PROCESS_INSTANCE_VOLUME
                        / (TimeUnit.MILLISECONDS.toSeconds(processInstanceCreationTime)));

        System.out.println();

        System.out.println("Avg. duration to complete an external task  : "
                + (externalTaskCompletionTime / PROCESS_INSTANCE_VOLUME) + " ms");
        System.out
                .println("Avg. number of completed tasks per second   : " + PROCESS_INSTANCE_VOLUME
                        / (TimeUnit.MILLISECONDS.toSeconds(externalTaskCompletionTime)));

        System.out.println();

    }
}
