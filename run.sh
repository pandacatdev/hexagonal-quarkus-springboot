#!/bin/bash
set -e

# ---------------------------------------------
# CONFIG
# ---------------------------------------------
PERSISTENCE=${1:-inmemory}
CONTAINER_NAME=hexagon-mysql
APP_DIR="adapter-quarkus"
JAR_PATH="$APP_DIR/build/quarkus-app/quarkus-run.jar"

# ---------------------------------------------
# CLEANUP
# ---------------------------------------------
cleanup() {
  echo -e "\n🧹 Stopping and removing MySQL container (if running)..."
  docker rm -f $CONTAINER_NAME >/dev/null 2>&1 || true
}
trap cleanup EXIT

# ---------------------------------------------
# BUILD THE APP
# ---------------------------------------------
echo "🏗️  Building Quarkus app..."
./gradlew :$APP_DIR:clean :$APP_DIR:quarkusBuild -x test

if [ ! -f "$JAR_PATH" ]; then
  echo "❌ Build failed: $JAR_PATH not found!"
  exit 1
fi

# ---------------------------------------------
# RUN WITH MYSQL
# ---------------------------------------------
if [ "$PERSISTENCE" == "mysql" ]; then
  echo "🐬 Starting MySQL container..."
  docker run --name $CONTAINER_NAME -d -p 3306:3306 \
    -e MYSQL_DATABASE=shop \
    -e MYSQL_ROOT_PASSWORD=test \
    mysql:8.1 >/dev/null

  echo "⏳ Waiting for MySQL to be ready..."
  until docker exec $CONTAINER_NAME mysqladmin ping -h "localhost" -p"test" --silent &>/dev/null; do
    sleep 2
  done

  echo -e "\n🚀 Starting app with MySQL persistence..."
  java -Dquarkus.profile=mysql -jar "$JAR_PATH"
else
  # ---------------------------------------------
  # RUN IN IN-MEMORY MODE
  # ---------------------------------------------
  echo -e "\n🚀 Starting app with in-memory persistence..."
  java -jar "$JAR_PATH"
fi
