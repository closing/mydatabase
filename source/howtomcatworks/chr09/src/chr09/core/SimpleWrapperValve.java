package chr09.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Servlet;

import org.apache.catalina.Valve;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.ValveContext;
import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Context;

public class SimpleWrapperValve implements Valve, Contained {
	protected Container container;
	public void setContainer(Container container) {
		this.container = container;
	}
	public Container getContainer() {
		return (this.container);
	}
	public String getInfo(){
		return null;
	}
	public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
		SimpleWrapper wrapper = (SimpleWrapper)getContainer();
		ServletRequest sreq = (ServletRequest)request;
		ServletResponse sres = (ServletResponse)response;
		Servlet servlet = null;
		HttpServletRequest hreq = null;
		if (sreq instanceof HttpServletRequest) {
			hreq = (HttpServletRequest)sreq;
		}
		HttpServletResponse hres = null;
		if (sres instanceof HttpServletResponse) {
			hres = (HttpServletResponse)sres;
		}
		
		// 
		Context context = (Context)wrapper.getParent();
		request.setContext(context);
		try {
			servlet = wrapper.allocate();
			if (hres != null && hreq != null) {
				servlet.service(hreq, hres);
			}
			else {
				servlet.service(sreq, sres);
			}
		}
		catch (ServletException e) {
		}
	}
}
