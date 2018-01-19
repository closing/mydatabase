package chr05.startup;

import chr05.core.SimpleWrapper;
import chr05.core.SimpleContext;
import chr05.core.SimpleContextMapper;
import chr05.core.SimpleWrapper;
import chr05.core.SimpleLoader;
import chr05.valves.ClientIPLoggerValve;
import chr05.valves.HeaderLoggerValve;

import org.apache.catalina.Valve;
import org.apache.catalina.Connector;
import org.apache.catalina.Mapper;
import org.apache.catalina.Context;
import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;


public class BootstrapNew {
	public static void main(String args[]) {
		HttpConnector connector = new HttpConnector();
		Wrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		Wrapper wrapper2 = new SimpleWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");
		
		Context context = new SimpleContext();
		context.addChild(wrapper1);
		context.addChild(wrapper2);
		
		Valve valve1 = new HeaderLoggerValve();
		Valve valve2 = new ClientIPLoggerValve();
		((Pipeline)context).addValve(valve1);
		((Pipeline)context).addValve(valve2);
		
		Mapper mapper = new SimpleContextMapper();
		mapper.setProtocol("http");
		context.addMapper(mapper);
		
		Loader loader = new SimpleLoader();
		context.setLoader(loader);
		
		context.addServletMapping("/Primitive","Primitive");
		context.addServletMapping("/Modern","Modern");
		
		connector.setContainer(context);
		
		try {
			connector.initialize();
			connector.start();
			System.in.read();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
