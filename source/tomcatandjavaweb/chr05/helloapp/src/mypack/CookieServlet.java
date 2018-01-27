package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

public class CookieServlet extends HttpServlet {
	int count=0;
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i=0; i<cookies.length; i++) {
				out.println("Cookie name:" + cookies[i].getName());
				out.println("Cookie value:" + cookies[i].getValue());
				out.println("Max age:" + cookies[i].getMaxAge() + "\r\n");
			}
		} else {
			out.println("No cookie");
		}
		response.addCookie(new Cookie("cookiename"+count, "cookievalue" + count));
		count++;
	}
}
