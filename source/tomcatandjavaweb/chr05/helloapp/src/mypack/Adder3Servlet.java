package mypack;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.SingleThreadModel;


import java.io.IOException;
import java.io.PrintWriter;

public class Adder3Servlet extends GenericServlet implements SingleThreadModel {

	private int sum = 100;
	
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		
		int increase = Integer.parseInt(request.getParameter("increase"));
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head><title>HelloServlet</title></head>");
		out.println("<body>");
		
		out.println(sum + "+" + increase + "=");
		try {
			Thread.sleep(3000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		sum += increase;
		out.println(sum);
		
		out.println("</body></html>");
		out.close();
	}
}
