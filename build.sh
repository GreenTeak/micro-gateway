#!/bin/bash

./gradlew clean bootRepackage

docker build --rm . --tag greenteak1992/gateway:${VER:?invalid version}
#docker push greenteak1992/gateway:${VER:?invalid version}

export VER
docker stack deploy todo -c docker-compose.yml