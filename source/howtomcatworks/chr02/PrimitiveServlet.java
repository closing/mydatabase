
import javax.servlet.ServletException;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletConfig;

import java.lang.System;

import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet{

	public void init(ServletConfig config) throws ServletException {		
	}
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("Hello. Rose are red.");
		out.print("Violets are bule.");
	}
	public void destroy() {
	}

	public String getServletInfo() {
		return null;
	}
	
	public ServletConfig getServletConfig() {
		return null;
	}
}
