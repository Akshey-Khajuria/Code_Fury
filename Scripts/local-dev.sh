#!/usr/bin/env sh
set -e

echo "======================================"
echo "🛠️  Starting Local Development Environment"
echo "======================================"

### Start Backend (Java Spring Boot)
start_backend() {
  echo "▶️ Starting Backend..."
  cd Backend
  chmod +x mvnw
  ./mvnw spring-boot:run
}

### Start Frontend (React)
start_frontend() {
  if [ -d "Frontend" ]; then
    echo "▶️ Starting Frontend..."
    cd Frontend
    npm install
    npm start
  else
    echo "⚠️ Frontend directory not found. Skipping..."
  fi
}

### Run both in parallel
start_backend &
BACKEND_PID=$!

start_frontend &
FRONTEND_PID=$!

### Trap CTRL+C to kill both
trap "echo '🛑 Stopping Dev Environment'; kill $BACKEND_PID $FRONTEND_PID" INT

wait
