

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**

/**
 * Servlet implementation class footer
 */
@WebServlet("/footer")
public class footer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public footer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		out.print("<html>");
		 out.print("<head>");
		 out.print("<title>Header Page</title>");
		out.println("<link rel='stylesheet' type='text/css' href='style/footer.css'>");
		 out.print("</head>");
		 out.print("<body>");
		 out.print("<div class='content-wrapper'>");
	        // Your existing form and content here
	        out.print("</div>");

	        // Add the footer
	        out.print("<footer>");
	        out.print("    <div class='footer-content'>");
	        out.print("        <div class='footer-section'>");
	        out.print("            <h3>About Us</h3>");
	        out.print("            <p>Your trusted partner for online bus ticket booking. We provide convenient and reliable bus travel services across Ethiopia.</p>");
	        out.print("        </div>");

	        out.print("        <div class='footer-section'>");
	        out.print("            <h3>Quick Links</h3>");
	        out.print("            <a href='das'>Book Ticket</a>");
	        out.print("            <a href='supportandrequest'>Support</a>");
	        out.print("            <a href='see_response'>Check Status</a>");
	        out.print("            <a href='BOOKING_HISTORY'>Booking History</a>");
	        out.print("        </div>");

	        out.print("        <div class='footer-section'>");
	        out.print("            <h3>Contact Info</h3>");
	        out.print("            <p>Email: info@ethiobus.com</p>");
	        out.print("            <p>Phone: +251 11 234 5678</p>");
	        out.print("            <p>Address: Addis Ababa, Ethiopia</p>");
	        out.print("        </div>");
	        out.print("    </div>");

	        out.print("    <div class='footer-bottom'>");
	        out.print("        <p>&copy; " + java.time.Year.now().getValue() + " Ethio Bus. All rights reserved.</p>");
	        out.print("    </div>");
	        out.print("</footer>");
		 out.print("</body>");
		 out.print("</html>");}
		
	        
	     // Wrap your main content
	        
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
