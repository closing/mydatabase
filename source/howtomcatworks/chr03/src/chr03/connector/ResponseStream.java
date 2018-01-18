package chr03.connector;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

import chr03.connector.http.HttpResponse;

public class ResponseStream extends ServletOutputStream {
	
	protected boolean commit;
	
	public ResponseStream(HttpResponse response) {
		super();
		commit = false;
	}
	
	public void setCommit(boolean commit) {
		this.commit = commit;
	}
	public boolean getCommit() {
		return (this.commit);
	}
	
	// Override
	public boolean isReady() {
		return false;
	}
	public void setWriteListener(WriteListener writeListener) {
		
	}
	public void write(int b) {
	}
}
