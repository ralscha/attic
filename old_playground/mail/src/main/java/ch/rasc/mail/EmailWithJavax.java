package ch.rasc.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailWithJavax {

	public static void main(String[] args) throws MessagingException {

		Properties sessionProperties = new Properties();
		sessionProperties.put("mail.smtp.host", "localhost");

		Session mailSession = Session.getDefaultInstance(sessionProperties);

		MimeMessage message = new MimeMessage(mailSession);
		message.setSender(new InternetAddress("boss@test.com"));
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress("test@test.com"));
		message.setSubject("The subject");
		message.setText("The content");

		Transport.send(message);

	}

}
