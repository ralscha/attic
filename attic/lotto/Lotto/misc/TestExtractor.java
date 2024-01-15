package misc;

import java.awt.*;
import java.awt.event.*;
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

public class TestExtractor {


	private int nextNr, nextJahr;
	private Calendar nextDatum;
	private Calendar lastDatum;
	
	
	public TestExtractor() {
		
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
	
		DbManager.commitTransaction(tr);
	}
	
	
	public void run() {
		Ziehung newZie = null;
		int loopCount = 0;

		Extractor ext2 = new Extractor_SwissLotto();

		LottoGewinnquote lgq = null;
		JokerGewinnquote jgq = null;
			
		lgq = ext2.extractLottoGewinnquote(nextDatum);
		jgq = ext2.extractJokerGewinnquote(nextDatum);
		
    System.out.println(lgq);
    System.out.println(jgq);

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

  public static void main(String[] args) {
		DbManager.initialize(AppProperties.getStringProperty("file.database"), false);
    new TestExtractor().run();
		DbManager.shutdown();
  }

}