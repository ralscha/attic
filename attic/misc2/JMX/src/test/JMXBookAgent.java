package test;

import javax.management.*;

import com.sun.jdmk.comm.*;

public class JMXBookAgent implements NotificationListener {

  private MBeanServer server = null;
  
  public JMXBookAgent() {
    System.out.println("\n\tCREATE the MBeanServer.");
    server = MBeanServerFactory.createMBeanServer("JMXBookAgent");
    
    startHTMLAdapter();
    startRMIConnector();

    try {
      ObjectName sm = new ObjectName( "JMXBookAgent:name=string");
      server.createMBean( "javax.management.monitor.StringMonitor", sm);
      server.addNotificationListener( sm, this, null, null );
      ObjectName gm = new ObjectName( "JMXBookAgent:name=gauge");
      server.createMBean( "javax.management.monitor.GaugeMonitor", gm );
      server.addNotificationListener( gm, this, null, null );
      ObjectName cm = new ObjectName( "JMXBookAgent:name=counter");
      server.createMBean( "javax.management.monitor.CounterMonitor", cm );
      server.addNotificationListener( cm, this, null, null );
      ObjectName oo = new ObjectName( "JMXBookAgent:name=subject");
      server.createMBean( "test.ObservableObject", oo );
    } catch (Exception e) {
      ExceptionUtil.printException(e);
    }

  }

  public void handleNotification( Notification not, Object obj ) {
    String type = not.getType();
    System.out.println( type );
  }



  protected void startHTMLAdapter() {
    HtmlAdaptorServer adapter = new HtmlAdaptorServer();
    ObjectName adapterName = null;
    
    try {
      adapter.setPort(9092);
      adapterName = new ObjectName("JMXBookAgent:name=html,port=9092");
      server.registerMBean(adapter, adapterName);

      AuthInfo ai = new AuthInfo();
      ai.setLogin("sr");
      ai.setPassword("fredo");
      adapter.addUserAuthenticationInfo(ai);
      adapter.start();      
      
    } catch (Exception e) {
      ExceptionUtil.printException(e);
      System.out.println("Error Starting HTML Adapter for Agent");
    }  
  }
  
  protected void startRMIConnector() {
    //Todo
  }
  
  public static void main(String[] args) {
    System.out.println("START of JMXBook Agent");
    System.out.println("CREATE the agent ...");
    JMXBookAgent agent = new JMXBookAgent();
    System.out.println("Agent is Ready for Service....");
  }
}
