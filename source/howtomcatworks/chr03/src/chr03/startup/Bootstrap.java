package chr03.startup;

import chr03.connector.http.HttpConnector;

public final class Bootstrap {
	public static void main(String[] args) {
		System.out.println("Bootstrap.main: Server starting...");
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
}
