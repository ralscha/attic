package common.log.handler;


import java.text.*;
import java.util.*;
import common.net.MailSender;
import common.util.AppProperties;
import common.log.*;

public class SMTPHandler extends Handler {

	private final static SimpleDateFormat dateFormat = 
					new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	private MailSender ms;
	private String sender;
	private String receiver;
	private String subject;
	private LogEventFormat format;
	
	public SMTPHandler(String prefix, String name) {
		String propertyPrefix = prefix + name;
		
		sender = AppProperties.getStringProperty(propertyPrefix + ".sender");
		receiver = AppProperties.getStringProperty(propertyPrefix + ".receiver");
		subject = AppProperties.getStringProperty(propertyPrefix + ".subject");
		
		String smtp = AppProperties.getStringProperty(propertyPrefix + ".smtp");
		ms = new MailSender(smtp);
		
		format = new LogEventFormat();
	}
	
	public SMTPHandler(String smtp, String sender, String receiver, String subject) {
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		
		ms = new MailSender(smtp);		
		format = new LogEventFormat();
		
	}

	public void handle(LogEvent event) {
		ms.sendMail(sender, receiver, subject + " " + dateFormat.format(new Date()),
					format.format(event));
   	}


}