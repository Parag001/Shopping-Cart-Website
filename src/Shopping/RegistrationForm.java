package Shopping;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationForm
 */
public class RegistrationForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   private Connection con;
   private PreparedStatement psmt;
    public RegistrationForm() {
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
		 // Set response content type
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      
	      
	      int my_id = 0;
	      String name =  request.getParameter("name");
	      String emailid = request.getParameter("email");
	      String phone = request.getParameter("phone");
	      String pwd = request.getParameter("pwd");
	      String rpwd = request.getParameter("rpwd");
	      
	      if(con != null)
	      {
	    	 
	    	  String query = "select reg_seq.nextval from dual";
	    	  try {
				psmt = con.prepareStatement(query);
				 ResultSet rs = psmt.executeQuery();
		    	  if(rs.next())
		    		   my_id = rs.getInt(1);
		    	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	    	   
	    	  String insertQuery = "INSERT INTO Register"
	    				+ "(id,name,pwd,email,phone) VALUES"
	    				+ "(?,?,?,?,?)";
	    		PreparedStatement preparedStatement;
				try {
					if(pwd.equals(rpwd))
					{
							preparedStatement = con.prepareStatement(insertQuery);
							preparedStatement.setInt(1,my_id);
							preparedStatement.setString(2,name);
				    		preparedStatement.setString(3,pwd);
				    		preparedStatement.setString(4,emailid);
				    		preparedStatement.setString(5,phone);
				    		int rec = preparedStatement .executeUpdate();
				    		if(rec>0)
				    			out.println("<center><h2>Registered Successfully!You can now Login</h2></center>");
				    			request.getRequestDispatcher("index.html").include(request, response);
				    }
					else
					{
							out.println("Passwords doesn't match");
							RequestDispatcher rd=request.getRequestDispatcher("/Registration.html");
						    rd.include(request, response);//method may be include or forward  
					}
					con.commit();
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	    		
	      }
	      
	      
	      
	      
	     
	      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
