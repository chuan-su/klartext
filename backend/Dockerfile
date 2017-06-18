FROM openjdk:8-alpine
VOLUME /tmp
ADD ./build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'


ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
