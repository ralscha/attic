import org.springframework.context.support.ClassPathXmlApplicationContext;



public class Test {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    
    
//    TextResourceService fs = (TextResourceService)context.getBean("fieldService");
//    List<HomepageField> fields = fs.getAll();
//    for (HomepageField homepageField : fields) {
//      System.out.println(homepageField.getName());
//    }
  }

}
