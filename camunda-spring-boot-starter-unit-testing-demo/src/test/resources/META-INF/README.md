This directory contains an empty 'processes.xml' ([Deployment Descriptor](https://docs.camunda.org/manual/7.6/user-guide/process-applications/the-processes-xml-deployment-descriptor/) file. In case of an [empty processes.xml](https://docs.camunda.org/manual/7.6/user-guide/process-applications/the-processes-xml-deployment-descriptor/#empty-processes-xml)
default values will be used.

Although an empty 'processes.xml' file already exists in the 'src/main/resources/META-INF' directory 
this file is still necessary to also trigger the deployment of the test BPMN files in the 'src/test/resouces/bpmn' directory.
