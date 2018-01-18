package chr03.connector.http;

import java.io.BufferedReader;
import java.io.InputStream;

import java.net.InetAddress;
import java.net.Socket;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

import java.security.Principal;

import javax.servlet.DispatcherType;
import javax.servlet.AsyncContext;
import javax.servlet.ServletInputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;

import org.apache.catalina.util.ParameterMap;
;

public class HttpRequest implements HttpServletRequest {
	private InputStream input;
	private InetAddress inetAddress;
	private String method;
	private String protocol;
	private String contentType;
	private int contentLength;
	private String queryString;
	private String requestURI;
	private String serverName;
	private int serverPort;
	private Socket socket;
	private boolean requestedSessionCookie;
	private String requestedSessionId;
	private boolean requestedSessionURL;
	
	
	
	protected HashMap headers = new HashMap();
	protected ArrayList cookies = new ArrayList();
	protected ParameterMap parameters = null;
	
	public HttpRequest(InputStream input) {
		this.input = input;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public void setRequestedSessionId(String requestedSessionId) {
		this.requestedSessionId = requestedSessionId;
	}
	public void setRequestedSessionURL(boolean flag) {
		this.requestedSessionURL = flag;
	}
	public InputStream getStream() {
		return input;
	}
	public void setContentLength(int length) {
		this.contentLength = length;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setServername(String serverName) {
		this.serverName = serverName;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public void setRequestedSessionCookie(boolean flag) {
		this.requestedSessionCookie = flag;
	}
	
	public void addCookie(Cookie cookie) {
		synchronized(cookies) {
			cookies.add(cookie);
		}
	}
	public void addHeader(String name, String value) {
		name = name.toLowerCase();
		synchronized (headers) {
			ArrayList values = (ArrayList)headers.get(name);
			if (values == null) {
				values = new ArrayList();
				headers.put(name, values);
			}
			values.add(value);
		}
	}
	
	// HttpServletRequest
	public boolean authenticate(HttpServletResponse response) {
		return false;
	}
	public String changeSessionId() {
		return null;
	}
	public String getAuthType() {
		return null;
	}
	public String getContextPath() {
		return null;
	}
	public Cookie[] getCookies() {
		return null;
	}
	public long getDateHeader(String name) {
		return 0L;
	}
	public String getHeader(String name) {
		return null;
	}
	public Enumeration<String> getHeaderNames() {
		return null;
	}
	public Enumeration<String> getHeaders(String name) {
		return null;
	}
	public int getIntHeader(String name) {
		return 0;
	}
	public String getMethod() {
		return this.method;
	}
	public Part getPart(String name) {
		return null;
	}
	public Collection<Part> getParts() {
		return null;
	}
	public String getPathInfo() {
		return null;
	}
	public String getPathTranslated() {
		return null;
	}
	public String getQueryString() {
		return queryString;
	}
	public String getRemoteUser() {
		return null;
	}
	public String getRequestedSessionId() {
		return null;
	}
	public String getRequestURI() {
		return this.requestURI;
	}
	public StringBuffer getRequestURL() {
		return null;
	}
	public String getServletPath() {
		return null;
	}
	public HttpSession getSession() {
		return null;
	}
	public HttpSession getSession(boolean create) {
		return null;
	}
	public Principal getUserPrincipal() {
		return null;
	}
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}
	@Deprecated
	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}
	public boolean isRequestedSessionIdValid() {
		return false;
	}
	public boolean isUserInRole(String role) {
		return false;
	}
	public void login(String username, String password) {
	}
	public void logout() {
	}
	public <T extends HttpUpgradeHandler>T upgrade(Class<T> handlerClass) {
		return null;
	}
	
	// ServletRequest
	
	
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
		return this.contentLength;
	}
	
	public long getContentLengthLong() {
		return 0L;
	}
	
	public String getContentType() {
		return contentType;
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
		return this.protocol;
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
		return serverName;
	}
	public int getServerPort() {
		return this.serverPort;
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
	public void setCharacterEncoding(String env) {
	}
	public AsyncContext startAsync() {
		return null;
	}
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
		return null;
	}	
}
