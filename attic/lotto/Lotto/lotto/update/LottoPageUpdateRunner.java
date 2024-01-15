package lotto.update;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import lotto.*;
import lotto.page.*;
import lotto.page.frame.*;
import lotto.util.*;
import lotto.extract.*;
import com.odi.util.*;
import com.odi.*;
import java.text.*;
import java.util.*;
import common.util.*;
import common.net.*;

public class LottoPageUpdateRunner extends Thread {

	private int maxLoop;
	private int waitTime; /* seconds */
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");

	private int nextNr, nextJahr;
	private Calendar nextDatum;
	private Calendar lastDatum;
	private JFrame frame = null;
	private WindowEvent windowClosingEvent;

	
	public LottoPageUpdateRunner() {
		
		try {
			maxLoop = AppProperties.getIntegerProperty("update.runner.maxloop", 60);
		} catch (NumberFormatException nfe) {
			maxLoop = 60;
		}

		try {
			waitTime = AppProperties.getIntegerProperty("update.runner.waittime", 450);
		} catch (NumberFormatException nfe) {
			waitTime = 450;
		}

		Transaction tr = DbManager.startReadTransaction();
		Ausspielungen auss = DbManager.getAusspielungen();
		Ziehung zie = auss.getLastZiehung();
		lastDatum = new GregorianCalendar();
		lastDatum.setTime(zie.getDatum());

		nextNr = zie.getNr() + 1;
		nextJahr = zie.getJahr();
		nextDatum = calculateNextDate(lastDatum);

		if (nextJahr != nextDatum.get(Calendar.YEAR)) {
			nextJahr = nextDatum.get(Calendar.YEAR);
			nextNr = 1;
		}

		NextGewinnsumme.nextDatum = calculateNextDate(nextDatum);
		
		DbManager.commitTransaction(tr);
	}

	
	public void createPageAndSend() {
		NextGewinnsumme.nextDatum = nextDatum;
		
		produceHTMLPages();

		/*
		new FileSender(AppProperties.getStringProperty("path.html.output")).start();
		
		new FileSender(AppProperties.getStringProperty("path.html.output.include"),
						  AppProperties.getStringProperty("ftp.home.include")).start();
		
		FileSender fs = new FileSender(AppProperties.getStringProperty("path.html.output.frame"),
										  AppProperties.getStringProperty("ftp.home.frame"));		
		*/
		produceQuotenHTMLPages();
		/*
		new FileSender(AppProperties.getStringProperty("path.html.output.quoten")).run();
		new FileSender(AppProperties.getStringProperty("path.html.output.quoten.frame"),
						  AppProperties.getStringProperty("ftp.home.frame"));
				
		new DatabaseZipper().zip(true);
		*/
	}
	
	public void run() {
		Ziehung newZie = null;
		int loopCount = 0;

		//Extractor ext1 = new Extractor_Teletext();
		Extractor ext2 = new Extractor_SwissLotto();

		
		//Check for lastDatum
		if (!isToday(nextDatum)) {
			//newZie = ext1.extractZiehung(lastDatum);
			//if (newZie == null)
		  newZie = ext2.extractZiehung(lastDatum);
			
			if (newZie != null) {
				System.out.println("up-to-date");
				return;
			}
		}
		
		while ((Thread.currentThread() == this) && (loopCount < maxLoop) && (newZie == null)) {

			//newZie = ext1.extractZiehung(nextDatum);
			//if (newZie == null)
			newZie = ext2.extractZiehung(nextDatum);

			if (newZie == null) {
				try {
					sleep(waitTime * 1000);
				} catch (InterruptedException ex) {
					return;
				}
			}
			loopCount++;
		}

		if (newZie != null) {
			
			System.out.println(newZie);
			
			Transaction tr = DbManager.startUpdateTransaction();
			Ausspielungen auss = DbManager.getAusspielungen();
			newZie.setNr(nextNr);
			newZie.setJahr(nextJahr);
			auss.addZiehung(newZie);
			DbManager.commitTransaction(tr);

			produceHTMLPages();

			FileSender fs1 = new FileSender(AppProperties.getStringProperty("path.html.output"));
		
			FileSender fs2 = new FileSender(AppProperties.getStringProperty("path.html.output.include"),
							  AppProperties.getStringProperty("ftp.home.include"));
		
			FileSender fs3 = new FileSender(AppProperties.getStringProperty("path.html.output.frame"),
							  AppProperties.getStringProperty("ftp.home.frame"));
							  
			FileSender fs4 = 	new FileSender(AppProperties.getStringProperty("path.html.output.bin"), true);						  
					
			fs1.start();
			fs2.start();
			fs3.start();
			try {
				fs1.join();
				fs2.join();
				fs3.join();
			} catch (InterruptedException ie) {
				System.err.println(ie);
			}
			
			fs4.start();
			try {
				fs4.join();
			} catch (InterruptedException ie) {
				System.err.println(ie);
			}
			
			

			
			loopCount = 0;
			LottoGewinnquote lgq = null;
			JokerGewinnquote jgq = null;
			
			while ((Thread.currentThread() == this) && (loopCount < maxLoop) 
							&& ((lgq == null) || (jgq == null))) {
	
				//lgq = ext1.extractLottoGewinnquote(nextDatum);
				//if (lgq == null)
				lgq = ext2.extractLottoGewinnquote(nextDatum);
	
				//jgq = ext1.extractJokerGewinnquote(nextDatum);
				//if (jgq == null)
				jgq = ext2.extractJokerGewinnquote(nextDatum);
		
				if ((lgq == null) || (jgq == null)) {
					try {
						sleep(waitTime * 1000);
					} catch (InterruptedException ex) {
						return;
					}
				}
				loopCount++;
			}
			
			if ((lgq != null) && (jgq != null)) {
				Calendar nextNext = calculateNextDate(nextDatum);
				
				System.out.println(lgq);
				System.out.println(jgq);
				
				tr = DbManager.startUpdateTransaction();
				auss = DbManager.getAusspielungen();
				Ziehung zie = auss.getLastZiehung();
				zie.setLottoGewinnquote(lgq);
				zie.setJokerGewinnquote(jgq);
				DbManager.commitTransaction(tr);
	
				produceQuotenHTMLPages();
	
				fs1 = new FileSender(AppProperties.getStringProperty("path.html.output.quoten"));
				fs2 = new FileSender(AppProperties.getStringProperty("path.html.output.quoten.frame"),
								  AppProperties.getStringProperty("ftp.home.frame"));
				fs1.start();
				fs2.start();
				try {
					fs1.join();
					fs2.join();
				} catch (InterruptedException ie) {
					System.err.println(ie);
				}

				FileSender zipSender = new DatabaseZipper().zip(true);
				try {
					zipSender.join();
				} catch (InterruptedException ie) {
					System.err.println(ie);
				}
				System.out.println("END SEND");
			}
			
		}

	}
	
