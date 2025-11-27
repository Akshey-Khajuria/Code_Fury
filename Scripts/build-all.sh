#!/usr/bin/env sh
set -e

echo "======================================"
echo "🚀 Starting Full Project Build"
echo "======================================"

### Check Commands
command -v java >/dev/null 2>&1 || { echo "❌ Java is not installed"; exit 1; }
command -v node >/dev/null 2>&1 || { echo "❌ Node.js is not installed"; exit 1; }
command -v npm >/dev/null 2>&1 || { echo "❌ npm is not installed"; exit 1; }

### Backend Build
echo "▶️ Building Backend..."
cd Backend

# Ensure mvnw is executable
chmod +x mvnw

./mvnw clean package -DskipTests

echo "✅ Backend build complete."
cd ..

### Frontend Build
if [ -d "Frontend" ]; then
  echo "▶️ Building Frontend..."
  cd Frontend
  npm install
  npm run build
  echo "✅ Frontend build complete."
  cd ..
else
  echo "⚠️ Frontend directory not found. Skipping..."
fi

echo "======================================"
echo "🎉 Build Completed Successfully!"
echo "======================================"
