# Use maven:3.6.0-jdk-8-alpine as a parent image
FROM maven:3.6.0-jdk-8-alpine AS build

# Set the working directory in the container to /app
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application
COPY src /app/src

# Package the application
RUN mvn package

# Use openjdk:8-jdk-alpine for the runtime
FROM openjdk:8-jdk-alpine

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar /app.jar

# Set the JAVA_OPTS environment variable
ENV JAVA_OPTS=""

# Set the POMO_PROFILE environment variable
ENV POMO_PROFILE="default"

# Run the jar file when the container launches
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar --spring.profiles.active=$POMO_PROFILE