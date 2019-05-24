# https://hub.docker.com/_/openjdk
# 11-jre-slim-stretch size:100M This image is based on the popular Debian
# 8-jdk-alpine size:73M This image is based on the popular Alpine Linux

FROM openjdk:11-jre-slim-stretch
# FROM openjdk:8-jdk-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    NEBULA_SLEEP=0 \
    JAVA_OPTS=""

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh nebula
WORKDIR /home/nebula

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown nebula:nebula entrypoint.sh
USER nebula

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8080

ADD *.war app.war


# FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","nebula.platform.NebulaPlatformApplication"]