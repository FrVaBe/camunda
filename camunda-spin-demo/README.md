# camunda-spin-demo

This is a demonstration how to use [Camunda Spin](https://docs.camunda.org/manual/latest/reference/spin/) in a Camunda Application.

It shows
* the [pom.xml](pom.xml) configuration
* the Spring Configuration ([Spin Process Engine Plugin Configuration](src/main/java/de/frvabe/bpm/camunda/spin/demo/Main.java))
* how to write and read process variables and check their serialization format ([ProcessEngineSerializationFormatTest](src/test/java/de/frvabe/bpm/camunda/spin/demo/json/ProcessEngineSerializationFormatTest))
  * the _readAndWriteImmutablePersonsAsJson_ tests fails because of the missing Jackson annotation support in Camunda Spin (better `camunda-spin-dataformat-all` )


If you use Camunda SPIN with JSON you probably run into the following issues
* [Spin plugin dependency](https://forum.camunda.org/t/spin-plugin-dependency/387)
  * [camunda-spin #7](https://github.com/camunda/camunda-spin/pull/7) Update json-path to 2.0.0
* missing Jackson annotation support in camunda-spin-dataformat-all; thus serializable classes need to have a nor arg constructor and getters/setters on their properties - which is not the case for the Immutable classes in this demo project and therefore the [ProcessEngineSerializationFormatTest](src/test/java/de/frvabe/bpm/camunda/spin/demo/json/ProcessEngineSerializationFormatTest) fails