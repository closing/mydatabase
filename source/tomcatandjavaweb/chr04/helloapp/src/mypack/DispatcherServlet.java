package mypack;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

public class DispatcherServlet extends GenericServlet {
	private String target = "/hello.jsp";
	
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		request.setAttribute("USER", username);
		request.setAttribute("PASSWORD", password);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
	}
}
