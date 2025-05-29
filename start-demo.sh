#!/bin/bash

# Function to handle cleanup on exit
cleanup() {
  echo "Shutting down servers..."
  kill $FRONTEND_PID $BACKEND_PID 2>/dev/null
  exit 0
}

# Set up trap to catch Ctrl+C and other termination signals
trap cleanup SIGINT SIGTERM

echo "Starting React + Kotlin Demo"
echo "============================"

# Start the backend
echo "Starting Kotlin backend..."
cd backend
./gradlew bootRun &
BACKEND_PID=$!
cd ..

# Wait a bit for backend to initialize
sleep 5

# Start the frontend
echo "Starting React frontend..."
cd frontend
npm run dev &
FRONTEND_PID=$!
cd ..

echo "Both servers are running!"
echo "- Frontend: http://localhost:3000"
echo "- Backend: http://localhost:8080"
echo "- H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop both servers."

# Wait for both processes
wait $FRONTEND_PID $BACKEND_PID
