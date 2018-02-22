package chr19;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {
    private String greeting;

    public void init() {
        ServletConfig config = getServletConfig();
        greeting = config.getInitParameter("greeting");
        if (greeting == null) {
          greeting = "Hello World!";
        }
    }

    public void doGet(HttpServletRequest request, 
        HttpServletResponse response) 
        throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        out.println(greeting);
    }
    public void destroy() {
      greeting = null;
    }
}
