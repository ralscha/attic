package ch.ess.common.service.mail.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import ch.ess.blank.db.TextResource;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.service.mail.TemplateMailSender;



/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public class TemplateMailSenderImpl implements TemplateMailSender {

  private JavaMailSender mailSender;
  private String subject;
  private String body;

  
  public void send(Locale locale, SimpleMailMessage message, Map ctx) throws MailException {

    String bodyStr = getTextResource(locale, body);
    String subjectStr = getTextResource(locale, subject);

    VelocityContext context = new VelocityContext(ctx);
    
    message.setText(evaluate(context, bodyStr));
    message.setSubject(evaluate(context, subjectStr));

    mailSender.send(message);
  }  

  public void setBody(String bodyTemplate) {
    this.body = bodyTemplate;
  }

  public void setSubject(String subjectTemplate) {
    this.subject = subjectTemplate;
  }

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  protected String getTextResource(Locale locale, String name) throws MailException {

    Transaction tx = null;
    Session sess = null;
    try {
      sess = HibernateSession.getSession();

      tx = sess.beginTransaction();

      List l = TextResource.findName(name);
      String text = "";
      if (!l.isEmpty()) {
        TextResource tr = (TextResource) l.get(0);

        Map trans = tr.getTranslations();
        text = (String) trans.get(locale);
      }

      tx.commit();
      return text;

    } catch (HibernateException e) {
      try {
        tx.rollback();
      } catch (HibernateException e1) {
        throw new MailPreparationException("getTextResource", e1);
      }
      throw new MailPreparationException("getTextResource", e);
    } finally {
      try {
        if (sess != null) {
          sess.close();
        }
      } catch (HibernateException e1) {
        throw new MailPreparationException("getTextResource", e1);
      }
    }
  }

  protected String evaluate(VelocityContext context, String resource) throws MailException {
    StringWriter w = new StringWriter();
    try {
      Velocity.evaluate(context, w, "log", resource);
    } catch (ParseErrorException e) {
      throw new MailPreparationException("evaluate", e);
    } catch (MethodInvocationException e) {
      throw new MailPreparationException("evaluate", e);
    } catch (ResourceNotFoundException e) {
      throw new MailPreparationException("evaluate", e);
    } catch (IOException e) {
      throw new MailPreparationException("evaluate", e);
    }

    String output = w.getBuffer().toString();
    return output;
  }
}