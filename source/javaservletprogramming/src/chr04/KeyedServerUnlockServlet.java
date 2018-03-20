package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public class KeyedServerUnlockServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    PrintWriter out = res.getWriter();

    // Get the host and port
    String host = req.getParameter("host");
    String port = req.getParameter("port");

    // If no host, use the current host
    if (host == null) {
      host = req.getServerName();
    }

    // Convert the port to an integer, if none use current port
    int numericPort;
    try {
      numericPort = Integer.parseInt(port);
    } catch (NumberFormatException e) {
      numericPort = req.getServerPort();
    }

    // Generate and print the key
    // Any KeyGenerationException is caught and displayed
    try {
      long key = generateKey(host, numericPort);
      out.println(host + ":" + numericPort + " has the key " + key);
    } catch (KeyGenerationException e) {
      out.println("Could not generate key: " + e.getMessage());
    }
  }

  private long generateKey(String host, int port) throws KeyGenerationException {

    byte hostIP[];
    try {
      hostIP = InetAddress.getByName(host).getAddress();

    } catch (UnknownHostException e) {
      throw new KeyGenerationException(e.getMessage());
    }

    // Get the 32-bit IP address
    long servercode = 0;
    for (int i = 0; i < 4; i++) {
      servercode <<= 8;
      servercode |= hostIP[i];
    }

    // Concatentate the 32-bit port number
    servercode <<= 32;
    servercode |= port;

    // The key is the logical not
    return ~servercode;
  }
}

class KeyGenerationException extends Exception {
  private static final long serialVersionUID = -1L;

  public KeyGenerationException() {
    super();
  }

  public KeyGenerationException(String msg) {
    super(msg);
  }
}
