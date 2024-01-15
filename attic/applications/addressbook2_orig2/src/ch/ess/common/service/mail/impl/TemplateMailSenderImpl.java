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

import ch.ess.addressbook.db.TextResource;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.service.mail.MailException;
import ch.ess.common.service.mail.MailMessage;
import ch.ess.common.service.mail.MailSender;
import ch.ess.common.service.mail.TemplateMailSender;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:10:52 $ 
  */
public class TemplateMailSenderImpl implements TemplateMailSender {

  private MailSender sender;
  private String subject;
  private String body;
  
  public void send(Locale locale, MailMessage message, Map ctx) throws MailException {
    
    String bodyStr = getTextResource(locale, body);
    String subjectStr = getTextResource(locale, subject);
    
    VelocityContext context = new VelocityContext(ctx);
    
    message.setText(evaluate(context, bodyStr));    
    message.setSubject(evaluate(context, subjectStr));
    
    sender.send(message);
  }

  
  public void setBody(String bodyTemplate) {
    this.body = bodyTemplate;
  }

  public void setSubject(String subjectTemplate) {
    this.subject = subjectTemplate;
  }  
    
  public void setSender(MailSender sender) {
    this.sender = sender;
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
        TextResource tr = (TextResource)l.get(0);

        Map trans = tr.getTranslations();
        text = (String)trans.get(locale);
      }

      tx.commit();
      return text;

    } catch (HibernateException e) {
      try {
        tx.rollback();
      } catch (HibernateException e1) {
        throw new MailException("getTextResource", e1);
      }
      throw new MailException("getTextResource", e);
    } finally {
      try {
        if (sess != null) {
          sess.close();
        }
      } catch (HibernateException e1) {
        throw new MailException("getTextResource", e1);
      }
    }
  }

  protected String evaluate(VelocityContext context, String resource) throws MailException {
    StringWriter w = new StringWriter();
    try {
      Velocity.evaluate(context, w, "log", resource);
    } catch (ParseErrorException e) {
      throw new MailException("evaluate", e);
    } catch (MethodInvocationException e) {
      throw new MailException("evaluate", e);
    } catch (ResourceNotFoundException e) {
      throw new MailException("evaluate", e);
    } catch (IOException e) {
      throw new MailException("evaluate", e);
    }

    String output = w.getBuffer().toString();
    return output;
  }  
}
