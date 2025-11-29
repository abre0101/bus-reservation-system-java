

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
 * Servlet implementation class payment
 */
@WebServlet("/payment")
public class payment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public payment() {
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
		 String bookingId = request.getParameter("book");
		 String paymentMethod = request.getParameter("paymentMethod");
		 String amount = request.getParameter("price");
	
		out.print(" <html><head>");
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile5.css'>");
		out.println("</head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);
		out.print("<h1 style=color:#56cc43>payment</h1>");
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
                 
					 String sqlllmm="select bid from booking ORDER BY DATE_OF_BOOKING DESC LIMIT 1";  
			         rs=stmt.executeQuery(sqlllmm);
					  
				if(!rs.next()) {
				out.print("<font color=red>THERE IS NO BOOKING MADE</font>");
				  }
				else{ 	 	
					 rs=stmt.executeQuery(sqlllmm);
					    rs.next();
					int booking=rs.getInt("bid");	
				 String sqll="select totalprice from booking where bid ='"+ booking+"'";
				rs=stmt.executeQuery(sqll);
				if(!rs.next()) {
					out.print("<font color=red>There is no user information registered with user name </font>");
					  }
					else{ 	 	
						 rs=stmt.executeQuery(sqll);
						    rs.next();
						int price1=rs.getInt("totalprice");
				
						out.print("<form action= payment method=post >");
						out.print(" booking id: <input type=text name=book value="+booking+" readonly /><br>");
						out.print("payment method: <select id=pay name=paymentMethod ><br>");
						out.print("<option value=telebirr>telebir</option><br>");
						out.print("<option value=cbe>cbe</option><br>");
						out.print("<option value=nib>nib</option><br>");
						out.print("</select ><br>");
						out.print(" amount: <input type=text name=price value="+price1+" readonly /><br>");
						out.print("<input type=submit name=submit value=PAY /><input type=reset value=Clear>");
						out.print("</form>");
	
						}
					
				}
				}catch (SQLException e){
					 out.println(e.getMessage());
					 }

		}else {
	    
			try { 
				 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
				 int bok= Integer.parseInt(bookingId); 
				 int Amount= Integer.parseInt(amount);
		String sqlk="insert into payments (bid,payment_method,amount) VALUES (?,?,?)";
		ps = conn.prepareStatement(sqlk);
		ps.setInt(1,bok);
		ps.setString(2,paymentMethod);
		ps.setInt(3,Amount);
	
		int i = ps.executeUpdate();
		 if(i>0) {
		out.println("<font color=green>PAYMENT SUCCESSFULL!!!</font>");
		out.print("<form action=payment method=post >");
		out.print(" BOOKING ID: <input type=text name=username value="+bok+" readonly /><br>");
		out.print(" PAYMENT METHOD: <input type=text name=password value="+paymentMethod+" readonly /><br>");
		out.print(" AMOUNT : <input type=text name=gender value="+Amount+" readonly /><br>");

		out.print("<input type=submit value=Close />");
		out.print("</form>");
		 }else
			 out.println("Failed to insert the data");
			out.println("</body></html>");
			}catch (SQLException e){
			 out.println(e.getMessage());
			 }
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
