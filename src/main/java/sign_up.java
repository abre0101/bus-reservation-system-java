

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
/**
 * Servlet implementation class sign_up
 */
@WebServlet("/sign_up")
public class sign_up extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sign_up() {
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
		out.println("<link rel='stylesheet' type='text/css' href='style/NewFile8.css'>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
	RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		rd.include(request,response);

		if(submit==null)
		{
			out.print("<center><table width=800>");
			out.print("<tr><th colspan=3><h1 style=color:black>SIGN UP PAGE </h1></th></tr>");
		out.print("<form action= sign_up method=post >");
		out.print("<tr><td>User Name: <input type=text name=username required /></td></tr><br>");
		out.print("<tr><td>Password: <input type=password name=password min=6 required /></td></tr><br>");
		out.print("<tr><td>Phone: <input type=text name=phone required /></td></tr><br>");
		out.print("<tr><td><input type=submit name=submit value=Register /></td></tr>");
		out.print("<tr><td><input type=reset value=Clear /></td></tr>");
		out.print("</table></center>");
		out.print("</form>");
		}else { 
		String encrypted_password=null;
		String Username = request.getParameter("username");
		 String Password = request.getParameter("password");
		 String Phone = request.getParameter("phone");
		 String Status  = "Active";
		 try { 
		 int KEY_LENGTH = 256;
		 int ITERATION_COUNT = 65536;
		// Define your secret key and salt (keep these secure and don't hardcode in production)
		 String secretKey = "12_win";
		 String salt = "_gm+";
		//....Encryption 
		 SecureRandom secureRandom = new SecureRandom();
		 byte[] iv = new byte[16];
		 secureRandom.nextBytes(iv);
		 IvParameterSpec ivspec = new IvParameterSpec(iv);
		 SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		 KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
		 SecretKey tmp = factory.generateSecret(spec);
		 SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
		 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		 cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
		 byte[] cipherText = cipher.doFinal(Password.getBytes("UTF-8")); // password read from the form
		 byte[] encryptedData = new byte[iv.length + cipherText.length];
		 System.arraycopy(iv, 0, encryptedData, 0, iv.length);
		 System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);
		 encrypted_password=Base64.getEncoder().encodeToString(encryptedData);
		 }catch (Exception e) {
		 // Handle the exception properly
		 e.printStackTrace();
		 
		 } String driverName="com.mysql.cj.jdbc.Driver";
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
		String sql="insert into user (name, password,phone,Status) VALUES (?,?,?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1,Username);
		ps.setString(2,encrypted_password);
		ps.setString(3,Phone);
		ps.setString(4,Status);
		int i = ps.executeUpdate();
		 if(i>0) {
		out.println("<font color=green>The following user record has been inserted into user table</font>");
		out.print("<form action= sign_up method=post >");
		out.print(" User Name: <input type=text name=username value="+Username+" readonly /><br>");
		out.print(" Password: <input type=text name=password value="+Password+" readonly /><br>");
		out.print(" Phone_no : <input type=text name=Phone_no value="+Phone+" readonly /><br>");
		out.print(" status : <input type=Active name=Status value="+Status+" readonly /><br>");
		out.print("<input type=submit value=Close />");
		out.print("</form>");
		 }else
		 out.println("Failed to insert the data");
		out.println("</body></html>");
		}catch (SQLException e){
		 out.println(e.getMessage());
		 }}RequestDispatcher rr = request.getRequestDispatcher("footer");
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
