package chr03.connector.http;

import chr03.ServletProcessor;
import chr03.StaticResourcesProcessor;

import java.net.Socket;

import java.io.OutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.catalina.util.RequestUtil;

public class HttpProcessor {

	private HttpConnector connector = null;
	private HttpRequest request = null;
	private HttpRequestLine requestLine = new HttpRequestLine();
	private HttpResponse response = null;
	
	public HttpProcessor(HttpConnector connector) {
		this.connector = connector;
	}
	
	// Porcessing the socket
	public void process(Socket socket) {
		System.out.println("HttpProcessor.process: processing the request......");
		SocketInputStream input = null;
		OutputStream output = null;
		try {
			input = new SocketInputStream(socket.getInputStream(), 2048);
			output = socket.getOutputStream();
			
			// Create Request
			request = new HttpRequest(input);
			
			// Create Response
			response = new HttpResponse(output);
			response.setRequest(request);
			response.setHeader("Server", "Servlet Container");
			
			parseRequest(input, output);
			parseHeaders(input);
			
			if (request.getRequestURI().startsWith("/servlet/")) {
				System.out.println("Servlet processing......");
				// Servlet
				ServletProcessor processor = new ServletProcessor();
				processor.process(request, response);
			}
			else {
				System.out.println("StaticResource processing......");
				// StaticResources
				StaticResourcesProcessor processor = new StaticResourcesProcessor();
				processor.process(request, response);
			}
			
			socket.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// parse request header
	private void parseHeaders(SocketInputStream input) throws IOException, ServletException {
		System.out.println("HttpProcessor.parseHeaders......");
		while (true) {
			HttpHeader header = new HttpHeader();
			
			// Read the next header
			input.readHeader(header);
			if (header.nameEnd == 0) {
				if (header.valueEnd == 0) {
					return;
				}
				else {
					throw new ServletException("Invalid HTTP header format");
				}
			}
			String name = new String(header.name, 0, header.nameEnd);
			String value = new String(header.value, 0, header.valueEnd);
			request.addHeader(name,value);
			if("cookie".equals(name)) {
				// Cookie cookies[] = RequestUtil.parseCookieHeader(value);
				// for (int i = 0; i < cookies.length; i++) {
				// 	if (cookies[i].getName().equals("jsessionid")) {
				// 		// Override anything requested in the URL
				// 		if (!request.isRequestedSessionIdFromCookie()) {
				// 			// Accept only the first session id cookie
				// 			request.setRequestedSessionId(cookies[i].getValue());
				// 			request.setRequestedSessionCookie(true);
				// 			request.setRequestedSessionURL(false);
				// 		}
				// 	}
				// 	request.addCookie(cookies[i]);
				// }
			}
			else if ("content-length".equals(name)) {
				int n = -1;
				try {
					n = Integer.parseInt(value);
				}
				catch (Exception e) {
					throw new ServletException("Invalid 'Content-Length' header");
				}
				request.setContentLength(n);
			}
			else if ("conten-type".equals(name)) {
				request.setContentType(value);
			}
		}
		
	}
	
	// parse request
	private void parseRequest(SocketInputStream input, OutputStream output) throws IOException, ServletException {
		System.out.println("HttpProcessor.paraseRequest......");
		// Parse the incoming request line
		input.readRequestLine(requestLine);
		
		// debug info 
		System.out.println("Request Info: " + new String(requestLine.method));
		System.out.println("Request Info: " + new String(requestLine.uri));
		System.out.println("Request Info: " + new String(requestLine.protocol));
		
		// obtains the method,uri,protocol
		String method = new String(requestLine.method, 0, requestLine.methodEnd);
		String uri = null;
		String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);
		
		// Validate the incoming request line
		if (method.length() < 1) {
			throw new ServletException("Missing HTTP request method");
		}
		else if (requestLine.uriEnd < 1) {
			throw new ServletException("Missing HTTP request URI");
		}
		
		// Parse any query parameters out of the request URI
		int question = requestLine.indexOf("?");
		if (question >=0) {
			request.setQueryString(new String(requestLine.uri,question+1, requestLine.uriEnd-question-1));
			uri = new String(requestLine.uri, 0, question);
		}
		else {
			request.setQueryString(null);
			uri = new String(requestLine.uri, 0, requestLine.uriEnd);
		}
		
		// Checking for an absolute URI
		if (!uri.startsWith("/")) {
			int pos = uri.indexOf("://");
			if (pos == -1) {
				pos = uri.indexOf("/", pos + 3);
				if (pos == -1) {
					uri = "";
				}
				else {
					uri = uri.substring(pos);
				}
			}
		}
		
		// Parse the  session ID
		String match = ";jsessionid=";
		int semicolon = uri.indexOf(match);
		if (semicolon >= 0) {
			String rest = uri.substring(semicolon + match.length());
			int semicolon2 = rest.indexOf(";");
			if(semicolon2 >=0) {
				request.setRequestedSessionId(uri.substring(0,semicolon2));
				rest = rest.substring(semicolon2);
			}
			else {
				request.setRequestedSessionId(rest);
				rest = "";
			}
			request.setRequestedSessionURL(true);
			uri = uri.substring(0, semicolon) + rest;
		}
		else {
			request.setRequestedSessionId(null);
			request.setRequestedSessionURL(false);
		}
		
		// normalize the URI
		String normalizedUri = normalize(uri);
		
		// set the properties
		((HttpRequest) request).setMethod(method);
		request.setProtocol(protocol);
		if (normalizedUri != null) {
			((HttpRequest) request).setRequestURI(normalizedUri);
		}
		else {
			((HttpRequest) request).setRequestURI(uri);
		}
		
		if (normalizedUri == null) {
			throw new ServletException("Invalid URI:" + uri + "'");
		}
	}
	
	protected String normalize(String path) {
		if (path == null) {
			return path;
		}
		String normalized = path;
		if (normalized.startsWith("/%7e") || normalized.startsWith("/%7E")) {
			normalized = "/~" + normalized.substring(4);
		}
		
		if ((normalized.indexOf("%25") >= 0)
			|| (normalized.indexOf("%2F") >= 0) 
			|| (normalized.indexOf("%2E") >= 0) 
			|| (normalized.indexOf("%5C") >= 0) 
			|| (normalized.indexOf("%2f") >= 0) 
			|| (normalized.indexOf("%2e") >= 0) 
			|| (normalized.indexOf("%5c") >= 0) ) {
				return null;
		}
		
		if (normalized.equals("/.")) {
			return "/";
		}
		
		// Normalize the slashes and add leading slash if necessary
		if (normalized.indexOf('\\') >= 0) {
			normalized = normalized.replace('\\', '/');
		}
		if (!normalized.startsWith("/")) {
			normalized = "/" + normalized;
		}
		
		// Resolve occurrences of "//" in the normalized path
		while (true) {
			int index = normalized.indexOf("//");
			if (index < 0) {
				break;
			}
			
			normalized = normalized.substring(0, index) + normalized.substring(index + 1);
		}
		
		// Resolve occurrences of "/./" in the normalized path
		while (true) {
			int index = normalized.indexOf("/./");
			if (index < 0) {
				break;
			}
			normalized = normalized.substring(0, index) + normalized.substring(index + 2);
		}
		
		// Resolve occurrences of "/../" in the normalized path
		while (true) {
			int index = normalized.indexOf("/../");
			if (index < 0) {
				break;
			}
			
			if (index == 0) {
				return (null);  // Trying to go outside our context
			}
			int index2 = normalized.lastIndexOf('/', index - 1);
			normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
		}
		
		// Declare occurrences of "/..." (three or more dots) to be invalid
		// (on some Windows platforms this walks the directory tree!!!)
		if (normalized.indexOf("/...") >= 0) {
			return (null);
		}
		
		// Return the normalized path that we have completed
		return (normalized);
	}
}
