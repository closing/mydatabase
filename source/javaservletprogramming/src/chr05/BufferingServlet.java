package chr05;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BufferingServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setBufferSize(8 * 1024); // 8K buffer
    res.setContentType("text/html;charset=utf-8");
    PrintWriter out = res.getWriter();

    int size = res.getBufferSize(); // returns 8096 or greater

    // Record the default size, in the log
    log("The default buffer size is " + size);

    out.println("The client won't see this");
    res.reset();
    out.println("Nor will the client see this!");
    res.reset();
    out.println("And this won't be seen if sendError() is called");
    if (req.getParameter("important_parameter") == null) {
      res.sendError(res.SC_BAD_REQUEST, "important_parameter needed");
    }
  }
}
