import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
	
public class HelloWorld extends Task {
	String message;
	public void setMessage(String message) {
		this.message = message;
	}
	public void execute() {
		if (message == null) {
			throw new BuildException("No message set.");
		}
		log(message);
		System.out.println("Hello World!");
	}
}
