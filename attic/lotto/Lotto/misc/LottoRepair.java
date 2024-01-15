package misc;

import lotto.*;
import lotto.util.*;
import lotto.extract.*;
import com.odi.util.*;
import com.odi.*;
import java.util.*;
import java.io.*;
import common.util.*;

public class LottoRepair {

			
	public LottoRepair() {		
		DbManager.initialize(AppProperties.getStringProperty("file.database"), false);
		
		com.odi.Transaction tr = DbManager.startUpdateTransaction();
		Ausspielungen auss = DbManager.getAusspielungen();
/*
		Ziehung zie = auss.getLastZiehung();
		
		Iterator it = auss.getListIterator(0);
    
    while (it.hasNext()) {
      zie = (Ziehung)it.next();      

      if ((zie.getJahr() == 2000) && (zie.getNr() >= 25) && (zie.getNr() <= 86)) {
        System.out.println(zie.getJahr());
        System.out.println(zie.getNr());
        Calendar cal = new GregorianCalendar();
        cal.setTime(zie.getDatum());
        cal.add(Calendar.DATE, +1);
        zie.setDatum(cal.getTime());
        
      }
    }
			
*/			
			Ziehung newZiehung = new Ziehung();
			newZiehung.setNr(39);
			newZiehung.setJahr(2002);
			newZiehung.setDatum(new Date(2002,4,15));
			newZiehung.setJoker("434775");
			newZiehung.setZahlen(new int[]{11,16,18,21,27,45});
			newZiehung.setZusatzZahl(20);
			
			LottoGewinnquote newlgq = new LottoGewinnquote();

			newlgq.setQuote(GewinnquoteType.JACKPOT, 381746.05);
			
				
			newlgq.setAnzahl(GewinnquoteType.RICHTIGE_5_PLUS, 2);
			newlgq.setQuote(GewinnquoteType.RICHTIGE_5_PLUS,153112.2);
			
			newlgq.setAnzahl(GewinnquoteType.RICHTIGE_5, 90);
			newlgq.setQuote(GewinnquoteType.RICHTIGE_5,5352.70);
						
			newlgq.setAnzahl(GewinnquoteType.RICHTIGE_4,5420);
			newlgq.setQuote(GewinnquoteType.RICHTIGE_4,50.00);
						
			newlgq.setAnzahl(GewinnquoteType.RICHTIGE_3,103588);
			newlgq.setQuote(GewinnquoteType.RICHTIGE_3,6.00);
			
			newZiehung.setLottoGewinnquote(newlgq);


			JokerGewinnquote newjgq = new JokerGewinnquote();

			newjgq.setQuote(GewinnquoteType.JACKPOT, 208942.60);
										
			newjgq.setAnzahl(GewinnquoteType.RICHTIGE_5, 3);
			newjgq.setQuote(GewinnquoteType.RICHTIGE_5,10000);
						
			newjgq.setAnzahl(GewinnquoteType.RICHTIGE_4,32);
			newjgq.setQuote(GewinnquoteType.RICHTIGE_4,1000);
						
			newjgq.setAnzahl(GewinnquoteType.RICHTIGE_3,283);
			newjgq.setQuote(GewinnquoteType.RICHTIGE_3,100);

			newjgq.setAnzahl(GewinnquoteType.RICHTIGE_2,2949);
			newjgq.setQuote(GewinnquoteType.RICHTIGE_2,10.00);
			
			newZiehung.setJokerGewinnquote(newjgq);
			
auss.addZiehung(newZiehung);
		DbManager.commitTransaction(tr);
		DbManager.shutdown(); 
		
	}
	
	public static void main(String args[]) {
		new LottoRepair();
	}
	
			
}