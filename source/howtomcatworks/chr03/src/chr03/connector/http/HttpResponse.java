package chr03.connector.http;

import chr03.connector.ResponseWriter;
import chr03.connector.ResponseStream;

import java.util.Locale;
import java.util.Collection;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

public class HttpResponse implements HttpServletResponse {
	private final static int BUFFER_SIZE = 1024;
	private HttpRequest request;
	private OutputStream output;
	private ResponseWriter writer;
	
	protected String encoding = null;
	
	public HttpResponse (OutputStream output) {
		this.output = output;
	}
	
	public void setRequest(HttpRequest request) {
		this.request = request;
	}
	
	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(Constants.WEB_ROOT, request.getRequestURI());
			if (!file.exists()) {
				System.out.println("File not found");
				throw new FileNotFoundException(file.getName());
			}
			fis = new FileInputStream(file);
			
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
			while (ch != -1) {
				output.write(bytes, 0, ch);
				ch = fis.read(bytes, 0, BUFFER_SIZE);
			}
		}
		catch(FileNotFoundException e) {
			 String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
								   "Content-Type: text/html\r\n" +
								   "Content-Length: 23\r\n" +
								   "\r\n" +
								   "<h1>File Not Found</h1>";
			System.out.println(errorMessage);
			output.write(errorMessage.getBytes());
		}
		finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
	// HttpServletResponse
	public void addCookie(Cookie cookie) {}
	public void addDateHeader(String name, long date) {}
	public void addHeader(String name, String value) {}
	public void addIntHeader(String name, int value) {}
	public boolean containsHeader(String name) {
		return false;
	}
	@Deprecated
	public String encodeRedirectUrl(String url) {
		return null;
	}
	public String encodeRedirectURL(String url) {
		return null;
	}
	@Deprecated
	public String encodeUrl(String url) {
		return null;
	}
	public String encodeURL(String url) {
		return null;
	}
	public String getHeader(String name) {
		return null;
	}
	public Collection<String> getHeaderNames() {
		return null;
	}
	public Collection<String> getHeaders(String name) {
		return null;
	}
	public int getStatus() {
		return 0;
	}
	public void sendError(int sc) {}
	public void sendError(int sc, String msg) {}
	public void sendRedirect(String location) {}
	public void setDateHeader(String name, long date) {}
	public void setHeader(String name, String value) {}
	public void setIntHeader(String name, int value) {}
	public void setStatus(int sc) {}
	@Deprecated	
	public void setStatus(int sc, String sm) {}
	
	// ServletResponse
	public void flushBuffer() throws IOException {}
	public int getBufferSize() {
		return 0;
	}
	public String getCharacterEncoding() {
		if (encoding == null) {
			encoding = ("ISO-8859-");
		}
		return encoding;
	}
	
	public String getContentType() {
		return null;
	}
	public Locale getLocale() {
		return null;
	}
	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}
	public PrintWriter getWriter() throws IOException {
		ResponseStream newStream = new ResponseStream(this);
		newStream.setCommit(false);
		OutputStreamWriter osr = new OutputStreamWriter(newStream, getCharacterEncoding());
		writer =  new ResponseWriter(osr);
		return writer;
	}
	public boolean isCommitted() {
		return false;
	}
	public void reset() {}
	public void resetBuffer() {}
	public void setBufferSize(int size) {}
	public void setCharacterEncoding(String charset) {}
	public void setContentLength(int len) {}
	public void setContentLengthLong(long len) {}
	public void setContentType(String type) {}
	public void setLocale(Locale loc) {}
}
