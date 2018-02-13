import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		if ("HEAD".equals(request.getMethod())) {
			return;
		}
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		out.println("<html>");
		out.println("<head><title>Hello ," + name +"</title></head>");
		out.println("<body>");
		out.println("Hello, " + name);
		out.println("</body></html>");
	}
	
	public String getServletInfo() {
		return "A servlet that knows the name of the person to whom it's saying hello";
	}
}
