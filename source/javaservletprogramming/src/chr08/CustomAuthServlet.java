package chr08;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.servlet.Base64Decoder;

public class CustomAuthServlet extends HttpServlet {

  Hashtable users = new Hashtable();

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    // Names and passwords are case sensitive!
    users.put("Wallace:cheese",     "allowed");
    users.put("Gromit:sheepnapper", "allowed");
    users.put("Penguin:evil",       "allowed");
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // Get Authorization header
    String auth = req.getHeader("Authorization");

    // Do we allow that user?
    if (!allowUser(auth)) {
      // Not allowed, so report he's unauthorized
      res.setHeader("WWW-Authenticate", "BASIC realm=\"users\"");
      res.sendError(res.SC_UNAUTHORIZED);
      // Could offer to add him to the allowed user list
    }
    else {
      // Allowed, so show him the secret stuff
    	out.println(auth);
    	out.println(Base64Decoder.decode(auth.substring(6)));
      out.println("Top-secret stuff");
    }
  }

  // This method checks the user information sent in the Authorization
  // header against the database of users maintained in the users Hashtable.
  protected boolean allowUser(String auth) throws IOException {
    if (auth == null) return false;  // no auth

    if (!auth.toUpperCase().startsWith("BASIC "))
      return false;  // we only do BASIC

    // Get encoded user and password, comes after "BASIC "
    String userpassEncoded = auth.substring(6);

    // Decode it, using any base 64 decoder (we use com.oreilly.servlet)
    String userpassDecoded = Base64Decoder.decode(userpassEncoded);

    // Check our user list to see if that user and password are "allowed"
    if ("allowed".equals(users.get(userpassDecoded)))
      return true;
    else
      return false;
  }
}
