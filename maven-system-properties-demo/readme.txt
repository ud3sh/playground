This project demonstrates:
1)Reading properties from file using maven properties plugin (figure out a main class for the java exec plugin)
2)Setting properties as system properties so that they can be accessed from java during runtime


The properties from files could be overriden
e.g
mvn package -Dtest.maven.prop=com.ud3sh.playground.NonExistingClass
