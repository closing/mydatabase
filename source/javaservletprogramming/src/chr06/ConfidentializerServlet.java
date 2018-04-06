package chr06;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

import Acme.JPM.Encoders.GifEncoder;

public class Confidentializer extends HttpServlet {

  Frame frame = null;

  public void init() throws ServletException {
    // Construct a reusable unshown frame
    frame = new Frame();
    frame.addNotify();
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    ServletOutputStream out = res.getOutputStream();
    Graphics g = null;

    try {
      // Get the image location from the path info
      // Use ServletUtils (Chapter 4) for safety
      URL source =
        ServletUtils.getResource(getServletContext(), req.getPathInfo());

      // Load the image (from bytes to an Image object)
      MediaTracker mt = new MediaTracker(frame);  // frame acts as ImageObserver
      Image image = Toolkit.getDefaultToolkit().getImage(source);
      mt.addImage(image, 0);
      try {
        mt.waitForAll();
      }
      catch (InterruptedException e) {
        res.sendError(res.SC_INTERNAL_SERVER_ERROR,
                "Interrupted while loading image: " +
                ServletUtils.getStackTraceAsString(e));
        return;
      }

      // Get the width and height
      int w = image.getWidth(frame);
      int h = image.getHeight(frame);

      // Make sure we are reading valid image data
      if (w <= 0 || h <= 0) {
        res.sendError(res.SC_NOT_FOUND,
                "Extra path information must point to a valid image");
        return;
      }

      // Construct a matching-size off screen graphics context
      Image offscreen = frame.createImage(w, h);
      g = offscreen.getGraphics();

      // Draw the image to the off-screen graphics context
      g.drawImage(image, 0, 0, frame);

      // Write CONFIDENTIAL over its top
      g.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 30));
      g.drawString("CONFIDENTIAL", 10, 30);

      // Encode the off-screen graphics into a GIF and send it to the client
      res.setContentType("image/gif");
      GifEncoder encoder = new GifEncoder(offscreen, out);
      encoder.encode();
    }
    finally {
      // Clean up resources
      if (g != null) g.dispose();
    }
  }

  public void destroy() {
    // Clean up resources
    if (frame != null) frame.removeNotify();
  }
}
