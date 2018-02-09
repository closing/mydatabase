import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import java.util.Vector;
import java.util.Iterator;

public class HelloWorld extends Task {
	String message;
	public void setMessage(String message) {
		this.message = message;
	}
	boolean fail = false;
	public void setFail(boolean fail) {
		this.fail = fail;
	}
	public void execute() {
		if (fail) {
			throw new BuildException("Fail requested");
		}
		
		if (message != null) {
			log(message);
		}
		for (Iterator it = messages.iterator();it.hasNext();) {
			Message msg = (Message)it.next();
			log(msg.getMsg());
		}
		
		System.out.println("Hello World!");
	}
	public void addText(String text) {
		message = text;
	}
	Vector messages = new Vector();
	public Message createMessage() {
		Message msg = new Message();
		messages.add(msg);
		return msg;
	}
	public class Message {
		public Message() {}
		String msg;
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getMsg() {
			return this.msg;
		}
	}
}
