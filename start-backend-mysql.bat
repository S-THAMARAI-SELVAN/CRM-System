@echo off
echo ========================================
echo    CRM Backend with MySQL Database
echo ========================================
echo.
echo Navigating to backend directory...
cd backend
echo.
echo Setting up Java environment...
set JAVA_HOME=C:\Program Files\Java\jdk-23
set PATH=%JAVA_HOME%\bin;%PATH%
echo.
echo Building application...
call mvnw.cmd clean install -q
echo.
echo Starting Spring Boot application with MySQL...
echo.
echo ========================================
echo   Backend API Endpoints:
echo ========================================
echo   📍 Base URL: http://localhost:8080/api
echo   👥 Customers: http://localhost:8080/api/customers
echo   📞 Contacts: http://localhost:8080/api/contacts
echo   📈 Leads: http://localhost:8080/api/leads
echo   💼 Opportunities: http://localhost:8080/api/opportunities
echo   📋 Activities: http://localhost:8080/api/activities
echo   📊 Dashboard: http://localhost:8080/api/dashboard/stats
echo ========================================
echo.
echo Frontend URL: http://localhost:3000
echo.
echo Press Ctrl+C to stop the server
echo ========================================
echo.
call mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=mysql
