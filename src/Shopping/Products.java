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
 * Servlet implementation class Products
 */
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	Connection con;
    public Products() {
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

		HttpSession sess = request.getSession(false);
		
		String session_user;
		if(sess != null)
		{
	     session_user = (String) sess.getAttribute("name");
		
		RequestDispatcher rd = request.getRequestDispatcher("link.html");
		   rd.include(request, response);
		   out.println("<div align=left ><h2><i>Welcome "+session_user+"</i></h2></div>");
		try
        {
			//Statement stmt = con.createStatement();	
			
			
		         
			  String query =  "select product_name from Products";
			  PreparedStatement prepareSQL = con.prepareStatement(query);
			     
			     ResultSet rs = prepareSQL.executeQuery();
			    	 out.println("<html><title>Products</title><body>");
			    	 out.println("<form name='products' action='Products'>");
			    	 out.println("<center><p><h2>Select a Product</h2>");
			    	 out.println("<select name='products' id='products'>");
			    	 out.println("<option value='select'>Select </option>");
			    
			    	 while(rs.next())
				     {
				     	 out.println("<option value='"+rs.getString("product_name")+"'>"+rs.getString("product_name")+"</option>");
				     }
			    	 out.println("</select></p>");
			    	 out.println("<center><p><h2>Select Quantity</h2>");
			    	 out.println("<select name='qty'><option value=1 selected>1</option><option value=2>2</option>" +
			    	 		"<option value=3>3</option><option value=4>4</option><option value=5>5</option></select></p></center>");
			    	 out.println("&nbsp;&nbsp;&nbsp;&nbsp;<input type='submit' value='Show Details'>");
			    	 out.println("</center></form>");
			    	 out.println("</body></html>");
			    
			    	 String selecteditem = request.getParameter("products");
			    	 String qty = request.getParameter("qty");
			 		//out.println("your selected item is "+selecteditem);
			    	 if(!selecteditem.equals("select") || !selecteditem.equals(""))
			    	 {
				    	 Statement stmt1 = con.createStatement();
					     String query1 =  "select * from Products where product_name='"+selecteditem+"'";
					     ResultSet rs1 = stmt1.executeQuery(query1);
				 		//show Selected item in tabular format
				 		 out.println("<p><h1><center>Product Description</center></h1></p>");
				 		 out.println("<div><center><table border=1 width=60%>");
				 		 out.println("<tr><th>Product Name</th><th>Model</th><th>Description</th><th>Price</th>");
				 		 String pro=null;
				 		 while(rs1.next())
				 		 {
				 			 pro = rs1.getString("product_name");
					 		 out.println("<tr><td>"+pro+"</td>");
					 		 out.println("<td>"+rs1.getString("model")+"</td>");
					 		 out.println("<td>"+rs1.getString("description")+"</td>");
					 		 out.println("<td>"+rs1.getString("price")+"</td>");
					 		
				 		 }
				 		 
				 	
				 		out.println("</table></center></div>");
				 		out.println("<center><a href='Cart?product="+pro+"&qty="+qty+"'>Add to Cart</a></center");
			    	 }
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
