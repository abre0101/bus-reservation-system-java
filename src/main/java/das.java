

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class das
 */
@WebServlet("/das")
public class das extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public das() {
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
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile7.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
	 	 RequestDispatcher rd = request.getRequestDispatcher("Header");
			//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
			  rd.include(request,response);
		 String submit = request.getParameter("submit");
		 String price = request.getParameter("price");
		 
	if(submit==null && price==null) {	

        // Display the bus selection form
        out.println("<html><body>");
      
        out.println("<form action='das' method='post'>");
        
     
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

		try { 

		 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
		 stmt=conn.createStatement();
		 String sqlll="select busname from bus"; 
		 String sq="select source from route"; 
		 String sm="select destination from route";
		
		 rs=stmt.executeQuery(sqlll);
		 out.print("<h2>Online Bus Ticket Booking</h2>");
		 out.print("<br>"+"busname:");
			if(!rs.next()) {
		
			out.print("<font color=red>There is no user information registered </font>");
			}
			else{ 
				out.println("<select name='busName'>");
			     while (rs.next()) {
	                String busName = rs.getString("busname");
	                out.println("<option value='" + busName + "'>" + busName + "</option>");
	            }
	            out.println("</select>");
			}	
			
			out.print("<br>"+"source:");
			 rs=stmt.executeQuery(sq);
	       	if(!rs.next()) {
			out.print("<font color=red>There is no user information registered </font>");}
	     	else {out.println("<select name='source'>");
	           while (rs.next()) {
               String busame = rs.getString("source");
              out.println("<option value='" + busame + "'>" + busame + "</option>");
              }
         out.println("</select>");
		}  
		
		
		  out.print("<br>"+"distination:");
		  rs=stmt.executeQuery(sm);
		  if(!rs.next()) {
			out.print("<font color=red>There is no user information registered </font>");}
	         	else {out.println("<select name='distination'>");
	            while (rs.next()) {
                String busame = rs.getString("destination");
                out.println("<option value='" + busame + "'>" + busame + "</option>");
               }
              out.println("</select>");
		   }
		
		 }catch (SQLException e){
		 out.println(e.getMessage());
		 }

        out.print("<br>"+"date <input type=date name=date required /><br>");
        out.println("<label for='numTickets'>Number of Tickets:</label>");
        out.println("<input type='number' id='numTickets' name='numTickets' required><br><br>");
    
        out.println("<label for='name'>NAME:</label>");
        out.println("<input type='text'  name='NAME' required><br><br>");
        
        out.println("<input type='submit' name=submit value='Book Tickets'>");
        out.println("<input type='submit' name=price value='PRICE'>");
        
        out.println("</form>");
        out.println("</body>");
        
        
        out.println("<footer>");
        
        out.println("</html></footer>");
	}	
	
	
	else if(submit==null && price!=null) {
		
	    	  String noofticket  = request.getParameter("numTickets");
		      String source = request.getParameter("source");
              String distination = request.getParameter("distination");
        
		      int no_ofticket= Integer.parseInt(noofticket);
		 	  int km=0;
		 	  int route_id=0;
		 	  double tarif=0.0;
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

			try { 
				
			 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
			 stmt=conn.createStatement();
		
			  String sqlll="select * from route where source='"+ source+"'and destination='"+distination+ "'";
			
			  rs=stmt.executeQuery(sqlll);
				
				if(!rs.next()) {
				out.print("<font color=red>There is no user information registered with user name "+km+"</font>");
				  }
				else{ 	    route_id=rs.getInt("route_id");
				        km=rs.getInt("distance");
		
			}String sl="select tarif from bus_tarif where route_id='"+ route_id+"'";
			
			  rs=stmt.executeQuery(sl);
			  if(!rs.next()) {
					out.print("<font color=red>There is no user information registered with user name "+km+"</font>");
					  }
					else{ 	 
					    rs=stmt.executeQuery(sl);
					    rs.next();
					   
					    tarif=rs.getInt("tarif");
					    
					    double totalPrice = no_ofticket * tarif * km;
					 
					    out.print("no of ticket="+no_ofticket+"<br>");
					    out.print("price per kilometere="+tarif+"<br>");
					    out.print("kilometer is="+km+"<br>");
					    out.print("total price is="+totalPrice);
			
				}
			 }catch (SQLException e){
			 out.println(e.getMessage());
			 }
			
		}
			
