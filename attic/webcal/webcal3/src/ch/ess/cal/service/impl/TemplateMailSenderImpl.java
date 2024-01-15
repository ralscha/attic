package ch.ess.cal.service.impl;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import ch.ess.cal.persistence.TextResourceDao;
import ch.ess.cal.service.TemplateMailSender;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:12 $
 */
public class TemplateMailSenderImpl implements TemplateMailSender {

  private TextResourceDao textResourceDao;
  private JavaMailSender mailSender;
  private String subject;
  private String body;

  public void send(final Locale locale, final SimpleMailMessage message, final Map ctx) throws MailException {

    final String bodyStr = textResourceDao.getText(body, locale.toString());
    final String subjectStr = textResourceDao.getText(subject, locale.toString());

    final VelocityContext context = new VelocityContext(ctx);

    message.setText(evaluate(context, bodyStr));
    message.setSubject(evaluate(context, subjectStr));

    mailSender.send(message);
  }

  public void setBody(final String bodyTemplate) {
    this.body = bodyTemplate;
  }

  public void setSubject(final String subjectTemplate) {
    this.subject = subjectTemplate;
  }

  public void setMailSender(final JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setTextResourceDao(final TextResourceDao textResourceDao) {
    this.textResourceDao = textResourceDao;
  }

  protected String evaluate(final VelocityContext context, final String resource) throws MailException {
    final StringWriter writer = new StringWriter();
    try {
      Velocity.evaluate(context, writer, "log", resource);
    } catch (Exception e) {
      throw new MailSendException("evaluate", e);
    }

    String output = writer.getBuffer().toString();
    return output;
  }
}
