package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.io.PrintWriter;

public class Output1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		String message =(String) request.getAttribute("msg");
		System.out.println("The message of request scope:" + message);
		message = request.getParameter("msg");
		System.out.println("The message of request parameter:" + message);
		PrintWriter out = response.getWriter();
		out.println(message);
		out.close();
	}
}
