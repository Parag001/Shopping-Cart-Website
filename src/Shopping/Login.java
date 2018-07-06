package Shopping;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	   private Connection con;
	  
	    public Login() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	    
	    public void init() throws ServletException {
			System.out.println("In init");

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin123");
				
			}//try
			catch(ClassNotFoundException e1){
				e1.printStackTrace();
	                }
	                catch(SQLException e2){
				e2.printStackTrace();
	                }
	                catch(Exception e3){
				e3.printStackTrace();
			}
		}//init
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		
		 response.setContentType("text/html");
	     PrintWriter out = response.getWriter();
	     String user = request.getParameter("uname");
         String password = request.getParameter("pass");
         if(user==null||user.equals("")||password==null||password.equals(""))
         {	    	
		    	 out.println("<center>User Name and Password cannot be empty</center><br/>");
		    	 RequestDispatcher rd = request.getRequestDispatcher("/index.html");
			     rd.include(request, response);	    		    	 	
	     }
         else
         {
         try
         {
		         Statement stmt = con.createStatement();
			     String query =  "select email,pwd,name from Register where email = '"+user+"'";
			     ResultSet rs = stmt.executeQuery(query);
			    
			     while(rs.next())
			     {		
			    	 String username = rs.getString(1);
				     String Password = rs.getString(2);
				     String session_user = rs.getString(3);
			    	if(username.equals(user) && Password.equals(password))
			    	{
			    		   HttpSession session=request.getSession();  
			    		   System.out.println("inside");
			    	       session.setAttribute("name",session_user);  			    	       
			    	        RequestDispatcher rd = request.getRequestDispatcher("HomePage");
						     rd.forward(request, response);
			    	     //  response.sendRedirect("HomePage");
			    	}else
			    	{
			    		 out.println("<center>Invalid Username/Password</center><br/>");
			    		 System.out.println("inside");
				    	 RequestDispatcher rd = request.getRequestDispatcher("/index.html");
					     rd.include(request, response);
			    	}
			    		
			     }
         }
	     catch(Exception e)
         {
        	 e.printStackTrace();
         }
         }
	}

}
