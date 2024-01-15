package ch.ess.cal.service;

import java.util.Locale;
import java.util.Map;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:12 $
 */
public interface TemplateMailSender {
  void send(Locale locale, SimpleMailMessage message, Map context) throws MailException;
}
