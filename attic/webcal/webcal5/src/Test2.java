import java.math.BigDecimal;

public class Test2 {


  public static void main(String[] args) {
//      ApplicationContext ap = new ClassPathXmlApplicationContext(new String[]{"/spring-datasource-local.xml",
//          "/spring.xml", "/spring-mail.xml", "/spring-hibernate.xml", "/spring-init.xml"});
//
//      
//      Test2 test2 = (Test2)ap.getBean("test");
//      File f = new File(test2.tmpDir);
//      f.mkdirs();
//      System.out.println(test2.tmpDir);

    System.out.println(new BigDecimal("-1").compareTo(new BigDecimal(0)) > 0);
  }
}
