# 🚀 CRM Backend API

A comprehensive Spring Boot REST API for the CRM system, providing complete backend functionality for customer relationship management.

## 📋 Features

### Core API Endpoints
- **Customer Management**: CRUD operations, search, filtering, and statistics
- **Contact Management**: Contact lifecycle with customer relationships
- **Lead Management**: Lead tracking, scoring, and conversion
- **Opportunity Management**: Sales pipeline and revenue forecasting
- **Activity Management**: Task and communication tracking
- **Dashboard API**: Real-time statistics and analytics

### Technical Features
- **RESTful API Design**: Clean, consistent API endpoints
- **Data Validation**: Comprehensive input validation
- **Database Integration**: H2 in-memory database with JPA
- **CORS Support**: Configured for frontend integration
- **Sample Data**: Pre-loaded demonstration data
- **Error Handling**: Proper HTTP status codes and error responses

## 🛠️ Technology Stack

- **Spring Boot 2.7.18**: Main framework
- **Spring Data JPA**: Data persistence layer
- **H2 Database**: In-memory database
- **Spring Web**: REST API development
- **Spring Validation**: Input validation
- **Maven**: Dependency management
- **Java 11**: Programming language

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- Maven 3.6+ (or use included Maven wrapper)

### Running the Application

#### Option 1: Using Startup Scripts
**Windows:**
```bash
start-backend.bat
```

**Mac/Linux:**
```bash
chmod +x start-backend.sh
./start-backend.sh
```

#### Option 2: Manual Start
```bash
# Install dependencies
mvn clean install

# Start the application
mvn spring-boot:run
```

### Access Points
- **API Base URL**: http://localhost:8080/api
- **H2 Database Console**: http://localhost:8080/h2-console
- **Health Check**: http://localhost:8080/actuator/health

### Database Console Access
- **JDBC URL**: `jdbc:h2:mem:crmdb`
- **Username**: `sa`
- **Password**: `password`

## 📚 API Documentation

### Customer Endpoints
```
GET    /api/customers              - Get all customers (paginated)
GET    /api/customers/{id}         - Get customer by ID
POST   /api/customers              - Create new customer
PUT    /api/customers/{id}         - Update customer
DELETE /api/customers/{id}         - Delete customer
GET    /api/customers/stats        - Get customer statistics
```

### Contact Endpoints
```
GET    /api/contacts               - Get all contacts (paginated)
GET    /api/contacts/{id}          - Get contact by ID
POST   /api/contacts               - Create new contact
PUT    /api/contacts/{id}          - Update contact
DELETE /api/contacts/{id}          - Delete contact
GET    /api/contacts/customer/{id} - Get contacts by customer
```

### Lead Endpoints
```
GET    /api/leads                  - Get all leads (paginated)
GET    /api/leads/{id}             - Get lead by ID
POST   /api/leads                  - Create new lead
PUT    /api/leads/{id}             - Update lead
DELETE /api/leads/{id}             - Delete lead
PUT    /api/leads/{id}/convert     - Convert lead to customer
```

### Opportunity Endpoints
```
GET    /api/opportunities          - Get all opportunities (paginated)
GET    /api/opportunities/{id}     - Get opportunity by ID
POST   /api/opportunities          - Create new opportunity
PUT    /api/opportunities/{id}     - Update opportunity
DELETE /api/opportunities/{id}     - Delete opportunity
GET    /api/opportunities/pipeline-value - Get weighted pipeline value
```

### Activity Endpoints
```
GET    /api/activities             - Get all activities (paginated)
GET    /api/activities/{id}        - Get activity by ID
POST   /api/activities             - Create new activity
PUT    /api/activities/{id}        - Update activity
DELETE /api/activities/{id}        - Delete activity
PUT    /api/activities/{id}/complete - Mark activity as completed
```

### Dashboard Endpoints
```
GET    /api/dashboard/stats        - Get dashboard statistics
GET    /api/dashboard/lead-sources - Get lead source breakdown
GET    /api/dashboard/opportunity-stages - Get opportunity stage stats
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:h2:mem:crmdb
spring.datasource.username=sa
spring.datasource.password=password

# CORS Configuration
spring.web.cors.allowed-origins=http://localhost:3000
```

### Sample Data
The application comes with pre-loaded sample data including:
- 5 sample customers
- 5 sample contacts
- 5 sample leads
- 5 sample opportunities
- 5 sample activities

## 🧪 Testing

### Manual Testing
Use tools like Postman, curl, or your browser to test the API endpoints.

### Example API Calls
```bash
# Get all customers
curl http://localhost:8080/api/customers

# Get customer by ID
curl http://localhost:8080/api/customers/1

# Create new customer
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"companyName":"Test Company","industry":"Technology","email":"test@company.com","status":"ACTIVE"}'
```

## 🔍 Troubleshooting

### Common Issues

1. **Port 8080 already in use**
   - Change port in `application.properties`: `server.port=8081`

2. **Database connection issues**
   - Verify H2 configuration in `application.properties`
   - Check H2 console at http://localhost:8080/h2-console

3. **CORS errors**
   - Verify frontend URL in CORS configuration
   - Check that frontend is running on port 3000

4. **Maven build issues**
   - Ensure Java 11+ is installed
   - Try `mvn clean install -U` to force update dependencies

### Logs
Check application logs for detailed error information. Debug logging is enabled for the `com.crm` package.

## 📁 Project Structure

```
backend/
├── src/main/java/com/crm/
│   ├── CrmBackendApplication.java  # Main application class
│   ├── controller/                 # REST controllers
│   │   ├── CustomerController.java
│   │   ├── ContactController.java
│   │   ├── LeadController.java
│   │   ├── OpportunityController.java
│   │   ├── ActivityController.java
│   │   └── DashboardController.java
│   ├── model/                      # JPA entities
│   │   ├── Customer.java
│   │   ├── Contact.java
│   │   ├── Lead.java
│   │   ├── Opportunity.java
│   │   └── Activity.java
│   └── repository/                 # Data access layer
│       ├── CustomerRepository.java
│       ├── ContactRepository.java
│       ├── LeadRepository.java
│       ├── OpportunityRepository.java
│       └── ActivityRepository.java
├── src/main/resources/
│   ├── application.properties      # Configuration
│   └── data.sql                   # Sample data
├── pom.xml                        # Maven configuration
└── README.md                      # This file
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.
