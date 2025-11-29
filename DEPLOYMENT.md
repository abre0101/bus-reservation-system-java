# Deployment Guide

## Development Environment Setup

### 1. Prerequisites Installation

#### Install Java JDK
```bash
# Windows: Download from Oracle website
# Or use chocolatey
choco install openjdk11

# Verify installation
java -version
```

#### Install MySQL
```bash
# Windows: Download MySQL Installer from mysql.com
# Or use chocolatey
choco install mysql

# Start MySQL service
net start MySQL80
```

#### Install Apache Tomcat
```bash
# Download Tomcat 9.0 from tomcat.apache.org
# Extract to C:\Program Files\Apache\Tomcat9
```

### 2. Database Setup

```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE ticket_db;

# Import schema
mysql -u root -p ticket_db < database/schema.sql

# Verify tables
USE ticket_db;
SHOW TABLES;
```

### 3. Eclipse IDE Setup

1. **Import Project**:
   - File → Import → Existing Projects into Workspace
   - Browse to `ticket` folder
   - Click Finish

2. **Configure Build Path**:
   - Right-click project → Build Path → Configure Build Path
   - Add External JARs:
     - MySQL Connector/J (download from mysql.com)
     - Servlet API (from Tomcat lib folder)

3. **Configure Server**:
   - Window → Preferences → Server → Runtime Environments
   - Add → Apache Tomcat v9.0
   - Browse to Tomcat installation directory
   - Apply and Close

4. **Add Project to Server**:
   - Right-click on Servers tab → New → Server
   - Select Tomcat v9.0
   - Add `ticket` project to configured
   - Finish

### 4. Configuration Files

Update `config/DatabaseConfig.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/ticket_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

### 5. Run Application

1. Right-click project → Run As → Run on Server
2. Select Tomcat v9.0 Server
3. Click Finish
4. Access: `http://localhost:8080/ticket/home1`

## Production Deployment

### 1. Security Hardening

#### Update Database Credentials
- Create dedicated database user (not root)
- Use strong passwords
- Grant only necessary privileges

```sql
CREATE USER 'ticket_app'@'localhost' IDENTIFIED BY 'strong_password_here';
GRANT SELECT, INSERT, UPDATE, DELETE ON ticket_db.* TO 'ticket_app'@'localhost';
FLUSH PRIVILEGES;
```

#### Move Sensitive Data to Environment Variables
```java
// In DatabaseConfig.java
private static final String URL = System.getenv("DB_URL");
private static final String USERNAME = System.getenv("DB_USERNAME");
private static final String PASSWORD = System.getenv("DB_PASSWORD");
```

#### Enable HTTPS
Update `web.xml`:
```xml
<security-constraint>
    <web-resource-collection>
        <web-resource-name>Entire Application</web-resource-name>
        <url-pattern>/*</url-pattern>
    </web-resource-collection>
    <user-data-constraint>
        <transport-guarantee>CONFIDENTIAL</transport-guarantee>
    </user-data-constraint>
</security-constraint>
```

### 2. Build WAR File

#### Using Eclipse
1. Right-click project → Export
2. Select WAR file
3. Choose destination
4. Click Finish

#### Using Command Line
```bash
# If using Maven
mvn clean package

# WAR file will be in target/ticket.war
```

### 3. Deploy to Tomcat

#### Option 1: Manual Deployment
```bash
# Copy WAR file to Tomcat webapps
copy ticket.war "C:\Program Files\Apache\Tomcat9\webapps\"

# Restart Tomcat
net stop Tomcat9
net start Tomcat9
```

#### Option 2: Tomcat Manager
1. Access Tomcat Manager: `http://localhost:8080/manager`
2. Login with admin credentials
3. Upload WAR file
4. Deploy

### 4. Configure Tomcat for Production

