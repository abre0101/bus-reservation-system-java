

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class response
 */
@WebServlet("/response")
public class response extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public response() {
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
		String request_id=request.getParameter("reqid");
		String search=request.getParameter("search");
		String repp=request.getParameter("resp");

		out.print("<html>");
		out.print("<head>");
		out.print("<title> RESPONSE PAGE</title>"); 
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile9.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);
		
		out.print("<h1> User Information View Form </h1>");
		out.print("<form action= response method=POST>");
		out.print("Search By request id: <input type=text name=reqid required />");
		out.print("<br>");
		out.print("<input type=submit name=search value=Search />");
		out.print("<input type=Reset value=Reset />");
		out.print("</form>");
		out.print("<html>");
		out.print("<head>");
		out.print("<title> View user request</title>"); 
		out.print("</head>");
		out.print("</html>");
		
		
		String driverName="com.mysql.cj.jdbc.Driver";
		String dbUrl="jdbc:mysql://localhost:3306/ticket_db";
		String dbusername="root";
		String dbpassword="root";
		 String msgg = request.getParameter("msg");
		 String reeqid = request.getParameter("reqid");
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
		 
	if(search!=null&&repp==null) { 
			
			 String sql="select * from support_request where request_id='"+request_id+"'";
			 rs=stmt.executeQuery(sql);
			if(!rs.next())
			out.print("<font color=red>There is no user information registered with user name </font>");
			
			else{ 
				String username=null,subject=null,email=null,message=null;
				Timestamp request_date;
				
		rs=stmt.executeQuery(sql);
		rs.next();
		username=rs.getString("username");
		email=rs.getString("email");
		subject=rs.getString("subject");
		message=rs.getString("message");
	    request_date=rs.getTimestamp("request_date");
		
		out.print("<form action= response method=post >");
		out.print(" requested id: <input type=text name=busname value='"+request_id+"' readonly /><br>");
		out.print(" username: <input type=text name=source value='"+username+"' readonly /><br>");
		out.print(" email: <input type=text name=source value='"+email+"' readonly /><br>");
		out.print(" subject: <input type=text name=distination value='"+subject+"' readonly /><br>");
		out.print(" message: <textarea id=message  name=message placeholder='"+message+"' readonly /></textarea><br> ");
		out.print(" date: <input type=TIMESTAMP name=request_date value='"+request_date+"' readonly /><br>");
		out.print("request id: <input type=text name=reqid required />");
	    out.print("resp: <input type=text name=msg required />");
		out.print("<input type=submit name=resp value=response />");
		out.print("</form>");
		conn.close();
	}
		
		}
	else if(search==null&&repp!=null)
	{
		
	
		PreparedStatement ps=null;
	        	String updatesql ="UPDATE support_request SET response = ?, response_date = CURRENT_TIMESTAMP WHERE request_id = ?"; 
	        	ps=conn.prepareStatement(updatesql);
	        	ps.setString(1, msgg);
	        	ps.setString(2, reeqid);
				               int updated=ps.executeUpdate();
				               if(updated>0)
				               {
				            	   out.print(updated + " row(s) updated.");
				                
				                } else{
				                out.println("<font color=red>Failed to update</font>");
				                }
				 
					 }
		
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
