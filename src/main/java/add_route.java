

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class add_route
 */
@WebServlet("/add_route")
public class add_route extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public add_route() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Pragma","no-cache"); 
		response.setHeader("Cache-Control","no-store"); 
		response.setHeader("Expires","0"); 
		response.setDateHeader("Expires",-1); 
		PrintWriter out = response.getWriter();
	

		out.print("<html>");
		out.print("<head>");
		out.print("<title>Home Page</title>"); 
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile9.css'>");
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
		out.print("<tr><th colspan=3><h1 style=color:black>ADD BUS </h1></th></tr>");

		out.print("<tr><td><form action=add_route method=post>");
	
		out.print("<tr><td>ROUTE SOURCE:<input type=text name=source required/></td><td></td><td></td></tr>"); 
		out.print("<tr><td>ROUTE DISTINATION:<input type=text name=distination required/></td><td></td><td></td></tr>");
	
		out.print("<tr><td>distance:<input type=text name=km required/></td><td></td><td></td></tr>");
		out.print("<tr><td><input type=submit name=submit value=Add />"+ "<input type=Reset value=Reset /></td><td></td><td></td></tr></form>");
		out.print("</table></center>");
	
	 
		} else { 
	
	
		 String source = request.getParameter("source");
		 String distination = request.getParameter("distination");
		 String  KILOMETE=request.getParameter("km");
		 int KILOMETER= Integer.parseInt(KILOMETE);
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
	
	String sql="insert into route (source,destination,distance) VALUES (?,?,?)";
	ps = conn.prepareStatement(sql);

	ps.setString(1,source);
	ps.setString(2,distination);
	ps.setInt(3,KILOMETER);

	int i = ps.executeUpdate();
	 if(i>0) {
	out.println("<font color=green>The following user record has been inserted into user table</font>");
	out.print("<form action= add_route method=post >");
	
	out.print(" source: <input type=text name=sour value="+source+" readonly /><br>");
	out.print(" distination : <input type=text name=dist value="+distination+" readonly /><br>");
	out.print(" kilometer : <input type=text name=KM value="+KILOMETER+" readonly /><br>");
	out.print("<li><a href=tarif>TARIF</a></li>");
	out.print("<input type=submit value=Close />");
	out.print("</form>");
	 }else
	 out.println("Failed to insert the data");
	out.println("</body></html>");
	}
	catch (SQLException e){
	 out.println(e.getMessage());}
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
