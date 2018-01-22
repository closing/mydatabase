package mypack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

public class CounterClearServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		context.removeAttribute("counter");
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>CounterServlet</title>");
		out.println("</head><body>");
		out.println("<h1>The counter is removed.</h1>");
		out.println("</body></html>");

		out.close();
	}
}
