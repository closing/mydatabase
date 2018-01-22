package chr06.startup;

import chr06.core.SimpleWrapper;
import chr06.core.SimpleContext;
import chr06.core.SimpleContextMapper;
import chr06.core.SimpleWrapper;
import chr06.core.SimpleLoader;
import chr06.core.SimpleContextLifecycleListener;

import org.apache.catalina.Valve;
import org.apache.catalina.Connector;
import org.apache.catalina.Mapper;
import org.apache.catalina.Context;
import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Wrapper;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.connector.http.HttpConnector;


public class Bootstrap {
	public static void main(String args[]) {
		Connector connector = new HttpConnector();
		Wrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		Wrapper wrapper2 = new SimpleWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");
		
		Context context = new SimpleContext();
		context.addChild(wrapper1);
		context.addChild(wrapper2);
		
		Mapper mapper = new SimpleContextMapper();
		mapper.setProtocol("http");
		LifecycleListener listener  = new SimpleContextLifecycleListener();
		((Lifecycle)context).addLifecycleListener(listener);
		context.addMapper(mapper);
		
		Loader loader = new SimpleLoader();
		context.setLoader(loader);
		
		context.addServletMapping("/Primitive","Primitive");
		context.addServletMapping("/Modern","Modern");
		
		connector.setContainer(context);
		
		try {
			connector.initialize();
			((Lifecycle)connector).start();
			((Lifecycle)context).start();
			System.in.read();
			((Lifecycle)context).stop();
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
