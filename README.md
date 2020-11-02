## Work Tracker

Java Web Application that can be used to track and get reports about the project tasks.

### Prerequisite
* [Java 11](https://openjdk.java.net/)
* [Maven](https://maven.apache.org/)
* [PostgreSQL](https://hub.docker.com/_/postgres)

### Build and run
```shell script
mvn clean install
java -jar target/worktracker-0.0.1-SNAPSHOT.jar
```

### DB diagram
![db-diagram](https://github.com/ifdi/worktracker/blob/master/db-diagram.png "DB diagram")

### Swagger documentation
To view the swagger documentation please use [swagger editor](https://editor.swagger.io/) and import the 
[project's swagger documentation](https://github.com/ifdi/worktracker/blob/master/swagger-doc.json).

