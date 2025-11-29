# Security Guide

## Current Security Issues

### Critical Issues (Must Fix Before Production)

1. **Hardcoded Database Credentials**
   - Location: `config/DatabaseConfig.java`
   - Risk: Credentials exposed in source code
   - Fix: Use environment variables or external configuration

2. **Hardcoded Encryption Keys**
   - Location: `util/EncryptionUtil.java`
   - Risk: Keys exposed in source code
   - Fix: Use secure key management system

3. **SQL Injection Vulnerabilities**
   - Location: Legacy servlet files (payment.java, SEARCH.java, etc.)
   - Risk: Database compromise
   - Fix: Migrate to PreparedStatements (see new servlet implementations)

4. **No Input Validation**
   - Risk: XSS, injection attacks
   - Fix: Implement server-side validation

5. **Passwords Stored with Reversible Encryption**
   - Risk: Passwords can be decrypted
   - Fix: Use bcrypt or Argon2 for one-way hashing

### Medium Priority Issues

6. **No CSRF Protection**
   - Risk: Cross-site request forgery
   - Fix: Implement CSRF tokens

7. **Session Management**
   - Risk: Session fixation, hijacking
   - Fix: Regenerate session ID after login

8. **No Rate Limiting**
   - Risk: Brute force attacks
   - Fix: Implement rate limiting

9. **Error Messages Expose System Info**
   - Risk: Information disclosure
   - Fix: Generic error messages for users

10. **No HTTPS Enforcement**
    - Risk: Man-in-the-middle attacks
    - Fix: Force HTTPS in production

## Security Improvements Implemented

✅ Centralized database configuration  
✅ Encryption utility class  
✅ PreparedStatements in new servlets  
✅ Authentication filter  
✅ Session timeout configuration  
✅ Secure cookie configuration (web.xml)  

## Recommended Security Enhancements

### 1. Use Environment Variables

Create `setenv.bat` in Tomcat bin folder:
```batch
set DB_URL=jdbc:mysql://localhost:3306/ticket_db
set DB_USERNAME=ticket_app
set DB_PASSWORD=your_secure_password
set ENCRYPTION_KEY=your_encryption_key
set ENCRYPTION_SALT=your_salt
```

Update `DatabaseConfig.java`:
```java
private static final String URL = System.getenv("DB_URL");
private static final String USERNAME = System.getenv("DB_USERNAME");
private static final String PASSWORD = System.getenv("DB_PASSWORD");
```

### 2. Implement Password Hashing with BCrypt

Add BCrypt dependency and update password handling:

```java
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }
    
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
```

### 3. Add Input Validation

```java
public class ValidationUtil {
    public static boolean isValidUsername(String username) {
        return username != null && 
               username.matches("^[a-zA-Z0-9_]{3,20}$");
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && 
               phone.matches("^[0-9]{10}$");
    }
    
    public static String sanitizeInput(String input) {
        if (input == null) return "";
        return input.replaceAll("[<>\"']", "");
    }
}
```

### 4. Implement CSRF Protection

```java
// Generate CSRF token
public static String generateCSRFToken() {
    return UUID.randomUUID().toString();
}

// In login form
HttpSession session = request.getSession();
String csrfToken = generateCSRFToken();
session.setAttribute("csrfToken", csrfToken);

// In form
out.print("<input type='hidden' name='csrfToken' value='" + csrfToken + "' />");

// Validate on submission
String sessionToken = (String) session.getAttribute("csrfToken");
String requestToken = request.getParameter("csrfToken");
if (!sessionToken.equals(requestToken)) {
    throw new SecurityException("Invalid CSRF token");
}
```

### 5. Implement Rate Limiting

```java
@WebFilter("/LoginServlet")
public class RateLimitFilter implements Filter {
    private Map<String, Integer> attemptMap = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = httpRequest.getRemoteAddr();
        
        Integer attempts = attemptMap.getOrDefault(ip, 0);
        if (attempts >= MAX_ATTEMPTS) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(429, "Too many requests");
            return;
        }
        
        attemptMap.put(ip, attempts + 1);
        chain.doFilter(request, response);
    }
}
```

### 6. Secure Session Management

