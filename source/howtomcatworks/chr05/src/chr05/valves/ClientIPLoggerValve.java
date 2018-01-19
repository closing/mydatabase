package chr05.valves;

import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Contained;
import org.apache.catalina.Container;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

public class ClientIPLoggerValve implements Valve, Contained {
	protected Container container;
	
	public String getInfo() {
		return null;
	}
	public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
		context.invokeNext(request, response);
		System.out.println("Client IP Logger valve");
		ServletRequest sreq = request.getRequest();
		System.out.println(sreq.getRemoteAddr());
		if (sreq instanceof HttpServletRequest) {
			System.out.println(((HttpServletRequest)sreq).getRequestURI());
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
