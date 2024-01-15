import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.ess.blankrc.remote.EchoService;
import ch.ess.blankrc.remote.LogonService;
import ch.ess.blankrc.remote.support.TicketHolder;


/**
 * @author sr
 */
public class Test {
  public static void main(String[] args) {
    
    ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
    
    
    LogonService logonService = (LogonService)context.getBean("logonService");
    TicketHolder holder = (TicketHolder)context.getBean("ticketHolder");
    
    String ticket = logonService.logon("admin", DigestUtils.shaHex("admin"));
    if (ticket != null) {
      System.out.println(ticket);
      holder.setTicket(ticket);

      EchoService basic = (EchoService)context.getBean("echoService");
      System.out.println("hello(): " + basic.echo("Hallo"));

      
    } else {
      System.out.println("login failed");
    }
    
    

    


  }
}
