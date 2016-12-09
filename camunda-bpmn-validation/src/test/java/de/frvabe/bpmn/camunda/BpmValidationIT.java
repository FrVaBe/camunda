package de.frvabe.bpmn.camunda;

import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.util.ClassNameUtil;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.ServiceTaskImpl;
import org.junit.Rule;
import org.junit.Test;

/**
 * Process validation integration tests. The process definitions (BPMN diagrams) will be deployed to
 * the Camunda BPM framework in these tests. Some semantical process errors will be detected in this
 * case.
 */
public class BpmValidationIT {

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    /**
     * Test to deploy a valid process. No failure is expected here.
     */
    @Test
    @Deployment(resources = "bpmn/valid-process.bpmn")
    public void deployValidProcess() {
        // nothing is done here, as we just want to check for exceptions during deployment
    }

    /**
     * Test to deploy a process with a missing service class.
     * <p>
     * This test is expected to throw an exception. It does not use the @Deployment annotation
     * because the exception would be thrown outside the test in this case.
     * </p>
     */
    @Test(expected = ProcessEngineException.class)
    public void deployProcessWithMissingServiceClass() {
        DeploymentBuilder deploymentBuilder = rule.getRepositoryService().createDeployment()
                .name(ClassNameUtil.getClassNameWithoutPackage(BpmValidationIT.class) + "."
                        + "deployProcessWithMissingServiceClass");
        deploymentBuilder.addClasspathResource("bpmn/process-with-missing-servicetask-class.bpmn");
        deploymentBuilder.deploy().getId();
    }

    /**
     * Try to deploy a process with an unknown service task (unknown Java class).
     * <p>
     * No failure is expected here as the availability of the service class will first be checked
     * when needed at runtime.
     * </p>
     */
    @Test
    @Deployment(resources = "bpmn/process-with-unknown-servicetask-class.bpmn")
    public void deployProcessWithUnknwonServiceClass() {
        String processDefinitionId = rule.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionKey("demoProcess").singleResult().getId();
        BpmnModelInstance modelInstance =
                rule.getRepositoryService().getBpmnModelInstance(processDefinitionId);
        ServiceTaskImpl serviceTask =
                (ServiceTaskImpl) modelInstance.getModelElementById("demoTask");
        assertEquals("FooBar", serviceTask.getCamundaClass());
    }

}
