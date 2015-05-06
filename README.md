# Spring Boot EclipseLink Sample

This project shows how to use Spring Boot with EclipseLink as a JPA provider.

It's pretty trivial to set up but you do have to watch out for the JVM agent  setup
so that the domain classes can be weaved at runtime. The `spring-instrument` agent
is added for you in the pom.xml for testing and running with `spring-boot:run`.

When you run in an IDE you might have to set up a launcher with the agent
attached manually.

### Setting up Agents manually

You need following arguments for JVM:

    -noverify
    -javaagent:YOURMAVENREPO\org\springframework\spring-instrument\4.1.6.RELEASE\spring-instrument-4.1.6.RELEASE.jar 
    -javaagent:YOURMAVENREPO\org\springframework\springloaded\1.2.3.RELEASE\springloaded-1.2.3.RELEASE.jar

The order of the agents is important. Remove line breaks.