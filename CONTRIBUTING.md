# Contributing to Bus Reservation System

Thank you for considering contributing to this project!

## How to Contribute

### Reporting Bugs

1. Check if the bug has already been reported in Issues
2. If not, create a new issue with:
   - Clear title and description
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots if applicable
   - Environment details (OS, Java version, Tomcat version)

### Suggesting Enhancements

1. Check if the enhancement has been suggested
2. Create a new issue with:
   - Clear description of the enhancement
   - Why it would be useful
   - Possible implementation approach

### Pull Requests

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature-name`)
3. Make your changes
4. Test thoroughly
5. Commit with clear messages
6. Push to your fork
7. Create a Pull Request

## Development Guidelines

### Code Style

- Use meaningful variable and method names
- Follow Java naming conventions:
  - Classes: PascalCase
  - Methods: camelCase
  - Constants: UPPER_SNAKE_CASE
- Add comments for complex logic
- Keep methods small and focused

### Security

- Never commit credentials or API keys
- Use PreparedStatements for database queries
- Validate all user inputs
- Follow OWASP security guidelines

### Testing

- Test all new features
- Ensure existing functionality still works
- Test on different browsers (if UI changes)

### Commit Messages

Format:
```
<type>: <subject>

<body>

<footer>
```

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting)
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Maintenance tasks

Example:
```
feat: Add password reset functionality

- Added forgot password link on login page
- Implemented email verification
- Created password reset servlet

Closes #123
```

## Project Structure

```
ticket/
├── src/main/java/
│   ├── config/       # Configuration classes
│   ├── servlet/      # Servlet implementations
│   ├── filter/       # Filters (auth, security)
│   ├── util/         # Utility classes
│   └── *.java        # Legacy servlets (to be migrated)
├── src/main/webapp/
│   ├── style/        # CSS files
│   ├── WEB-INF/      # Configuration and libraries
│   └── Uploaded_Files/
├── database/         # Database schemas and migrations
└── docs/             # Documentation
```

## Migration Priority

Help migrate legacy servlets to new secure implementations:

High Priority:
1. payment.java → PaymentServlet.java
2. SEARCH.java → SearchServlet.java
3. BOOKING_HISTORY.java → BookingHistoryServlet.java

Medium Priority:
4. add_bus.java → AddBusServlet.java
5. add_route.java → AddRouteServlet.java
6. tarif.java → TariffServlet.java

## Questions?

Feel free to create an issue for any questions about contributing.

Thank you for your contributions!
