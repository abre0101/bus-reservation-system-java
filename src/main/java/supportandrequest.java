

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class supportandrequest
 */
@WebServlet("/supportandrequest")
public class supportandrequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public supportandrequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Pragma","no-cache"); 
		response.setHeader("Cache-Control","no-store"); 
		response.setHeader("Expires","0"); 
		response.setDateHeader("Expires",-1); 
		PrintWriter out = response.getWriter();
	

		out.print("<html>");
		out.print("<head>");
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile7.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);
		 String submit = request.getParameter("submit");
		 
		if(submit==null)
		{
		 out.print("<center><table width=800>");
		out.print("<tr><th colspan=3><h1 style=color:#cc6600>support and request page </h1></th></tr>");
		out.print("<tr><td colspan=3 align=left><h2>Help and Support</h2></td></tr>");
		out.print("<tr><td><form action=supportandrequest method=post>");
		out.print("<tr><td>USERNAME:<input type=text name=username required/></td><td></td><td></td></tr>"); 
		out.print("<tr><td>EMAIL:<input type=text name=email required/></td><td></td><td></td></tr>"); 
		out.print("<tr><td>SUBJECT:<input type=text name=subject required/></td><td></td><td></td></tr>"); 	
		out.print("<tr><td>MESSAGE:<input type=text name=message rows=4 cols=150 required/></td><td></td><td></td></tr>"); 	
		out.print("<tr><td><input type=submit name=submit value=Add />"+ "<input type=Reset value=Reset /></td><td></td><td></td></tr></form>");
		out.print("</table></center>");
	 
		} else { 
	
		String USERNAME= request.getParameter("username");
		 String EMAIL = request.getParameter("email");
		 String SUBJECT = request.getParameter("subject");
		 String message = request.getParameter("message");
		 String driverName="com.mysql.cj.jdbc.Driver";
	String dbUrl="jdbc:mysql://localhost:3306/ticket_db";
	String dbusername="root";
	String dbpassword="root";
	 
	Connection conn=null;
	PreparedStatement ps=null;
	 try{
	 Class.forName(driverName);
	 }catch(ClassNotFoundException e){
	System.out.println(e.getMessage());
	}

	try {
	conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
	String sql="insert into support_request (username, email,subject,message) VALUES (?,?,?,?)";
	ps = conn.prepareStatement(sql);
	ps.setString(1,USERNAME);
	ps.setString(2,EMAIL);
	ps.setString(3,SUBJECT);
	ps.setString(4,message);
	int i = ps.executeUpdate();
	 if(i>0) {
	out.println("<font color=green>The following request has been successfully delivered to the support group</font>");
	
	 }else
	 out.println("Failed to deliver your request");
	out.println("</body></html>");
	}
	catch (SQLException e){
	 out.println(e.getMessage());}
		}RequestDispatcher rr = request.getRequestDispatcher("footer");
		  rr.include(request, response);

		}// TODO Auto-generated method stub
	

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
