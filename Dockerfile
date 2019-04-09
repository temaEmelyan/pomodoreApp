FROM openjdk:8-jdk-alpine

ARG POMO_PROFILE
ENV POMO_PROFILE $POMO_PROFILE
VOLUME /tmp
ADD target/pomodoreApp-*.jar app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar --spring.profiles.active=$POMO_PROFILE
