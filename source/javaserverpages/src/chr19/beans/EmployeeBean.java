package chr19.beans;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.Serializable;

public class EmployeeBean implements Serializable {
  private static final long serialVersionUID = -1L;

  private String userName;
  private String firstName;
  private String lastName;
  private String dept;
  private Date empDate;
  private String emailAddr;
  private String password;
  private List projects;

  public EmployeeBean() {
  }

  public void setPassword(String value) {
    this.password = value;
  }

  public void setUserName(String value) {
    this.userName = value;
  }

  public void setLastName(String value) {
    this.lastName = value;
  }

  public void setDept(String value) {
    this.dept = value;
  }

  public void setFirstName(String value) {
    this.firstName = value;
  }

  public void setEmailAddr(String value) {
    this.emailAddr = value;
  }

  public void setEmpDate(Date value) {
    this.empDate = value;
  }

  public String getUserName() {
    return this.userName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getDept() {
    return this.dept;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getEmailAddr() {
    return this.emailAddr;
  }

  public Date getEmpDate() {
    return this.empDate;
  }

  public String getPassword() {
    return this.password;
  }

  public String[] getProjects() {
    if (projects == null) {
      return new String[0];
    }
    return (String[]) projects.toArray(new String[projects.size()]);
  }

  public void setProjects(String[] projects) {
    if (projects == null) {
      this.projects = null;
    } else {
      this.projects = Arrays.asList(projects);
    }
  }

  public void setProject(String project) {
    if (projects == null) {
      projects = new ArrayList();
    }
    projects.add(project);
  }
}
