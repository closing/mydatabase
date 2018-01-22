package mypack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FontServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String word = request.getParameter("word");
    
    if (word == null) {
      word = "Hello";
    }
    String color = getInitParameter("color");
    String size = getInitParameter("size");
    
    System.out.println("servletName: " + getServletName());
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html><head><title>FontServlet</title></head>");
    out.println("<body>");
    
    out.println("<font size='" + size + "' color='" + color + "'>" + word + "</font>");
    
    out.println("</body></html>");
    
    out.close();
  }
}
