package chr19;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloYouServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, 
        HttpServletResponse response) 
        throws ServletException, IOException {

        String name = request.getParameter("name");
        if (name == null) {
            name =  "you";
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Hello " + name + "</h1>");

        out.println("I see that:<ul>");
        String userAgent = request.getHeader("User-Agent");
        out.println("<li>your browser is: " + userAgent);
        String requestURI = request.getRequestURI();
        out.println("<li>the URI for this page is: " +
            requestURI);
        String contextPath = request.getContextPath();
        out.println("<li>the context path for this app is" +
            contextPath);
        String servletPath = request.getServletPath();
        out.println("<li>this servlet is mapped to: " + 
            servletPath);
        String pathInfo = request.getPathInfo();
        out.println("<li>the remaining path is: " + pathInfo);
        Map parameters = request.getParameterMap();
        out.println("<li>you sent the following params:<ul>");
        Iterator i = parameters.keySet().iterator();
        while (i.hasNext()) {
            String paramName = (String) i.next();
            out.println("<li><b>" + paramName + "</b>:");
            String[] paramValues = 
                (String[]) parameters.get(paramName);
            for (int j = 0; j < paramValues.length; j++) {
                if (j != 0) {
                    out.print(", ");
                }
                out.print(paramValues[j]);
            }
        }
        out.println("</ul></ul></body></html>");
    }
}
