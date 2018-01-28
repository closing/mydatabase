package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		String username = request.getParameter("username");
		if (username == null || username == "") {
			username = "anonymous";
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head><title>helloapp</title></head>");
		out.println("<body>");		
		out.println("<b>Hello, " + username + "</b>");
		out.println("</body></html>");
		out.close();
	}
}
