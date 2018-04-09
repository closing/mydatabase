package chr07;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class SessionBindingsServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // Get the current session object, create one if necessary
    HttpSession session = req.getSession();

    // Add a CustomBindingListener
    session.setAttribute("bindings.listener",
                         new CustomBindingListener(getServletContext()));

    out.println("This page intentionally left blank");
  }
}

class CustomBindingListener implements HttpSessionBindingListener {

  // Save a ServletContext to be used for its log() method
  ServletContext context;

  public CustomBindingListener(ServletContext context) {
    this.context = context;
  }

  public void valueBound(HttpSessionBindingEvent event) {
    context.log("[" + new Date() + "] BOUND as " + event.getName() +
                " to " + event.getSession().getId());
  }

  public void valueUnbound(HttpSessionBindingEvent event) {
    context.log("[" + new Date() + "] UNBOUND as " + event.getName() +
                " from " + event.getSession().getId());
  }
}
