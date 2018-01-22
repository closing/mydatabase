package chr07.startup;

import chr07.core.SimpleContext;
import chr07.core.SimpleContextLifecycleListener;
import chr07.core.SimpleContextMapper;
import chr07.core.SimpleLoader;
import chr07.core.SimpleWrapper;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Mapper;
import org.apache.catalina.Wrapper;
import org.apache.catalina.Context;
import org.apache.catalina.Connector;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.logger.FileLogger;

public class Bootstrap {
	public static void main(String args[]) {
		Connector connector = new HttpConnector();
		Wrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		Wrapper wrapper2 = new SimpleWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");
		
		Loader loader = new SimpleLoader();
		
		Context context = new SimpleContext();
		context.addChild(wrapper1);
		context.addChild(wrapper2);
		
		Mapper mapper = new SimpleContextMapper();
		mapper.setProtocol("http");
		LifecycleListener listener  = new SimpleContextLifecycleListener();
		((Lifecycle)context).addLifecycleListener(listener);
		context.addMapper(mapper);
		context.setLoader(loader);
		
		context.addServletMapping("/Primitive","Primitive");
		context.addServletMapping("/Modern","Modern");
	
		// add logger
		System.setProperty("catalina.base", System.getProperty("user.dir"));
		FileLogger logger = new FileLogger();
		logger.setPrefix("FileLog_");
		logger.setSuffix(".txt");
		logger.setTimestamp(true);
		logger.setDirectory("webroot");
		context.setLogger(logger);
		
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
