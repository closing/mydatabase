package mypack;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class Servlet2 extends GenericServlet {
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		
		int num1 = Integer.parseInt((String)request.getAttribute("num1"));
		int num2 = Integer.parseInt((String)request.getAttribute("num2"));
		int sum = num1 + num2;
		
		request.setAttribute("sum", sum);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/servlet3");
		
		dispatcher.forward(request, response);
	}
}
