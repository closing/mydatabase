package mypack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String userName = request.getParameter("username");
    if (userName != null) {
      userName = new String(userName.getBytes("UTF-8"), "UTF-8");
    }
    if (userName == null) {
      response.sendError(response.SC_FORBIDDEN);
      return;
    }
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>HelloServlet</title></head>");
    out.println("<body>");
    out.println("你好: " + userName);
    out.println("</body></html>");
    
    System.out.println("before close(): " + response.isCommitted());
    out.close();
    System.out.println("before close(): " + response.isCommitted());
  }
}
