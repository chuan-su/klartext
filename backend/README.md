## Spring boot REST ##
> A Spring boot application

### Development ###

    $ docker-compose up --build backend

  * The command above uses the Spring Boot Gradle plugin `bootRun` task to run the application. If you have `Java` installed and prefer to create an executable jar and run the application using `java -jar`, then run the following commands:

        $ ./gradlew assemble
        $ docker-compose -f docker-compose.prod.yml up --build backend

      * Remote debug is enabled by default, you can toggle `REMOTE_DEBUG=0` in `docker-compose.prod.yml`

Server (with automatic restart on compile) can be accessed at [localhost:8080](http://localhost:8080)

### Debug dockerized Spring boot application ###

By default, Spring boot application uses embedded Tomcat server container which supports
remote debugging Java Platform Debugger Architecture JPDA.
Remote debugging was enabled by setting `$JAVA_OPTS` on spring boot application start:

    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000

##### Intellij IDEA remote debugging config #####

Go to Intellij IDEA `Run` -> `Edit Configurations`
And set remote transport port to `8000`
![ ](https://github.com/chuan-su/klartext/blob/master/backend/intellij_remote_debug_config.png)
Now you can start debugging dockerized spring boot application with Intellij remote debug config

Click on Intellij IDEA `Run` -> `Debug docker springBoot`
