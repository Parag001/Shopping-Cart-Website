package Shopping;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Cart
 */
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
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


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String selected_product = request.getParameter("product");
		String qty =  request.getParameter("qty");
		int cid = 0,price = 0;
		String email="";
		//int quantity = Integer.parseInt(qty);
		HttpSession sess_user = request.getSession(false);
		
		if(sess_user !=  null)
		{
			 String user = (String) sess_user.getAttribute("name");
		//out.println("your selected product "+selected_product+"  "+qty);
			 RequestDispatcher rd = request.getRequestDispatcher("link.html");
			   rd.include(request, response);
			   out.println("<h2><i>Welcome "+user+"</i></h2>");
			
			try
			{
				//Generating card id 
			 Statement stmt = con.createStatement();
			 String query = "select cart_seq1.nextval from dual";
			 ResultSet rs = stmt.executeQuery(query);
			 
	    	  if(rs.next())
	    		   cid = rs.getInt(1);
	    	  //retrieving price of the selected product
	    	  Statement stmt1 = con.createStatement();
	    	  String query1 = "select price from products where product_name = '"+selected_product+"'";
	    	  ResultSet rs1 = stmt1.executeQuery(query1);
	    	  while(rs1.next())
	    		   price = rs1.getInt("price");
	    	  
	    	  //retrieving userid of the particular session
	    	  Statement stmt2 = con.createStatement();
	    	  String query2 = "select * from register where name='"+user+"'";
	    	  ResultSet rs2 = stmt2.executeQuery(query2);
	    	  while(rs2.next())
	    		   email = rs2.getString("email");
	    	
	    	  
	    	  //inserting cart items into table Cart;
	    	  String insert_cart = "insert into cart values(?,?,?,?,?)";
	    	  PreparedStatement psmt = con.prepareStatement(insert_cart);
	    	  psmt.setInt(1, cid);
	    	  psmt.setString(2,email);
	    	  psmt.setString(3, selected_product);
	    	  int quantity = Integer.parseInt(qty);
	    	  psmt.setInt(4,quantity);
	    	  psmt.setInt(5,quantity*price);
	    	  int rec = psmt.executeUpdate();
	    	  if(rec >0)
	    	 	  out.println("<center><i>Added to Cart Successfully!<a href='Products'> Continue Shopping</a></i><center>");
	    	  
	    	// request.getRequestDispatcher("Products").include(request, response);
	    	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		else
		{
			out.println("<center><i>Session expired! Please Login again</i></center>");
			request.getRequestDispatcher("index.html").include(request,response);
		}
			
		
		   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
