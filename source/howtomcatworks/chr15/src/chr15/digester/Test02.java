package chr15.digester;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.digester.Digester;

public class Test02 {
  public static void main(String[] args){
    String path = System.getProperty("user.dir") + File.separator + "etc";
    File file = new File(path, "employee2.xml");
    Digester digester = new Digester();
    digester.addObjectCreate("employee","chr15.digester.Employee");
    digester.addSetProperties("employee");
    digester.addObjectCreate("employee/office","chr15.digester.Office");
    digester.addSetProperties("employee/office");
    digester.addSetNext("employee/office","addOffices");
    digester.addObjectCreate("employee/office/address","chr15.digester.Address");
    digester.addSetProperties("employee/office/address");
    digester.addSetNext("employee/office/address","setAddress");
    try{
      Employee employee = (Employee) digester.parse(file);
      ArrayList offices = employee.getOffices();
      Iterator iterator = offices.iterator();
      System.out.println("----------------------------------------");
      while(iterator.hasNext()){
        Office office = (Office)iterator.next();
        Address address = office.getAddress();
        System.out.print(office.getDescription());
        System.out.println("Address: "+address.getStreetNumber() + " " +address.getStreetName());
        System.out.println("-------------------------");
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
}
