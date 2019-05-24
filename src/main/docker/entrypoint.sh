#!/bin/sh

echo "The application will start in ${NEBULA_SLEEP}s..." && sleep ${NEBULA_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -jar "${HOME}/app.war" "$@"

