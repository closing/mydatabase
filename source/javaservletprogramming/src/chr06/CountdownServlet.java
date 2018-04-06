package chr06;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartResponse;
import com.oreilly.servlet.ServletUtils;

public class CountdownServlet extends HttpServlet {

  static final String LAUNCH = "/images/launch.gif";

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    ServletOutputStream out = res.getOutputStream();  // some binary output

    // Prepare a multipart response
    MultipartResponse multi = new MultipartResponse(res);

    // First send a countdown
    for (int i = 10; i > 0; i--) {
      multi.startResponse("text/plain");
      out.println(i + "...");
      multi.endResponse();
      try { Thread.sleep(1000); } catch (InterruptedException e) { }
    }

    // Then send the launch image
    multi.startResponse("image/gif");
    try {
      ServletUtils.returnFile(req.getRealPath(LAUNCH), out);
    }
    catch (FileNotFoundException e) {
      throw new ServletException("Could not find file: " + e.getMessage());
    }

    // Don't forget to end the multipart response
    multi.finish();
  }
}
