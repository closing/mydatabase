import java.lang.System;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;
import java.io.PrintWriter;

import java.util.Locale;

import javax.servlet.ServletResponse;
import javax.servlet.ServletOutputStream;

public class Response implements ServletResponse {
	private static final int BUFFER_SIZE = 1024;
	private OutputStream output;
	private Request request;
	private PrintWriter writer;
	
	public Response(OutputStream output) {
		this.output = output;
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void sendStaticResource() throws IOException{
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		
		try {
			File file = new File(Constants.WEB_ROOT, request.getUri());
			
			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			} else {
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
				"Content-Type: text/html\r\n" +
				"Content-Length: 23\r\n" +
				"\r\n" +
				"<h1>File Not Found<h1/>";
				output.write(errorMessage.getBytes());
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (fis != null) fis.close();
		}
	}
	
	public void flushBuffer() {}
	public int getBufferSize() { return 0; }
	public String getCharacterEncoding() { return null; }
	public String getContentType() { return null; }
	public Locale getLocale() { return null; }
	public ServletOutputStream getOutputStream() { return null; }
	public PrintWriter getWriter() throws IOException {
		writer = new PrintWriter(output, true);
		return writer; 
	}
	public boolean isCommitted() { return false; }
	public void reset() {}
	public void resetBuffer() {}
	public void setBufferSize(int size) {}
	public void setCharacterEncoding(String charset) {}
	public void setContentLength(int len) {}
	public void setContentLengthLong(long len) {}
	public void setContentType(String type) {}
	public void setLocale(Locale loc) {}
}
