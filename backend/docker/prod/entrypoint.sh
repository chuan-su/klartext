#!/bin/sh

if ["$DEBUG" = true ]; then
  printf "Running the application in debug mode\n"
  JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"
fi

exec java $JAVA_OPTS \
     -Djava.security.egd=file:/dev/./urandom \
     -jar /klartext/backend/app.jar
