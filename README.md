# Spring Boot EclipseLink Sample

This project shows how to use Spring Boot with EclipseLink as a JPA provider.
It's pretty trivial to set up, but you do have to watch out for the agent setup
so that the domain classes can be enhanced at runtime. The spring-instrument agent
is added for you in the pom.xml for testing and running with spring-boot:run.
When you run in an IDE you might have to set up a launcher with the agent
attached manually.

To run the jar you need to attach the load time weaver
E.g.

```
$ java -javaagent:$HOME/.m2/repository/org/springframework/spring-instrument/4.2.4.RELEASE/spring-instrument-4.2.4.RELEASE.jar -jar target/*.jar
```

The agent jar is downloaded already by the test configuration in the pom and attached
to the JVM during tests or when running from Maven.
