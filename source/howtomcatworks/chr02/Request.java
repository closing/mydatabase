import java.lang.System;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;


public class Request implements ServletRequest {
	private InputStream input;
	private String uri;
	public Request(InputStream input) {
		this.input = input;
	}
	
	public String getUri() {
		return this.uri;
	}
	
	
	private String parseUri(String requestString) {
		int index1,index2;
		index1 = requestString.indexOf(' ');
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1+1, index2);
			}
			
		}
		return null;
	}

	public void parse(){
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		
		for (int j = 0; j < i; j++) {
			request.append((char)buffer[j]);
		}
		uri = this.parseUri(request.toString());
	}
	
	public AsyncContext getAsyncContext() {
		return null;
	}
	public Object getAttribute(String name) {
		return null;
	}
	
	public Enumeration<String> getAttributeNames() {
		return null;
	}
	
	public String getCharacterEncoding() {
		return null;
	}
	
	public int getContentLength() {
		return 0;
	}
	
	public long getContentLengthLong() {
		return 0L;
	}
	
	public String getContentType() {
		return null;
	}
	public DispatcherType getDispatcherType() {
		return null;
	}
	
	public ServletInputStream getInputStream() {
		return null;
	}

	public String getLocalAddr() {
		return null;
	}
	public Locale getLocale() {
		return null;
	}
	
	public Enumeration<Locale> getLocales() {
		return null;
	}
	public String getLocalName() {
		return null;
	}
	
	public int getLocalPort() {
		return 0;
	}
	
	public String getParameter(String name) {
		return null;
	}
	
	public Map<String, String[]> getParameterMap() {
		return null;
	}
	
	public Enumeration<String> getParameterNames(){
		return null;
	}
	
	public String[] getParameterValues(String name) {
		return null;
	}
	public String getProtocol() {
		return null;
	}
	
	public BufferedReader getReader() {
		return null;
	}
	
	@Deprecated
	public String getRealPath(String path) {
		return null;
	}
	
	public String getRemoteAddr() {
		return null;
	}
	
	public String getRemoteHost() {
		return null;
	}
	
	public int getRemotePort() {
		return 0;
	}
	
	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}
	
	public String getScheme() {
		return null;
	}
	
	public String getServerName() {
		return null;
	}
	public int getServerPort() {
		return 0;
	}
	public ServletContext getServletContext() {
		return null;
	}
	
	public boolean isAsyncStarted() {
		return false;
	}
	public boolean isAsyncSupported() {
		return false;
	}
	public boolean isSecure() {
		return false;
	}
	public void removeAttribute(String name) {
	}
	public void setAttribute(String name, Object o) {
	}
	public void 	setCharacterEncoding(String env) {
	}
	public AsyncContext startAsync() {
		return null;
	}
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
		return null;
	}
}
