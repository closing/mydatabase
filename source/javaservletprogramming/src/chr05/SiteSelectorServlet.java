package chr05;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SiteSelectorServlet extends HttpServlet {

  Vector sites = new Vector();
  Random random = new Random();

  public void init() throws ServletException {
    sites.addElement("http://www.oreilly.com/catalog/jservlet");
    sites.addElement("http://www.servlets.com");
    sites.addElement("http://java.sun.com/products/servlet");
    sites.addElement("http://www.newInstance.com");
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    int siteIndex = Math.abs(random.nextInt()) % sites.size();
    String site = (String)sites.elementAt(siteIndex);

    res.setStatus(res.SC_MOVED_TEMPORARILY);
    res.setHeader("Location", site);
  }
}
