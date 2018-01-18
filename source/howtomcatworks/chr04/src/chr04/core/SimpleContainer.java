package chr04.core;

import java.beans.PropertyChangeListener;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.io.IOException;
import java.io.File;


import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.management.ObjectName;

import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Mapper;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Cluster;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Pipeline;
import org.apache.catalina.LifecycleListener;

public class SimpleContainer implements Container {
	
	public static final String WEB_ROOT = 
		System.getProperty("user.dir") + File.separator + "webroot";
	
	public SimpleContainer() {
	}
	public void destroy() {
	}
	public String getLogName() {
		return null;
	}
	public String getStateName() {
		return null;
	}
	public void stop() {
	}
	public void start() {
	}
	public void init() {
	}
	public void removeLifecycleListener(LifecycleListener listener) {
	}
	public void addLifecycleListener(LifecycleListener listener) {
	}
	public LifecycleListener[] findLifecycleListeners() {
		return null;
	}
	public String getInfo() {
		return null;
	}
	
	public Loader getLoader() {
		return null;
	}
	
	public void setLoader(Loader loader) {
	}
	
	public Logger getLogger() {
		return null;
	}
	
	public void setLogger(Logger logger) {
	}
	
	public Manager getManager() {
		return null;
	}
	
	public void setManager(Manager manager) {
	}
	public File getCatalinaBase() {
		return null;
	}
	public File getCatalinaHome() {
		return null;
	}
	public Cluster getCluster() {
		return null;
	}
	
	public void setCluster(Cluster cluster) {
	}
	
	public String getName() {
		return null;
	}
	
	public void setName(String name) {
	}
	
	public Container getParent() {
		return null;
	}
	
	public void setParent(Container container) {
	}
	
	public ClassLoader getParentClassLoader() {
		return null;
	}
	
	public void setParentClassLoader(ClassLoader parent) {
	}
	public Realm getRealm() {
		return null;
	}
	public void setRealm(Realm realm) {
	}
	public DirContext getResources() {
		return null;
	}
	public void setStartStopThreads(int startStopThreads) {
	}
	public int getStartStopThreads() {
		return 0;
	}
	public void logAccess(Request request, Response response, long time, boolean useDefault) {
	}
	public void fireContainerEvent(String type, Object data) {
	}
	
	public void setResources(DirContext resources) {
	}
	public void addChild(Container child) {
	}
	public void addContainerListener(ContainerListener listener) {
	}
	public void addMapper(Mapper mapper) {
	}
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	}
	public Container findChild(String name) {
		return null;
	}
	public Container[] findChildren() {
		return null;
	}
	public String getMBeanKeyProperties() {
		return null;
	}
	public String getDomain() {
		return null;
	}
	public ObjectName getObjectName() {
		return null;
	}
	public void backgroundProcess() {
	}
	public void setBackgroundProcessorDelay(int delay) {
	}
	public int getBackgroundProcessorDelay() {
		return 0;
	}
	public Pipeline getPipeline() {
		return null;
	}
	public ContainerListener[] findContainerListeners() {
		return null;
	}
	
	public Mapper findMapper(String protocol) {
		return null;
	}
	public Mapper[] findMappers() {
		return null;
	}
	
	public void invoke(Request request, Response response) 
	  throws IOException, ServletException {
		String servletName = ((HttpServletRequest)request).getRequestURI();
		servletName = servletName.substring(servletName.lastIndexOf("/") + 1);
		
		URLClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classpath = new File(WEB_ROOT);
			String repository = (new URL("file", null, classpath.getCanonicalPath() +
										File.separator)).toString();
			urls[0] = new URL(null, repository,streamHandler);
			loader = new URLClassLoader(urls);
		}
		catch(IOException e) {
			System.out.println(e.toString());
		}
		Class myClass = null;
		try {
			myClass = loader.loadClass(servletName);
		}
		catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
		
		Servlet servlet = null;
		try {
			servlet = (Servlet)myClass.newInstance();
			servlet.service((HttpServletRequest)request, 
							(HttpServletResponse)response);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		catch (Throwable e) {
			System.out.println(e.toString());
		}
	}
	
	public Container map(Request request, boolean update) {
		return null;
	}
	
	public void removeChild(Container child) {
	}
	
	public void removeContainerListener(ContainerListener listener) {
	}
	
	public void removeMapper(Mapper mapper) {
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	}
}
