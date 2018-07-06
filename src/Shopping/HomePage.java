package Shopping;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomePage
 */
public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePage() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
		   response.setContentType("text/html");
		  // System.out.println("hello");
		   PrintWriter out = response.getWriter();
		   RequestDispatcher rd = request.getRequestDispatcher("link.html");
		   rd.include(request, response);
		  
		   HttpSession session=request.getSession(false);
		   if(session != null)
		   {
	        String n=(String)session.getAttribute("name");
	        out.println("<html><body><center><h1><i>Welcome "+n);
	        out.println("<p>Go to Products and enjoy shopping</p></h1><center></i></body></html>");
		   } 
		   else
		   {
			   out.println("<html><body><i>Session expired! Please Login again</i></body></html>");
			   RequestDispatcher rd1= request.getRequestDispatcher("index.html");
			   rd1.include(request, response);
			  
		   }
	  
		
		
	}

}
