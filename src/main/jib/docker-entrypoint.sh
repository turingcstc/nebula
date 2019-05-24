#!/bin/sh

echo "The application will start in ${SLEEP_SECONDS}s..." && sleep ${SLEEP_SECONDS}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "io.github.turingcstc.nebula.application.NebulaApplication"  "$@"
