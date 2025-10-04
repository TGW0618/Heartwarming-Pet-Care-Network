# Heartwarming Pet Care Network

This is a warm-hearted pet care platform designed to establish an efficient and convenient communication bridge between pet owners and service providers. Through this platform, users can easily manage pet information, book pet care services, view medical records, and more.

## Features

- **User Management**: Supports user management for multiple roles including administrators, veterinarians, and pet sitters.
- **Pet Information Management**: Users can add, edit, and delete pet information.
- **Service Booking**: Provides booking functionality for various pet care services, including boarding and medical services.
- **Order Management**: Users can view order status, pay for orders, cancel orders, etc.
- **Data Statistics**: Offers detailed business data statistics and analysis to help managers better understand business performance.
- **Notification System**: The system sends important notifications to users to ensure they don't miss any critical information.
- **File Upload**: Allows users to upload pet photos and other related files.

## Technology Stack

- **Backend**: Built using the Spring Boot framework, integrated with MyBatis for database operations, Redis for caching, and JWT for authentication.
- **Frontend**: Built using the Vue.js framework with the Vant UI component library to provide a great user experience.
- **Database**: MySQL is used as the primary database management system.
- **Other Tools**: Maven is used for project dependency management, Redis for caching, and Nginx for reverse proxy and load balancing.

## Installation and Deployment

### Backend Deployment

1. **Environment Setup**: Ensure Java 8 or higher, Maven, MySQL, and Redis are installed.
2. **Database Configuration**: Create a database in MySQL and import the SQL file provided in the project.
3. **Configuration File Modification**: Update the database connection information and Redis configuration in the `application.yml` file.
4. **Start the Project**: Launch the project using the Maven command `mvn spring-boot:run`, or package it into a JAR file and run it.

### Frontend Deployment

1. **Environment Setup**: Ensure Node.js and npm are installed.
2. **Install Dependencies**: Run `npm install` in the project root directory to install dependencies.
3. **Start Development Server**: Run `npm run dev` to start the development server, and access the application at `http://localhost:3000`.
4. **Build for Production**: Run `npm run build` to build the production environment code, which will be generated in the `dist` directory.

## Usage Guide

### User Management

- **Administrator**: Can manage all users, service items, orders, etc.
- **Veterinarian**: Can view and manage medical records.
- **Pet Sitter**: Can view and manage boarding records.
- **Regular User**: Can add pet information, book services, view orders, etc.

### Pet Information Management

- **Add Pet**: Users can add basic pet information, including name, species, breed, gender, weight, health status, etc.
- **Edit Pet Information**: Users can update pet information at any time.
- **Delete Pet Information**: Users can delete pet information that is no longer needed.

### Service Booking

- **Select Service**: Users can choose from different service items, including boarding and medical services.
- **Book Service**: Users can submit orders after selecting service time, pet, and other details.
- **Pay for Order**: Users can complete order payments through various payment methods.

### Order Management

- **View Orders**: Users can view the status and payment details of all orders.
- **Cancel Orders**: Users can cancel orders before they start.
- **Order Details**: Users can view detailed order information, including service content, time, price, etc.

### Data Statistics

- **Business Statistics**: Provides daily, weekly, and monthly business statistics.
- **Revenue Statistics**: Displays revenue data across different time periods.
- **User Growth**: Shows user growth trends.

### Notification System

- **System Notifications**: The system sends important notifications to users, such as order status changes and service reminders.
- **Message Center**: Users can view all notifications in the message center and mark them as read or unread.

### File Upload

- **Upload Pet Photos**: Users can upload pet photos for easy identification and management.
- **Upload Other Files**: Users can upload other pet-related files, such as vaccination certificates.

## Contribution Guide

Contributions of code and suggestions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch.
3. Submit code changes.
4. Create a Pull Request.

## License

This project is licensed under the MIT License. For details, please refer to the `LICENSE` file.