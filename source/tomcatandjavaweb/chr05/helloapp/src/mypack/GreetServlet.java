package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class GreetServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("Hi, " + request.getParameter("username") + "<p>");
	}
}
