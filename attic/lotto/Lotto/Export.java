
import java.io.*;

import lotto.*;
import common.util.*;
import java.util.*;
import java.text.*;
import com.odi.Transaction;

public class Export {
	
private final static DateFormat Format = new SimpleDateFormat("dd.MM.yyyy");

	public static void main(java.lang.String[] args) {
		try {
			

			PrintWriter pwz = new PrintWriter(new FileWriter("ziehung.txt"));
			PrintWriter pwl = new PrintWriter(new FileWriter("lottogewinnquoten.txt"));
			PrintWriter pwj = new PrintWriter(new FileWriter("jokergewinnquoten.txt"));
	
			/* Funktioniert nur mit folgendem Fix. Warum? 
			try {
				Class.forName("lotto.Ausspielungen");
			} catch(ClassNotFoundException cnfe) {
				System.err.println(cnfe);
			}
			*/
			
			DbManager.initialize(AppProperties.getStringProperty("file.database"), false);
			Transaction tr = DbManager.startReadTransaction();
			Ausspielungen auss = DbManager.getAusspielungen();               
			
			Iterator zieit = auss.iterator();
			while(zieit.hasNext()) {
			  Ziehung zie = (Ziehung)zieit.next();
			  
			  pwz.print(zie.getNr());
			  pwz.print(";");
			  pwz.print(zie.getJahr());
			  pwz.print(";");
			  pwz.print(Format.format(zie.getDatum()));
			  pwz.print(";");
			  
			  int[] zahlen = zie.getZahlen();
			  for (int j = 0; j < zahlen.length; j++) {
			    pwz.print(zahlen[j]);
			    pwz.print(";");
			  }
			  
			  pwz.print(zie.getZusatzZahl());
			  pwz.print(";");
			  pwz.println(zie.getJoker());
			  
			  
			  LottoGewinnquote lgq = zie.getLottoGewinnquote();
			  JokerGewinnquote jgq = zie.getJokerGewinnquote();
			  
			  if (lgq != null) {
			  pwl.print(zie.getNr());
			  pwl.print(";");
			  pwl.print(zie.getJahr());
			  pwl.print(";");
			  pwl.print(lgq.getQuote(GewinnquoteType.JACKPOT));
			  pwl.print(";");
			  pwl.print(lgq.getAnzahl(GewinnquoteType.RICHTIGE_6));
			  pwl.print(";");
			  pwl.print(lgq.getQuote(GewinnquoteType.RICHTIGE_6));
			  pwl.print(";");
			  pwl.print(lgq.getAnzahl(GewinnquoteType.RICHTIGE_5_PLUS));
			  pwl.print(";");
			  pwl.print(lgq.getQuote(GewinnquoteType.RICHTIGE_5_PLUS));
			  pwl.print(";");
			  pwl.print(lgq.getAnzahl(GewinnquoteType.RICHTIGE_5));
			  pwl.print(";");
			  pwl.print(lgq.getQuote(GewinnquoteType.RICHTIGE_5));
			  pwl.print(";");
			  pwl.print(lgq.getAnzahl(GewinnquoteType.RICHTIGE_4));
			  pwl.print(";");
			  pwl.print(lgq.getQuote(GewinnquoteType.RICHTIGE_4));
			  pwl.print(";");
			  pwl.print(lgq.getAnzahl(GewinnquoteType.RICHTIGE_3));
			  pwl.print(";");
			  pwl.println(lgq.getQuote(GewinnquoteType.RICHTIGE_3));			  						  
			} else {
			  System.out.println(zie.getNr());
			  System.out.println(zie.getJahr());
			}


			  if (jgq != null) {
			  pwj.print(zie.getNr());
			  pwj.print(";");
			  pwj.print(zie.getJahr());
			  pwj.print(";");
			  pwj.print(jgq.getQuote(GewinnquoteType.JACKPOT));
			  pwj.print(";");
			  pwj.print(jgq.getAnzahl(GewinnquoteType.RICHTIGE_6));
			  pwj.print(";");
			  pwj.print(jgq.getQuote(GewinnquoteType.RICHTIGE_6));
			  pwj.print(";");
			  pwj.print(jgq.getAnzahl(GewinnquoteType.RICHTIGE_5));
			  pwj.print(";");
			  pwj.print(jgq.getQuote(GewinnquoteType.RICHTIGE_5));
			  pwj.print(";");
			  pwj.print(jgq.getAnzahl(GewinnquoteType.RICHTIGE_4));
			  pwj.print(";");
			  pwj.print(jgq.getQuote(GewinnquoteType.RICHTIGE_4));
			  pwj.print(";");
			  pwj.print(jgq.getAnzahl(GewinnquoteType.RICHTIGE_3));
			  pwj.print(";");
			  pwj.print(jgq.getQuote(GewinnquoteType.RICHTIGE_3));			  						  
			  pwj.print(";");
			  pwj.print(jgq.getAnzahl(GewinnquoteType.RICHTIGE_2));
			  pwj.print(";");
			  pwj.println(jgq.getQuote(GewinnquoteType.RICHTIGE_2));			  						  

		} else {
			  if (zie.getJahr() > 1997) {
			  System.out.println(zie.getNr());
			  System.out.println(zie.getJahr());
			  }
			}
			
			}
			
			pwz.close();
			pwl.close();
			pwj.close();
			
			DbManager.commitTransaction(tr);            
			DbManager.shutdown();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
}