package spring;

import java.io.InputStream;

import org.springframework.beans.factory.xml.XmlBeanFactory;

public class TestSpring {

  public static void main(String[] args) {
    
    InputStream is = TestSpring.class.getResourceAsStream("wire.xml");
    XmlBeanFactory bf = new XmlBeanFactory(is);
    IBusiness tb = (IBusiness)bf.getBean("test");
    
    tb.doStuff();
    
  }
}
