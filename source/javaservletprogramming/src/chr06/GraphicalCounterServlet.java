package chr06;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

import Acme.JPM.Encoders.GifEncoder;

public class GraphicalCounter extends HttpServlet {

  public static final String DIR = "/images/odometer";
  public static final String COUNT = "314159";

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    ServletOutputStream out = res.getOutputStream();

    Frame frame = null;
    Graphics g = null;

    try {
      // Get the count to display, must be sole value in the raw query string
      // Or use the default
      String count = (String)req.getQueryString();
      if (count == null) count = COUNT;

      int countlen = count.length();
      Image images[] = new Image[countlen];

      for (int i = 0; i < countlen; i++) {
        URL imageSrc = 
          getServletContext().getResource(DIR + "/" + count.charAt(i) + ".GIF");
        if (imageSrc == null) {
          imageSrc = new URL("file:");  // placeholder, handle errors later
        }
        images[i] = Toolkit.getDefaultToolkit().getImage(imageSrc);
      }

      // Create an unshown Frame
      frame = new Frame();
      frame.addNotify();

      // Load the images
      MediaTracker mt = new MediaTracker(frame);
      for (int i = 0; i < countlen; i++) {
        mt.addImage(images[i], i);
      }
      try {
        mt.waitForAll();
      }
      catch (InterruptedException e) {
        res.sendError(res.SC_INTERNAL_SERVER_ERROR,
                "Interrupted while loading image: " +
                ServletUtils.getStackTraceAsString(e));
        return;
      }

      // Check for problems loading the images
      if (mt.isErrorAny()) {
        // We had a problem, find which image(s)
        StringBuffer problemChars = new StringBuffer();
        for (int i = 0; i < countlen; i++) {
          if (mt.isErrorID(i)) {
            problemChars.append(count.charAt(i));
          }
        }
        res.sendError(res.SC_INTERNAL_SERVER_ERROR,
                "Could not load an image for these characters: " +
                problemChars.toString());
        return;
      }

      // Get the cumulative size of the images
      int width = 0;
      int height = 0;
      for (int i = 0; i < countlen; i++) {
        width += images[i].getWidth(frame);
        height = Math.max(height, images[i].getHeight(frame));
      }

      // Get a graphics region to match, using the Frame
      Image image = frame.createImage(width, height);
      g = image.getGraphics();

      // Draw the images
      int xindex = 0;
      for (int i = 0; i < countlen; i++) {
        g.drawImage(images[i], xindex, 0, frame);
        xindex += images[i].getWidth(frame);
      }

      // Encode and return the composite
      res.setContentType("image/gif");
      GifEncoder encoder = new GifEncoder(image, out);
      encoder.encode();
    }
    finally {
      // Clean up resources
      if (g != null) g.dispose();
      if (frame != null) frame.removeNotify();
    }
  }
}
