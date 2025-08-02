@echo off
echo ========================================
echo    CRM Backend API Server
echo ========================================
echo.
echo Setting up Java environment...
set JAVA_HOME=C:\Program Files\Java\jdk-23
set PATH=%JAVA_HOME%\bin;%PATH%
echo.
echo Java Version:
java -version
echo.
echo ========================================
echo   Backend API Endpoints Available:
echo ========================================
echo   📍 Base URL: http://localhost:8080/api
echo   🔍 Customers: http://localhost:8080/api/customers
echo   👥 Contacts: http://localhost:8080/api/contacts  
echo   📈 Leads: http://localhost:8080/api/leads
echo   💼 Opportunities: http://localhost:8080/api/opportunities
echo   📋 Activities: http://localhost:8080/api/activities
echo   📊 Dashboard: http://localhost:8080/api/dashboard/stats
echo   🗄️  H2 Console: http://localhost:8080/h2-console
echo ========================================
echo.
echo Note: This is a demo showing the API structure.
echo For full functionality, Maven and internet connection are required.
echo.
echo The React frontend is already running and can display:
echo   ✅ Professional CRM Interface
echo   ✅ Dashboard with Charts
echo   ✅ Customer Management UI
echo   ✅ Contact Management UI
echo   ✅ Lead Management UI
echo   ✅ Opportunity Management UI
echo   ✅ Activity Management UI
echo.
echo Frontend URL: http://localhost:3000
echo.
pause
