# 🗄️ MySQL Database Setup Guide for CRM System

## 📋 **Prerequisites**

1. **MySQL Server** installed and running
2. **MySQL Workbench** or command line access
3. **Java 11+** installed
4. **Maven** or use the included Maven wrapper

## 🔧 **Step 1: Create MySQL Database**

### Option A: Using MySQL Workbench
1. Open MySQL Workbench
2. Connect to your MySQL server
3. Open the SQL script: `database/mysql-setup.sql`
4. Execute the entire script

### Option B: Using Command Line
```bash
# Login to MySQL
mysql -u root -p

# Run the setup script
source /path/to/spring-boot-app/database/mysql-setup.sql
```

### Option C: Copy and Paste
Copy the contents of `database/mysql-setup.sql` and execute in your MySQL client.

## 🔑 **Step 2: Configure Database Connection**

### Update MySQL Password
Edit `backend/src/main/resources/application.properties`:

```properties
# Update this line with your MySQL password
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Alternative: Create Local Configuration
Create `backend/src/main/resources/application-local.properties`:

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/crm_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 🚀 **Step 3: Start the Backend**

### Option A: Using Startup Script
```bash
# Windows
start-backend-mysql.bat

# Mac/Linux (create similar script)
./start-backend-mysql.sh
```

### Option B: Manual Start
```bash
cd backend

# Build the application
mvnw.cmd clean install

# Start with MySQL profile
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=mysql
```

### Option C: Using Local Profile
```bash
cd backend
mvnw.cmd spring-boot:run -Dspring.profiles.active=local
```

## 🔍 **Step 4: Verify Connection**

### Check Backend Status
1. **API Health**: http://localhost:8080/api/customers
2. **Sample Data**: Should return 5 customers from MySQL

### Check Database
```sql
USE crm_system;
SELECT COUNT(*) FROM customers;  -- Should return 5
SELECT COUNT(*) FROM contacts;   -- Should return 5
SELECT COUNT(*) FROM leads;      -- Should return 5
```

## 🌐 **Step 5: Connect Frontend**

The frontend is already configured to connect to the backend API. Once the backend is running:

1. **Frontend**: http://localhost:3000 (already running)
2. **Backend API**: http://localhost:8080/api
3. **Full Integration**: Frontend will automatically fetch data from MySQL

## 📊 **Database Schema Overview**

```
crm_system
├── customers (5 sample records)
├── contacts (5 sample records)  
├── leads (5 sample records)
├── opportunities (5 sample records)
└── activities (5 sample records)
```

## 🔧 **Troubleshooting**

### Common Issues:

1. **Connection Refused**
   ```
   Error: Connection refused to MySQL
   Solution: Ensure MySQL server is running on port 3306
   ```

2. **Authentication Failed**
   ```
   Error: Access denied for user 'root'
   Solution: Check username/password in application.properties
   ```

3. **Database Not Found**
   ```
   Error: Unknown database 'crm_system'
   Solution: Run the mysql-setup.sql script first
   ```

4. **Port Already in Use**
   ```
   Error: Port 8080 already in use
   Solution: Stop other applications or change port in application.properties
   ```

### Verification Commands:

```bash
# Check MySQL is running
mysql -u root -p -e "SHOW DATABASES;"

# Check CRM database exists
mysql -u root -p -e "USE crm_system; SHOW TABLES;"

# Check sample data
mysql -u root -p -e "USE crm_system; SELECT company_name FROM customers;"
```

## 🎯 **Expected Results**

After successful setup:

1. **MySQL Database**: `crm_system` with 5 tables and sample data
2. **Backend API**: Running on http://localhost:8080/api
3. **Frontend**: Connected to backend, displaying real data from MySQL
4. **Full CRM**: Complete customer, contact, lead, opportunity, and activity management

## 📝 **Configuration Files**

- `database/mysql-setup.sql` - Database schema and sample data
- `backend/src/main/resources/application.properties` - Main configuration
- `backend/src/main/resources/application-mysql.properties` - MySQL-specific config
- `start-backend-mysql.bat` - Windows startup script

## 🎉 **Success Indicators**

✅ MySQL server running
✅ Database `crm_system` created with tables
✅ Backend starts without errors
✅ Frontend displays data from MySQL
✅ API endpoints return JSON data
✅ CRUD operations work through the UI

Your CRM system will now be fully connected with MySQL as the persistent database!
