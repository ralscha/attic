package gtf.tools;

import common.util.*;
import common.net.*;


public class SendMail {

	public static void main(String args[]) {
		if (args.length == 2) {
			String s = AppProperties.getStringProperty("mail.receiver");
			String s1 = AppProperties.getStringProperty("mail.sender");
			MailSender mailsender = new MailSender(AppProperties.getStringProperty("smtp.host"),
                                       			AppProperties.getBooleanProperty("debug").booleanValue());
			mailsender.sendMail(s1, s, args[0], args[1]);
		} else {
			System.out.println("SendMail <title> <text>");
		}
	}
}