package ch.sr.lotto.update;

import java.awt.event.*;

import java.util.*;

import javax.swing.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.extract.*;
import ch.sr.lotto.producer.*;
import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class LottoPageUpdateRunner extends Thread {

	private int maxLoop;
	private int waitTime; /* seconds */
	

	private int nextNr, nextJahr;
	private Calendar nextDatum;
	private Calendar lastDatum;
	private JFrame frame = null;
	private WindowEvent windowClosingEvent;

	private Server server;

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


    try {
      server = ServerUtil.createServer();
      
      Database db = server.getDatabase();
      
      Draw draw = Draw.getLastDraw(db);
      
      lastDatum = new GregorianCalendar();
      lastDatum.setTime(draw.getDrawdate());
      
      nextNr = draw.getNo().intValue() + 1;
      nextJahr = draw.getYear().intValue();
      nextDatum = calculateNextDate(lastDatum);
      
      if (nextJahr != nextDatum.get(Calendar.YEAR)) {
      	nextJahr = nextDatum.get(Calendar.YEAR);
      	nextNr = 1;
      }
      
      //nextDatum = calculateNextDate(nextDatum);
      
      db.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
				
	}

	
	public void createPageAndSend() {
		Database db = server.getDatabase();
    Draw draw = Draw.getLastDraw(db);
    
		produceHTMLPages();

		
		new FileSender(AppProperties.getStringProperty("path.html.output")).sendFiles();
		
		new FileSender(AppProperties.getStringProperty("path.html.output.include"),
						  AppProperties.getStringProperty("ftp.home.include")).sendFiles();
				
		produceQuotenHTMLPages(draw);
		
		new FileSender(AppProperties.getStringProperty("path.html.output.quoten")).sendFiles();
				
    db.close();
	}
	
	public void run() {
		Draw newDraw = null;
		int loopCount = 0;

		Extractor ext = new Loterie2Extractor();

		
		//Check for lastDatum
		if (!isToday(nextDatum)) {

		  newDraw = ext.extractDraw(lastDatum);
			
			if (newDraw != null) {
				System.out.println("up-to-date");
				return;
			}
		}
		
		while ((Thread.currentThread() == this) && (loopCount < maxLoop) && (newDraw == null)) {


			newDraw = ext.extractDraw(nextDatum);

			if (newDraw == null) {
				try {

					sleep(waitTime * 1000);
				} catch (InterruptedException ex) {
					return;
				}
			}
			loopCount++;
		}

		if (newDraw != null) {
      
      Database db = server.getDatabase();
      newDraw.setNo(new Integer(nextNr));
      newDraw.setYear(new Integer(nextJahr));

      db.insert(newDraw);		
      db.close();	

			produceHTMLPages();
      
      
			FileSender fs1 = new FileSender(AppProperties.getStringProperty("path.html.output"));
		
			FileSender fs2 = new FileSender(AppProperties.getStringProperty("path.html.output.include"),
							  AppProperties.getStringProperty("ftp.home.include"));
		
							  
			FileSender fs4 = 	new FileSender(AppProperties.getStringProperty("path.html.output.bin"), true);						  
		  
			fs1.sendFiles();
			fs2.sendFiles();
			
			fs4.sendFiles();
			
      	

			
			loopCount = 0;
			LottoGainQuota lgq = null;
			JokerGainQuota jgq = null;
      ExtraJokerGainQuota ejgq = null;
			
			while ((Thread.currentThread() == this) && (loopCount < maxLoop) 
							&& ((lgq == null) || (jgq == null) || (ejgq == null))) {
	
				lgq = ext.extractLottoGainQuota(nextDatum);
	
				jgq = ext.extractJokerGainQuota(nextDatum);
        
        ejgq = ext.extractExtraJokerGainQuota(nextDatum);
		
				if ((lgq == null) || (jgq == null) || (ejgq == null)) {
					try {
						sleep(waitTime * 1000);
					} catch (InterruptedException ex) {
						return;
					}
				}
				loopCount++;
			}
			
			if ((lgq != null) && (jgq != null) && (ejgq != null)) {
				db = server.getDatabase();

        lgq.setNo(newDraw.getNo());
        lgq.setYear(newDraw.getYear());
        jgq.setNo(newDraw.getNo());
        jgq.setYear(newDraw.getYear());        
        ejgq.setNo(newDraw.getNo());
        ejgq.setYear(newDraw.getYear());

        db.insert(lgq);
        db.insert(jgq);
        db.insert(ejgq);
        db.close();
        Calendar nextNext = calculateNextDate(nextDatum);
				
        newDraw.setNextDate(nextNext.getTime());			
				produceQuotenHTMLPages(newDraw);
        
        
				fs1 = new FileSender(AppProperties.getStringProperty("path.html.output.quoten"));
				fs1.sendFiles();
				fs2.sendFiles();
        


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

    Database db = server.getDatabase();
    AusspielungenStatistiken as = new AusspielungenStatistiken();
    OQuery query = new OQuery();
    query.addOrder("year");
    query.addOrder("no");

    Draw[] draw = (Draw[]) db.get(Draw.class, query);
    for (int i = 0; i < draw.length; i++) {
      as.addDraw(draw[i]);
    }
		
		new AktuelleProducer().producePage(db, null, as);
		new AusstehendProducer().producePage(db, null, as);
		new DateProducer().producePage(db, null, as);
		new PaarProducer().producePage(db, null, as);
		new VerbundProducer().producePage(db, null, as);
		new HaeufigkeitProducer().producePage(db, null, as);
		new GeradeUngeradeProducer().producePage(db, null, as);
		
		GeradeUngeradeChartsProducer pguc = new GeradeUngeradeChartsProducer();
		pguc.producePage(db, null, as);
		this.frame = pguc.getFrame();
		
		new SummeProducer().producePage(db, null, as);
		new EndziffernSummeProducer().producePage(db, null, as);
		new QuersummeProducer().producePage(db, null, as);
		new SummeDifferenzProducer().producePage(db, null, as);
		new Klasse3Producer().producePage(db, null, as);
		new Klasse5Producer().producePage(db, null, as);
		new Klasse9Producer().producePage(db, null, as);		
		new TiefeHoheProducer().producePage(db, null, as);
		new TiefeHoheEndziffernProducer().producePage(db, null, as);

		new TipsProducer().producePage(db, null, as);
		new ZiehungenTxtProducer().producePage(db, null, as);
    new LottoWinProducer().producePage(db, null, as);

		

		Properties props = new Properties();
		props.put("withDate", Boolean.TRUE);
		props.put("joker", Boolean.TRUE);
    props.put("extrajoker", Boolean.TRUE);
		props.put("year", new Integer(2004));
		props.put("asc", Boolean.FALSE);		
		new ZiehungenProducer().producePage(db, props, as);

		props.clear();
		props.put("orderByHaeufigkeit", Boolean.FALSE);
		new HaeufigkeitProducer().producePage(db, props, as);
		
    db.close();
	}
	
	private void produceQuotenHTMLPages(Draw lastDraw) {
		System.out.println("produceQuotenHTMLPage");

    Database db = server.getDatabase();
    AusspielungenStatistiken as = new AusspielungenStatistiken();
    OQuery query = new OQuery();
    query.addOrder("year");
    query.addOrder("no");

    Draw[] draw = (Draw[]) db.get(Draw.class, query);
    for (int i = 0; i < draw.length; i++) {
      as.addDraw(draw[i]);
    }
		
		Properties props = new Properties();
		props.put("year", new Integer(2004));
		props.put("asc", Boolean.FALSE);		
		new LottoGewinnquotenProducer().producePage(db, props, as);
		new JokerGewinnquotenProducer().producePage(db, props, as);
    new ExtraJokerGewinnquotenProducer().producePage(db, props, as); 		

    props.clear();
    if (lastDraw.getNextDate() != null) {
      props.put("nextDate", lastDraw.getNextDate());
      props.put("nextLottoQuota", new Double(lastDraw.getNextLottoGainQuota()));
      props.put("nextJokerQuota", new Double(lastDraw.getNextJokerGainQuota()));
      props.put("nextExtraJokerQuota", new Double(lastDraw.getNextExtraJokerGainQuota()));
    }    
		new QuotenProducer().producePage(db, props, as);
		
    new AktuelleWMLProducer().producePage(db, null, as);
    new AusstehendWMLProducer().producePage(db, null, as);

		db.close();
	 
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