package misc;

import lotto.*;
import lotto.page.*;
import lotto.page.frame.*;
import common.util.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class TestProducer {

	public static void main(String args[]) {
		DbManager.initialize(AppProperties.getStringProperty("file.database"), false);
		
		com.odi.Transaction tr = DbManager.startReadTransaction();
		Ausspielungen auss = DbManager.getAusspielungen();
		
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, +3);
		NextGewinnsumme.nextDatum = cal;
		NextGewinnsumme.nextLottoGewinn = 500000.0;
		NextGewinnsumme.nextJokerGewinn = 1500000.0;
		/*
    new Producer_Aktuelle_WML().producePage(auss, null);
    new Producer_Ausstehend_WML().producePage(auss, null);
    new Producer_Ausstehend().producePage(auss, null);
		*/
    /*
		//new Producer_GeradeUngerade_ChartData().producePage(auss, null);
		Producer_GeradeUngerade_Charts p = new Producer_GeradeUngerade_Charts();
		p.producePage(auss, null);
		WindowEvent windowClosingEvent = new WindowEvent(p.getFrame(), WindowEvent.WINDOW_CLOSING);
		p.getFrame().dispatchEvent(windowClosingEvent);
    */
		/*
		new Producer_Quoten().producePage(auss, null);
		

		Properties props = new Properties();
		props.put("year", new Integer(1999));
		props.put("asc", Boolean.FALSE);
		
		new Producer_F_LottoGewinnquoten().producePage(auss, props);
		new Producer_F_JokerGewinnquoten().producePage(auss, props);

				
		new Producer_F_Ziehungen().producePage(auss, props);
		
		new Producer_F_Paar().producePage(auss, null);
		new Producer_Appl_Data().producePage(auss,  null);
		
		
		new Producer_F_Verbund().producePage(auss, null);
		
		Properties props = new Properties();
		new Producer_F_Haeufigkeit().producePage(auss, null);
		
		props.put("orderByHaeufigkeit", Boolean.FALSE);
		new Producer_F_Haeufigkeit().producePage(auss, props);
		
		
		new Producer_F_Ausstehend().producePage(auss, null);
		new Producer_F_Title().producePage(auss, null);
		new Producer_F_Aktuelle().producePage(auss, null);
		*/
		/*
		System.out.println("Aktuelle");
		new Producer_Aktuelle().producePage(auss, null);
		System.out.println("Ausstehend");
		new Producer_Ausstehend().producePage(auss, null);
		System.out.println("Date");
		new Producer_Date().producePage(auss, null);
	
		
		System.out.println("GU");
		new Producer_GeradeUngerade().producePage(auss, null);
		
		
		new Producer_F_GeradeUngerade().producePage(auss, null);
		
		
		System.out.println("GU Chart");
		new Producer_GeradeUngerade_ChartData().producePage(auss, null);
		System.out.println("LGQ");

*/
/*
    Properties p = new Properties();
     p.put("year", new Integer(1990));
	
		new Producer_LottoGewinnquoten().producePage(auss, p);
*/
/*


		System.out.println("JGQ");		
		new Producer_JokerGewinnquoten().producePage(auss, null);
		System.out.println("Quoten");
		new Producer_Quoten().producePage(auss, null);
		
		System.out.println("Tips");
		new Producer_Tips().producePage(auss, null);
		new Producer_F_Tips().producePage(auss, null);
*/

		System.out.println("Ziehung TXT");
		new Producer_Ziehungen_Txt().producePage(auss, null);
	
		
		System.out.println("Ziehungen");
		
		new Producer_Appl_Data().producePage(auss,  null);
		
		Properties props = new Properties();
		props.put("withDate", Boolean.FALSE);
		PageProducer pp = new Producer_Ziehungen();
		int i;
		for (i = 1970; i <= 1996; i++) {
			props.put("year", new Integer(i));
			pp.producePage(auss, props);
		}
		
		props.put("withDate", Boolean.TRUE);
		props.put("joker", Boolean.TRUE);
		for (; i <= 2000; i++) {
			props.put("year", new Integer(i));
			pp.producePage(auss, props);
		}
		props.put("year", new Integer(1999));
		props.put("asc", Boolean.FALSE);
		pp.producePage(auss, props);
/*		
	
		System.out.println("Paar");
		new Producer_Paar().producePage(auss, null);

		System.out.println("Verbund");
		new Producer_Verbund().producePage(auss, null);
		
		
		System.out.println("Summe");
		new Producer_Summe().producePage(auss, null);
		
		System.out.println("EndziffernSumme");
		new Producer_EndziffernSumme().producePage(auss, null);
		
		System.out.println("Quersumme");
		new Producer_Quersumme().producePage(auss, null);
		
		System.out.println("Summe Differenz");
		new Producer_SummeDifferenz().producePage(auss, null);
		
		System.out.println("Klasse 3");
		new Producer_Klasse3().producePage(auss, null);

		System.out.println("Klasse 5");
		new Producer_Klasse5().producePage(auss, null);
		
		System.out.println("Klasse 9");
		new Producer_Klasse9().producePage(auss, null);		
		
		System.out.println("Tiefe / Hohe");
		new Producer_TiefeHohe().producePage(auss, null);
		
		System.out.println("Tiefe / Hohe Endziffern");
		new Producer_TiefeHoheEndziffern().producePage(auss, null);
		
		
		System.out.println("Haeufigkeit");
		new Producer_Haeufigkeit().producePage(auss, null);
		
		props.clear();
		props.put("orderByHaeufigkeit", Boolean.FALSE);
		new Producer_Haeufigkeit().producePage(auss, props);
		
		*/
		DbManager.commitTransaction(tr);
		
		DbManager.shutdown();
	}
}