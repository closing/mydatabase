import java.lang.System;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
*    HttpServer:Response to the request!
*    Date 2018/01/09
*    Version 0.2
*/
public class HttpServer {
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private boolean shutdown = false;

	public static void main(String[] args) throws IOException {
		HttpServer httpServer = new HttpServer();
		System.out.println("Server Started.");
		httpServer.await();
		System.exit(0);
	}
	
	public void await() {
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
				
				System.out.println("Requested.");
				
				// create request object and parse
				Request request = new Request(input);
				request.parse();
				
				// Create response object
				Response response = new Response(output);
				response.setRequest(request);
				if (request.getUri() != null && request.getUri().startsWith("/servlet/")) {
					ServletProcessor processor = new ServletProcessor();
					processor.process(request, response);
				} else {
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request, response);
				}
				
				// close the socket 
				socket.close();
				shutdown = SHUTDOWN_COMMAND.equals(request.getUri());
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	
}
