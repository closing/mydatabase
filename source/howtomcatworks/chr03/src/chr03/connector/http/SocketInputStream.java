package chr03.connector.http;

import java.io.InputStream;
import java.io.IOException;
import java.io.EOFException;

public class SocketInputStream extends InputStream {
	
	private static final byte CR = (byte) '\r';
	private static final byte LF = (byte) '\n';
	private static final byte SP = (byte) ' ';
	private static final byte HT = (byte) '\t';
	private static final byte COLON = (byte) ':';
	private static final int LC_OFFSET = 'A' - 'a';
	
	protected int pos;
	protected int count;
	
	protected InputStream is;
	protected byte buf[];
	
	public SocketInputStream(InputStream is, int bufferSize) {
		this.is = is;
		buf = new byte[bufferSize];
		
	}
	
	public void readRequestLine(HttpRequestLine requestLine) throws IOException {
		
		// Recycling check
		if (requestLine.methodEnd != 0) {
			requestLine.recycle();
		}
		
		// Checking for a blank line
		int chr = 0;
		do {
			try {
				chr = read();
			} catch (IOException e) {
				chr = -1;
			}
		} while ((chr == CR) || (chr == LF));
		
		if (chr == -1) {
			throw new EOFException("Couldn't read line");
		}
		
		pos--;
		
		// Reading the mothod name
		int maxRead = requestLine.method.length;
		int readStart = pos;
		int readCount = 0;
		
		boolean space = false;
		while (!space) {
			// if the buffer is full,extend it
			if (readCount > maxRead) {
				if ((2 * maxRead) <= HttpRequestLine.MAX_METHOD_SIZE) {
					char[] newBuffer = new char[2*maxRead];
					System.arraycopy(requestLine.method, 0, newBuffer, 0, maxRead);
					requestLine.method = newBuffer;
					maxRead = requestLine.method.length;
				}
				else {
					throw new IOException("Line too long");
				} 
			}
			
			// We're at the end of the internal buffer
			if (pos >= count) {
				int val = read();
				if (val == -1) {
					throw new IOException("Couldn't read line");
				}
				pos = 0;
				readStart = 0;
			}
			if (buf[pos] == SP) {
				space = true;
			}
			requestLine.method[readCount] = (char)buf[pos];
			readCount++;
			pos++;
		}
		
		requestLine.methodEnd = readCount - 1;
		
		// read uri
		maxRead = requestLine.uri.length;
		readStart = pos;
		readCount = 0;
		
		space = false;
		boolean eof = false;
		
		while(!space) {
			// if the buffer is full extend it
			if (readCount >= maxRead) {
				if ((2 * maxRead) <= requestLine.MAX_URI_SIZE) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.uri, 0, newBuffer, 0, maxRead);
					requestLine.uri = newBuffer;
					maxRead = requestLine.uri.length;
				}
				else {
					throw new IOException("Line too long");
				}
			}
			
			// We're at the end of the intenal buffer;
			if (pos >= count) {
				int val = read();
				if (val == -1) {
					throw new IOException("Couldn't read line");
				}
				pos = 0;
				readStart = 0;
			}
			
			if (buf[pos] == SP) {
				space = true;
			} else if((buf[pos] == CR) || (buf[pos] == LF)) {
				// HTTP/0.9 style request
				eof = true;
				space = true;
			
			}
			requestLine.uri[readCount] = (char)buf[pos];
			readCount++;
			pos++;
		}
		
		requestLine.uriEnd = readCount - 1;
		
		// Reading the protocol
		
		maxRead = requestLine.protocol.length;
		readStart = pos;
		readCount = 0;
		
		while(!eof) {
			// if the buffer is full extend it
			if (readCount >= maxRead) {
				if ((2 * maxRead) <= requestLine.MAX_PROTOCOL_SIZE) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.protocol, 0, newBuffer, 0, maxRead);
					requestLine.protocol = newBuffer;
					maxRead = requestLine.protocol.length;
				}
				else {
					throw new IOException("Line too long");
				}
			}
			
			// We're at the end the internal buffer
			if (pos >= count) {
				int val = read();
				if (val == -1) {
					throw new IOException("Couldn't read line");
				}
				pos = 0;
				readStart = 0;
			}
			
			if (buf[pos] == CR) {
				// Skip
			} else if (buf[pos] == LF) {
				eof = true;
			} else {
				requestLine.protocol[readCount] = (char)buf[pos];
				readCount++;
			}
			pos++;		
		}
		
		requestLine.protocolEnd = readCount;
	}
	
	public void readHeader(HttpHeader header) {
		header.nameEnd = 0;
		header.valueEnd = 0;
	}
	public int read() throws IOException {
		if (pos >= count) {
			fill();
			if (pos >= count) {
				return -1;
			}
		}
		return buf[pos++] & 0xff;
	}
	
	protected void fill() throws IOException {
		pos = 0;
		count = 0;
		int nRead = is.read(buf, 0, buf.length);
		if (nRead > 0) {
			count = nRead;
		}
	}
}
