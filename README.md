# IReviewMovies
A movie review website for movie lovers developed with spring and other opensource technologies
## Developing
This project uses Docker to make life easy. 

### Build project and including docker image
``mvn clean package docker:build -DskipTests=true``

### Running

Running with docker compose:

```docker-compose up -d app db```

Don't use `-d` if you don't want to daemonize (run in background).

This will use ``resources/config/application-docker``

The website will be available at: `http://localhost:8080/`
#### Intellij IDEA
If you love InteliJ all you'll need to do is run the docker database 
and then use the `dev` spring environment which can be supplied via the Application run config.

Inside the run configuration window, where it says Environment Variables, give the following:
``SPRING_PROFILES_ACTIVE=dev``
This will allow Spring to connect to localhost exposed by the Mysql docker container.

Running:

1\. Start the database
```
$ docker-compose up -d db   
```
2\.  Press the run button in IntelliJ to start Spring Boot

3\. The website will be available at: `http://localhost:8080/`

### Running tests

1\. You need to start the integeration tests database with docker

Linux,Mac
```
$ mvn docker:stop docker:start 
```
if above doesn't work:
```
$ mvn docker:stop docker:start
```

or if you are on Windows:
```
.\mvnw.cmd docker:stop docker:start
```

2\. Once you have the test database running you can use Intelij to run the tests or use maven verify goal.
```
mvn verify
```
That will run all the tests and show you the results in your terminal.

