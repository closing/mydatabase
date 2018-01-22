package mypack;

import java.util.Enumeration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

public class RequestInfoServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>RequestInfo</title></head>");
    out.println("<body>");
    out.println("<br>LocalAddr: " + request.getLocalAddr());
    out.println("<br>LocalName: " + request.getLocalName());
    out.println("<br>LocalPort: " + request.getLocalPort());
    out.println("<br>Protocol: " + request.getProtocol());
    out.println("<br>RemoteAddr: " + request.getRemoteAddr());
    out.println("<br>RemoteHost: " + request.getRemoteHost());
    out.println("<br>RemotePost: " + request.getRemotePort());
    out.println("<br>Method: " + request.getMethod());
    out.println("<br>URI: " + request.getRequestURI());
    out.println("<br>ContextPath: " + request.getContextPath());
    out.println("<br>QueryString: " + request.getQueryString());
    out.println("<br>***Print the Http Request Header***");
    Enumeration eu = request.getHeaderNames();
    while(eu.hasMoreElements()) {
		String headerName = (String)eu.nextElement();
		out.println("<br>" + headerName + ": " + request.getHeader(headerName));
    }
    out.println("<br>***Print the Http Request Header END***<br>");
    out.println("<br>username: " + request.getParameter("username"));
    out.println("</body></html>");
    
    out.close();
  }
}
