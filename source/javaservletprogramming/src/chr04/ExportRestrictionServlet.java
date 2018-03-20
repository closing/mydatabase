package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExportRestrictionServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // ...Some introductory HTML...

    // Get the client's hostname
    String remoteHost = req.getRemoteHost();
    out.println("----------debug info----------");
    out.println("<br>");
    out.println(remoteHost);
    out.println("<br>");
    out.println("----------debug info----------");
    out.println("<br>");
    // See if the client is allowed
    if (! isHostAllowed(remoteHost)) {
      out.println("Access <BLINK>denied</BLINK>");
    }
    else {
      out.println("Access granted");
      // Display download links, etc...
    }
  }

  // Disallow hosts ending with .cu, .ir, .iq, .kp, .ly, .sy, and .sd.
  private boolean isHostAllowed(String host) {
    return (!host.endsWith(".cu") &&
            !host.endsWith(".ir") &&
            !host.endsWith(".iq") &&
            !host.endsWith(".kp") &&
            !host.endsWith(".ly") &&
            !host.endsWith(".sy") &&
            !host.endsWith(".sd"));
  }
}
