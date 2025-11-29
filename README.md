# Bus Reservation System

A comprehensive Java-based web application for bus ticket reservation and management built with Java Servlets, JSP, and MySQL.

## Features

### User Module
- User registration with encrypted password storage
- Secure login with session management
- Bus search by route and date
- Ticket booking with seat availability check
- Payment processing (Telebirr, CBE, NIB)
- Booking history view
- Support request submission

### Admin Module
- Admin user management
- Bus fleet management (add/update buses)
- Route management (add/update routes)
- Tariff management
- View all bookings
- Search bookings by bus name

### Support Module
- View customer support requests
- Respond to customer queries
- Track request status

## Technology Stack

- **Backend**: Java Servlets, JSP
- **Database**: MySQL 8.0+
- **Server**: Apache Tomcat 9.0+
- **IDE**: Eclipse IDE
- **Security**: AES-256 encryption for passwords
- **Build Tool**: Maven (optional)

## Project Structure

```
ticket/
├── src/main/
│   ├── java/
│   │   ├── config/          # Database configuration
│   │   ├── util/            # Utility classes (encryption)
│   │   ├── servlet/         # Improved servlet implementations
│   │   ├── filter/          # Authentication filters
│   │   └── *.java           # Legacy servlet files
│   └── webapp/
│       ├── style/           # CSS files
│       ├── Uploaded_Files/  # Bus images
│       ├── WEB-INF/
│       │   ├── web.xml      # Deployment descriptor
│       │   └── lib/         # JAR dependencies
│       └── META-INF/
├── database/
│   └── schema.sql           # Database schema
└── README.md
```

## Setup Instructions

### Prerequisites
- JDK 8 or higher
- Apache Tomcat 9.0+
- MySQL 8.0+
- Eclipse IDE (or any Java IDE)

### Database Setup

1. Install MySQL and start the service
2. Create the database:
```sql
CREATE DATABASE ticket_db;
```

3. Run the schema file:
```bash
mysql -u root -p ticket_db < database/schema.sql
```

4. Update database credentials in `config/DatabaseConfig.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/ticket_db";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### Application Setup

1. **Import Project into Eclipse**:
   - File → Import → Existing Projects into Workspace
   - Select the `ticket` folder
   - Click Finish

2. **Configure Tomcat Server**:
   - Window → Preferences → Server → Runtime Environments
   - Add Apache Tomcat 9.0
   - Set Tomcat installation directory

3. **Add MySQL Connector**:
   - Download MySQL Connector/J (JDBC driver)
   - Copy `mysql-connector-java-x.x.x.jar` to `src/main/webapp/WEB-INF/lib/`

4. **Deploy and Run**:
   - Right-click project → Run As → Run on Server
   - Select Tomcat server
   - Access application at: `http://localhost:8080/ticket/home1`

## Security Considerations

⚠️ **Important Security Notes**:

1. **Password Encryption**: Currently uses AES-256 encryption. For production, consider using bcrypt or Argon2 for password hashing.

2. **Database Credentials**: Move database credentials to environment variables or external configuration files.

3. **Encryption Keys**: Store encryption keys in secure key management systems (not in source code).

4. **SQL Injection**: New servlet implementations use PreparedStatements. Migrate all legacy servlets.

5. **HTTPS**: Enable HTTPS in production and set secure cookies.

6. **Input Validation**: Add comprehensive input validation on both client and server side.

## Default Users

After running the schema, create an admin user:
```sql
-- Password will be encrypted by the application
INSERT INTO user (name, password, phone, Status, User_Type) 
VALUES ('admin', 'ENCRYPTED_PASSWORD', '1234567890', 'Active', 'Admin');
```

## API Endpoints

### Public Endpoints
- `/home1` - Home page and login
- `/sign_up` - User registration
- `/SignUpServlet` - Improved registration (recommended)

### Protected Endpoints (Require Login)
- `/Dashboard` - User dashboard
- `/Admin_page` - Admin dashboard
- `/support_dashboard` - Support dashboard
- `/payment` - Payment processing
- `/BOOKING_HISTORY` - View booking history
- `/SEARCH` - Search bookings

## Database Schema

### Main Tables
- `user` - User accounts and authentication
- `bus` - Bus fleet information
- `route` - Available routes
- `tariff` - Pricing information
- `booking` - Ticket bookings
- `payments` - Payment transactions
- `support_requests` - Customer support tickets
- `support_responses` - Support responses

## Improvements Made

✅ Centralized database configuration  
✅ Encryption utility class  
✅ PreparedStatements to prevent SQL injection  
✅ Authentication filter for protected pages  
✅ Proper session management  
✅ Database schema documentation  
✅ Web.xml configuration  
✅ Error handling improvements  

## TODO / Future Enhancements

- [ ] Migrate all legacy servlets to new secure implementations
- [ ] Add JSP pages instead of HTML in servlets
- [ ] Implement connection pooling (HikariCP)
- [ ] Add logging framework (Log4j2)
- [ ] Implement RESTful API
- [ ] Add unit tests
- [ ] Add email notifications
- [ ] Implement password reset functionality
- [ ] Add admin analytics dashboard
- [ ] Mobile responsive design
- [ ] Payment gateway integration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is open source and available for educational purposes.

## Support

For issues and questions, please create an issue in the GitHub repository.

## Authors

- Original Implementation: [Your Name]
- Security Improvements: Kiro AI Assistant

---

**Note**: This is an educational project. For production use, implement additional security measures, proper error handling, logging, and testing.
