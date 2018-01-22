package mypack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextTesterServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    ServletContext context = getServletContext();
        
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>ContextTesterServlet</title></head>");
    out.println("<body>");
    out.println("<br>Email: " + context.getInitParameter("emailofwebmaster"));
    out.println("<br>Path: " + context.getRealPath("/WEB-INF"));
    out.println("<br>MimeType: " + context.getMimeType("/WEB-INF/web.xml"));
    out.println("<br>MajorVersion: " + context.getMajorVersion());
    out.println("<br>ServerInfo: " + context.getServerInfo());
    out.println("</body></html>");
    
    System.out.println("before close(): " + response.isCommitted());
    out.close();
    System.out.println("before close(): " + response.isCommitted());
  }
}
