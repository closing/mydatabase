import java.io.OutputStream;

public interface Servlet {
	public void init() throws Exception;
	public void service(byte[] buffer, OutputStream output) throws Exception;
}
