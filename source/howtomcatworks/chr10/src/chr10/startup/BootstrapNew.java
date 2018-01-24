package chr10.startup;

import chr10.core.SimpleContextConfig;
import chr10.core.SimpleWrapper;
import chr10.realm.SimpleUserDatabaseRealm;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.catalina.Mapper;
import org.apache.catalina.Wrapper;
import org.apache.catalina.Manager;
import org.apache.catalina.Realm;
import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.Connector;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.logger.FileLogger;
import org.apache.catalina.session.StandardManager;
import org.apache.naming.resources.ProxyDirContext;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;

public class BootstrapNew {
	public static void main(String args[]) {
		System.setProperty("catalina.base", System.getProperty("user.dir"));
		Connector connector = new HttpConnector();
		
		Wrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		Wrapper wrapper2 = new SimpleWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");
		
		Context context = new StandardContext();
		context.setPath("/myApp");
		context.setDocBase("myApp");
		
		LifecycleListener listener  = new SimpleContextConfig();
		((Lifecycle)context).addLifecycleListener(listener);
		
		context.addChild(wrapper1);
		context.addChild(wrapper2);
		
		Loader loader = new WebappLoader();
		context.setLoader(loader);
		
		context.addServletMapping("/Primitive","Primitive");
		context.addServletMapping("/Modern","Modern");
		
		// 
		SecurityCollection securityCollection = new SecurityCollection();
		securityCollection.addPattern("/");
		securityCollection.addMethod("GET");
		
		SecurityConstraint constraint = new SecurityConstraint();
		constraint.addCollection(securityCollection);
		constraint.addAuthRole("manager");
		LoginConfig loginConfig = new LoginConfig();
		loginConfig.setRealmName("Simple Realm");
		Realm realm = new SimpleUserDatabaseRealm();
		((SimpleUserDatabaseRealm)realm).createDatabase("conf/tomcat-users.xml");
		context.setRealm(realm);
		context.addConstraint(constraint);
		context.setLoginConfig(loginConfig);
		
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
