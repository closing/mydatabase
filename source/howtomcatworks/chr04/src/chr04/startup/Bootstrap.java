package chr04.startup;

import chr04.core.SimpleContainer;
import org.apache.catalina.connector.http.HttpConnector;

public final class Bootstrap {
	public static void main(String[] args) {
		System.out.println("Bootstrap.main: Server starting...");
		
		HttpConnector connector = new HttpConnector();
		SimpleContainer container = new SimpleContainer();
		connector.setContainer(container);
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
