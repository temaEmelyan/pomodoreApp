#!/usr/bin/env bash
set -e
mvn package

if $TRAVIS_BRANCH=="master"; then
    aws ecs update-service --cluster pomodoro-prod --service pomodoro-prod --force-new-deployment #todo create this cluster
elif $TRAVIS_BRANCH=="develop"; then
    aws ecs update-service --cluster pomodoro-dev --service pomodoro-dev --force-new-deployment #todo create this cluster
else
    aws ecs update-service --cluster pomodoro --service pomodoro --force-new-deployment #todo rename cluster to sandbox
fi
