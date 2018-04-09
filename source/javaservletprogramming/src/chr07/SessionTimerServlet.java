package chr07;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTimerServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // Get the current session object, create one if necessary
    HttpSession session = req.getSession();

    out.println("<HTML><HEAD><TITLE>SessionTimer</TITLE></HEAD>");
    out.println("<BODY><H1>Session Timer</H1>");

    // Display the previous timeout
    out.println("The previous timeout was " +
                session.getMaxInactiveInterval());
    out.println("<BR>");

    // Set the new timeout
    session.setMaxInactiveInterval(2*60*60);  // two hours

    // Display the new timeout
    out.println("The newly assigned timeout is " +
                session.getMaxInactiveInterval());

    out.println("</BODY></HTML>");
  }
}
