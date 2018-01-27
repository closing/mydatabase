package mypack;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;

import java.util.Enumeration;

public class DirTesterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		ServletContext context = getServletContext();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Enumeration eu = context.getAttributeNames();
		while (eu.hasMoreElements()) {
			String attributeName = (String)eu.nextElement();
			out.println("<br>" + attributeName + ": " + context.getAttribute(attributeName));
		}
		
		out.close();
		
		File workDir = (File)context.getAttribute("javax.servlet.context.tempdir");
		FileOutputStream fileout = new FileOutputStream(workDir + "/temp.txt");
		fileout.write("Hello World".getBytes());
		fileout.close();
	}
}
