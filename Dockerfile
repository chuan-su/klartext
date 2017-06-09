FROM openjdk:8
ADD . /usr/src/app
WORKDIR /usr/src/app
EXPOSE 8080
CMD ["./gradlew","bootRun"]
