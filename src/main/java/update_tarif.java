

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 * Servlet implementation class update_tarif
 */
@WebServlet("/update_tarif")
public class update_tarif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public update_tarif() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setHeader("Pragma","no-cache"); 
		response.setHeader("Cache-Control","no-store"); 
		response.setHeader("Expires","0"); 
		response.setDateHeader("Expires",-1); 
	
		out.print("<html>");
		out.print("<head>");
		out.print("<title>Home Page</title>"); 
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile6.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
	
		RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);

		 try {
			 String search=request.getParameter("search");
			 String update=request.getParameter("update");
			 
			 String tarif_id=request.getParameter("tarif_id");
	
			 String TARIF=request.getParameter("TARIF");
			 String description=request.getParameter("description");
		
		       
			 out.print("<html>");
			 out.print("<head>");
			 out.print("<title> Update Student Profile</title>"); 
			 out.print("</head>");
			 out.print("<body bgcolor=lightblue>");
			 //Creating an object of RequestDispatcher to include the content of another servlet named -Header
			
		
			 
			 out.print("<form action= update_tarif method=POST>");
			 out.print("Search tarif id: <input type=text name=tarif_id required />");
			 out.print("<br>");
			 out.print("<input type=submit name=search value=Search />");
			 out.print("<input type=Reset value=Reset />");
			 out.print("</form>");
			 String driverName="com.mysql.cj.jdbc.Driver";
			 String dbUrl="jdbc:mysql://localhost:3306/ticket_db";
			 String dbusername="root";
			 String dbpassword="root";
			  
			  Connection conn=null;
			  Statement stmt=null;
			 PreparedStatement ps=null;
			  ResultSet rs=null;
			  try{
			  Class.forName(driverName);
			  }catch(ClassNotFoundException e){
			 System.out.println(e.getMessage());
			 }
			 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
			  stmt=conn.createStatement();
			  
			 if(search!=null&&update==null)
			 {
			 String sql="select * from bus_tarif where id='"+tarif_id+"'";
			 rs=stmt.executeQuery(sql);
			 if(!rs.next())
			 out.print("<font color=red>There is no student profile registered with id "+tarif_id+"</font>");
			 else{ 
			 rs=stmt.executeQuery(sql);
			 rs.next();
			 out.print("<form action= update_tarif method=post >");
			  out.print(" TARIF ID: <input type=text name=tarif_id value='"+rs.getString(1)+"' size=20 readonly /><br>");
			 out.print(" ROUTE ID: <input type=text name=route_id value='"+rs.getString(2)+"' size=30 readonly /><br>");
			 out.print(" TARIF: <input type=text name=TARIF value='"+rs.getDouble(3)+"' size=30 required /><br>");
			 out.print(" CURRENCY: <input type=text name=currency value='"+rs.getString(4)+"' size=30 readonly /><br>");
			 out.print(" date: <input type=text name=date value='"+rs.getString(5)+"' size=30 readonly /><br>");
			 out.print(" DESCRIPTION: <Textarea name=description value='"+rs.getString(6)+"' required /></Textarea ></<br>");
		
			 out.print("<input type=submit name=update value=Update /><input type=reset value=clear>");
			 out.print("</form>");
			 }
			 }else if(search==null&&update!=null)
			 {
			
			 String updatesql="update bus_tarif Set tarif=?,description=? where id='"+tarif_id+"'";
			 ps=conn.prepareStatement(updatesql);
		
			 ps.setString(1, TARIF);
			 ps.setString(2, description);
		
			 int updated=ps.executeUpdate();
			 if(updated>0)
			 {
			  out.println("<font color=green>Updated successfully</font>");
		
			  
			  } else{
			  out.println("<font color=red>Failed to update</font>");
			  }
			 }
			 } catch(Exception e) { 
			  out.print(e.getMessage());
			  }	RequestDispatcher rr = request.getRequestDispatcher("footer");
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
