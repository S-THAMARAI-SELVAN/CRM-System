# Professional CRM System

A comprehensive Customer Relationship Management (CRM) system built with Spring Boot backend and React TypeScript frontend. This system provides complete functionality for managing customers, contacts, leads, opportunities, and activities.

## 🚀 Features

### Core CRM Functionality
- **Customer Management**: Complete customer lifecycle management with company information, contact details, and status tracking
- **Contact Management**: Individual contact management with relationship mapping to customers
- **Lead Management**: Lead tracking with scoring, source attribution, and conversion tracking
- **Opportunity Management**: Sales pipeline management with stage progression and revenue forecasting
- **Activity Management**: Task, meeting, call, and email tracking with calendar integration

### Dashboard & Analytics
- **Interactive Dashboard**: Real-time KPIs and performance metrics
- **Sales Analytics**: Revenue trends, pipeline analysis, and conversion rates
- **Visual Charts**: Bar charts, pie charts, and line graphs for data visualization
- **Lead Source Analysis**: Track and analyze lead generation sources

### Technical Features
- **RESTful API**: Comprehensive REST API with Spring Boot
- **Responsive Design**: Mobile-friendly Material-UI interface
- **Real-time Updates**: React Query for efficient data fetching and caching
- **Search & Filtering**: Advanced search and filtering capabilities
- **Pagination**: Efficient data pagination for large datasets
- **Form Validation**: Client and server-side validation

## 🛠️ Technology Stack

### Backend
- **Spring Boot 2.7.5**: Main framework
- **Spring Data JPA**: Data persistence layer
- **H2 Database**: In-memory database for development
- **Spring Security**: Authentication and authorization
- **Maven**: Dependency management
- **Java 11**: Programming language

### Frontend
- **React 18**: UI library
- **TypeScript**: Type-safe JavaScript
- **Material-UI (MUI)**: Component library
- **React Router**: Client-side routing
- **React Query**: Data fetching and caching
- **Recharts**: Data visualization
- **React Hook Form**: Form management
- **Axios**: HTTP client

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 11 or higher**
- **Node.js 16 or higher**
- **npm or yarn**
- **Maven 3.6 or higher**

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd spring-boot-app
```

### 2. Backend Setup (Spring Boot)

#### Install Dependencies
```bash
mvn clean install
```

#### Run the Backend Server
```bash
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080`

#### Access H2 Database Console (Optional)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:crmdb`
- Username: `sa`
- Password: `password`

### 3. Frontend Setup (React)

#### Navigate to Frontend Directory
```bash
cd frontend
```

#### Install Dependencies
```bash
npm install
```

#### Start the Development Server
```bash
npm start
```

The frontend application will start on `http://localhost:3000`

## 📁 Project Structure

```
spring-boot-app/
├── backend/                # Spring Boot API Server
│   ├── src/main/java/com/crm/
│   │   ├── CrmBackendApplication.java  # Main application
│   │   ├── controller/     # REST API controllers
│   │   │   ├── CustomerController.java
│   │   │   ├── ContactController.java
│   │   │   ├── LeadController.java
│   │   │   ├── OpportunityController.java
│   │   │   ├── ActivityController.java
│   │   │   └── DashboardController.java
│   │   ├── model/          # JPA entities
│   │   │   ├── Customer.java
│   │   │   ├── Contact.java
│   │   │   ├── Lead.java
│   │   │   ├── Opportunity.java
│   │   │   └── Activity.java
│   │   └── repository/     # Data access layer
│   │       ├── CustomerRepository.java
│   │       ├── ContactRepository.java
│   │       ├── LeadRepository.java
│   │       ├── OpportunityRepository.java
│   │       └── ActivityRepository.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── data.sql        # Sample data
│   ├── pom.xml            # Backend Maven config
│   ├── start-backend.bat  # Windows startup script
│   ├── start-backend.sh   # Unix startup script
│   └── README.md          # Backend documentation
├── frontend/              # React TypeScript App
│   ├── public/
│   │   ├── index.html
│   │   └── manifest.json
│   ├── src/
│   │   ├── components/    # Reusable React components
│   │   │   └── Layout/
│   │   ├── pages/         # Page components
│   │   │   ├── Dashboard/
│   │   │   ├── Customers/
│   │   │   ├── Contacts/
│   │   │   ├── Leads/
│   │   │   ├── Opportunities/
│   │   │   └── Activities/
│   │   ├── services/      # API service layer
│   │   │   ├── api.ts
│   │   │   └── customerService.ts
│   │   ├── types/         # TypeScript definitions
│   │   │   └── index.ts
│   │   ├── App.tsx        # Main App component
│   │   └── index.tsx      # Application entry point
│   ├── package.json       # Frontend dependencies
│   └── tsconfig.json      # TypeScript config
├── start-backend.bat      # Main backend startup (Windows)
├── start-backend.sh       # Main backend startup (Unix)
├── start-frontend.bat     # Main frontend startup (Windows)
├── start-frontend.sh      # Main frontend startup (Unix)
├── QUICK_START.md         # Quick start guide
├── TROUBLESHOOTING.md     # Troubleshooting guide
└── README.md              # Main documentation
```

