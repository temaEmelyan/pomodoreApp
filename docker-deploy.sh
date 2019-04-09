#!/usr/bin/env bash
docker build -t pomodoro-dev .
docker tag pomodoro-dev:latest ${AWS_ACCOUNT_ID}.dkr.ecr.ap-southeast-2.amazonaws.com/pomodoro-dev:latest
docker push ${AWS_ACCOUNT_ID}.dkr.ecr.ap-southeast-2.amazonaws.com/pomodoro-dev:latest