```java
// After successful login
HttpSession oldSession = request.getSession(false);
if (oldSession != null) {
    oldSession.invalidate();
}

HttpSession newSession = request.getSession(true);
newSession.setAttribute("uname", username);
newSession.setMaxInactiveInterval(30 * 60);
```

### 7. Add Security Headers

```java
@WebFilter("/*")
public class SecurityHeaderFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Prevent clickjacking
        httpResponse.setHeader("X-Frame-Options", "DENY");
        
        // Prevent MIME sniffing
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        
        // Enable XSS protection
        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
        
        // Content Security Policy
        httpResponse.setHeader("Content-Security-Policy", 
            "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'");
        
        // Strict Transport Security (HTTPS only)
        httpResponse.setHeader("Strict-Transport-Security", 
            "max-age=31536000; includeSubDomains");
        
        chain.doFilter(request, response);
    }
}
```

### 8. Implement Logging and Monitoring

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String ip = request.getRemoteAddr();
        
        try {
            // Login logic
            logger.info("Successful login: user={}, ip={}", username, ip);
        } catch (Exception e) {
            logger.error("Login failed: user={}, ip={}, error={}", 
                username, ip, e.getMessage());
        }
    }
}
```

### 9. Database Security

```sql
-- Create dedicated user with limited privileges
CREATE USER 'ticket_app'@'localhost' IDENTIFIED BY 'strong_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON ticket_db.* TO 'ticket_app'@'localhost';
FLUSH PRIVILEGES;

-- Enable SSL for database connections
-- In my.cnf or my.ini
[mysqld]
require_secure_transport=ON

-- Regular backups
-- Encrypt sensitive data at rest
-- Use parameterized queries only
```

### 10. File Upload Security

If implementing file uploads:
```java
public class FileUploadUtil {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".pdf"};
    
    public static boolean isValidFile(Part filePart) {
        String fileName = filePart.getSubmittedFileName();
        long fileSize = filePart.getSize();
        
        // Check size
        if (fileSize > MAX_FILE_SIZE) return false;
        
        // Check extension
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(ALLOWED_EXTENSIONS).contains(extension);
    }
    
    public static String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "");
    }
}
```

## Security Checklist for Production

### Before Deployment
- [ ] Remove all hardcoded credentials
- [ ] Implement password hashing (bcrypt/Argon2)
- [ ] Fix all SQL injection vulnerabilities
- [ ] Add input validation on all forms
- [ ] Implement CSRF protection
- [ ] Add rate limiting on login
- [ ] Configure secure session management
- [ ] Add security headers
- [ ] Enable HTTPS
- [ ] Configure secure cookies
- [ ] Implement logging and monitoring
- [ ] Remove debug/error details from user-facing pages
- [ ] Update all dependencies
- [ ] Perform security audit
- [ ] Penetration testing

### Database Security
- [ ] Use dedicated database user (not root)
- [ ] Strong database password
- [ ] Limit database user privileges
- [ ] Enable SSL for database connections
- [ ] Regular backups
- [ ] Encrypt sensitive data

### Server Security
- [ ] Keep Tomcat updated
- [ ] Remove default Tomcat applications
- [ ] Disable directory listing
- [ ] Configure firewall
- [ ] Regular security updates
- [ ] Monitor logs for suspicious activity

### Application Security
- [ ] Validate all user inputs
- [ ] Sanitize outputs (prevent XSS)
- [ ] Use PreparedStatements (prevent SQL injection)
- [ ] Implement proper authentication
- [ ] Implement proper authorization
- [ ] Secure file uploads
- [ ] Implement audit logging

## Vulnerability Reporting

If you discover a security vulnerability:
1. Do NOT create a public GitHub issue
2. Email security concerns to: [your-email]
3. Include detailed description and steps to reproduce
4. Allow reasonable time for fix before public disclosure

## Security Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [OWASP Java Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Java_Security_Cheat_Sheet.html)
- [CWE Top 25](https://cwe.mitre.org/top25/)
- [Tomcat Security Considerations](https://tomcat.apache.org/tomcat-9.0-doc/security-howto.html)

## Regular Security Maintenance

- Weekly: Review access logs
- Monthly: Update dependencies
- Quarterly: Security audit
- Annually: Penetration testing

---

**Remember**: Security is an ongoing process, not a one-time task.
