package chr06.core;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Pipeline;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.ValveContext;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;

public class SimplePipeline implements Pipeline, Lifecycle {
	// Lifecycle
	public synchronized void start() throws LifecycleException {
		System.out.println("Starting SimplePipeline");
	}
	public void stop() throws LifecycleException {
	}
	public void addLifecycleListener(LifecycleListener listener) {
	}
	public void removeLifecycleListener(LifecycleListener listener) {
	}
	public LifecycleListener[] findLifecycleListeners() {
		return null;
	}
	
	// Pipeline
	public SimplePipeline(Container container) {
		setContainer(container);
	}
	protected Container container = null;
	protected Valve basic = null;
	protected Valve[] valves = new Valve[0];
	
	public void setContainer(Container container) {
		this.container = container;
	}
	
	public Valve getBasic() {
		return this.basic;
	}
	public void setBasic(Valve valve) {
		this.basic = valve;
		((Contained)valve).setContainer(this.container);
	}
	public void addValve(Valve valve) {
		if (valve instanceof Contained) {
			((Contained)valve).setContainer(this.container);
		}
		synchronized(valves) {
			Valve results[] = new Valve[valves.length +1];
			System.arraycopy(valves, 0, results, 0, valves.length);
			results[valves.length] = valve;
			valves = results;
		}
	}
	public Valve[] getValves() {
		return (this.valves);
	}
	public void removeValve(Valve valve) {
	}
	
	public void invoke(Request request, Response response) throws IOException, ServletException {
		(new SimplePipelineValveContext()).invokeNext(request, response);
	}
	
	protected class SimplePipelineValveContext implements ValveContext {
		protected int stage = 0;
		public String getInfo() {
			return null;
		}
		public void invokeNext(Request request, Response response) throws IOException, ServletException {
			int subscript = stage;
			stage = stage + 1;
			if (subscript < valves.length) {
				valves[subscript].invoke(request, response, this);
			}
			else if ((subscript == valves.length) && (basic != null)) {
				basic.invoke(request, response, this);
			}
			else {
				throw new ServletException("No valve");
			}
			
		}
	}
}
