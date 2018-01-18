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

public class RequestFacade implements ServletRequest {
	private ServletRequest request = null;
	
	public RequestFacade(Request request) {
		this.request = request;
	}
	
	public AsyncContext getAsyncContext() {
		return request.getAsyncContext();
	}
	public Object getAttribute(String name) {
		return request.getAttribute(name);
	}
	
	public Enumeration<String> getAttributeNames() {
		return request.getAttributeNames();
	}
	
	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}
	
	public int getContentLength() {
		return request.getContentLength();
	}
	
	public long getContentLengthLong() {
		return request.getContentLengthLong();
	}
	
	public String getContentType() {
		return request.getContentType();
	}
	public DispatcherType getDispatcherType(){
		return request.getDispatcherType();
	}
	
	public ServletInputStream getInputStream() throws IOException {
		return request.getInputStream();
	}

	public String getLocalAddr() {
		return request.getLocalAddr();
	}
	public Locale getLocale() {
		return request.getLocale();
	}
	
	public Enumeration<Locale> getLocales() {
		return request.getLocales();
	}
	public String getLocalName() {
		return request.getLocalName();
	}
	
	public int getLocalPort() {
		return request.getLocalPort();
	}
	
	public String getParameter(String name) {
		return request.getParameter(name);
	}
	
	public Map<String, String[]> getParameterMap() {
		return request.getParameterMap();
	}
	
	public Enumeration<String> getParameterNames(){
		return request.getParameterNames();
	}
	
	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}
	public String getProtocol() {
		return request.getProtocol();
	}
	
	public BufferedReader getReader() throws IOException {
		return request.getReader();
	}
	
	@Deprecated
	public String getRealPath(String path) {
		return request.getRealPath(path);
	}
	
	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}
	
	public String getRemoteHost() {
		return request.getRemoteHost();
	}
	
	public int getRemotePort() {
		return request.getRemotePort();
	}
	
	public RequestDispatcher getRequestDispatcher(String path) {
		return request.getRequestDispatcher(path);
	}
	
	public String getScheme() {
		return request.getScheme();
	}
	
	public String getServerName() {
		return request.getServerName();
	}
	public int getServerPort() {
		return request.getServerPort();
	}
	public ServletContext getServletContext() {
		return request.getServletContext();
	}
	
	public boolean isAsyncStarted() {
		return request.isAsyncStarted();
	}
	public boolean isAsyncSupported() {
		return request.isAsyncSupported();
	}
	public boolean isSecure() {
		return request.isSecure();
	}
	public void removeAttribute(String name) {
		request.removeAttribute(name);
	}
	public void setAttribute(String name, Object o) {
		request.setAttribute(name, o);
	}
	public void setCharacterEncoding(String env)  throws UnsupportedEncodingException {
		request.setCharacterEncoding(env);
	}
	public AsyncContext startAsync() {
		return request.startAsync();
	}
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
		return request.startAsync(servletRequest,servletResponse);
	}
	
}
