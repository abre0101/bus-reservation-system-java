

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;


/**
 * Servlet implementation class home1
 */
@WebServlet("/home1")
public class home1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public home1() {
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
		try {
		out.print("<html>");
		out.print("<head>");
		out.print("<title>Home Page</title>"); 
		out.println("<link rel='stylesheet' type='text/css' href='style/home.css'>");
		out.print("</head>");
		out.print("<body bgcolor=#66CCFF>");
		

		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
	    RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		  rd.include(request,response);
		  
		String wrongusername=request.getParameter("wrongusername");
		String wrongpassword=request.getParameter("wrongpassword");
		String logout=request.getParameter("logout");
		 out.print("<center><table width=800>");
		out.print("<tr><th colspan=3><h1 style=color:black>Welcome to ethio online bus booking </h1></th></tr>");
	
		out.print("<tr><td colspan=3 align=left><h2>Login</h2></td></tr>");
		out.print("<tr><td><form action=valid method=post>"+"User Name:<input type=text name=username required /></td><td></td><td></td></tr>");
		out.print("<tr><td>Password:<input type=password name=password required/></td><td></td><td></td></tr>"); 
		out.print("<tr><td><input type=submit name=submit value=Login />"+ "<input type=Reset value=Reset /></td><td></td><td></td></tr></form>");
		out.print("<tr><td colspan=3 align=left><a href= 'sign_up' style=color:white;fontsize:26>"+ "<input type=submit value= 'signup' style=color:black;font-size:20></a>");	out.print("</table></center>");
	
		if(wrongusername!=null)
		out.print("<font color=red>Wrong user name, try again, or your status may be blocked by system admin</font>");
		if(wrongpassword!=null)
		out.print("<font color=red>Wrong password,try again</font>");
		if(logout!=null)
		out.print("<font color=blue>You Logout</font>"); 
	
		out.print("</body></html>");
		
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
