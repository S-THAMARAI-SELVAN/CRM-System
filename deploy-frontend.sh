#!/bin/bash
echo "========================================"
echo "   Deploying CRM Frontend to Vercel"
echo "========================================"
echo
echo "Navigating to frontend directory..."
cd frontend
echo
echo "Installing Vercel CLI (if not installed)..."
npm install -g vercel
echo
echo "Building the application..."
npm run build
echo
echo "Deploying to Vercel..."
echo
echo "Follow the prompts:"
echo "1. Link to existing project or create new"
echo "2. Select your GitHub repository"
echo "3. Configure build settings if prompted"
echo
vercel --prod
echo
echo "========================================"
echo "  Deployment Complete!"
echo "========================================"
