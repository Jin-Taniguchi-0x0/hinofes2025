# React + Kotlin Demo

This is a simple demo application that showcases a full-stack web application with:

- React + TypeScript frontend
- Kotlin + Spring Boot backend
- User management system with CRUD operations
- RESTful API with comprehensive test coverage

## Project Structure

```
react-kotlin-demo/
├── frontend/                # React frontend application
│   ├── src/                 # React source code
│   │   ├── App.tsx          # Main React component
│   │   ├── App.css          # Component styling
│   │   ├── main.tsx         # Entry point
│   │   └── index.css        # Global styling
│   ├── public/              # Static assets
│   ├── package.json         # Frontend dependencies
│   ├── tsconfig.json        # TypeScript configuration
│   ├── vite.config.ts       # Vite configuration
│   └── ...
├── backend/                 # Kotlin Spring Boot backend
│   ├── src/
│   │   ├── main/kotlin/com/example/demo/
│   │   │   ├── DemoApplication.kt       # Application entry point
│   │   │   ├── model/                   # Data models
│   │   │   │   └── User.kt              # User entity
│   │   │   ├── repository/              # Data access layer
│   │   │   │   └── UserRepository.kt    # User repository
│   │   │   ├── service/                 # Business logic layer
│   │   │   │   └── UserService.kt       # User service
│   │   │   ├── controller/              # API controllers
│   │   │   │   └── UserController.kt    # User REST controller
│   │   │   └── config/                  # Configuration classes
│   │   │       └── WebConfig.kt         # CORS configuration
│   │   ├── main/resources/
│   │   │   ├── application.properties   # Application configuration
│   │   │   └── data.sql                 # Sample data initialization
│   │   └── test/                        # Unit tests
│   │       └── kotlin/com/example/demo/
│   │           ├── model/               # Model tests
│   │           ├── service/             # Service tests
│   │           └── controller/          # Controller tests
│   ├── build.gradle.kts                 # Backend dependencies
│   └── settings.gradle.kts              # Gradle settings
├── .gitignore                           # Git ignore file
├── README.md                            # Project documentation
├── setup.sh                             # Setup script
└── start-demo.sh                        # Script to run both frontend and backend
```

## Features

- **User Management System**

  - Create, read, update, and delete users
  - Form validation
  - Error handling
  - Responsive UI

- **Backend API**

  - RESTful API with Spring Boot
  - Proper HTTP status codes
  - Exception handling
  - CORS configuration for frontend communication

- **Modern Frontend**

  - React with functional components and hooks
  - TypeScript for type safety
  - CSS styling with responsive design
  - API integration with error handling

- **Database**

  - H2 in-memory database for development
  - JPA entity mapping
  - Sample data initialization

- **Testing**

  - Comprehensive unit tests for backend
  - Model, service, and controller layer tests
  - Mocking with Mockito

- **Developer Experience**
  - Convenience scripts for setup and running
  - Detailed documentation
  - Clean project structure

## Quick Start

For convenience, two scripts are provided to help you get started quickly:

### Setup Script

Run the setup script to install all dependencies:

```
./setup.sh
```

This script will:

- Install npm dependencies for the frontend

### Start Script

Run the start script to launch both frontend and backend servers:

```
./start-demo.sh
```

This script will:

- Start the Kotlin Spring Boot backend on port 8080
- Start the React frontend on port 3000
- Provide URLs for accessing the application

## Manual Setup

### Backend (Kotlin + Spring Boot)

1. Navigate to the backend directory:

   ```
   cd backend
   ```

2. Run the Spring Boot application:

   ```
   ./gradlew bootRun
   ```

   The backend will start on http://localhost:8080

3. Access the H2 database console at http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: `password`

### Frontend (React + TypeScript)

1. Navigate to the frontend directory:

   ```
   cd frontend
   ```

2. Install dependencies:

   ```
   npm install
   ```

3. Start the development server:

   ```
   npm run dev
   ```

   The frontend will start on http://localhost:3000

## API Endpoints

The backend provides the following RESTful API endpoints:

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get a specific user
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update a user
- `DELETE /api/users/{id}` - Delete a user

All endpoints return JSON responses and follow standard HTTP status codes:

- 200 OK - Successful operation
- 201 Created - Resource successfully created
- 204 No Content - Successful deletion
- 404 Not Found - Resource not found

## Testing

The backend includes comprehensive unit tests for all layers:

- Model tests - Verify data class behavior
- Service tests - Test business logic with mocked repositories
- Controller tests - Test REST endpoints with MockMvc

Run the tests with:

```
cd backend
./gradlew test
```

## Technologies Used

### Frontend

- React 18 - UI library
- TypeScript - Type-safe JavaScript
- Vite - Build tool and development server
- Axios - HTTP client for API calls
- CSS - Styling

### Backend

- Kotlin 1.8 - Modern JVM language
- Spring Boot 3.1 - Java framework
- Spring Data JPA - Data access layer
- Spring MVC - Web layer
- H2 Database - In-memory database
- JUnit 5 - Testing framework
- Mockito - Mocking library for tests
