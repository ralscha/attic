import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author sr
 */
public class Test {

  public static void main(String[] args) {
    ApplicationContext ac = new ClassPathXmlApplicationContext("/context.xml");
        
    InsertBeanInterface ib = (InsertBeanInterface)ac.getBean("insertBean");
    ib.insertTest();
    ib.insertTest2();    
  }
}
