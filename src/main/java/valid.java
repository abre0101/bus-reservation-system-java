

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.sql.*;
// For Encryption
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;
/**
 * Servlet implementation class valid
 */
@WebServlet("/valid")
public class valid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public valid() {
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
		try{
		String username=null,password=null,decrypted_value=null,submit=null;
		 username=request.getParameter("username");
		 password=request.getParameter("password");
		 submit=request.getParameter("submit");
		//RequestDispatcher rd; //used for page redirection
		if(submit!=null)
		{ String status="Active";
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
		String sql="select * from user where name='"+username+"' and Status='"+status+"'";
		rs=stmt.executeQuery(sql);
	
		if(!rs.next())
		{
		 response.sendRedirect("home1?wrongusername=true"); //redirect to Home page
		}else
		 { rs=stmt.executeQuery(sql);
		rs.next(); 
		 String utype=rs.getString("User_Type");
		 String uname=rs.getString("name");
		String pword=rs.getString("password");
		try { 
		 int KEY_LENGTH = 256;
		 int ITERATION_COUNT = 65536;
		// Define your secret key and salt (keep these secure and don't hardcode in production)
		 String secretKey = "12_win";
		 String salt = "_gm+";
		//...... Decrypt encrypted password read from database to its original value 
		byte[] encryptedvalue = Base64.getDecoder().decode(pword); // pword is encrypted password read from database
		 byte[] iv2 = new byte[16];
		 System.arraycopy(encryptedvalue, 0, iv2, 0, iv2.length);
		 IvParameterSpec ivspec2 = new IvParameterSpec(iv2);
		SecretKeyFactory factory2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		 KeySpec spec2 = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
		 SecretKey tmp2 = factory2.generateSecret(spec2);
		 SecretKeySpec secretKeySpec2 = new SecretKeySpec(tmp2.getEncoded(), "AES");
		 Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
		 cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec2, ivspec2);
		 byte[] cipherText2 = new byte[encryptedvalue.length - 16];
		 System.arraycopy(encryptedvalue, 16, cipherText2, 0, cipherText2.length);
		 byte[] decryptedText2 = cipher2.doFinal(cipherText2);
		 decrypted_value=new String(decryptedText2, "UTF-8");
		 
		 
		 }catch (Exception e) {
		 
		 e.printStackTrace();
		 
		 }
		
		if(!password.equals(decrypted_value)) 
		 { 
		 response.sendRedirect("home1?wrongpassword=true"); //redirect to Home page
		 }else
		 { 
		 HttpSession session=request.getSession(); 
		 session.setAttribute("utype",utype); 
		 session.setAttribute("uname",uname); 
		 if(utype.equals("user"))
		{ 
		 response.sendRedirect("Dashboard"); //redirect to Registrar Officer Home page
		}else if (utype.equals("Admin"))
		{ 
		response.sendRedirect("Admin_page"); //redirect to Department head home page
		 
		 }else if (utype.equals("support"))
			{ 
				response.sendRedirect("support_dashboard"); //redirect to Department head home page
				 
				 }
		 // you can validate other user type
		 } 
		 } 
		}
		}catch(Exception e)
		 { out.print(e.getMessage());
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
