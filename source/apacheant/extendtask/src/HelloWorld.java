import org.apache.tools.ant.Task;
	
public class HelloWorld extends Task {
	public void execute() {
		String message = project.getProperty("ant.project.name");
		log("Here is project '" + message +"'");
		log("I am used in:" + getLocation());
		System.out.println("Hello World!");
	}
}
