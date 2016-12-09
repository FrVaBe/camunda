# Camunda BPMN Validation

In the context of BPM (Business Project Management) processing a valid BPMN process description is crucial during the runtime of a process.
This demo project covers some possibilities how to validate a BPMN process description with the [Camunda](https://camunda.org/) framework.

**Possible validation steps discovered so far**:
* static validation of the BPMN file using the [Camunda Model API](https://docs.camunda.org/manual/latest/user-guide/model-api/) (see unit tests (*Test.java))
* doing test deployments which will fail under certain circumstances (see integration tests (*IT.java))
  * unfortunately it is not documented what makes deployments fail; so far I know
    * missing Java Class / Delegate Code in Service Tasks
* running (all execution pathes of) real process instances

**related references**

* Syntactic validation of Expressions and DelegateExpressions ([Camunda Forum](https://forum.camunda.org/t/syntactic-validation-of-expressions-and-delegateexpressions/1641?u=frvabe))
* BPMN Validation with the Parser of the Process Engine ([GitHub](https://github.com/camunda/camunda-consulting/tree/master/snippets/bpmn-validation))

_Disclaimer_

This project does not contain a set of comprehensice validation checks; In fact, I have only touched the subject here rudimentarily.
I will probably need the project as entry point for further investigantions on this topic (if needed by me).
