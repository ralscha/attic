package ch.rasc.mail;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class TextEmailWithAttachment {

	public static void main(String[] args) throws EmailException, MalformedURLException {

		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("localhost");
		email.addTo("test@test.com", "John Doe");
		email.setFrom("me@test.com", "Mr. Sender");
		email.setSubject("Email with Attachment");
		email.setMsg("The swiss flag");

		EmailAttachment attachment = new EmailAttachment();
		// attachment.setURL(TextEmailWithAttachment.class.getResource("/commons-logo.png"));
		attachment.setURL(new URL("http://www.flags.net/images/largeflags/SWIT0001.GIF"));
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Swiss Flag");
		attachment.setName("swissflag");

		email.attach(attachment);

		email.send();

	}

}
