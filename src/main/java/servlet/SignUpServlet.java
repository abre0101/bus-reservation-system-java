package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.DatabaseConfig;
import util.EncryptionUtil;

/**
 * Improved Sign Up Servlet with proper security
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.print("<html><head>");
        out.println("<link rel='stylesheet' type='text/css' href='style/NewFile8.css'>");
        out.print("</head><body bgcolor=#66CCFF>");
        
        RequestDispatcher rd = request.getRequestDispatcher("Header");
        rd.include(request, response);
        
        out.print("<center><table width=800>");
        out.print("<tr><th colspan=3><h1 style=color:black>SIGN UP PAGE</h1></th></tr>");
        out.print("<form action='SignUpServlet' method='post'>");
        out.print("<tr><td>User Name: <input type='text' name='username' required /></td></tr><br>");
        out.print("<tr><td>Password: <input type='password' name='password' minlength='6' required /></td></tr><br>");
        out.print("<tr><td>Phone: <input type='tel' name='phone' pattern='[0-9]{10}' required /></td></tr><br>");
        out.print("<tr><td><input type='submit' value='Register' /></td></tr>");
        out.print("<tr><td><input type='reset' value='Clear' /></td></tr>");
        out.print("</table></center></form>");
        
        RequestDispatcher footer = request.getRequestDispatcher("footer");
        footer.include(request, response);
        
        out.print("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String status = "Active";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            // Encrypt password
            String encryptedPassword = EncryptionUtil.encrypt(password);
            
            // Get database connection
            conn = DatabaseConfig.getConnection();
            
            // Use PreparedStatement to prevent SQL injection
            String sql = "INSERT INTO user (name, password, phone, Status) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, encryptedPassword);
            ps.setString(3, phone);
            ps.setString(4, status);
            
            int result = ps.executeUpdate();
            
            out.print("<html><head>");
            out.println("<link rel='stylesheet' type='text/css' href='style/NewFile8.css'>");
            out.print("</head><body bgcolor=#66CCFF>");
            
            RequestDispatcher rd = request.getRequestDispatcher("Header");
            rd.include(request, response);
            
            if (result > 0) {
                out.println("<center><font color='green' size='5'>");
                out.println("Registration Successful!</font><br><br>");
                out.println("<p>Username: " + username + "</p>");
                out.println("<p>Phone: " + phone + "</p>");
                out.println("<a href='home1'>Go to Login</a>");
                out.println("</center>");
            } else {
                out.println("<center><font color='red'>Registration failed. Please try again.</font></center>");
            }
            
            RequestDispatcher footer = request.getRequestDispatcher("footer");
            footer.include(request, response);
            
            out.print("</body></html>");
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                out.println("<center><font color='red'>Username already exists. Please choose another.</font></center>");
            } else {
                out.println("<center><font color='red'>Database error: " + e.getMessage() + "</font></center>");
            }
        } catch (Exception e) {
            out.println("<center><font color='red'>Error: " + e.getMessage() + "</font></center>");
        } finally {
            DatabaseConfig.closeResources(ps, conn);
        }
    }
}
