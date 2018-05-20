package common.servlet;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public abstract class DaemonHttpServlet extends HttpServlet {

  protected int DEFAULT_PORT = 1313;
  private Thread daemonThread;

  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    try {
      daemonThread = new Daemon(this);
      daemonThread.start();
    } catch (Exception e) {
      log("Problem starting socket server daemon thread" + e.getClass().getName() + ": " + e.getMessage());
    }
  }

  protected int getSocketPort() {
    try {
      return Integer.parseInt(getInitParameter("socketPort"));
    } catch (NumberFormatException e) {
      return DEFAULT_PORT;
    }
  }

  abstract public void handleClient(Socket client);

  public void destroy() {
    try {
      daemonThread.stop();
      daemonThread = null;
    } catch (Exception e) {
      log("Problem stopping server socket daemon thread: " + e.getClass().getName() + ": " + e.getMessage());
    }
  }
}

class Daemon extends Thread {

  private ServerSocket serverSocket;
  private DaemonHttpServlet servlet;

  public Daemon(DaemonHttpServlet servlet) {
    this.servlet = servlet;
  }

  public void run() {
    try {
      serverSocket = new ServerSocket(servlet.getSocketPort());
    } catch (Exception e) {
      servlet.log("Problem establishing server socket: " + e.getClass().getName() + ": " + e.getMessage());
      return;
    }

    try {
      while (true) {
        try {
          servlet.handleClient(serverSocket.accept());
        } catch (IOException ioe) {
          servlet.log(
              "Problem accepting client's socket connection: " + ioe.getClass().getName() + ": " + ioe.getMessage());
        }
      }
    } catch (ThreadDeath e) {
      // When the thread is killed, close the server socket
      try {
        serverSocket.close();
      } catch (IOException ioe) {
        servlet.log("Problem closing server socket: " + ioe.getClass().getName() + ": " + ioe.getMessage());
      }
    }
  }
}
