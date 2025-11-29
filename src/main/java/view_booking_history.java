

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class view_booking_history
 */
@WebServlet("/view_booking_history")
public class view_booking_history extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public view_booking_history() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub



		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
		String search=request.getParameter("search");
		String email=request.getParameter("email");
		 
		out.print("<html>");
		out.print("<head>");
		out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/booking.css' />");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		//RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		//rd.include(request,response);
		out.print("<h1><font color=green> User Information View Form </font></h1>");
		out.print("<form action= view_booking_history method=POST>");
		out.print("Search by your email: <input type=text name=email required />");
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

		String sql="select * from booking where email='"+email+"'";
		rs=stmt.executeQuery(sql);
		if(!rs.next())
		out.print("<font color=red>There is no user information registered with this EMAIL "+email+"</font>");
		
		else{ 
		while(rs.next()) {
		 String busname=null,source=null,distination=null,date=null,Email=null,NAME=null;
				 double km=0;
				 int bookingid=0; 
	
        bookingid=rs.getInt("bid");
		busname=rs.getString("busname");
		source=rs.getString("source");
		distination=rs.getString("distination");
		date=rs.getString("date");
		km=rs.getDouble("km");
		Email=rs.getString("email");
		NAME=rs.getString("name");
		
		
		out.print("<form action= view_booking_history method=post >");
		out.print(" booking id: <input type=text name=busname value='"+bookingid+"' readonly /><br>");
		out.print(" NAME: <input type=text name=name value='"+NAME+"' readonly /><br>");
		out.print(" Bus Name: <input type=text name=busname value='"+busname+"' readonly /><br>");
		out.print(" Source: <input type=text name=source value='"+source+"' readonly /><br>");
		out.print(" Distination: <input type=text name=distination value='"+distination+"' readonly /><br>");
		out.print(" Date: <input type=text name=date value='"+date+"' readonly /><br>");
		out.print(" Kilometer: <input type=text name=km value='"+km+"' readonly /><br>");
		out.print(" EMAIL: <input type=text name=email value='"+Email+"' readonly /><br>");


		out.print("<input type=submit value=Close />");
		out.print("</form>");
		}}
		}
		} catch(Exception e) { 
		 out.print(e.getMessage());
		 }
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
