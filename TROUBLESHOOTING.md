# 🔧 Troubleshooting Guide

## Common Issues and Solutions

### 1. Maven/POM.xml Issues

#### Problem: "mvn command not found"
**Solution**: Use the Maven wrapper instead:
- Windows: `mvnw.cmd clean install`
- Mac/Linux: `./mvnw clean install`

#### Problem: "Non-resolvable parent POM"
**Causes & Solutions**:
- **Network Issues**: Check internet connection
- **Corporate Firewall**: Configure Maven proxy settings
- **Repository Issues**: Try clearing Maven cache:
  ```bash
  # Delete .m2/repository folder and retry
  rm -rf ~/.m2/repository  # Mac/Linux
  rmdir /s %USERPROFILE%\.m2\repository  # Windows
  ```

#### Problem: "Spring Boot version not found"
**Solution**: The pom.xml has been updated to use Spring Boot 2.7.18. If issues persist:
1. Check internet connectivity
2. Try using a different Spring Boot version:
   ```xml
   <version>2.6.15</version>  <!-- Fallback version -->
   ```

### 2. Java Issues

#### Problem: "JAVA_HOME not set"
**Solution**:
- **Windows**: Set environment variable `JAVA_HOME=C:\Program Files\Java\jdk-11.x.x`
- **Mac**: `export JAVA_HOME=$(/usr/libexec/java_home -v 11)`
- **Linux**: `export JAVA_HOME=/usr/lib/jvm/java-11-openjdk`

#### Problem: "Unsupported Java version"
**Solution**: Ensure Java 11 or higher is installed:
```bash
java -version  # Should show version 11+
```

### 3. Database Issues

#### Problem: "H2 Database connection failed"
**Solution**: The application uses in-memory H2 database. If issues occur:
1. Check if port 8080 is available
2. Verify application.properties configuration
3. Access H2 console at: http://localhost:8080/h2-console

### 4. Frontend Issues

#### Problem: "npm install fails"
**Solution**:
1. Clear npm cache: `npm cache clean --force`
2. Delete node_modules: `rm -rf node_modules`
3. Reinstall: `npm install`

#### Problem: "React app won't start"
**Solution**:
1. Check Node.js version: `node --version` (should be 16+)
2. Check if port 3000 is available
3. Try different port: `PORT=3001 npm start`

### 5. CORS Issues

#### Problem: "CORS policy error"
**Solution**: The backend is configured for CORS. If issues persist:
1. Verify backend is running on port 8080
2. Check application.properties CORS settings
3. Ensure frontend is on port 3000

### 6. Build Issues

#### Problem: "Compilation errors"
**Solution**:
1. Clean and rebuild:
   ```bash
   mvnw.cmd clean compile  # Windows
   ./mvnw clean compile    # Mac/Linux
   ```
2. Check Java version compatibility
3. Verify all dependencies are downloaded

### 7. IDE Issues

#### Problem: "IDE shows errors but code compiles"
**Solution**:
1. Refresh/reload project
2. Invalidate caches and restart IDE
3. Reimport Maven project

## Quick Fixes

### Reset Everything
```bash
# Backend
mvnw.cmd clean install -U

# Frontend
cd frontend
rm -rf node_modules package-lock.json
npm install
```

### Check System Requirements
```bash
# Java
java -version

# Node.js
node --version

# npm
npm --version
```

### Verify Ports
```bash
# Check if ports are in use
netstat -an | findstr :8080  # Backend
netstat -an | findstr :3000  # Frontend
```

## Getting Help

If you're still experiencing issues:

1. **Check the logs**: Look for error messages in the console output
2. **Verify prerequisites**: Ensure Java 11+ and Node.js 16+ are installed
3. **Network connectivity**: Ensure you can access Maven Central repository
4. **Create an issue**: Include error messages and system information

## System Information Template

When reporting issues, please include:

```
Operating System: [Windows/Mac/Linux]
Java Version: [output of java -version]
Node.js Version: [output of node --version]
Maven Version: [output of mvn --version or mvnw.cmd --version]
Error Message: [full error message]
Steps to Reproduce: [what you were doing when the error occurred]
```
