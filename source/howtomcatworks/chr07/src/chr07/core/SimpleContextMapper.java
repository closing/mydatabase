package chr07.core;

import org.apache.catalina.Mapper;
import org.apache.catalina.Container;
import org.apache.catalina.Wrapper;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Request;

import javax.servlet.http.HttpServletRequest;


public class SimpleContextMapper implements Mapper {
	
	private SimpleContext context = null;
	
	public Container getContainer() {
		return (context);
	}
	public void setContainer(Container container) {
		if (!(container instanceof SimpleContext)) {
			throw new IllegalArgumentException("Illegal type of container");
		}
		context = (SimpleContext)container;
	}
	public String getProtocol() {
		return null;
	}
	public void setProtocol(String protocol) {}
	public Container map(Request request, boolean update) {
		String contextPath = ((HttpServletRequest)request.getRequest()).getContextPath();
		String requestURI =((HttpRequest)request).getDecodedRequestURI();
		String relativeURI =requestURI.substring(contextPath.length());
		Wrapper wrapper = null;
		String servletPath = relativeURI;
		String pathInfo = null;
		String name = context.findServletMapping(relativeURI);
		if (name != null) {
			wrapper = (Wrapper)context.findChild(name);
		}
		return (wrapper);
	}
}
