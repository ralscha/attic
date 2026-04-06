package ch.rasc.mail;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class HTMLEmail {

	public static void main(String[] args) throws EmailException, MalformedURLException {

		HtmlEmail email = new HtmlEmail();
		email.setHostName("localhost");
		email.setFrom("boss@test.com", "The Boss");
		email.addTo("test@test.com", "John Test");
		email.setSubject("Email with inline image");

		URL imageUrl = new URL("http://www.flags.net/images/largeflags/SWIT0001.GIF");
		String cid = email.embed(imageUrl, "The Swiss Flag");

		email.setHtmlMsg("<html>Swiss Flag - <img src=\"cid:" + cid + "\"></html>");

		email.setTextMsg("Your email client does not support HTML messages");

		email.send();

	}

}
