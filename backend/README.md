## Spring boot REST ##
> A Spring boot application

### Up and running ###

    $ ./gradlew clean build -x test
    $ docker-compose up backend
or

    $ docker-compose run --rm --service-ports backend

### Development ###
    
    $ docker-compose -f docker-compose.dev.yml up --build backend
    
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

