package mypack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;

public class LifeServlet extends GenericServlet {
	private int initVal = 0;
	private int serviceVal = 0;
	private int destroyVal = 0;
	private String name;
	
	public void init(ServletConfig config) throws ServletException {
      super.init(config);
      name = config.getServletName();
      initVal++;
      System.out.println(name + ">init(): Servlet 被初始化了"+initVal+"次");
	}
	
	public void destroy() {
		destroyVal++;
		System.out.println(name + ">destroy(): Servlet被销毁了"+destroyVal+"次");
	}
	public void service(ServletRequest request, ServletResponse response) 
						throws ServletException, IOException {
		serviceVal++;
		System.out.println(name+">service(): Servlet共响应了"+serviceVal+"次请求");
		
		String content1 = "初始化次数: " + initVal;
		String content2 = "响应客户请求次数: " + serviceVal;
		String content3 = "销毁次数: " + destroyVal;
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>LifeServlet</title>");
		out.println("</head><body>");
		out.println("<h1>" + content1 + "</h1>");
		out.println("<h1>" + content2 + "</h1>");
		out.println("<h1>" + content3 + "</h1>");
		out.println("</body></html>");
		
		out.close();
	}
}
