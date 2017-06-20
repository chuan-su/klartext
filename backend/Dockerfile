FROM openjdk:8-alpine

COPY ./entrypoint.sh /

VOLUME /tmp

RUN chmod +x ./entrypoint.sh

RUN mkdir -p /klartext/backend
ADD . /klartext/backend
# WORKDIR /
# CMD ["./entrypoint.sh"]
WORKDIR /klartext/backend

CMD ["./gradlew","bootRun"]
