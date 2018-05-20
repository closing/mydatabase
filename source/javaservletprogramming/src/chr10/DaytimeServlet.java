package chr10;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import common.servlet.RemoteDaemonHttpServlet;

public class DaytimeServlet extends RemoteDaemonHttpServlet
                            implements DaytimeServer {

  // The single method from DaytimeServer
  public Date getDate() {
    return new Date();
  }

  public void init(ServletConfig config) throws ServletException {
    // As before, if you override init() you have to call super.init()
    super.init(config);
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    if ("object".equals(req.getParameter("format"))) {
      ObjectOutputStream out = new ObjectOutputStream(res.getOutputStream());
      out.writeObject(getDate());
    }
    // Otherwise send the Date as a normal ASCII string
    else {
      PrintWriter out = res.getWriter();
      out.println(getDate().toString());
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    doGet(req, res);
  }

  public void destroy() {
    // If you override destroy() you also have to call super.destroy()
    super.destroy();
  }

  // Handle a client's socket connection by spawning a DaytimeConnection
  // thread.
  public void handleClient(Socket client) {
    new DaytimeConnection(this, client).start();
  }
}

class DaytimeConnection extends Thread {

  DaytimeServlet servlet;
  Socket client;

  DaytimeConnection(DaytimeServlet servlet, Socket client) {
    this.servlet = servlet;
    this.client = client;
    setPriority(NORM_PRIORITY - 1);
  }

  public void run() {
    try {
      // Read the first line sent by the client
      DataInputStream in = new DataInputStream(
                           new BufferedInputStream(
                           client.getInputStream()));
      String line = in.readLine();

      // If it was "object" then return the Date as a serialized object
      if ("object".equals(line)) {
        ObjectOutputStream out =
          new ObjectOutputStream(client.getOutputStream());
        out.writeObject(servlet.getDate());
        out.close();
      }
      // Otherwise, send the Date as a normal string
      else {
        // Wrap a PrintStream around the Socket's OutputStream
        PrintStream out = new PrintStream(client.getOutputStream());
        out.println(servlet.getDate().toString());
        out.close();
      }

      // Be sure to close the connection
      client.close();
    }
    catch (IOException e) {
      servlet.getServletContext()
        .log(e, "IOException while handling client request");
    }
    catch (Exception e) {
      servlet.getServletContext()
        .log("Exception while handling client request");
    }
  }
}

