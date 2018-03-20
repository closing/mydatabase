package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class InitSnoopServlet extends GenericServlet {

  // No init() method needed

  public void service(ServletRequest req, ServletResponse res)
                             throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    out.println("Init Parameters:");
    Enumeration enums = getInitParameterNames();
    while (enums.hasMoreElements()) {
      String name = (String) enums.nextElement();
      out.println(name + ": " + getInitParameter(name));
    }
  }
}
