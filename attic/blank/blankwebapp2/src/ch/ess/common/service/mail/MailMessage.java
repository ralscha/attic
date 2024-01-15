package ch.ess.common.service.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.3 $ $Date: 2004/05/22 16:51:13 $
 */
public class MailMessage {

  private List fromList;
  private List toList;
  private List ccList;
  private List bccList;
  private List replyToList;

  private String charset;
  private String subject;
  private String text;
  private boolean html;
  private MimeMultipart mp;

  public MailMessage(String charset) {
    this.charset = charset;
  }

  public MailMessage() {
    this.charset = "US-ASCII";
  }

  public String getCharset() {
    return charset;
  }

  //TO Addresses
  public void addTo(String address, String personal) throws UnsupportedEncodingException {
    toList = addAddress(toList, address, personal);
  }

  public void addTo(String address) throws UnsupportedEncodingException {
    toList = addAddress(toList, address);
  }

  public Address[] getTo() {
    if (toList != null) {
      return (Address[]) toList.toArray(new Address[toList.size()]);
    }
    return null;

  }

  //CC Addresses
  public void addCC(String address, String personal) throws UnsupportedEncodingException {
    ccList = addAddress(ccList, address, personal);
  }

  public void addCC(String address) throws UnsupportedEncodingException {
    ccList = addAddress(ccList, address);
  }

  public Address[] getCC() {
    if (ccList != null) {
      return (Address[]) ccList.toArray(new Address[ccList.size()]);
    }
    return null;

  }

  //BCC Addresses
  public void addBCC(String address, String personal) throws UnsupportedEncodingException {
    bccList = addAddress(bccList, address, personal);
  }

  public void addBCC(String address) throws UnsupportedEncodingException {
    bccList = addAddress(bccList, address);
  }

  public Address[] getBCC() {
    if (bccList != null) {
      return (Address[]) bccList.toArray(new Address[bccList.size()]);
    }
    return null;

  }

  //From Addresses
  public void addFrom(String address, String personal) throws UnsupportedEncodingException {
    fromList = addAddress(fromList, address, personal);
  }

  public void addFrom(String address) throws UnsupportedEncodingException {
    fromList = addAddress(fromList, address);
  }

  public Address[] getFrom() {
    if (fromList != null) {
      return (Address[]) fromList.toArray(new Address[fromList.size()]);
    }
    return null;

  }

  //Reply To Addresses
  public void addReplyTo(String address, String personal) throws UnsupportedEncodingException {
    replyToList = addAddress(replyToList, address, personal);
  }

  public void addReplyTo(String address) throws UnsupportedEncodingException {
    replyToList = addAddress(replyToList, address);
  }

  public Address[] getReplyTo() {
    if (replyToList != null) {
      return (Address[]) replyToList.toArray(new Address[replyToList.size()]);
    }
    return null;

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

  public boolean isHtml() {
    return html;
  }

  public void setHtmlText(String text) {

    this.text = text;
    this.html = true;
    this.mp = null;
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

  public String toString() {

    StringBuffer sb = new StringBuffer(100);

    if (fromList != null) {
      sb.append("FROM:\n");

      for (int i = 0; i < fromList.size(); i++) {
        sb.append(((Address) fromList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    sb.append(getReceiversString());

    if (replyToList != null) {
      sb.append("REPLY TO:\n");

      for (int i = 0; i < replyToList.size(); i++) {
        sb.append(((Address) replyToList.get(i)).toString());
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
        sb.append(((Address) toList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    if (ccList != null) {
      sb.append("CC:\n");

      for (int i = 0; i < ccList.size(); i++) {
        sb.append(((Address) ccList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    if (bccList != null) {
      sb.append("BCC:\n");

      for (int i = 0; i < bccList.size(); i++) {
        sb.append(((Address) bccList.get(i)).toString());
        sb.append("\n");
      }

      sb.append("\n");
    }

    return sb.toString();
  }

  private List addAddress(List list, String email) throws UnsupportedEncodingException {
    return addAddress(list, email, null);
  }

  private List addAddress(List list, String email, String personal) throws UnsupportedEncodingException {

    if (email == null) {
      return list;
    }

    if (list == null) {
      list = new ArrayList();
    }

    InternetAddress address = new InternetAddress(email, personal, charset);
    list.add(address);
    return list;

  }
}