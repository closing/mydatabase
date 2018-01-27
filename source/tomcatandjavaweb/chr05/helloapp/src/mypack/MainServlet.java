package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class MainServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>MainServlet</title></head>");
		out.println("<body>");
		ServletContext context = getServletContext();
		RequestDispatcher headDispatcher = context.getRequestDispatcher("/header.htm");
		RequestDispatcher greetDispatcher = context.getRequestDispatcher("/greet");
		RequestDispatcher footDispatcher = context.getRequestDispatcher("/footer.htm");
		
		headDispatcher.include(request, response);
		greetDispatcher.include(request, response);
		footDispatcher.include(request, response);
		out.println("</body></html>");
		
		out.close();
	}
}
