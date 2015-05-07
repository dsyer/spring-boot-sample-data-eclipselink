# Spring Boot Sample: EclipseLink, WAR-Deployment, Eclipse IDE

This project shows how to use Spring Boot with EclipseLink as a JPA provider, configure the project for to package WAR-file, and develop in Eclipse IDE.

### EclipseLink

* Maven
    * In `pom.xml` transitive dependency `hibernate-entitymanager` excluded from the `org.springframework.boot`
    * `eclipselink` dependency added
    * `maven-surefire-plugin` and `spring-boot-maven-plugin` are configured to use `spring-instrument` for dynamic weaving
* Spring Boot
    * `JpaConfiguration.java` sets up `EclipseLinkJpaVendorAdapter`
    * Best weaving mode is selected: `dynamic` if `spring-instrument` agent is present, and `static` mode otherwise *(even if classes are not weaved, the application will still run in `false` mode)*

### WAR-Deployment

* Maven
    * Packaging ist set to `war`
    * `spring-boot-starter-tomcat` set to `provided` scope
    * `staticweave-maven-plugin` is added to statically weave persistence classes for war-file which can then run without any javaagents.
* Spring Boot
    * Class `SampleDataJpaApplication` extends `SpringBootServletInitializer`

### Eclipse IDE

* Maven
    * `spring-boot-maven-plugin`: option `addResources` is set to `false` to not to conflict with Eclipse behaviour.

To run the application, JUnits or Tomcat inside the IDE with dynamic weaving, add following arguments to JVM:

    -noverify
    -javaagent:${env_var:USERPROFILE}\.m2\repository\org\springframework\spring-instrument\4.1.6.RELEASE\spring-instrument-4.1.6.RELEASE.jar 
    -javaagent:${env_var:USERPROFILE}\.m2\repository\org\springframework\springloaded\1.2.3.RELEASE\springloaded-1.2.3.RELEASE.jar

**Notes:**

* The order of the agents is important!
* Remove line breaks!
* Update versions according to your pom
* Agent `springloaded` is optional
* To download the required jars to your local Maven repository, just run `mvn spring-boot:run` once
 
### Running

This setup can run all possible ways:

* Deploy on Tomcat inside Eclipse
* Or execute `SampleDataJpaApplication` insede Eclipse
* Or run `mvn spring-boot:run`
* Or run `mvn package` and then:
  * either deploy `target\projectname.war`
  * or run `java -jar target\projectname.war` 

