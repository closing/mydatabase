package chr15.digester;

import org.apache.commons.digester.RuleSetBase;
import org.apache.commons.digester.Digester;

public class EmployeeRuleSet extends RuleSetBase{
  public void addRuleInstances(Digester digester) {
    digester.addObjectCreate("employee","chr15.digester.Employee");
    digester.addSetProperties("employee");
    digester.addObjectCreate("employee/office","chr15.digester.Office");
    digester.addSetProperties("employee/office");
    digester.addSetNext("employee/office","addOffices");
    digester.addObjectCreate("employee/office/address","chr15.digester.Address");
    digester.addSetProperties("employee/office/address");
    digester.addSetNext("employee/office/address","setAddress");
  }
}
