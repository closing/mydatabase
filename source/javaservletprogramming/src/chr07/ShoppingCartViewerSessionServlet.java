package chr07;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShoppingCartViewerSessionServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // Get the current session object, create one if necessary.
    HttpSession session = req.getSession();

    // Cart items are maintained in the session object.
    String[] items = (String[])session.getAttribute("cart.items");

    out.println("<HTML><HEAD><TITLE>SessionTracker</TITLE></HEAD>");
    out.println("<BODY><H1>Session Tracking Demo</H1>");

    // Print the current cart items.
    out.println("You currently have the following items in your cart:<BR>");
    if (items == null) {
      out.println("<B>None</B>");
    }
    else {
      out.println("<UL>");
      for (int i = 0; i < items.length; i++) {
        out.println("<LI>" + items[i]);
      }
      out.println("</UL>");
    }

    // Ask if they want to add more items or check out.
    out.println("<FORM ACTION=\"" +
                res.encodeURL("/servlet/ShoppingCart") + "\" METHOD=POST>");
    out.println("Would you like to<BR>");
    out.println("<INPUT TYPE=SUBMIT VALUE=\" Add More Items \">");
    out.println("<INPUT TYPE=SUBMIT VALUE=\" Check Out \">");
    out.println("</FORM>");

    // Offer a help page. Encode it as necessary.
    out.println("For help, click <A HREF=\"" +
                res.encodeURL("/servlet/Help?topic=ShoppingCartViewer") +
                "\">here</A>");

    out.println("</BODY></HTML>");
  }
}
