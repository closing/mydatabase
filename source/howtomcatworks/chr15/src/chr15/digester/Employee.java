package chr15.digester;

import java.util.ArrayList;

public class Employee {
	private String firstName;
	private String lastName;
	private ArrayList offices = new ArrayList();
	
	public Employee () {
		System.out.println("Creating Employee");
	}
	public void setFirstName(String firstName) {
		System.out.println("Setting firstName:" + firstName);
		this.firstName = firstName;
	}
	public String getFirstName() {
		return (this.firstName);
	}
	public void setLastName(String lastName) {
		System.out.println("Setting lastName:" + lastName);
		this.lastName = lastName;
	}
	public String getLastName() {
		return (this.lastName);
	}
	public void addOffices(Office office) {
		System.out.println("Adding Office to this employee");
		offices.add(office);
	}
	public ArrayList getOffices() {
		return (this.offices);
	}
	public void printName() {
		System.out.println("My name is " + firstName + " " + lastName);
	}
}
