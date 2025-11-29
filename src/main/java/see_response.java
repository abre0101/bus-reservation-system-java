

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
/**
 * Servlet implementation class see_response
 */
@WebServlet("/see_response")
public class see_response extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public see_response() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				//View_user.java
			


				// TODO Auto-generated method stub
				PrintWriter out = response.getWriter();
				try {
				String search=request.getParameter("search");
				String EMAIL=request.getParameter("email");
				 
				out.print("<html>");
				out.print("<head>");
				out.println("<link rel='stylesheet' type='text/css' href='style/NewFile8.css'>");
				out.print("</head>");
				out.print("<body bgcolor=#66CCFF>");
				//Creating an object of RequestDispatcher to include the content of another servlet named -Header
			RequestDispatcher rd = request.getRequestDispatcher("Header");
			  //Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
				rd.include(request,response);
		
				out.print("<form action= see_response method=POST>");
				out.print("Enter your email: <input type=text name=email required />");
				out.print("<br>");
				out.print("<input type=submit name=search value=Search />");
				out.print("<input type=Reset value=Reset />");
				
				out.print("</form>");
				out.print("</br>");
				out.print("</br>");
			
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
				
				if(search!=null)
				{

				String sql="select message from support_request where email='"+EMAIL+"'";
				rs=stmt.executeQuery(sql);
				if(!rs.next())
				out.print("<font color=red>TRY TO ENTER YOUR EMAIL CORRECTLT name "+EMAIL+"</font>");
				
				else{ 
				 String REQUEST=null;

				rs=stmt.executeQuery(sql);
				rs.next();
				REQUEST=rs.getString("message");
				out.print("<form action= see_response method=post >");
				out.print("REQUEST: <input type=text name=REQ value='"+REQUEST+"' readonly /><br>");
				out.print("</form>");
				}
				
				String sqlL="select response from support_request where email='"+EMAIL+"'";
				rs=stmt.executeQuery(sqlL);
				if(!rs.next())
					out.print("<font color=red>TRY TO ENTER YOUR EMAIL CORRECTLT name "+EMAIL+"</font>");
					
					else{ 
					 String RESPONSE=null;
					
					rs=stmt.executeQuery(sqlL);
					rs.next();
					RESPONSE=rs.getString("response");
					
					out.print("RESPONSE: <input type=text name=RES value='"+RESPONSE+"' readonly /><br>");

					out.print("</form>");
					}	}
				} catch(Exception e) { 
				 out.print(e.getMessage());
				 }
				RequestDispatcher rr = request.getRequestDispatcher("footer");
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