## 🔧 API Endpoints

### Customer Management
- `GET /api/customers` - Get all customers with pagination and search
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer
- `GET /api/customers/stats` - Get customer statistics

### Contact Management
- `GET /api/contacts` - Get all contacts with pagination and search
- `GET /api/contacts/{id}` - Get contact by ID
- `POST /api/contacts` - Create new contact
- `PUT /api/contacts/{id}` - Update contact
- `DELETE /api/contacts/{id}` - Delete contact
- `GET /api/contacts/customer/{customerId}` - Get contacts by customer

### Lead Management
- `GET /api/leads` - Get all leads with pagination and search
- `GET /api/leads/{id}` - Get lead by ID
- `POST /api/leads` - Create new lead
- `PUT /api/leads/{id}` - Update lead
- `DELETE /api/leads/{id}` - Delete lead
- `PUT /api/leads/{id}/convert` - Convert lead to customer

### Opportunity Management
- `GET /api/opportunities` - Get all opportunities with pagination and search
- `GET /api/opportunities/{id}` - Get opportunity by ID
- `POST /api/opportunities` - Create new opportunity
- `PUT /api/opportunities/{id}` - Update opportunity
- `DELETE /api/opportunities/{id}` - Delete opportunity
- `GET /api/opportunities/pipeline-value` - Get weighted pipeline value

### Activity Management
- `GET /api/activities` - Get all activities with pagination and search
- `GET /api/activities/{id}` - Get activity by ID
- `POST /api/activities` - Create new activity
- `PUT /api/activities/{id}` - Update activity
- `DELETE /api/activities/{id}` - Delete activity
- `PUT /api/activities/{id}/complete` - Mark activity as completed

## 🎯 Usage Guide

### 1. Dashboard
- Access the main dashboard at `http://localhost:3000/dashboard`
- View key performance indicators (KPIs)
- Analyze sales trends and lead sources
- Monitor opportunity pipeline

### 2. Customer Management
- Navigate to "Customers" in the sidebar
- Add new customers with company information
- Search and filter customers by various criteria
- View detailed customer profiles with contacts and opportunities

### 3. Contact Management
- Access "Contacts" to manage individual contacts
- Link contacts to customers
- Track communication history
- Set primary contacts for customers

### 4. Lead Management
- Use "Leads" section for prospect management
- Score leads based on qualification criteria
- Track lead sources and conversion rates
- Convert qualified leads to customers

### 5. Opportunity Management
- Manage sales opportunities in the "Opportunities" section
- Track deal progression through sales stages
- Forecast revenue with probability-weighted pipeline
- Monitor deal closure dates and outcomes

### 6. Activity Management
- Schedule and track activities in the "Activities" section
- Log calls, meetings, emails, and tasks
- Set reminders and follow-up activities
- Track activity completion and outcomes

## 🔧 Configuration

### Backend Configuration
The backend configuration is managed through `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:h2:mem:crmdb
spring.datasource.username=sa
spring.datasource.password=password

# Server Configuration
server.port=8080

# CORS Configuration
spring.web.cors.allowed-origins=http://localhost:3000
```

### Frontend Configuration
The frontend can be configured through environment variables:

```bash
# .env file in frontend directory
REACT_APP_API_URL=http://localhost:8080/api
```

## 🧪 Testing

### Backend Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CustomerControllerTest
```

### Frontend Testing
```bash
cd frontend
npm test
```

## 🚀 Production Deployment

### Backend Deployment
1. Build the application:
   ```bash
   mvn clean package
   ```

2. Run the JAR file:
   ```bash
   java -jar target/springbootapp-1.0-SNAPSHOT.jar
   ```

### Frontend Deployment
1. Build the production bundle:
   ```bash
   cd frontend
   npm run build
   ```

2. Serve the built files using a web server like Nginx or Apache

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Backend: Change port in `application.properties`
   - Frontend: Set `PORT=3001` environment variable

2. **Database Connection Issues**
   - Ensure H2 database configuration is correct
   - Check application.properties settings

3. **CORS Issues**
   - Verify CORS configuration in backend
   - Ensure frontend URL is whitelisted

4. **Node.js Version Issues**
   - Use Node.js 16 or higher
   - Consider using nvm for version management

### Getting Help

- Check the [Issues](../../issues) page for known problems
- Create a new issue if you encounter a bug
- Refer to the API documentation for endpoint details

## 🎉 Acknowledgments

- Spring Boot team for the excellent framework
- React team for the powerful UI library
- Material-UI team for the beautiful components
- All contributors who helped improve this project