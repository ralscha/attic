import lotto.*;
import lotto.html.*;
import lotto.util.*;
import lotto.extract.*;
import COM.odi.util.*;
import COM.odi.*;
import java.text.*;
import java.util.*;
import java.io.*;

public class LottoManually {

	private Calendar nextDate;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");    
	private int nextNr, nextJahr;
	double nextLottoGewinnsumme;  
	double nextJokerGewinnsumme;
	  
			
	public LottoManually() {		
		DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
		
		DbManager.startUpdateTransaction();
		
		Ausspielungen auss = DbManager.getAusspielungen();        
		Ziehung zie = auss.getLast();
		
		Calendar cal = new GregorianCalendar(1999, Calendar.AUGUST, 14);
		zie.setDate(cal);		
		
		DbManager.commitTransaction();                
		
		DbManager.startReadTransaction();
		//createHTMLPages();                    
		DbManager.commitTransaction();
		
		DbManager.shutdown();        
		/*
		new FileSender(AppProperties.getInstance().getProperty("htmlPath"), "lotto files");    
		new FileSender(AppProperties.getInstance().getProperty("htmlQuotenPath"), "quoten file");
		new DatabaseZipper();
		*/
	}
	
	public LottoManually(String args[]) {
		DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
		DbManager.startReadTransaction();
		Ausspielungen auss = DbManager.getAusspielungen();        
		Ziehung zie = auss.getLast();
		Calendar lastDate = zie.getDate();                        
		
		nextNr = zie.getNr() + 1;
		nextJahr = zie.getJahr();
		nextDate = calculateNextDate(lastDate);                    
		
		if (nextJahr != nextDate.get(Calendar.YEAR)) {
		nextJahr = nextDate.get(Calendar.YEAR);
		nextNr   = 1;
		}
		
		DbManager.commitTransaction();                
		DbManager.shutdown();        
		
		DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Letzte Ziehung   : " + dateFormat.format(lastDate.getTime()));
		DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Nächste Ziehung : " + dateFormat.format(nextDate.getTime()));

		Ziehung newZie = readZiehung();

		if (newZie != null) {
			DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Neue Ziehung : " + newZie);
			
			DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
			DbManager.startUpdateTransaction();
			auss = DbManager.getAusspielungen();            
			newZie.setNr(nextNr);
			newZie.setJahr(nextJahr);                        
			auss.addZiehung(newZie);
			DbManager.commitTransaction();                
			
			DbManager.startReadTransaction();
			createHTMLPages();                    
			DbManager.commitTransaction();                
			DbManager.shutdown();     
			
			new FileSender(AppProperties.getInstance().getProperty("htmlPath"), "lotto files");    
			new FileSender(AppProperties.getInstance().getProperty("htmlQuotenPath"), "quoten file");
			//new DatabaseZipper();
			
		}


	}
    
    
	private void createHTMLPages() {	
		DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "createHTMLPages:  START");
		Ausspielungen auss = DbManager.getAusspielungen();
		LottoHTMLProducer lhp = new LottoHTMLProducer(AppProperties.getInstance().getProperty("htmlPath"), 
		                                            AppProperties.getInstance().getProperty("htmlQuotenPath"),
		                                             auss);	    
		//lhp.writeTitle();
		//lhp.writeAktuelleAusspielung();
		//lhp.writeAlleZiehungenText();
		//lhp.writeHaeufigkeitOrderByHaeufigkeit();
		//lhp.writeHaeufigkeitOrderByZahlen();
		//lhp.writeVerbund();
		//lhp.writeJokerReverseOrder(1999);        
		//lhp.writeGeradeUngeradeChartData();
		//lhp.writeGeradeUngerade();
		//lhp.writeAusstehend();
		//lhp.writeTips();
		//lhp.writePaar();
		//lhp.writeWinSer();
		//lhp.writeWin();
		//lhp.writeZiehung1999ReverseOrder_1();		
      lhp.writeQuoten(nextLottoGewinnsumme, nextJokerGewinnsumme, 
                      calculateNextDate(nextDate));
      //lhp.writeLottoGewinnquotenReverseOrder(1999);
      //lhp.writeJokerGewinnquotenReverseOrder(1999);
		DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "createHTMLPages: END");		
	}
    
    
	public Calendar calculateNextDate(Calendar lDate) {
			Calendar nDate = new GregorianCalendar();
			nDate.setTime(lDate.getTime());
			
			if (lDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			   //Samstag
			   nDate.add(Calendar.DATE, +3);
			   nDate.set(Calendar.AM_PM, Calendar.PM);
			   nDate.set(Calendar.HOUR, 6);
			   nDate.set(Calendar.MINUTE, 00);
			   nDate.set(Calendar.SECOND, 00);
			   nDate.setTimeZone(TimeZone.getTimeZone("ECT"));
			} else {
			   //Mittwoch    
			   nDate.add(Calendar.DATE, +4);
			   nDate.set(Calendar.AM_PM, Calendar.PM);
			   nDate.set(Calendar.HOUR, 9);
			   nDate.set(Calendar.MINUTE, 00);
			   nDate.set(Calendar.SECOND, 00);
			   nDate.setTimeZone(TimeZone.getTimeZone("ECT"));
			}        
			return nDate;
		}
				
		public static void main(String args[]) {
			new LottoManually(args);
			//new LottoManually();
		}


		private Ziehung readZiehung() {
			Ziehung zie = new Ziehung();
			try {
				String line;
				BufferedReader br = new BufferedReader(new FileReader("minput.dat"));
				
				// Datum
				line = br.readLine();
				int d, m, y;
				int[] zahlen = new int[6];
				
				d = Integer.parseInt(line.substring(0,2));
				m = Integer.parseInt(line.substring(3,5));
				y = Integer.parseInt(line.substring(6));
				Calendar date = new GregorianCalendar(y,m,d);
				zie.setDate(date);
		
				zahlen[0] = Integer.parseInt(br.readLine()); 
				zahlen[1] = Integer.parseInt(br.readLine());
				zahlen[2] = Integer.parseInt(br.readLine());
				zahlen[3] = Integer.parseInt(br.readLine());
				zahlen[4] = Integer.parseInt(br.readLine());
				zahlen[5] = Integer.parseInt(br.readLine());
				zie.setZZ(Integer.parseInt(br.readLine()));
				zie.setJoker(br.readLine().trim());
				zie.setZahlen(zahlen);
				
				
				LottoGewinnquote lottogq = new LottoGewinnquote();
				line = br.readLine();
				if (line.trim().equalsIgnoreCase("Jackpot")) {
				   lottogq.set(LottoGewinnquote.JACKPOT, 0, new Double(br.readLine()).doubleValue());			   
				}
				else {
				   lottogq.set(LottoGewinnquote.R6, Integer.parseInt(line), new Double(br.readLine()).doubleValue());
				}
				
				lottogq.set(LottoGewinnquote.R5P, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				lottogq.set(LottoGewinnquote.R5, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				lottogq.set(LottoGewinnquote.R4, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				lottogq.set(LottoGewinnquote.R3, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				nextLottoGewinnsumme = new Double(br.readLine()).doubleValue();
		                            
		
				JokerGewinnquote jokergq = new JokerGewinnquote();
				line = br.readLine();
				if (line.trim().equalsIgnoreCase("Jackpot")) {
				   jokergq.set(JokerGewinnquote.JACKPOT, 0, new Double(br.readLine()).doubleValue());
				}
				else {
				   jokergq.set(JokerGewinnquote.R6, Integer.parseInt(line), new Double(br.readLine()).doubleValue());
				}
				
				jokergq.set(JokerGewinnquote.R5, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				jokergq.set(JokerGewinnquote.R4, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				jokergq.set(JokerGewinnquote.R3, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				jokergq.set(JokerGewinnquote.R2, Integer.parseInt(br.readLine()), new Double(br.readLine()).doubleValue());
				nextJokerGewinnsumme = new Double(br.readLine()).doubleValue();
		
				zie.setLottoGewinnquote(lottogq);
				zie.setJokerGewinnquote(jokergq);
				br.close();
				return zie;
			
			} catch (IOException ioe) {
				System.err.println(ioe);
			}
			return null;
			
		}
	
	
}