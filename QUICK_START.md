# 🚀 Quick Start Guide

Get your CRM system up and running in minutes!

## Option 1: Using Startup Scripts (Recommended)

### Windows Users
1. **Start Backend**: Double-click `start-backend.bat`
2. **Start Frontend**: Double-click `start-frontend.bat`

### Mac/Linux Users
1. **Start Backend**: Run `./start-backend.sh`
2. **Start Frontend**: Run `./start-frontend.sh`

## Option 2: Manual Setup

### Step 1: Start the Backend
```bash
# Install dependencies and start Spring Boot
mvn clean install
mvn spring-boot:run
```

### Step 2: Start the Frontend
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start React development server
npm start
```

## 🎯 Access Your CRM

- **Frontend Application**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **Database Console**: http://localhost:8080/h2-console

### Database Console Login
- **JDBC URL**: `jdbc:h2:mem:crmdb`
- **Username**: `sa`
- **Password**: `password`

## 📊 Sample Data

The system comes pre-loaded with sample data including:
- 5 sample customers
- 5 sample contacts
- 5 sample leads
- 5 sample opportunities
- 5 sample activities

## 🔧 Troubleshooting

### Port Already in Use
- **Backend (8080)**: Change port in `application.properties`
- **Frontend (3000)**: Set `PORT=3001` in environment

### Dependencies Issues
- **Java**: Ensure Java 11+ is installed
- **Node.js**: Ensure Node.js 16+ is installed
- **Maven**: Ensure Maven 3.6+ is installed

### Need Help?
Check the main [README.md](README.md) for detailed documentation.

## 🎉 You're Ready!

Your professional CRM system is now running. Start by exploring the dashboard and adding your own data!
