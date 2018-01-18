package chr03.connector.http;

import java.io.IOException;

import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;

public class HttpConnector implements Runnable {
	boolean stopped;
	private String scheme = "http";
	
	public String getScheme() {
		return scheme;
	}
	
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		while (!stopped) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			}
			catch(Exception e) {
				continue;
			}
			
			System.out.println("HttpConnector.run: serving request...");
			HttpProcessor processor = new HttpProcessor(this);
			processor.process(socket);
		}
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
