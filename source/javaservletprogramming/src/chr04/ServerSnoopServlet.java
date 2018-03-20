package chr04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletContext;
                                                                     
public class ServerSnoopServlet extends GenericServlet {                    
                                                                     
  public void service(ServletRequest req, ServletResponse res)       
                             throws ServletException, IOException {  
    res.setContentType("text/plain");                                
    PrintWriter out = res.getWriter();                               

    ServletContext context = getServletContext();
    out.println("req.getServerName(): " + req.getServerName());      
    out.println("req.getServerPort(): " + req.getServerPort());      
    out.println("context.getServerInfo(): " + context.getServerInfo());
    out.println("getServerInfo() name: " +                           
                 getServerInfoName(context.getServerInfo()));
    out.println("getServerInfo() version: " +                        
                 getServerInfoVersion(context.getServerInfo()));
    out.println("context.getAttributeNames():");
    Enumeration enums = context.getAttributeNames();
    while (enums.hasMoreElements()) {
      String name = (String) enums.nextElement();
      out.println("  context.getAttribute(\"" + name + "\"): " +
                     context.getAttribute(name));
    }
  }

  private String getServerInfoName(String serverInfo) {
    int slash = serverInfo.indexOf('/');
    if (slash == -1) return serverInfo;
    else return serverInfo.substring(0, slash);
  }

  private String getServerInfoVersion(String serverInfo) {
    // Version info is everything between the slash and the space
    int slash = serverInfo.indexOf('/');
    if (slash == -1) return null;
    int space = serverInfo.indexOf(' ', slash);
    if (space == -1) space = serverInfo.length();
    return serverInfo.substring(slash + 1, space);
  }
}
