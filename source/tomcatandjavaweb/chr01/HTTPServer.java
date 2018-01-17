import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;

import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;
import java.util.HashMap;

public class HTTPServer {
	private static Map servletCache = new HashMap();
	
	public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch(Exception e) {
            System.out.println("port:8080 (default)");
            port = 8080;
        }
        
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            System.out.println("port:" + serverSocket.getLocalPort());
            
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("connection: " + socket.getInetAddress() + ":" + socket.getPort());
                    service(socket);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void service(Socket socket) throws Exception {
        InputStream input = socket.getInputStream();
        Thread.sleep(500);
        
        int size = input.available();
        byte[] buffer = new byte[size];
        input.read(buffer);
        String request = new String(buffer);
        System.out.println(request);
        
        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
        String[] parts = firstLineOfRequest.split(" ");
        String uri = parts[1];
        
        if ( uri.indexOf("servlet") != -1) {
			String servletName = null;
			if (uri.indexOf("?") != -1) {
				servletName = uri.substring(uri.indexOf("servlet") + 8, uri.indexOf("?"));
			}
			else {
				servletName = uri.substring(uri.indexOf("servlet") + 8, uri.length());
			}
			
			Servlet servlet = (Servlet)servletCache.get(servletName);
			if (servlet == null) {
				servlet = (Servlet)Class.forName(servletName).newInstance();
				servlet.init();
				servletCache.put(servletName, servlet);
			}
			servlet.service(buffer, socket.getOutputStream());
			Thread.sleep(1000);
			socket.close();
			return;
        }
        
        String contentType;
        if (uri.indexOf("html") != -1 || uri.indexOf("htm") != -1) {
            contentType = "text/html";
        }
        else if (uri.indexOf("jpg") != -1 || uri.indexOf("jpeg") != -1) {
            contentType = "image/jpeg";
        }
        else if (uri.indexOf("gif") != -1) {
            contentType = "image/gif";
        }
        else {
            contentType = "application/octet-stream";
        }
        
        String responseFirstLine = "HTTP/1.1 200 OK\r\n";
        String responseHeader = "Content-Type:" + contentType + "\r\n";
        InputStream in = HTTPServer.class.getResourceAsStream("root/" + uri);
        System.out.println(uri);
        OutputStream output = socket.getOutputStream();
        output.write(responseFirstLine.getBytes());
        output.write(responseHeader.getBytes());
        int len = 0;
        buffer = new byte[128];
        while ((len = in.read(buffer)) != -1) {
            output.write(buffer, 0, len);
        }
        Thread.sleep(1000);
        socket.close();
    }
}
