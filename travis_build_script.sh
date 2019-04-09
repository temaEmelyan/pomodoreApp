#!/usr/bin/env bash
set -e
mvn package

if [ "$TRAVIS_BRANCH" == "master" ]; then
    export CLUSTER=pomodoro-prod #todo create this cluster
    export SERVICE=pomodoro-prod
    export POMO_PROFILE=default
elif [ "$TRAVIS_BRANCH" == "develop" ]; then
    export CLUSTER=pomodoro-dev #todo create this cluster
    export SERVICE=pomodoro-dev
    export POMO_PROFILE=default
else
    export CLUSTER=pomodoro     #todo rename cluster to sandbox
    export SERVICE=pomodoro
    export POMO_PROFILE=default
fi
docker build -t pomodoro-dev .
docker tag pomodoro-dev:latest ${AWS_ACCOUNT_ID}.dkr.ecr.ap-southeast-2.amazonaws.com/pomodoro-dev:latest
eval $(aws ecr get-login --no-include-email --region ap-southeast-2)
docker push ${AWS_ACCOUNT_ID}.dkr.ecr.ap-southeast-2.amazonaws.com/pomodoro-dev:latest
aws ecs update-service --cluster ${CLUSTER} --service ${SERVICE} --force-new-deployment
