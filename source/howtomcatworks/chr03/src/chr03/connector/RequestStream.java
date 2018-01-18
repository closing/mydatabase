package chr03.connector;

import chr03.connector.http.HttpRequest;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class RequestStream extends ServletInputStream {
	public RequestStream(HttpRequest request) {
		super();
	}
	
	// Override
	public void setReadListener(ReadListener readListener) {
	}
	
	public boolean isReady() {
		return false;
	}
	public boolean isFinished() {
		return false;
	}

	public int read() {
		return 0;
	}
}
