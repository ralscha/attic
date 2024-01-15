package chap2;

import javax.management.*;

import com.sun.jdmk.comm.*;

public class HelloAgent implements NotificationListener {

  private MBeanServer mbs = null;
  
  public HelloAgent() {
    mbs = MBeanServerFactory.createMBeanServer("HelloWorldAgent");
    HtmlAdaptorServer adapter = new HtmlAdaptorServer();
    HelloWorld hw = new HelloWorld();
    
    ObjectName helloWorldName = null;
    ObjectName adapterName = null;
    
    try {
      adapterName = new ObjectName("HelloWorldAgent:name=htmladapter,port=9092");
      helloWorldName = new ObjectName("HelloWorldAgent:name=helloWorld1");
      
      mbs.registerMBean(hw, helloWorldName);
      
      hw.addNotificationListener(this, null, null);      
      
      adapter.setPort(9092);
      mbs.registerMBean(adapter, adapterName);
      adapter.start();
      
      
    } catch (Exception e) {
      e.printStackTrace();      
    }
  }
  
  public static void main(String[] args) {
    System.out.println("HelloWorldAgent is running");
    HelloAgent agent = new HelloAgent();
  }
  
  public void handleNotification(Notification notif, Object handback) {
    System.out.println("receiving notification ...");
    System.out.println(notif.getType());
    System.out.println(notif.getMessage());
  }

}
