package mypack;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;

import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionTesterServlet extends GenericServlet {
	public void service(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		String condition = request.getParameter("condition");
		
		if (condition == null) {
			condition = "ok";
		}
		
		if (condition.equals("1")) {
			throw new ServletException("condition1");
		}
		else if (condition.equals("2")) {
			throw new UnavailableException("condition2", 2);
		}
		else if (condition.equals("3")) {
			throw new UnavailableException("conditon3");
		}
		PrintWriter out = response.getWriter();
		out.println("It's ok.");
		out.close();
	}
}
