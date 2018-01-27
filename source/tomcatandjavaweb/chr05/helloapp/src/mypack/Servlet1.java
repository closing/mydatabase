package mypack;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class Servlet1 extends GenericServlet {
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		if ((num1 == null || num1 == "") || (num2 == null || num2 == "") || 
			!(num1.matches("^[0-9]*$")) || 
			!(num2.matches("^[0-9]*$"))) {
			
			String message = "Please input number.";
			out.println(message);
			out.close();
		} else {
			request.setAttribute("num1", num1);
			request.setAttribute("num2", num2);
			ServletContext context = getServletContext();
			RequestDispatcher dispatcher = context.getRequestDispatcher("/servlet2");
			
			dispatcher.forward(request, response);
			
		}
	}
}
