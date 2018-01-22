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

public class CounterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		Counter counter = (Counter)context.getAttribute("counter");
		if (counter == null) {
			counter = new Counter(1);
			context.setAttribute("counter", counter);
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>CounterServlet</title>");
		out.println("</head><body>");
		out.println("<h1>欢迎光临本站。您是第" + counter.getCount() + "位访问者。</h1>");
		out.println("</body></html>");
		counter.add();
		
		out.close();
	}
}