#### Update server.xml
```xml
<!-- Enable compression -->
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443"
           compression="on"
           compressionMinSize="2048"
           noCompressionUserAgents="gozilla, traviata"
           compressableMimeType="text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json"/>

<!-- Configure SSL (if using HTTPS) -->
<Connector port="8443" protocol="org.apache.coyote.http11.Http11NioProtocol"
           maxThreads="150" SSLEnabled="true">
    <SSLHostConfig>
        <Certificate certificateKeystoreFile="conf/keystore.jks"
                     type="RSA" />
    </SSLHostConfig>
</Connector>
```

#### Update context.xml
```xml
<!-- Configure database connection pool -->
<Resource name="jdbc/TicketDB"
          auth="Container"
          type="javax.sql.DataSource"
          maxTotal="100"
          maxIdle="30"
          maxWaitMillis="10000"
          username="ticket_app"
          password="strong_password"
          driverClassName="com.mysql.cj.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/ticket_db"/>
```

### 5. Monitoring and Logging

#### Enable Access Logs
In `server.xml`:
```xml
<Valve className="org.apache.catalina.valves.AccessLogValve"
       directory="logs"
       prefix="ticket_access_log"
       suffix=".txt"
       pattern="%h %l %u %t &quot;%r&quot; %s %b" />
```

#### Application Logging
Add Log4j2 configuration in `src/main/resources/log4j2.xml`

### 6. Backup Strategy

#### Database Backup
```bash
# Daily backup script
mysqldump -u ticket_app -p ticket_db > backup_$(date +%Y%m%d).sql

# Automated backup (Windows Task Scheduler)
# Create batch file: backup.bat
@echo off
set BACKUP_DIR=C:\backups\ticket_db
set TIMESTAMP=%date:~-4,4%%date:~-10,2%%date:~-7,2%
mysqldump -u ticket_app -p[password] ticket_db > %BACKUP_DIR%\backup_%TIMESTAMP%.sql
```

#### Application Backup
- Backup WAR file
- Backup configuration files
- Backup uploaded files directory

### 7. Performance Optimization

1. **Enable Connection Pooling**: Use HikariCP or Tomcat JDBC Pool
2. **Enable Caching**: Implement Redis or Memcached
3. **Optimize Database**: Add indexes, optimize queries
4. **Enable Gzip Compression**: Configure in Tomcat
5. **Use CDN**: For static resources

### 8. Health Checks

Create a health check endpoint:
```java
@WebServlet("/health")
public class HealthCheckServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            Connection conn = DatabaseConfig.getConnection();
            conn.close();
            out.print("{\"status\":\"UP\",\"database\":\"connected\"}");
        } catch (Exception e) {
            response.setStatus(503);
            out.print("{\"status\":\"DOWN\",\"database\":\"disconnected\"}");
        }
    }
}
```

## Troubleshooting

### Common Issues

#### 1. ClassNotFoundException: com.mysql.cj.jdbc.Driver
**Solution**: Add MySQL Connector JAR to `WEB-INF/lib/`

#### 2. Connection refused to database
**Solution**: 
- Check MySQL service is running
- Verify database credentials
- Check firewall settings

#### 3. 404 Error on deployment
**Solution**:
- Verify context path in server.xml
- Check web.xml configuration
- Ensure WAR deployed correctly

#### 4. Session timeout issues
**Solution**: Adjust session timeout in web.xml

#### 5. Memory issues
**Solution**: Increase Tomcat heap size in catalina.bat:
```bash
set JAVA_OPTS=-Xms512m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m
```

## Maintenance

### Regular Tasks
- [ ] Weekly database backups
- [ ] Monthly security updates
- [ ] Quarterly performance review
- [ ] Log rotation and cleanup
- [ ] SSL certificate renewal (if applicable)

### Monitoring Checklist
- [ ] Server uptime
- [ ] Database connections
- [ ] Response times
- [ ] Error rates
- [ ] Disk space
- [ ] Memory usage

## Support

For deployment issues, check:
1. Tomcat logs: `logs/catalina.out`
2. Application logs
3. MySQL error log
4. System event logs

---

**Last Updated**: November 2025
