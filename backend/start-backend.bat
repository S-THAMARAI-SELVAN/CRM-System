@echo off
echo ========================================
echo    Starting CRM Backend API Server
echo ========================================
echo.
echo Installing dependencies...
call mvnw.cmd clean install -q
echo.
echo Starting Spring Boot application...
echo.
echo Backend API will be available at:
echo   📍 http://localhost:8080/api
echo   🗄️  H2 Console: http://localhost:8080/h2-console
echo.
echo Press Ctrl+C to stop the server
echo ========================================
echo.
call mvnw.cmd spring-boot:run
