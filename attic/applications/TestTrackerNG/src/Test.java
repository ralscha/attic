import org.springframework.context.support.ClassPathXmlApplicationContext;
import ch.ess.tt.service.PrincipalService;

public class Test {

  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    PrincipalService cs = (PrincipalService)context.getBean("userService");
    cs.logoutPrincipal();
  }

}
