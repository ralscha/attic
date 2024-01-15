package ch.ess.common.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class MailSendThread implements Runnable {

  private static Log LOG = LogFactory.getLog(MailSendThread.class);

  private MailQueue pool;
  private Properties sessionProperties;
  private boolean debug;

  public MailSendThread(MailQueue p, MailConfiguration config) {

    this.pool = p;
    this.debug = config.isDebug();
    sessionProperties = new Properties();

    sessionProperties.put("mail.smtp.host", config.getSmtpServer());
  }

  public void run() {

    MailMessage mm;

    while (true) {
      try {
        while ((mm = pool.dequeue()) != null) {
          try {
            Session session = Session.getDefaultInstance(sessionProperties);

            session.setDebug(debug);

            MimeMessage msg = new MimeMessage(session);

            mm.fillMessage(msg);

            //send message
            Transport.send(msg);
          } catch (MessagingException me) {
            LOG.error("send e-mail", me);

            //Status von mm setzen und in die Error Queue stellen
            mm.setException(me);
            pool.addToErrorQueue(mm);
          }
        }
      } catch (InterruptedException ie) {
        return;
      } catch (Exception e) {
        LOG.error("send e-mail: ", e);
      }
    }
  }
}
