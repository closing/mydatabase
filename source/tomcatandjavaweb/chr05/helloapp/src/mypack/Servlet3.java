package mypack;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class Servlet3 extends GenericServlet {
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		
		int sum = (int)request.getAttribute("sum");
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("The sum is :" + sum);
		
		out.close();
	}
}
