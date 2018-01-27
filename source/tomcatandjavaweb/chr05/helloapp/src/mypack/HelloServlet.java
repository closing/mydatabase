package mypack;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends GenericServlet {

	private String username = null;
	
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		
		username = request.getParameter("username");
		String message = null;
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head><title>HelloServlet</title></head>");
		out.println("<body>");
		
		out.println("你好： " + username);
		out.println("</body></html>");
		out.close();
	}
}
