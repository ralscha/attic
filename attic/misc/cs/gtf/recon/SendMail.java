package gtf.recon;

/**
 * This type was created in VisualAge.
 */
import gtf.recon.util.*;
 
public class SendMail {
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main(java.lang.String[] args) {
	
	if (args.length == 2) {
		String receiver = AppProperties.getProperty("mail.receiver");
		String sender   = AppProperties.getProperty("mail.sender");
		MailSender ms = new MailSender(AppProperties.getProperty("smtp.ip"),
										new Boolean(AppProperties.getProperty("smtp.debug")).booleanValue());
		ms.sendMail(sender, receiver, args[0], args[1]);
	} else {
		System.out.println("SendMail <title> <text>");
	}
}
}