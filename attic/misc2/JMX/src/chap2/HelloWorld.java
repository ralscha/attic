package chap2;

import javax.management.*;

public class HelloWorld extends NotificationBroadcasterSupport implements HelloWorldMBean {

  private String greeting = null;

  public HelloWorld() {
    this.greeting = "Hello World. I am a Standard MBean";
  }

  public HelloWorld(String greeting) {
    this.greeting = greeting;
  }

  public void setGreeting(String greeting) {
    this.greeting = greeting;  
    
    Notification notification = new Notification("chap2.HelloWorld.test", this, -1, System.currentTimeMillis(), greeting);
    sendNotification(notification);
  }

  public String getGreeting() {
    return greeting;
  }

  public void printGreeting() {
    System.out.println(greeting);
  }

}
