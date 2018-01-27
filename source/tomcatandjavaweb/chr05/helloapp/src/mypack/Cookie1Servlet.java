package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

public class Cookie1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		
		Cookie cookie = null;
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i=0; i<cookies.length; i++) {
				out.println("Cookie name:" + cookies[i].getName());
				out.println("Cookie value:" + cookies[i].getValue());
				if ("username".equals(cookies[i].getName())) {
					cookie = cookies[i];
				}
			}
		} else {
			out.println("No cookie");
		}
		
		if (cookie == null) {
			cookie = new Cookie("username", "Tom");
			cookie.setMaxAge(60*60);
		} else if ("Tom".equals(cookie.getValue())) {
			cookie.setValue("Jack");
		} else if ("Jack".equals(cookie.getValue())) {
			cookie.setMaxAge(0);
		}
		response.addCookie(cookie);
	}
}
