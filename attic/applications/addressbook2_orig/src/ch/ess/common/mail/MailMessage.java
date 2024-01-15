package ch.ess.common.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class MailMessage {

  private List fromList;
  private List toList;
  private List ccList;
  private List bccList;
  private List replyToList;
  private String subject;
  private String text;
  private boolean html;
  private MimeMultipart mp;
  private long nextTry;
  private int tries;
  private Exception lastException;
  private long timeout;
  private int maxTries;
  private Address[] test;

  public MailMessage() {

    tries = 0;
    nextTry = -1;
    test = null;
  }

  public void send() throws MessagingException, InterruptedException {
    MailQueue.put(this);
  }

  public void setTimeout(int timeoutSec) {
    this.timeout = timeoutSec * 1000;
  }

  public void setMaxTries(int tries) {
    this.maxTries = tries;
  }

  public void setTest(String test) throws AddressException {
    this.test = new InternetAddress[] { new InternetAddress(test)};
  }

  private List addAddress(List list, String address) throws AddressException {

    if (list == null) {
      list = new ArrayList();
    }

    if (address.indexOf(";") != -1) {
      StringTokenizer st = new StringTokenizer(address, ";");

      while (st.hasMoreTokens()) {
        list.add(new InternetAddress(st.nextToken()));
      }
    } else if (address.indexOf(",") != -1) {
      StringTokenizer st = new StringTokenizer(address, ",");

      while (st.hasMoreTokens()) {
        list.add(new InternetAddress(st.nextToken()));
      }
    } else {
      list.add(new InternetAddress(address));
    }

    return list;
  }

  private List addAddress(List list, String address, String personal) throws UnsupportedEncodingException {

    if (list == null) {
      list = new ArrayList();
    }

    list.add(new InternetAddress(address, personal));

    return list;
  }

  //TO Addresses
  public void addTo(String address, String personal) throws UnsupportedEncodingException {
    toList = addAddress(toList, address, personal);
  }

  public void addTo(String address) throws AddressException {
    toList = addAddress(toList, address);
  }

  public void addTo(String[] addresses) throws AddressException {

    if (addresses != null) {
      for (int i = 0; i < addresses.length; i++) {
        toList = addAddress(toList, addresses[i]);
      }
    }
  }

  //CC Addresses
  public void addCC(String address, String personal) throws UnsupportedEncodingException {
    ccList = addAddress(ccList, address, personal);
  }

  public void addCC(String address) throws AddressException {
    ccList = addAddress(ccList, address);
  }

  public void addCC(String[] addresses) throws AddressException {
    if (addresses != null) {
      for (int i = 0; i < addresses.length; i++) {
        ccList = addAddress(ccList, addresses[i]);
      }
    }
  }

  //BCC Addresses
  public void addBCC(String address, String personal) throws UnsupportedEncodingException {
    bccList = addAddress(bccList, address, personal);
  }

  public void addBCC(String address) throws AddressException {
    bccList = addAddress(bccList, address);
  }

  //From Addresses
  public void addFrom(String address, String personal) throws UnsupportedEncodingException {
    fromList = addAddress(fromList, address, personal);
  }

  public void addFrom(String address) throws AddressException {
    fromList = addAddress(fromList, address);
  }

  //Reply To Addresses
  public void addReplyTo(String address, String personal) throws UnsupportedEncodingException {
    replyToList = addAddress(replyToList, address, personal);
  }

  public void addReplyTo(String address) throws AddressException {
    replyToList = addAddress(replyToList, address);
  }

  //Subject
  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getSubject() {
    return subject;
  }

  //Text
  public void setText(String text) {

    this.text = text;
    if (text == null) {
      this.text = "";
    }

    this.html = false;
    this.mp = null;
  }

  public void setHTML(String text) {

    this.text = text;
    this.html = true;
    this.mp = null;
  }

  public boolean isHTML() {
    return html;
  }

  public boolean isMimeMultipart() {
    return (mp != null);
  }

  public String getText() {
    return text;
  }

  public void setMimeMultipart(MimeMultipart mp) {
    this.mp = mp;
  }

  public MimeMultipart getMimeMultipart() {
    return mp;
  }

  void checkMessage() throws MessagingException {

    if (fromList == null) {
      throw new MessagingException("no FROM address");
    }

    if (toList == null) {
      throw new MessagingException("no TO address");
    }
  }

  void fillMessage(MimeMessage msg) throws MessagingException {

    msg.addFrom((Address[])fromList.toArray(new Address[fromList.size()]));

    if (replyToList != null) {
      msg.setReplyTo((Address[])replyToList.toArray(new Address[replyToList.size()]));
    }

    if (test == null) {
      msg.addRecipients(Message.RecipientType.TO, (Address[])toList.toArray(new Address[toList.size()]));

      if (ccList != null) {
        msg.addRecipients(Message.RecipientType.CC, (Address[])ccList.toArray(new Address[ccList.size()]));
      }

      if (bccList != null) {
        msg.addRecipients(Message.RecipientType.BCC, (Address[])bccList.toArray(new Address[bccList.size()]));
      }
    } else {
      msg.addRecipients(Message.RecipientType.TO, test);

      if (text != null) {
        text = text + getReceiversString();
      }
    }

    msg.setSubject(subject);
    msg.setSentDate(new Date());

    if (mp != null) {
      msg.setContent(mp);
    } else {
      if (html) {
        msg.setContent(text, "text/html");
      } else {
        msg.setText(text);
      }
    }
  }

  public long getNextTry() {
    return nextTry;
  }

  public void setNextTry() {

    if (tries <= maxTries) {
      nextTry = System.currentTimeMillis() + timeout;
    } else {
      nextTry = -1;
    }
  }

  public void incTries() {
    tries++;
  }

  public void setException(Exception e) {
    lastException = e;
  }

  public Exception getException() {
    return lastException;
  }

  public String toString() {

    StringBuffer sb = new StringBuffer(100);

    if (fromList != null) {
      sb.append("FROM:\n");

      for (int i = 0; i < fromList.size(); i++) {
        sb.append(((Address)fromList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    sb.append(getReceiversString());

    if (replyToList != null) {
      sb.append("REPLY TO:\n");

      for (int i = 0; i < replyToList.size(); i++) {
        sb.append(((Address)replyToList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    if (subject != null) {
      sb.append("Subject: " + subject);
      sb.append("\n");
    }

    if (text != null) {
      sb.append("Text: " + text);
      sb.append("\n");
    }

    if (mp != null) {
      sb.append("MimeMultipart : " + mp);
      sb.append("\n");
    }

    return sb.toString();
  }

  private String getReceiversString() {

    StringBuffer sb = new StringBuffer(100);

    if (toList != null) {
      sb.append("\n\nTO:\n");

      for (int i = 0; i < toList.size(); i++) {
        sb.append(((Address)toList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    if (ccList != null) {
      sb.append("CC:\n");

      for (int i = 0; i < ccList.size(); i++) {
        sb.append(((Address)ccList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    if (bccList != null) {
      sb.append("BCC:\n");

      for (int i = 0; i < bccList.size(); i++) {
        sb.append(((Address)bccList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    return sb.toString();
  }
}
