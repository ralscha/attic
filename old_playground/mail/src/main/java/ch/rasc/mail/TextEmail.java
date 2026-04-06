package ch.rasc.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class TextEmail {

	public static void main(String[] args) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("localhost");
		email.setFrom("boss@test.com");
		email.addTo("test@test.com");
		email.setSubject("The subject");
		email.setMsg("The content");
		email.send();
	}

}
