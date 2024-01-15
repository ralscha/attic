package ch.ess.timemail;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author sr
 */
public class Starter {

  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");    
  }
}
