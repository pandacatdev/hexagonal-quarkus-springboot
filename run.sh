#!/bin/bash
set -e

PERSISTENCE=${1:-inmemory}
CONTAINER_NAME=hexagon-mysql

cleanup() {
  echo -e "\n🧹 Stopping and removing container..."
  docker rm -f $CONTAINER_NAME >/dev/null 2>&1 || true
}
trap cleanup EXIT

if [ "$PERSISTENCE" == "mysql" ]; then
  echo "🐬 Starting MySQL container..."
  docker run --name $CONTAINER_NAME -d -p 3306:3306 \
    -e MYSQL_DATABASE=shop \
    -e MYSQL_ROOT_PASSWORD=test \
    mysql:8.1 >/dev/null

  echo "⏳ Waiting for MySQL to initialize..."
  until docker exec $CONTAINER_NAME mysqladmin ping -h "localhost" -p"test" --silent; do
    sleep 2
  done

  echo "🚀 Starting app with MySQL persistence..."
  ./gradlew :bootstrap:run -Dpersistence=mysql
else
  echo "🚀 Starting app with in-memory persistence..."
  ./gradlew :bootstrap:run -Dpersistence=inmemory
fi
