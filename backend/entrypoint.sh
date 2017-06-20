#!/bin/sh

JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"

exec java $JAVA_OPTS \
     -Djava.security.egd=file:/dev/./urandom \
     -jar /klartext/backend/build/libs/backend-0.0.1-SNAPSHOT.jar
