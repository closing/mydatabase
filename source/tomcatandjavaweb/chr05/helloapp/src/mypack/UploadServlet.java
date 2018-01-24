package mypack;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import java.util.List;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

public class UploadServlet extends HttpServlet {
	private String filePath;
	private String tempFilePath;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		filePath = config.getInitParameter("filePath");
		tempFilePath = config.getInitParameter("tempFilePath");
		
		filePath=getServletContext().getRealPath(filePath);
		tempFilePath=getServletContext().getRealPath(tempFilePath);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/plain");
		PrintWriter outNet = response.getWriter();
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4*1024);
			factory.setRepository(new File(tempFilePath));
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(4*1024*1024);
			
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					processFormField(item, outNet);
				} else {
					processUploadFile(item, outNet);
				}
			}
			outNet.close();
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private void processFormField(FileItem item, PrintWriter outNet) {
		String name = item.getFieldName();
		String value = item.getString();
		
		outNet.println(name + ":" + value + "\r\n");
	}
	private void processUploadFile(FileItem item, PrintWriter outNet) throws Exception {
		String fileName = item.getName();
		int index = fileName.lastIndexOf("\\");
		fileName = fileName.substring(index + 1, fileName.length());
		long fileSize = item.getSize();
		
		if (fileName.equals("") && fileSize == 0) {
			return;
		}
		File uploadedFile = new File(filePath + "/" + fileName);
		item.write(uploadedFile);
		outNet.println(fileName + " is saved.");
		outNet.println("The size of " + fileName + " is " + fileSize + "\r\n");
	}
}
