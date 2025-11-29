

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
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class SEARCH
 */
@WebServlet("/SEARCH")
public class SEARCH extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SEARCH() {
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
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile9.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);
		String seearch=request.getParameter("search");
		String busname=request.getParameter("bid");
		if(seearch==null)
		{ out.print("<h1> User Information View Form </h1>");
		out.print("<form action= SEARCH method=POST>");
		out.print("Search By Bus name: <input type=text name=bid required />");
		out.print("<br>");
	    out.print("<input type=submit name=search value=Search />");
		out.print("<input type=Reset value=Reset />");
		out.print("</form>");}
		else{ 
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
	
	 String sql = "SELECT * FROM booking  where busname='"+busname+"'ORDER BY DATE_OF_BOOKING DESC LIMIT 10";
	 rs=stmt.executeQuery(sql);
      
	 out.print("<table border= 2>");
	 out.print("<tr>");
	  out.print("<th>bid </th><th>source </th> <th>distination </th><th>date</th><th>numTicket </th><th>km </th><th>totalprice </th><th>seatsAvailable </th><th>DATE_OF_BOOKING </th><th>name </th>");
	 out.print("</tr>");
		while(rs.next()){ 
			out.print("<tr> ");
			out.print("<td>"+
			        rs.getString("bid") + "</td><td>" +
			        rs.getString("source")+"</td><td>" +
			        rs.getString("distination")+"</td><td>" +
					rs.getString("date")+"</td> <td>"+
					rs.getString("numTicket")+"</td><td>"+
					rs.getString("km")+"</td><td>"+
					rs.getString("totalprice")+"</td><td>"+
					rs.getString("seatsAvailable")+"</td><td>"+
			        rs.getString("DATE_OF_BOOKING")+"</td><td>"+
					rs.getString("name")+"</td><td>");
			 out.print("</tr> ");
			 }
			out.print ("</table>");
			/*out.print("<br>");
			out.print("<br>");
			out.print("<td>"+rs.getString("bid")+"</td><br>");
			out.print("<td>"+busname+"</td><br>");
			//out.print("<td>"+rs.getString("busname")+"</td><br>");
			out.print("<td>"+rs.getString("source")+"</td><br>");
			out.print("<td>"+rs.getString("distination")+"</td><br>");
			out.print("<td>"+rs.getString("date")+"</td><br>");
			out.print("<td>"+rs.getString("numTicket")+"</td><br>");
			out.print("<td>"+rs.getString("km")+"</td><br>");
			out.print("<td>"+rs.getString("totalprice")+"</td><br>");
			out.print("<td>"+rs.getString("seatsAvailable")+"</td><br>");	
			out.print("<td>"+rs.getString("DATE_OF_BOOKING")+"</td><br>");
			out.print("<td>"+rs.getString("name")+"</td>");	
			out.print("<br>");	
			out.print("<br>");*/
		//}
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
