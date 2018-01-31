package chr20.modelmbeantest2;

import java.net.URL;
import java.io.InputStream;

import javax.management.Attribute;
import javax.management.MalformedObjectNameException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import javax.naming.directory.DirContext;

import org.apache.commons.modeler.Registry;
import org.apache.commons.modeler.ManagedBean;

public class ModelAgent {
  private Registry registry;
  private MBeanServer mBeanServer;
  public ModelAgent() {
    registry = createRegistry();
    try{
      mBeanServer= Registry.getServer();
    }
    catch(Throwable t){
      t.printStackTrace(System.out);
      System.exit(1);
    }
  }  
  public MBeanServer getMBeanServer() {
    return this.mBeanServer;
  }
  public Registry createRegistry(){
    Registry registry=null;
    try {

      URL url = ModelAgent.class.getResource("/chr20/modelmbeantest2/car-mbean-descriptor.xml");      
      InputStream stream = url.openStream();
      Registry.loadRegistry(stream);
      stream.close();
      registry= Registry.getRegistry();
    }catch(Throwable t){
      System.out.println(t.toString());
    }
    return (registry);
  }
  public ModelMBean createModelMBean(String mBeanName) throws Exception {
    
    ManagedBean managed = registry.findManagedBean(mBeanName);
    if ( managed == null) {
      return null;
    }
    
    ModelMBean mbean = managed.createMBean();
    ObjectName objectName = createObjectName();
    return mbean;

  }
  public ObjectName createObjectName() {
    ObjectName objectName = null;
    String domain = mBeanServer.getDefaultDomain();
    try{
      objectName = new ObjectName(domain+":type=MyCar");
    } 
    catch(MalformedObjectNameException e) {
      e.printStackTrace();
    } 
    
    return objectName;
  }
  public static void main(String[] args){
    ModelAgent agent = new ModelAgent();
    MBeanServer mBeanServer = agent.getMBeanServer();
    Car car = new Car();
    ObjectName objectName=agent.createObjectName();
    try {
      ModelMBean modelMBean = agent.createModelMBean("myMBean"); 
      modelMBean.setManagedResource(car,"ObjectReference");
      mBeanServer.registerMBean(modelMBean,objectName);
    }
    catch(Exception e){
      System.out.println(e.toString());
    }
    try{
      Attribute attribute = new Attribute("Color", "green");
      mBeanServer.setAttribute(objectName, attribute);
      String color = (String)mBeanServer.getAttribute(objectName, "Color");
      System.out.println("Color:" + color);
      attribute=new Attribute("Color","blue");
      mBeanServer.setAttribute(objectName, attribute);
      color=(String)mBeanServer.getAttribute(objectName, "Color");
      System.out.println("Color:" + color);
      mBeanServer.invoke(objectName,"drive", null,null);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
