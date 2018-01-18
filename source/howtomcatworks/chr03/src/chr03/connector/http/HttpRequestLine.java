package chr03.connector.http;

public class HttpRequestLine {
	
	public static final int INITIAL_METHOD_SIZE = 8;
	public static final int INITIAL_URI_SIZE = 64;
	public static final int INITIAL_PROTOCOL_SIZE = 8;
	public static final int MAX_METHOD_SIZE = 1024;
	public static final int MAX_URI_SIZE = 32768;
	public static final int MAX_PROTOCOL_SIZE = 1024;
	
	
	public HttpRequestLine () {
		this(new char[INITIAL_METHOD_SIZE], 0, new char[INITIAL_URI_SIZE], 0,
			 new char[INITIAL_PROTOCOL_SIZE], 0);
	}
	
	public HttpRequestLine(char[] method, int methodEnd, 
						   char[] uri, int uriEnd,
						   char[] protocol, int protocolEnd) {
		this.method = method;
		this.methodEnd = methodEnd;
		this.protocol = protocol;
		this.protocolEnd = protocolEnd;
		this.uri = uri;
		this.uriEnd = uriEnd;
	}
	
	public char[] method;
	public int methodEnd;
	public char[] protocol;
	public int protocolEnd;
	public char[] uri;
	public int uriEnd;
	
	public void recycle() {
		methodEnd = 0;
		protocolEnd = 0;
		uriEnd = 0;
	}
	
	public int indexOf(String str) {
		return indexOf(str.toCharArray(), str.length());
	}
	
	public int indexOf(char c, int start) {
		return -1;
	}
	
	public int indexOf(char[] buf, int end) {
		return -1;
	}
	
	public int indexOf(char[] buf) {
		return indexOf(buf, buf.length);
	}
}
