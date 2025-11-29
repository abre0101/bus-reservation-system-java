

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
 * Servlet implementation class BOOKING_HISTORY
 */
@WebServlet("/BOOKING_HISTORY")
public class BOOKING_HISTORY extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BOOKING_HISTORY() {
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
		String name=request.getParameter("namMe");
		 
		out.print("<html>");
		out.print("<head>");
		out.print("<title> View user information</title>"); 
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile9.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
	    RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);

		out.print("<form action= BOOKING_HISTORY method=POST>");
		out.print("enter your name: <input type=text name=namMe required />");
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

		String sql="select * from booking where name='"+name+"'";
		rs=stmt.executeQuery(sql);
		if(!rs.next())
		out.print("<font color=red>There is no user information registered with this name "+name+"</font>");
		
		else{ 
		while(rs.next()) {
		 String busname=null,source=null,distination=null,date=null;
				 double km=0;
				 int bookingid=0; 
	
        bookingid=rs.getInt("bid");
		busname=rs.getString("busname");
		source=rs.getString("source");
		distination=rs.getString("distination");
		date=rs.getString("date");
		km=rs.getDouble("km");
	
		
		
		
		out.print("<form action= BOOKING_HISTORY method=post >");
		out.print(" booking id: <input type=text name=busname value='"+bookingid+"' readonly /><br>");
		out.print(" Bus Name: <input type=text name=busname value='"+busname+"' readonly /><br>");
		out.print(" Source: <input type=text name=source value='"+source+"' readonly /><br>");
		out.print(" Distination: <input type=text name=distination value='"+distination+"' readonly /><br>");
		out.print(" Date: <input type=text name=date value='"+date+"' readonly /><br>");
		out.print(" Kilometer: <input type=text name=km value='"+km+"' readonly /><br>");
	


		out.print("<input type=submit value=Close />");
		out.print("</form>");
		}}
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
