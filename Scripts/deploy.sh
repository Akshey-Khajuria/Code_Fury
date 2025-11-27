#!/usr/bin/env sh
set -e

echo "======================================"
echo "🚀 Deployment Script Started"
echo "======================================"

APP_NAME="code_fury"
BACKEND_IMAGE="codefury-backend"
FRONTEND_IMAGE="codefury-frontend"
TAG="${1:-latest}"

### Check Docker
command -v docker >/dev/null 2>&1 || { echo "❌ Docker is not installed"; exit 1; }

### Build Images
echo "▶️ Building Backend Docker Image..."
docker build -t $BACKEND_IMAGE:$TAG Backend

echo "▶️ Building Frontend Docker Image..."
docker build -t $FRONTEND_IMAGE:$TAG Frontend

echo "✅ Docker Images Built Successfully"

### Optional: Push to registry
if [ "$PUSH" = "true" ]; then
  echo "▶️ Pushing images to registry..."
  docker push $BACKEND_IMAGE:$TAG
  docker push $FRONTEND_IMAGE:$TAG
  echo "✅ Images pushed."
fi

### Docker Compose Deployment
if [ -f docker-compose.yml ]; then
  echo "▶️ Starting Deployment using docker-compose..."
  docker compose down || true
  docker compose up -d --build
  echo "✅ Deployment Completed"
else
  echo "⚠️ docker-compose.yml not found."
  echo "Skipping docker compose deployment."
fi

echo "======================================"
echo "🎉 Deployment Finished!"
echo "======================================"
