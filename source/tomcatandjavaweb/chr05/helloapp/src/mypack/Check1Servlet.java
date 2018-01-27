package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class Check1Servlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String message = null;
		if (username == null) {
			message = "Please input username.";
		} else {
			message = "Hello, " + username;
		}
		request.setAttribute("msg", message);
		
		out.println("Output from Check1Servlet before redirecting.");
		System.out.println("Output from Check1Servlet before redirecting.");
		
		response.sendRedirect("/helloapp/output1?msg=" + message);
		out.println("Output from Check1Servlet after redirecting.");
		System.out.println("Output from Check1Servlet after redirecting.");
	}
}
