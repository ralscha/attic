package ch.ess.eventlog;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author sr
 */
public class Main {

  public static void main(String[] args) {
    try {
      new ClassPathXmlApplicationContext("/applicationContext.xml");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
