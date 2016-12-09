package de.frvabe.bpmn.camunda;

import java.io.File;
import java.util.Collection;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.ServiceTaskImpl;
import org.camunda.bpm.model.bpmn.impl.instance.UserTaskImpl;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.junit.Test;

/**
 * A static validation of a BPM model can be done (partially) by using the
 * <a href="https://docs.camunda.org/manual/latest/user-guide/model-api/bpmn-model-api/">Camunda
 * BPMN Model API</a>.
 * <p>
 * Quote from the Camunda documentation: The BPMN model API enables easy extraction of information
 * from an existing process definition, editing an existing process definition or creating a
 * complete new one without manual XML parsing.
 * </p>
 * 
 */
public class BpmnValidationTest {

    private static final File BPMN_FILE = new File(
            System.getProperty("user.dir") + "/src/test/resources/bpmn/valid-process.bpmn");

    private static final File INVALID_BPMN_FILE = new File(System.getProperty("user.dir")
            + "/src/test/resources/bpmn/process-with-missing-servicetask-delegate.bpmn");

    @Test
    public void validateUserTasks() {
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(BPMN_FILE);
        ModelElementType taskType = modelInstance.getModel().getType(Task.class);
        Collection<ModelElementInstance> taskInstances =
                modelInstance.getModelElementsByType(taskType);
        for (ModelElementInstance task : taskInstances) {
            if (task instanceof UserTaskImpl) {
                UserTaskImpl userTask = (UserTaskImpl) task;
                System.out.println(userTask.getId() + "/" + userTask.getName());
            }
        }
    }

    @Test
    public void validateServiceTasks() {
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(BPMN_FILE);
        ModelElementType taskType = modelInstance.getModel().getType(Task.class);
        Collection<ModelElementInstance> taskInstances =
                modelInstance.getModelElementsByType(taskType);
        for (ModelElementInstance task : taskInstances) {
            if (task instanceof ServiceTaskImpl) {
                ServiceTaskImpl serviceTask = (ServiceTaskImpl) task;
                System.out.println(serviceTask.getId() + " / " + serviceTask.getName() + " / "
                        + serviceTask.getCamundaClass());

            }
        }
    }

    /**
     * This test reads an invalid BPMN file. In this case a service task has no implementation
     * information. This would make a deployment to fail but nevertheless it is still possible to
     * parse it as a BpmModelInstance.
     */
    @Test
    public void readSematicallyInvalidBpmnFile() {
        Bpmn.readModelFromFile(INVALID_BPMN_FILE);
    }

}
