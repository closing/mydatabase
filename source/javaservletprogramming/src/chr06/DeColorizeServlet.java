package chr06;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

import Acme.JPM.Encoders.*;

public class DeColorizeServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("image/gif");
    ServletOutputStream out = res.getOutputStream();

    // Get the image location from the path info
    URL source = ServletUtils.getResource(getServletContext(),
                                          req.getPathInfo());
    if (source == null) {
      res.sendError(res.SC_NOT_FOUND,
              "Extra path information must point to an image");
      return;
    }

    // Construct an unshown frame
    // No addNotify() because its peer isn't needed
    Frame frame = new Frame();

    // Load the image
    Image image = Toolkit.getDefaultToolkit().getImage(source);
    MediaTracker mt = new MediaTracker(frame);
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

    // Get the size of the image
    int width = image.getWidth(frame);
    int height = image.getHeight(frame);

    // Make sure we are reading valid image data
    if (width <= 0 || height <= 0) {
      res.sendError(res.SC_NOT_FOUND,
              "Extra path information must point to a valid image");
      return;
    }

    // Create an image to match, run through a filter
    Image filtered = frame.createImage(
      new FilteredImageSource(image.getSource(),
                              new GrayscaleImageFilter()));

    // Encode and return the filtered image
    GifEncoder encoder = new GifEncoder(filtered, out);
    encoder.encode();
  }
}
