package chr08;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerServlet extends HttpServlet {

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // Get the user's account number, password, and pin
    String account = req.getParameter("account");
    String password = req.getParameter("password");
    String pin = req.getParameter("pin");

    // Check the name and password for validity
    if (!allowUser(account, password, pin)) {
      out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
      out.println("<BODY>Your login and password are invalid.<BR>");
      out.println("You may want to <A HREF=\"/login.html\">try again</A>");
      out.println("</BODY></HTML>");
    } else {
      // Valid login. Make a note in the session object.
      HttpSession session = req.getSession();
      session.setAttribute("logon.isDone", account); // just a marker object

      // Try redirecting the client to the page he first tried to access
      try {
        String target = (String) session.getAttribute("login.target");
        if (target != null) {
          res.sendRedirect(target);
          return;
        }
      } catch (Exception ignored) {
      }

      // Couldn't redirect to the target. Redirect to the site's home page.
      res.sendRedirect("/");
    }
  }

  protected boolean allowUser(String account, String password, String pin) {
    return true; // trust everyone
  }
}
