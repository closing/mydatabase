package mypack;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class CrossServlet extends GenericServlet {
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
		ServletContext crossContext = context.getContext("/helloapp1");
		RequestDispatcher dispatcher = crossContext.getRequestDispatcher("/output");
		
		PrintWriter out = response.getWriter();
		
		dispatcher.forward(request, response);
	}
}
