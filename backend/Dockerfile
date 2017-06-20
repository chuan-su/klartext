FROM openjdk:8-alpine

COPY ./entrypoint.sh /

VOLUME /tmp

RUN chmod +x ./entrypoint.sh

CMD ["./entrypoint.sh"]
