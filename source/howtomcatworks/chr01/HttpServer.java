
import java.lang.System;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;


/**
*    HttpServer:Response to the request!
*    Date 2018/01/09
*    Version 0.1
*/
public class HttpServer {
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;

	public static void main(String[] args) throws IOException {
		System.out.println("Enter the main method...");
		HttpServer httpServer = new HttpServer();
		System.out.println(WEB_ROOT);
		httpServer.await();
		System.exit(0);
	}
	
	public void await() {
		System.out.println("Enter the await method...");
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		while(!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				
				// create request object and parse
				Request request = new Request(input);
				request.parse();
				
				// Create response object
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				
				// close the socket 
				socket.close();
				System.out.println(request.getUri());
				shutdown = SHUTDOWN_COMMAND.equals(request.getUri());
				
				
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
