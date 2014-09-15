# Spring Boot EclipseLink Sample

This project shows how to use Spring Boot with EclipseLink as a JPA provider.
It's pretty trivial to set up, but you do ahve to watch out for the agent setup
so that the domain classes can be enhanced at runtime. The spring-instrument agent
is added for you in the pom.xml for testing and running with spring-boot:run.
When you run in an IDE you might have to set up a launcher with the agent
attached manually.