	public void closeFrame() {
		if (frame != null) {
			windowClosingEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
			frame.dispatchEvent(windowClosingEvent);
		}
	}
	
	private void produceHTMLPages() {
		System.out.println("produceHTMLPages");
		Transaction tr = DbManager.startReadTransaction();
		final Ausspielungen auss = DbManager.getAusspielungen();
		
		new Producer_Aktuelle().producePage(auss, null);
		new Producer_Ausstehend().producePage(auss, null);
		new Producer_Date().producePage(auss, null);
		new Producer_Paar().producePage(auss, null);
		new Producer_Verbund().producePage(auss, null);
		new Producer_Haeufigkeit().producePage(auss, null);
		new Producer_GeradeUngerade().producePage(auss, null);
		new Producer_GeradeUngerade_ChartData().producePage(auss, null);
		
		Producer_GeradeUngerade_Charts pguc = new Producer_GeradeUngerade_Charts();
		pguc.producePage(auss, null);
		this.frame = pguc.getFrame();
		
		new Producer_Summe().producePage(auss, null);
		new Producer_EndziffernSumme().producePage(auss, null);
		new Producer_Quersumme().producePage(auss, null);
		new Producer_SummeDifferenz().producePage(auss, null);
		new Producer_Klasse3().producePage(auss, null);
		new Producer_Klasse5().producePage(auss, null);
		new Producer_Klasse9().producePage(auss, null);		
		new Producer_TiefeHohe().producePage(auss, null);
		new Producer_TiefeHoheEndziffern().producePage(auss, null);

		new Producer_Tips().producePage(auss, null);
		new Producer_Ziehungen_Txt().producePage(auss, null);

		
		//Frame Pages
		new Producer_F_Ausstehend().producePage(auss, null);
		new Producer_F_Title().producePage(auss, null);
		new Producer_F_Aktuelle().producePage(auss, null);
		new Producer_F_Tips().producePage(auss, null);
		new Producer_F_GeradeUngerade().producePage(auss, null);
		new Producer_F_Verbund().producePage(auss, null);		
		new Producer_F_Haeufigkeit().producePage(auss, null);
		new Producer_F_Paar().producePage(auss, null);
		
		new Producer_Appl_Data().producePage(auss,  null);
		
		Properties props = new Properties();
		props.put("withDate", Boolean.TRUE);
		props.put("joker", Boolean.TRUE);
		props.put("year", new Integer(2002));
		props.put("asc", Boolean.FALSE);		
		new Producer_Ziehungen().producePage(auss, props);
		new Producer_F_Ziehungen().producePage(auss, props);

		props.clear();
		props.put("orderByHaeufigkeit", Boolean.FALSE);
		new Producer_Haeufigkeit().producePage(auss, props);
		new Producer_F_Haeufigkeit().producePage(auss, props);
		
		DbManager.commitTransaction(tr);
	}
	
	private void produceQuotenHTMLPages() {
		System.out.println("produceQuotenHTMLPage");
		Transaction tr = DbManager.startReadTransaction();
		Ausspielungen auss = DbManager.getAusspielungen();
		
		Properties props = new Properties();
		props.put("year", new Integer(2002));
		props.put("asc", Boolean.FALSE);		
		new Producer_LottoGewinnquoten().producePage(auss, props);
		new Producer_JokerGewinnquoten().producePage(auss, props);		
		new Producer_Quoten().producePage(auss, null);
		new Producer_F_LottoGewinnquoten().producePage(auss, props);
		new Producer_F_JokerGewinnquoten().producePage(auss, props);
		new Producer_F_Quoten().producePage(auss, props);
		
    new Producer_Aktuelle_WML().producePage(auss, null);
    new Producer_Ausstehend_WML().producePage(auss, null);

		DbManager.commitTransaction(tr);
	 
	}

	private Calendar calculateNextDate(Calendar lDate) {
		Calendar nDate = new GregorianCalendar();
		nDate.setTime(lDate.getTime());

		if (lDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			//Samstag
			nDate.add(Calendar.DATE, +3);
		} else {
			//Mittwoch
			nDate.add(Calendar.DATE, +4);
		}
		return nDate;
	}
	
	private static boolean isToday(Calendar anyDay) {
		Calendar today = Calendar.getInstance();
		return ((today.get(Calendar.DATE) == anyDay.get(Calendar.DATE)) &&
		    (today.get(Calendar.MONTH) == anyDay.get(Calendar.MONTH)) &&
		    (today.get(Calendar.YEAR) == anyDay.get(Calendar.YEAR)));
	
	}

}