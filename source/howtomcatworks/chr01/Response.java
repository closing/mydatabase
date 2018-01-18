import java.lang.System;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;

public class Response {
	private static final int BUFFER_SIZE = 1024;
	private OutputStream output;
	private Request request;
	
	public Response(OutputStream output) {
		System.out.println("Response constructor...");
		this.output = output;
	}
	public void setRequest(Request request) {
		System.out.println("setRequest...");
		this.request = request;
	}
	public void sendStaticResource() throws IOException{
		System.out.println("sendStaticResource...");
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(HttpServer.WEB_ROOT, request.getUri());
			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			} else {
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
				"Content-Type: text/html\r\n" +
				"Content-Length: 23\r\n" +
				"\r\n" +
				"<h1>File Not Found<h1/>";
				output.write(errorMessage.getBytes());
				
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (fis != null) fis.close();
		}
	}
}
