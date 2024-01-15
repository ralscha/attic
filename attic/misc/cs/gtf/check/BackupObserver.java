package gtf.check;

import java.util.*;
import java.io.*;
import java.text.*;

import common.io.*;
import common.net.*;
import common.util.*;

public class BackupObserver {

	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	private Calendar backupDate;
	private Calendar deleteDate;
	private Calendar deleteLocalDate;
	
	private String smtp;
	private String sender;
	private List receiverList;
	
	private void doIt() {

		String status = "OK";
		
		deleteDate = Calendar.getInstance();
		backupDate = Calendar.getInstance();
		deleteLocalDate = Calendar.getInstance();
		
		int keep = AppProperties.getIntegerProperty("keep.days", 6);
		int keepLocal = AppProperties.getIntegerProperty("keep.days.local", 6);
		
		if (backupDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			backupDate.add(Calendar.DATE, -3);
			keep += 2;
		} else {
			backupDate.add(Calendar.DATE, -1);
		}
		
		if (deleteDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			keep += 2;
		}
		
		deleteDate.add(Calendar.DATE, -keep);
		deleteLocalDate.add(Calendar.DATE, -keepLocal);
		
		smtp = AppProperties.getStringProperty("smtp.host");
		sender = AppProperties.getStringProperty("mail.sender");
		receiverList = AppProperties.getStringArrayProperty("mail.receiver");

		File logPath = new File(AppProperties.getStringProperty("log.path"));
		File backupPath = new File(AppProperties.getStringProperty("backup.path"));
		File zipPath = new File(AppProperties.getStringProperty("zip.path"));

		String backupDateStr = dateFormat.format(backupDate.getTime());
		String deleteDateStr = dateFormat.format(deleteDate.getTime());
		String lookFileName = backupDateStr + ".zip";
		String deleteFileName = deleteDateStr + ".zip";


		List msg = new ArrayList();
		boolean found = false;

		if (zipPath.exists() && zipPath.isDirectory()) {
			File[] files = zipPath.listFiles(new IncludeEndingFileFilter(".zip"));
			for (int i = 0; i < files.length; i++) {

				if (files[i].getName().compareTo(deleteFileName) <= 0) {
					if (!files[i].delete()) {
						msg.add("delete " + files[i].getPath() + " failed");
						if (status.equals("OK"))
							status = "WARNING";
					}
				}

				if (files[i].getName().equals(lookFileName)) {
					found = true;
				}

			}
			if (!found) {
				status = "ERROR";
				msg.add("no backup found : "+backupDateStr);
			} 
			
		} else {
			status = "ERROR";			
			msg.add(zipPath.getPath() + " not found");
		}



		//Delete Log files
		deleteFileName = "log"+deleteDateStr;

		if (logPath.exists() && logPath.isDirectory()) {
			File[] files = logPath.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().startsWith("log")) {
					if (files[i].getName().compareTo(deleteFileName) <= 0) {
						if (!files[i].delete()) {
							msg.add("delete " + files[i].getPath() + " failed");
							if (status.equals("OK"))							
								status = "WARNING";
						}
					}
				}

			}
		} else {
			status = "ERROR";			
			msg.add(zipPath.getPath() + " not found");
		}


		//Backup files
		//nur wenn alles OK ist, löschen
		if (status.equals("OK")) {
			deleteFileName = dateFormat.format(deleteLocalDate.getTime());
	
			if (backupPath.exists() && backupPath.isDirectory()) {
				File[] files = backupPath.listFiles();
				for (int i = 0; i < files.length; i++) {
	
					if (files[i].getName().compareTo(deleteFileName) <= 0) {
						if (files[i].isDirectory()) {
							deleteFiles(files[i]);
							if (!files[i].delete()) {
								msg.add("delete " + files[i].getPath() + " failed");
								if (status.equals("OK"))
									status = "WARNING";
							}
						}
					}
	
				}
			} else {
				status = "ERROR";
				msg.add(zipPath.getPath() + " not found");
			}
		}

		String center = AppProperties.getStringProperty("proc.center", "");
		if (!AppProperties.getBooleanProperty("send.ok.mail", true)) {
			if (!"OK".equals(status)) {
				sendMail("Backup: "+center+" "+backupDateStr+" "+status, msg);
			}
		} else {	
			sendMail("Backup: "+center+" "+backupDateStr+" "+status, msg);
		}

	}

	public void deleteFiles(File dir) {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

	private void sendMail(String subject, List msg) {
		try {
			MailSender ms = new MailSender(smtp, false);

			ms.sendMail(sender, receiverList, subject, msg);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static void main(String args[]) {
		new BackupObserver().doIt();
	}

}