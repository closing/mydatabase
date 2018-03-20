package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PersonalizedWelcomeServlet extends HttpServlet {

  Hashtable accesses = new Hashtable();

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // ...Some introductory HTML...

    String remoteUser = req.getRemoteUser();
    // DEBUG
    out.println("---------debug info----------");
    out.print("<br>");
    out.println("Remote User:" + remoteUser);
    out.println("<br>");
    out.println("---------debug info----------");
    out.print("<br>");
    // DEBUG
    
    if (remoteUser == null) {
      out.println("Welcome!");
    }
    else {
      out.println("Welcome, " + remoteUser + "!");
      Date lastAccess = (Date) accesses.get(remoteUser);
      if (lastAccess == null) {
        out.println("This is your first visit!");
      }
      else {
        out.println("Your last visit was " + accesses.get(remoteUser));
      }

      if (remoteUser.equals("PROFESSOR FALKEN")) {
        out.println("Shall we play a game?");
      }

      accesses.put(remoteUser, new Date());
    }

    // ...Continue handling the request...
  }
}
