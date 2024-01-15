package ch.ess.calendar.util;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {

	private String smtp = null;
	private boolean debug = false;

	public MailSender(String smtp) {
		this(smtp, false);
	}
	
	public MailSender(String smtp, boolean debug) {
		this.smtp = smtp;
		this.debug = debug;
	}
	
	public void sendMail(String from, String to, String subject, String msg) {
		String[] m = new String[1];
		m[0] = msg;
		String[] toArray = new String[1];
		toArray[0] = to;
		
		sendMail(from, toArray, subject, m);
	}

	public void sendMail(String from, List to, String subject, String msg) {
		String[] toArray = (String[])to.toArray(new String[to.size()]);
		String[] m = new String[1];
		m[0] = msg;
	
		sendMail(from, toArray, subject, m);
	}

	public void sendMail(String from, String to, String subject, List msg) {		
		String[] m = (String[])msg.toArray(new String[msg.size()]);		
		String[] toArray = new String[1];
		toArray[0] = to;
		sendMail(from, toArray, subject, m);
	}

	public void sendMail(String from, List to, String subject, List msg) throws IOException, ProtocolException, UnknownHostException {
		
		String[] m = (String[])msg.toArray(new String[msg.size()]);
		String[] toArray = (String[])to.toArray(new String[to.size()]);
		
		sendMail(from, toArray, subject, m);
		
	}

	public void sendMail(String from, String[] to, String subject, String[] msgtxt) {
		// create some properties and get the default Session
		Properties props = new Properties();
		props.put("mail.smtp.host", smtp);
		props.put("mail.debug", new Boolean(debug).toString());
	
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(debug);
		
		try {
		    // create a message
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress(from));

			InternetAddress[] address = new InternetAddress[to.length];		    
		    for (int i = 0; i < to.length; i++) {
		    	address[i] = new InternetAddress(to[i]);
		    }
		    msg.setRecipients(Message.RecipientType.TO, address);
		    msg.setSubject(subject);
		    msg.setSentDate(new Date());
		    // If the desired charset is known, you can use
		    // setText(text, charset)
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < msgtxt.length; i++) {
		    	sb.append(msgtxt[i]).append("\n");
		    }
		    msg.setText(sb.toString());
		    
		    Transport.send(msg);
		} catch (MessagingException mex) {
			System.err.println(mex);
		}
	}
	
	public static void main(java.lang.String[] args) {
		//TEST
		
		try {
			MailSender ms = new MailSender("smtp.datacomm.ch", true);
			List msg = new ArrayList();
			msg.add("1. Line");
			msg.add("2. Line");
			msg.add("3. Line");
			
			List to = new ArrayList();
			to.add("ralphschaer@yahoo.com");
			to.add("rschaer@datacomm.ch");
			to.add("rschaer@datacomm.ch");
			to.add("rschaer@datacomm.ch");			
						
			ms.sendMail("ralphschaer@yahoo.com", "rschaer@datacomm.ch", "TEST", msg);
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
}