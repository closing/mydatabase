package chr15.digester;
import java.io.File;
import org.apache.commons.digester.Digester;

public class Test01 {
	public static void main (String args[]) {
		String path = System.getProperty("user.dir") + File.separator + "etc";
		
		File file = new File(path, "employee1.xml");
		Digester digester = new Digester();
		digester.addObjectCreate("employee", "chr15.digester.Employee");
		digester.addSetProperties("employee");
		digester.addCallMethod("employee", "printName");
		
		try {
			Employee employee = (Employee)digester.parse(file);
			System.out.println("First name:" + employee.getFirstName());
			System.out.println("Last name:" + employee.getLastName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
