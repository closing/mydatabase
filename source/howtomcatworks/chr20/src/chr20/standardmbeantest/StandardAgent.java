package chr20.standardmbeantest;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.Attribute;

public class StandardAgent {
  private MBeanServer mBeanServer = null;
  public StandardAgent() {
    mBeanServer= MBeanServerFactory.createMBeanServer();
  }
  public MBeanServer getMBeanServer() {
    return this.mBeanServer;
  }
  public ObjectName createObjectName(String name) {
    ObjectName objectName = null;
    try{
      objectName = new ObjectName(name);
    } 
    catch(Exception e) {
    } 
    return objectName;
  }
  private void createStandardBean(ObjectName objectName,String managedResourceClassName) {
    try {
      mBeanServer.createMBean(managedResourceClassName,objectName);
    }
    catch(Exception e){      
    }
  }
  public static void main(String[] args){
    StandardAgent agent = new StandardAgent();
    MBeanServer mBeanServer = agent.getMBeanServer();
    String domain  =mBeanServer.getDefaultDomain();
    String managedResourceClassName = "chr20.standardmbeantest.Car";
    ObjectName objectName=agent.createObjectName(domain+":type="+managedResourceClassName);
    agent.createStandardBean(objectName, managedResourceClassName);
    try {
      Attribute colorAttribute=new Attribute("Color","Blue");
      mBeanServer.setAttribute(objectName, colorAttribute);
      System.out.println(mBeanServer.getAttribute(objectName, "Color"));
      mBeanServer.invoke(objectName,"drive",null,null);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
