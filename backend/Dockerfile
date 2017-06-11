FROM openjdk:8-alpine
ADD . /klartext/backend
WORKDIR /klartext/backend
EXPOSE 8080
RUN ./gradlew clean build -x test
CMD ["java","-jar","./build/libs/backend-0.0.1-SNAPSHOT.jar"]
