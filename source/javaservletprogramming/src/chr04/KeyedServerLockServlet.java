package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.net.InetAddress;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;

public class KeyedServerLockServlet extends GenericServlet {

  // for-debug start
  private String strIp = "";
  private String strKeyCode = "";
  private String strServerCode = "";
  private String strOkKey = "";
  // for-debug end

  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // The piracy check shouldn't be done in init
    // because name/port are part of request.
    String key = getInitParameter("key");
    String host = req.getServerName();
    int port = req.getServerPort();

    // Check if the init parameter "key" unlocks this server.
    if (!keyFitsServer(key, host, port)) {
      // Explain, condemn, threaten, etc.
      out.println("Pirated!");
    } else {
      // Give 'em the goods
      out.println("Valid");
      // etc...
    }
    out.println("----------debug info----------");
    out.println("host:" + host);
    out.println("ip:" + strIp);
    out.println("port:" + port);
    out.println("servercode:" + strServerCode);
    out.println("key:" + key);
    out.println("OK KEY:" + strOkKey);
    out.println("accesscode:" + strKeyCode);
    out.println("----------debug info----------");
  }

  private boolean keyFitsServer(String key, String host, int port) {

    if (key == null)
      return false;

    long numericKey = 0;
    try {
      numericKey = Long.parseLong(key);
    } catch (NumberFormatException e) {
      return false;
    }

    byte hostIP[];
    try {
      hostIP = InetAddress.getByName(host).getAddress();
      strIp = InetAddress.getByName(host).getHostAddress();
    } catch (UnknownHostException e) {
      return false;
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
    strServerCode = String.valueOf(servercode);
    // Logical not
    long accesscode = ~numericKey;
    strOkKey = String.valueOf(~Long.valueOf(strServerCode));
    strKeyCode = String.valueOf(strOkKey);

    // The moment of truth: Does the key match?
    return (servercode == accesscode);
  }
}
