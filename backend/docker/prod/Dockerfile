FROM openjdk:8-alpine

RUN mkdir -p /klartext/backend
VOLUME /tmp

COPY ./build/libs/backend-0.0.1-SNAPSHOT.jar /klartext/backend/app.jar
COPY ./docker/prod/entrypoint.sh /klartext/backend
WORKDIR /klartext/backend

RUN chmod +x ./entrypoint.sh
ENTRYPOINT ["./entrypoint.sh"]

