package ch.ess.restretto;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author sr
 */
public class Test {

  public static void main(String[] args) {
    
     RistrettoMailSenderImpl rm = new RistrettoMailSenderImpl();
     rm.setDebug(true);
     rm.setHost("mail.ess.ch");
     rm.setPort(25);
    
     SimpleMailMessage mm = new SimpleMailMessage();
     mm.setFrom("sr@ess.ch");
     mm.setReplyTo("sr@ess.ch");
     mm.setTo("sr@ess.ch");
     mm.setBcc("sr@ess.ch");

     mm.setSubject("jetzt geht's צהü");
     mm.setText("coole sache. צהü");
     rm.send(mm);
  }
}
