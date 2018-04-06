package chr06;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

public class ViewResourceCompressServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    OutputStream out = null;

    // Select the appropriate content encoding based on the
    // client's Accept-Encoding header. Choose GZIP if the header
    // includes "gzip". Choose ZIP if the header includes "compress".
    // Choose no compression otherwise. Make sure the Content-Encoding
    // uses the "x-" prefix if and only if the Accept-Encoding does.
    String encodings = req.getHeader("Accept-Encoding");
    if (encodings != null && encodings.indexOf("gzip") != -1) {
      // Go with GZIP
      if (encodings.indexOf("x-gzip") != -1) {
        res.setHeader("Content-Encoding", "x-gzip");
      }
      else {
        res.setHeader("Content-Encoding", "gzip");
      }
      out = new GZIPOutputStream(res.getOutputStream());
    }
    else if (encodings != null && encodings.indexOf("compress") != -1) {
      // Go with ZIP
      if (encodings.indexOf("x-compress") != -1) {
        res.setHeader("Content-Encoding", "x-compress");
      }
      else {
        res.setHeader("Content-Encoding", "compress");
      }
      out = new ZipOutputStream(res.getOutputStream());
      ((ZipOutputStream)out).putNextEntry(new ZipEntry("dummy name"));
    }
    else {
      // No compression
      out = res.getOutputStream();
    }
    res.setHeader("Vary", "Accept-Encoding");

    // Get the resource to view
    URL url = null;
    try {
      url = ServletUtils.getResource(getServletContext(), req.getPathInfo());
    }
    catch (IOException e) {
      res.sendError(
        res.SC_NOT_FOUND,
        "Extra path info must point to a valid resource to view: " +
        e.getMessage());
    }

    // Connect to the resource
    URLConnection con = url.openConnection();
    con.connect();

    // Get and set the type of the resource
    String contentType = con.getContentType();
    res.setContentType(contentType);

    // Return the resource
    try {
      ServletUtils.returnURL(url, out);
    }
    catch (IOException e) {
      res.sendError(res.SC_INTERNAL_SERVER_ERROR,
              "Problem sending resource: " + e.getMessage());
    }

    // Write the compression trailer and close the output stream
    out.close();
  }
}
