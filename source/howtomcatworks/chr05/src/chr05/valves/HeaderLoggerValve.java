package chr05.valves;

import java.util.Enumeration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Contained;
import org.apache.catalina.Container;

public class HeaderLoggerValve implements Valve, Contained {
	protected Container container;
	
	public String getInfo() {
		return null;
	}
	public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
		context.invokeNext(request, response);
		System.out.println("Header Logger valve");
		ServletRequest sreq = request.getRequest();
		if (sreq instanceof HttpServletRequest) {
			HttpServletRequest hreq = (HttpServletRequest)sreq;
			Enumeration headerNames = hreq.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement().toString();
				String headerValue = hreq.getHeader(headerName);
				System.out.println(headerName + ":" + headerValue);
			}
		}
		else {
			System.out.println("Not an HTTP Request");
		}
		System.out.println("-----------------------------------");
	}
	
	public void setContainer(Container container) {
		this.container = container;
	}
	public Container getContainer() {
		return (this.container);
	}
}
