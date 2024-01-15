package test;

import java.util.Iterator;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

  public static void main(String[] args) {
    //InputStream is = Main.class.getResourceAsStream("/beans.xml");
    //XmlBeanFactory bf = new XmlBeanFactory(is);
    
    try {
      ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/beans.xml");
      
      
      MyTestBean test = (MyTestBean)ctx.getBean("test");
      System.out.println(test.getId());
      
      
      MyConfig myconf = (MyConfig)ctx.getBean("myconfig");
      Properties props = myconf.getConf();
      
      
      for (Iterator it = props.keySet().iterator(); it.hasNext();) {
        String key = (String)it.next();
        System.out.println(key + " --> " + props.get(key));
        
      }
      
    } catch (BeansException e) {      
      e.printStackTrace();
    } 
    
  }
}
