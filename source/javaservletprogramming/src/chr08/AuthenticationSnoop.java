package chr08;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationSnoopServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    out.println("<HTML><HEAD><TITLE>AuthenticationSnoop</TITLE></HEAD><BODY>");

    out.println("<H1>This is a password protected resource</H1>");
    out.println("<PRE>");
    out.println("User Name: " + req.getRemoteUser());
    String name = (req.getUserPrincipal() == null) ? null : req.getUserPrincipal().getName();
    out.println("Principal Name: " + name);
    out.println("Authentication Type: " + req.getAuthType());
    out.println("Is a admin: " + req.isUserInRole("admin"));
    out.println("</PRE>");
    out.println("</BODY></HTML>");
  }
}
