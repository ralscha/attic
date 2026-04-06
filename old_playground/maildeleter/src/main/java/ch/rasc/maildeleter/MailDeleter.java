package ch.rasc.maildeleter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailDeleter extends TimerTask {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	private final Config config;

	MailDeleter(Config config) {
		this.config = config;
	}

	@Override
	public void run() {
		Folder folder = null;
		Store store = null;
		try {
			Properties props = System.getProperties();
			Session session = Session.getDefaultInstance(props);

			store = session.getStore("imap");
			store.connect(this.config.getHost(), this.config.getUser(),
					this.config.getPassword());

			folder = store.getFolder("INBOX");

			folder.open(Folder.READ_WRITE);

			LocalDateTime aCoupleOfDaysAgo = LocalDateTime.now()
					.minusDays(this.config.getDays());

			Message[] messages = folder.getMessages();
			for (Message msg : messages) {

				LocalDateTime receivedDateTime = LocalDateTime.ofInstant(
						msg.getReceivedDate().toInstant(), ZoneId.systemDefault());
				if (receivedDateTime.isBefore(aCoupleOfDaysAgo)) {
					logger.info("delete msg: {} ", msg.getMessageNumber());
					msg.setFlag(Flags.Flag.DELETED, true);
				}
			}
		}
		catch (MessagingException e) {
			logger.error("delete emails", e);
		}
		finally {
			if (folder != null && folder.isOpen()) {
				try {
					folder.close(true);
				}
				catch (MessagingException e) {
					logger.error("close folder", e);
				}
			}
			if (store != null) {
				try {
					store.close();
				}
				catch (MessagingException e) {
					logger.error("close store", e);
				}
			}
		}

	}

}
