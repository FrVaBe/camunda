# Camunda Spring Boot Starter Unit Testing Demo

This is a showcase on how to write [Camunda BPM](https://camunda.org/) related
unit tests using the [Camunda Spring Boot Starter](https://github.com/camunda/camunda-bpm-spring-boot-starter) component.

The _Camunda Spring Boot Starter_ makes it easy to bootstrap a Camunda application. There is a lot of autoconfiguration in place and the process engine initialization as well as the the Camunda webapps  setup (Admin, Cockpit, Tasklist) work as expected.

The metadata for a process application (e.g. which BPMN files should be deployed) is written down in the **processes.xml** [Deployment Descriptor](https://docs.camunda.org/manual/7.6/user-guide/process-applications/the-processes-xml-deployment-descriptor). An [empty](https://docs.camunda.org/manual/7.6/user-guide/process-applications/the-processes-xml-deployment-descriptor/#empty-processes-xml) processes.xml file can be used - in this case default configuration values will be taken. In a Camunda Spring Boot Application all BPMN files located in `src/main/resources/bpmn` will be deployed to the process engine in this case.

## Testing Scenario

Unit testing of process applications will probably contain tests of the real world process definitions that are used in production. Especially when they trigger some business logic.

But in some cases it makes sence to define special BPM processes that are only used for testing. The advantage is that the processes can be adapted exactly to the test and changes of the real world processes will not affect the test.

I thought providing of test specific BPMN models would be an easy task and I just have to place them in the `src/test/resouces/bpmn` directory but that did not do the trick. I also had no luck with providing a specific _test-processes.xml_ file and using the [@ProcessApplication](https://docs.camunda.org/manual/7.6/user-guide/process-applications/the-processes-xml-deployment-descriptor/#custom-location-for-the-processes-xml-file) annotation to specify its location. The test BPM processes were always ignored in my tests. Also the usage of the testing annotations _@Rule_ and _@Deployment_ as described in the Camunda [testing](https://docs.camunda.org/manual/7.6/user-guide/testing/) chapter did not help.

**Solution**: As hard as my attempt to find a solution was as simple was the final solution. I just had to put another empty `src/test/resources/META-INF/processes.xml` file into my project. Together with the already existing `src/main/resources/META-INF/processes.xml` file all BPM resources are now deployed to the processes engine during unit testing.

**Disclaimer**: I wouldn't have thought that this works (or better that a second file would make any difference) - and I still do not know why - and how long - it works.

## Demo Project setup

This project hat the following content

```
src
├───main
│   ├───java
│   │   └───de
│   │       └───frvabe
│   │           └───bpm
│   │               └───camunda
│   │                       Main.java                   Spring/Camunda process application class
│   │                       package-info.java
│   │
│   └───resources
│       │   application.properties
│       │
│       ├───bpmn
│       │       productionDemoProcess.bpmn              the real worl production process
│       │       README.md
│       │
│       ├───META-INF
│       │       processes.xml                           empty **prod** deployment descriptor
│       │       README.md
│       │
│       └───static
│               index.html
│
└───test
    ├───java
    │   └───de
    │       └───frvabe
    │           └───bpm
    │               └───camunda
    │                       ProductionProcessTest.java  JUnit test for the production process
    │                       TestProcessTest.java        JUnit test for the test process
    │
    └───resources
        │   test.properties                             additional properties for testing
        │
        ├───bpmn
        │       README.md
        │       testDemoProcess.bpmn                    the test deploprocess
        │
        └───META-INF
                processes.xml                           empty **test** deployment descriptor
                README.md
```

Have a look at the project. There are two tests

- [ProductionProcessTest.java](src/main/java/de/frvabe/bpm.camunda/ProductionProcessTest.java) and  
- [TestProcessTest.java](src/test/java/de/frvabe/bpm.camunda/TestProcessTest.java).

Both tests try to start the corresponding process and should succeed.
