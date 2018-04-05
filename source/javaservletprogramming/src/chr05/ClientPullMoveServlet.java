package chr05;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientPullMoveServlet extends HttpServlet {

  static final String NEW_HOST = "http://www.oreilly.com";

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    String newLocation = NEW_HOST + req.getRequestURI();

    res.setHeader("Refresh", "10; URL=" + newLocation);

    out.println("The requested URI has been moved to a different host.<BR>");
    out.println("Its new location is " + newLocation + "<BR>");
    out.println("Your browser will take you there in 10 seconds.");
  }
}
