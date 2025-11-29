

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class tarif
 */
@WebServlet("/tarif")
public class tarif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public tarif() {
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
		 String submit = request.getParameter("submit");

		out.print(" <html><head>");
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile7.css'>");
		out.println("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);

		 String driverName="com.mysql.cj.jdbc.Driver";
			String dbUrl="jdbc:mysql://localhost:3306/ticket_db";
			String dbusername="root";
			String dbpassword="root";
			 Statement stmt=null;
			 ResultSet rs=null;
		   	Connection conn=null;
		    PreparedStatement ps=null;
			 try{
			 Class.forName(driverName);
			 }catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
			}if(submit==null)
		{
		
	
				try { 
					 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
					 stmt=conn.createStatement();
                 
					 String sqlllmm="select route_id from route ORDER BY DATE_OF_adding DESC LIMIT 1";  
			         rs=stmt.executeQuery(sqlllmm);
					  
				if(!rs.next()) {
				out.print("<font color=red>THERE IS NO BOOKING MADE</font>");
				  }
				else{ 	 	
					 rs=stmt.executeQuery(sqlllmm);
					    rs.next();
					int id=rs.getInt("route_id");	
					out.print("<center><table width=800>");
					out.print("<tr><th colspan=3><h1 style=color:#cc6600>TARIF PAGE </h1></th></tr>");
				out.print("<form action= tarif method=post >");
				out.print("<tr><td>: route id<input type=text name=bi value="+id+" readonly /></td></tr><br>");
				out.print("<tr><td>TARIF: <input type=password name=tarif required /></td></tr><br>");
				out.print("<tr><td>description: <Textarea name=description /></Textarea></td></tr><br>");
				out.print("<tr><td><input type=submit name=submit value=add /></td></tr>");
				out.print("<tr><td><input type=reset value=Clear /></td></tr>");
				out.print("</table></center>");
				out.print("</form>");
	
					
					
					
					
	
						}
				}catch (SQLException e){
					 out.println(e.getMessage());}
						
				
		

		}else {
			try {
    		 String idd = request.getParameter("bi");
    	 String tarif = request.getParameter("tarif");
    	 String description = request.getParameter("description");
		 double tarifff= Double.parseDouble(tarif);
		 int idr= Integer.parseInt(idd);


	
	conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
	String sql="insert into bus_tarif (route_id,tarif,description) VALUES (?,?,?)";
	
	ps = conn.prepareStatement(sql);
	
	ps.setInt(1,idr);
	ps.setDouble(2,tarifff);
	ps.setString(3,description);
	
	int i = ps.executeUpdate();
	
	 if(i>0) {
	out.println("<font color=green>The following user record has been inserted into user table</font>");

	 }else
	 out.println("Failed to insert the data");
	out.println("</body></html>");
	}
	catch (SQLException e){
	 out.println(e.getMessage());}
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
