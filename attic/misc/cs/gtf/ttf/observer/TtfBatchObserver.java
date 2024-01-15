package gtf.ttf.observer;

import java.util.*;
import java.io.*;
import java.text.*;

import gtf.common.*;

import common.net.*;
import common.util.*;

public class TtfBatchObserver implements gtf.common.Constants {
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yy");
	private String[] logFiles;
	private String[] centers;
	private String smtp;
	private String sender;
	private List receiverList;
	private String todayString;
	private String errorString;
	private String okStartString;
	private String okEndString;

	private TtfBatchObserver() {
		
		List logFilesList = AppProperties.getStringArrayProperty(P_TTF_LOG_FILE);

		logFiles = (String[])logFilesList.toArray(new String[logFilesList.size()]);

		List centerList = AppProperties.getStringArrayProperty(P_TTF_LOG_CENTER);

		centers = (String[])centerList.toArray(new String[centerList.size()]);
		smtp = AppProperties.getStringProperty(P_SMTP_HOST);
		sender = AppProperties.getStringProperty(P_TTF_LOG_MAIL_SENDER);
		receiverList = AppProperties.getStringArrayProperty(P_TTF_LOG_MAIL_RECEIVER);

		Calendar rightNow = Calendar.getInstance();

		todayString = dateFormat.format(rightNow.getTime());
		errorString = AppProperties.getStringProperty(P_TTF_LOG_ERROR_STR);
		okStartString = AppProperties.getStringProperty(P_TTF_LOG_OK_START_STR);
		okEndString = AppProperties.getStringProperty(P_TTF_LOG_OK_END_STR);
	}

	private void go() {
		for (int i = 0; i < logFiles.length; i++) {
			if (Constants.DEBUG) {
				System.out.println(logFiles[i]);
			} 

			File logFile = new File(logFiles[i]);

			if (logFile.exists()) {
				if (Constants.DEBUG) {
					System.out.println("file exists");
				} 

				List lines = readFile(logFile);

				if (searchString(lines, todayString)) {
					if (Constants.DEBUG) {
						System.out.println(todayString + " found");
					} 

					List msg = new ArrayList();

					msg.add("Logfile: " + logFiles[i]);
					msg.add("");
					msg.addAll(lines);

					if (!searchString(lines, errorString)) {
						if (Constants.DEBUG) {
							System.out.println(errorString + " not found");
						} 

						if (searchString(lines, okStartString) 
								  && searchString(lines, okEndString)) {
							if (Constants.DEBUG) {
								System.out.println("LogFile ok");

								// sendMail("TtfBatch "+centers[i]+" OK", msg);
							} 
						} else {
							if (Constants.DEBUG) {
								System.out.println("Missing start or end string");
							} 

							sendMail("TtfBatch " + centers[i] + " ERROR", msg);
						} 
					} else {
						if (Constants.DEBUG) {
							System.out.println(errorString + " found");
						} 

						sendMail("TtfBatch " + centers[i] + " ERROR", msg);
					} 
				} else {
					if (Constants.DEBUG) {
						System.out.println(todayString + " not found");
					} 

					sendMail("TtfBatch " + centers[i] + " ERROR", 
								"Logfile: " + logFiles[i] + " nicht vom : " 
								+ todayString);
				} 
			} else {
				if (Constants.DEBUG) {
					System.out.println(logFiles[i] + " not found");
				} 

				sendMail("TtfBatch " + centers[i] + " ERROR", 
							"Logfile: " + logFiles[i] + " nicht gefunden");
			} 
		} 
	} 

	private List readFile(File file) {
		List lines = null;

		try {
			String line;
			lines = new ArrayList();
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((line = br.readLine()) != null) {
				lines.add(line);
			} 

			br.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		} 

		return lines;
	} 

	private boolean searchString(List l, String date) {
		Iterator it = l.iterator();

		while (it.hasNext()) {
			String line = (String)it.next();

			if (line.indexOf(date) != -1) {
				return true;
			} 
		} 

		return false;
	} 

	private void sendMail(String subject, String msg) {
		try {
			MailSender ms = new MailSender(smtp, Constants.DEBUG);
			ms.sendMail(sender, receiverList, subject, msg);
		} catch (Exception e) {
			System.err.println(e);
		} 
	} 

	private void sendMail(String subject, List msg) {
		try {
			MailSender ms = new MailSender(smtp, Constants.DEBUG);

			ms.sendMail(sender, receiverList, subject, msg);
		} catch (Exception e) {
			System.err.println(e);
		} 
	} 

	public static void main(String args[]) {
		new TtfBatchObserver().go();
	} 

}