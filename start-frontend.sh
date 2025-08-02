#!/bin/bash
echo "Starting CRM Frontend Application..."
echo
cd frontend
echo "Installing dependencies..."
npm install
echo
echo "Starting React development server..."
echo "Frontend will be available at: http://localhost:3000"
echo
npm start
