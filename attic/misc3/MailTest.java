
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import java.io.*;

public class MailTest {

  public static void main(String[] args) {

    try {
      Properties prop = new Properties();
		  prop.put("mail.smtp.host", "smtp.datacomm.ch");

	    Session session = Session.getDefaultInstance(prop, null);
	    session.setDebug(true);

      Message msg = new MimeMessage(session);
      InternetAddress addr = new InternetAddress("rschaer@datacomm.ch");
      msg.addRecipients(Message.RecipientType.TO, new InternetAddress[]{addr});

      InternetAddress from = new InternetAddress("rschaer@datacomm.ch");
      msg.setFrom(from);

      msg.setSubject("TEST");

      //msg.setContent("<html><head><title>TEST</title></head><body><h1>Dies ist ein Test</h1></body></html>", "text/html");
      //Transport.send(msg);

      MimeMultipart mp = new MimeMultipart();
      MimeBodyPart text = new MimeBodyPart();
      text.setDisposition(Part.INLINE);
      text.setContent("test, test, test", "text/plain");
      mp.addBodyPart(text);

      MimeBodyPart filePart = new MimeBodyPart();
      File file = new File("base.class");
      FileDataSource fds = new FileDataSource(file);
      DataHandler dh = new DataHandler(fds);
      filePart.setFileName(file.getName());
      filePart.setDisposition(Part.ATTACHMENT);
      filePart.setDescription("Attached file: " + file.getName());
      filePart.setDataHandler(dh);
      mp.addBodyPart(filePart);

      msg.setContent(mp);
      Transport.send(msg);


    } catch (Exception e) {
      System.err.println(e);
    }  
  }
    
}