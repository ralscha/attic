package ch.ess.common.mail;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public abstract class Mail {

  private Map context;
  private ResourceBundle messages;
  private Locale locale;

  public static synchronized void send(MailType type, Map context) throws MailException {
    send(type, context, null);
  }

  public static synchronized void send(MailType type, Map context, Locale locale) throws MailException {

    Mail mail = null;

    try {

      Class mailClass = (Class)MailQueue.getConfig().getClassMap().get(type);
      mail = (Mail)mailClass.newInstance();
      if (locale != null) {
        mail.setLocale(locale);
      }

      if (mail == null) {
        throw new MailException("no mail for type found");
      }

      if (context == null) {
        throw new NullPointerException("context == null");
      }

      mail.setContext(context);
      mail.init();

      //Wurde Locale gesetzt, wenn nein default
      if (mail.getLocale() == null) {
        mail.setLocale(Locale.getDefault());
      }

      mail.send();

    } catch (Exception e) {
      throw new MailException(e);
    } finally {
      if (mail != null) {
        mail.release();
      }
    }
  }

  protected abstract String getBody() throws MailException;

  protected abstract String getSubject();

  protected abstract Recipient getRecipient();

  protected abstract void init();

  protected abstract void release();

  protected boolean isHTML() {
    return false;
  }

  protected MailAddress getReplyTo() {

    //default behaviour: no reply to address
    return null;
  }

  protected void setContext(Map context) {
    this.context = context;
  }

  public Map getContext() {
    return this.context;
  }

  protected void setLocale(Locale locale) {
    this.locale = locale;

    String bundle = MailQueue.getConfig().getBundle();
    if (bundle == null) {
      messages = ResourceBundle.getBundle("ct", this.locale);
    } else {
      messages = ResourceBundle.getBundle(bundle, this.locale);
    }
  }

  protected Locale getLocale() {
    return this.locale;
  }

  protected String getString(String key, Object[] params) {

    MessageFormat formatter = new MessageFormat(messages.getString(key));

    formatter.setLocale(getLocale());

    return formatter.format(params);
  }

  protected String getGreeting() {

    String greetingsKey = MailQueue.getConfig().getGreetingsKey();

    if (greetingsKey != null) {
      return messages.getString(greetingsKey);
    } else {
      return "";
    }

  }

  protected String getString(String key) {
    return messages.getString(key);
  }

  private void send() throws MailException {

    try {
      MailMessage mm = new MailMessage();
      Recipient to = getRecipient();

      if (to == null) {
        throw new MailException("no receiver found");
      }

      mm.addFrom(to.getSender());
      mm.addTo(to.getRecipient());

      if (to.getCC() != null) {
        mm.addCC(to.getCC());
      }

      MailAddress replyTo = getReplyTo();

      if (replyTo != null) {
        mm.addReplyTo(replyTo.getAddress());
      }

      mm.setSubject(getSubject());

      if (isHTML()) {
        mm.setHTML(getBody());
      } else {
        mm.setText(getBody());
      }

      mm.send();
    } catch (MessagingException me) {
      throw new MailException(me);
    } catch (InterruptedException e) {
      throw new MailException(e);
    }
  }
}
