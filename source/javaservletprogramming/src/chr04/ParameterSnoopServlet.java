package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParameterSnoopServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    out.println("Query String:");
    out.println(req.getQueryString());
    out.println();

    out.println("Request Parameters:");
    Enumeration enums = req.getParameterNames();
    while (enums.hasMoreElements()) {
      String name = (String) enums.nextElement();
      String values[] = req.getParameterValues(name);
      if (values != null) {
        for (int i = 0; i < values.length; i++) {
          out.println(name + " (" + i + "): " + values[i]);
        }
      }
    }
  }
} 
