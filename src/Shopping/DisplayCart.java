package Shopping;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DisplayCart
 */
public class DisplayCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	String email;
	int product_price;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayCart() {
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
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		request.getRequestDispatcher("link.html").include(request, response);
		HttpSession sess_user = request.getSession(false);
		
		if(sess_user !=  null)
		{
				try
				{
				String user = (String)sess_user.getAttribute("name");
				out.println("<center><i><h3>"+user+" Your Cart details: </h2></i></center>");
				//retrieving username(emailid) by using session var;
				Statement stmt = con.createStatement();
				String q = "select email from register where name='"+user+"'";
				ResultSet rs = stmt.executeQuery(q);
				while(rs.next())
					 email = rs.getString("email");
				
				//display the cart details
				String query = "select * from cart where username=?";
				PreparedStatement psmt = con.prepareStatement(query);
				psmt.setString(1, email);
				ResultSet rs1 = psmt.executeQuery();
				
				 out.println("<p><h1><center>Cart Details</center></h1></p>");
		 		 out.println("<div><center><table border=1 width=60%>");
		 		 out.println("<th>Product Name</th><th>Price</th><th>Quantity</th><th>Total Price</th>");
		 		
				while(rs1.next())
				{
					// out.println("<tr><td>"+rs1.getInt("cid")+"</td>");
			 		 out.println("<tr><td>"+rs1.getString("product_name")+"</td>");
			 		 
			 		    //retrieve the price of the product
						String quer = "select price from Products where product_name=?";
						PreparedStatement psmt1 = con.prepareStatement(quer);
						psmt1.setString(1,rs1.getString("product_name") );
						ResultSet r = psmt1.executeQuery();
						while(r.next())
							product_price = r.getInt("price");
							
						
			 		 
			 		 out.println("<td align=right>"+product_price+"</td>");
			 		 out.println("<td align=right>"+rs1.getInt("qty")+"</td>");
			 		 out.println("<td align=right>"+rs1.getInt("price")+"</td>");
				}
				out.println("</table></center></div>");
				out.println("<center><a href='over'> Checkout </a><center>");
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
