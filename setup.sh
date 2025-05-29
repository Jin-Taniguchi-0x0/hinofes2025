#!/bin/bash

echo "Setting up React + Kotlin Demo"
echo "============================="

# Setup frontend
echo "Setting up React frontend..."
cd frontend
echo "Installing npm dependencies..."
npm install

# Return to project root
cd ..

echo ""
echo "Setup complete!"
echo ""
echo "To run the application:"
echo "1. Start the backend: cd backend && ./gradlew bootRun"
echo "2. Start the frontend: cd frontend && npm run dev"
echo ""
echo "Or use the start-demo.sh script to run both at once:"
echo "./start-demo.sh"
echo ""
echo "Frontend will be available at: http://localhost:3000"
echo "Backend API will be available at: http://localhost:8080/api"
echo "H2 Database console will be available at: http://localhost:8080/h2-console"
