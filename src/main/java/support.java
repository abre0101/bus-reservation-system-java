

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class support
 */
@WebServlet("/support")
public class support extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public support() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		try {
		 
		out.print("<html>");
		out.print("<head>");
		out.print("<title> View user information</title>"); 
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile7.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
	rd.include(request,response);

		String driverName="com.mysql.cj.jdbc.Driver";
		String dbUrl="jdbc:mysql://localhost:3306/ticket_db";
		String dbusername="root";
		String dbpassword="root";
		 
		 Connection conn=null;
		 Statement stmt=null;
		 ResultSet rs=null;
		 try{
		 Class.forName(driverName);
		 }catch(ClassNotFoundException e){
		System.out.println(e.getMessage());
		}
		 
		conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
		 stmt=conn.createStatement();
		
		
		String sql="select * from support_request";
		rs=stmt.executeQuery(sql);
		if(!rs.next())
		out.print("<font color=red>There is no user information registered with user name </font>");
		
		else{ 
			String username=null,subject=null,email=null,message;
			int reqid=0;
			Timestamp request_date;
	rs=stmt.executeQuery(sql);
	rs.next();
	reqid=rs.getInt("request_id");
	username=rs.getString("username");
	email=rs.getString("email");
	subject=rs.getString("subject");
	message=rs.getString("message");
     request_date=rs.getTimestamp("request_date");
	
	out.print("<form action= support method=post >");
	out.print(" requested id: <input type=text name=busname value='"+reqid+"' readonly /><br>");
	out.print(" username: <input type=text name=source value='"+username+"' readonly /><br>");
	out.print(" email: <input type=text name=source value='"+email+"' readonly /><br>");
	out.print(" subject: <input type=text name=distination value='"+subject+"' readonly /><br>");
	out.print(" message: <textarea id=message  name=message placeholder='"+message+"' readonly /></textarea><br> ");
	out.print(" date: <input type=TIMESTAMP name=request_date value='"+request_date+"' readonly /><br>");
	out.print("<input type=submit value=Close />");
	out.print("</form>");

			}
		 String submit = request.getParameter("submit");
			String reqid= request.getParameter("reqid");
			 String msg = request.getParameter("msg");
		if(submit==null)
		{	out.print("<form action=response method=post >");

		 out.print("request id: <input type=text name=reqid required />");
			out.print("resp: <input type=text name=msg required />");
			out.print("<input type=submit name=submit value=Register /><input type=reset value=Clear>");
		out.print("</form>");

		} else { 

	try {
	conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);		
			try (PreparedStatement statement = conn.prepareStatement("UPDATE support_request SET response = ?, response_date = CURRENT_TIMESTAMP WHERE request_id = "+reqid+"" )) 
			{ 		
					
			               statement.setString(1,msg);	
			int rowsUpdated = statement.executeUpdate();
					out.print(rowsUpdated+"row updated");
			}
			catch (SQLException e)
			{ e.printStackTrace(); out.println("Error processing request: " + e.getMessage());
			}RequestDispatcher rr = request.getRequestDispatcher("footer");
			  rr.include(request, response);
			}catch(Exception e) { 
				 out.print(e.getMessage());
				 }}

} catch(Exception e) { 
		 out.print(e.getMessage());
		 }RequestDispatcher rr = request.getRequestDispatcher("footer");
		  rr.include(request, response);
		}

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
