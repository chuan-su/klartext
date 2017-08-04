FROM openjdk:8-alpine

RUN mkdir -p /klartext/backend
ADD . /klartext/backend
WORKDIR /klartext/backend

CMD ["./gradlew","clean","bootRun"]
