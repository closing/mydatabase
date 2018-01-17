import java.io.OutputStream;

public class HelloServlet implements Servlet {

	public void init() throws Exception {
		System.out.println("Hello Servlet is inited");
	}
	
	public void service(byte[] buffer, OutputStream output) throws Exception {
		String requestString = new String(buffer);
		
		String firstLineOfRequest = requestString.substring(0,requestString.indexOf("\r\n"));
		
		String[] parts = firstLineOfRequest.split(" ");
		String method = parts[0];
		String uri = parts[1];
		String username = null;
		if ("get".equalsIgnoreCase(method) && uri.indexOf("username") != -1) {
			String parameters = uri.substring(uri.indexOf("?"),uri.length());
			parts = parameters.split("&");
			parts = parts[0].split("=");
			username = parts[1];
		}
		else if ("post".equalsIgnoreCase(method)) {
			int locate = requestString.indexOf("\r\n\r\n");
			
			String content = requestString.substring(locate + 4, requestString.length());
			if (content.indexOf("username=") != -1) {
				parts = content.split("&");
				parts = parts[0].split("=");
				username = parts[1];
			}
		}
		
		output.write("HTTP/1.1 200 OK\r\n".getBytes());
		output.write("Content-Type: text/html\r\n".getBytes());
		output.write("<html><head><title>Hello</title></head><body>".getBytes());
		output.write(("<h1>Hello " + username + "</h1>").getBytes());
		output.write("</body></html>".getBytes());
	}
}
