package mypack;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class CheckServlet extends GenericServlet {
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String message = null;
		if (username == null) {
			message = "Please input username.";
		} else {
			message = "Hello, " + username;
		}
		request.setAttribute("msg", message);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/output");
		
		PrintWriter out = response.getWriter();
		out.println("Output from CheckServlet before forwarding request.");
		System.out.println("Output from CheckServlet before forwarding request.");
		
		dispatcher.forward(request, response);
		out.println("Output from CheckServlet after forwarding request.");
		System.out.println("Output from CheckServlet after forwarding request.");
	}
}