	      else{
	    	  
	    	  String BusName = request.getParameter("busName");
	    	  String noofticket  = request.getParameter("numTickets");
		      String source = request.getParameter("source");
              String distination = request.getParameter("distination");
              String Date  = request.getParameter("date");
              int route_id=0;
		 	  double tarif=0.0;
			  int km=0;
              String name = request.getParameter("NAME");
              int no_ofticket= Integer.parseInt(noofticket);
		      String driverName="com.mysql.cj.jdbc.Driver";
			  String dbUrl="jdbc:mysql://localhost:3306/ticket_db";
			  String dbusername="root";
			  String dbpassword="root"; 
			  Connection conn=null;
			  PreparedStatement ps=null;
			  Statement stmt=null;
			  ResultSet rs=null;
			 
			 try{
			 Class.forName(driverName);
			 }catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
			}

		 

				try { 
					
				 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
				 stmt=conn.createStatement();
			
				  String sqlll="select * from route where source='"+ source+"'and destination='"+distination+ "'";
				
				  rs=stmt.executeQuery(sqlll);
					
					if(!rs.next()) {
					out.print("<font color=red>There is no user information registered with user name "+km+"</font>");
					  }
					else{ 	    route_id=rs.getInt("route_id");
					            km=rs.getInt("distance");
			
				}String sl="select tarif from bus_tarif where route_id='"+ route_id+"'";
				
				  rs=stmt.executeQuery(sl);
				  if(!rs.next()) {
						out.print("<font color=red>There is no user information registered with user name "+km+"</font>");
						  }
						else{ 	 
						    rs=stmt.executeQuery(sl);
						    rs.next();
						   
						    tarif=rs.getInt("tarif");
						    
						    double totalPrice = no_ofticket * tarif * km;
						 
						    out.print("no of ticket="+no_ofticket+"<br>");
						    out.print("price per kilometere="+tarif+"<br>");
						    out.print("kilometer is="+km+"<br>");
						    out.print("total price is="+totalPrice);
				
					}
				
				
			conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
			 stmt=conn.createStatement();

			 String st="select seatsAvailable from booking  where busname='"+BusName+"'and source='"+ source+"'and distination='"+distination+ "'ORDER BY date DESC LIMIT 3";
			 
			 rs=stmt.executeQuery(st);
			
			if(!rs.next())
			{
				 response.sendRedirect("das");//redirect to Home page
			}else {
				
				
				rs.next();
				int seatavl=rs.getInt("seatsAvailable");
				 if (no_ofticket > seatavl) {
			            
		                out.println("<h2>Error: Not enough seats available on </h2>"); 
		                return;
		            }
				 seatavl = seatavl-no_ofticket;
			
			
			if(!rs.next())
			{
				 response.sendRedirect("das");//redirect to Home page
			}else
		
			 {
			
             
			String sql="insert into booking (busname,source,distination,date,numTicket,km,totalprice,seatsAvailable,name) VALUES (?,?,?,?,?,?,?,?,?)";
			double totalPrice = no_ofticket  * tarif * km;
			ps = conn.prepareStatement(sql);
		
			ps.setString(1,BusName);
			ps.setString(2,source);
			ps.setString(3,distination);
			ps.setString(4,Date);
			ps.setString(5,noofticket);
			ps.setDouble(6,km);
			ps.setDouble(7,totalPrice);
			ps.setInt(8,seatavl);
		
			ps.setString(9,name);
			int i = ps.executeUpdate();
			 if(i>0) {
		 
				 
				 out.println("<html><body>");
				 out.println("<h1>");
		    out.println("Booking Confirmation<br>");
			out.println("<font color=green>Your ticket has been booked successfully</font>");
			out.println("</h1>");
			out.print("<form action= das method=post >");
			out.print(" BusName : <input type=text name=bus value="+ BusName+" readonly /><br>");
			out.print(" EMAIL : <input type=text name=email value="+name+" readonly /><br>");
			out.print(" Source: <input type=text name=source value="+ source+" readonly /><br>");
			out.print(" Distination : <input type=text name=distination value="+ distination+" readonly /><br>");
			out.print(" Date : <input type=date name=date value="+Date+" readonly /><br>");
			out.print(" Num of ticket : <input type=text name=noofticket value="+noofticket+" readonly /><br>");
			out.print(" Km : <input type=text name=km value="+km+" readonly /><br>");
			out.print(" seatsAvailable : <input type=text name=seatsAvailable value="+seatavl+" readonly /><br>");
		
			out.print("Total price: <input type=text name=TotalPrice value="+totalPrice+" readonly /><br>");
			out.print(" <li><a href=payment>PAY</a></li>");
			  out.println("<br><a href='das'>Book More Tickets</a>");
			out.print("<input type=submit value=Close />");
			
			out.print("</form>");
			 out.println("</body></html>");
			 }else {
			 out.println("Failed to insert the data");
			out.println("</body></html>");}}
			 }
			}catch (SQLException e){
			 out.println(e.getMessage());
			 }
 } RequestDispatcher rr = request.getRequestDispatcher("footer");
 rr.include(request, response); }
      

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
