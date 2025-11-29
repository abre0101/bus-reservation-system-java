package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import config.DatabaseConfig;
import util.EncryptionUtil;

/**
 * Improved Login Servlet with proper security
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Prevent caching
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Expires", "0");
        response.setDateHeader("Expires", -1);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String status = "Active";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConfig.getConnection();
            
            // Use PreparedStatement to prevent SQL injection
            String sql = "SELECT * FROM user WHERE name = ? AND Status = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, status);
            
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                response.sendRedirect("home1?wrongusername=true");
                return;
            }
            
            String userType = rs.getString("User_Type");
            String storedPassword = rs.getString("password");
            
            // Decrypt stored password and compare
            String decryptedPassword = EncryptionUtil.decrypt(storedPassword);
            
            if (!password.equals(decryptedPassword)) {
                response.sendRedirect("home1?wrongpassword=true");
                return;
            }
            
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("utype", userType);
            session.setAttribute("uname", username);
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            
            // Redirect based on user type
            switch (userType) {
                case "user":
                    response.sendRedirect("Dashboard");
                    break;
                case "Admin":
                    response.sendRedirect("Admin_page");
                    break;
                case "support":
                    response.sendRedirect("support_dashboard");
                    break;
                default:
                    response.sendRedirect("home1?error=invalidusertype");
            }
            
        } catch (SQLException e) {
            PrintWriter out = response.getWriter();
            out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println("Error: " + e.getMessage());
        } finally {
            DatabaseConfig.closeResources(rs, ps, conn);
        }
    }
}
