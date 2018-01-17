import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.Socket;
import java.net.InetAddress;

public class HTTPClient {
	public static void main(String[] args) {
		String uri = "index.html";
		if (args.length != 0) {
			uri = args[0];
		}
		
		doGet("localhost", 8080, uri);
	}
	
	public static void doGet(String host, int port, String uri) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			StringBuffer sb = new StringBuffer("GET " + uri + " HTTP/1.1\r\n");
			sb.append("Accept: */*\r\n");
			sb.append("Accept-Language: zh-cn\r\n");
			sb.append("Accept-Encoding: gzip, deflate\r\n");
			sb.append("User-Agent: HTTPClient\r\n");
			sb.append("Host: localhost:8080\r\n");
			sb.append("Connection: Keep-Alive\r\n");
			
			OutputStream output = socket.getOutputStream();
			output.write(sb.toString().getBytes());
			System.out.println(sb.toString());
			
			Thread.sleep(2000);
			InputStream input = socket.getInputStream();
			int size = input.available();
			System.out.println(size);
			byte[] buffer = new byte[size];
			input.read(buffer);
			System.out.println(new String(buffer));
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				socket.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
