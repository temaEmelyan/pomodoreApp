#!/usr/bin/env bash
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64/
mvn package
docker build -t pomodoro-dev .
docker tag pomodoro-dev:latest 316859757097.dkr.ecr.ap-southeast-2.amazonaws.com/pomodoro-dev:latest
docker push 316859757097.dkr.ecr.ap-southeast-2.amazonaws.com/pomodoro-dev:latest
aws ecs update-service --cluster pomodoro --service pomodoro --force-new-deploymen