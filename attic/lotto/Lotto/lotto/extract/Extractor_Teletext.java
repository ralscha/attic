package lotto.extract;

import common.util.*;
import common.io.*;
import common.util.log.*;

import lotto.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Extractor_Teletext extends Extractor {

	
	public Ziehung extractZiehung(Calendar nextDate) {
		Ziehung zie = extractZiehung(nextDate, 1);
		if (zie == null) 
			zie = extractZiehung(nextDate, 2);
		return zie;
	}
	
	private Ziehung extractZiehung(Calendar nextDate, int no) {
		Ziehung newZiehung;
		
		List wordList = readData(no);		
		if (wordList == null)
			return null;
		
		int startIndex = searchStartIndex(wordList);
		if (startIndex == -1)
			return null;
		
		try {
	
			if (newData((String)wordList.get(startIndex-1), nextDate)) {
			
				newZiehung = new Ziehung();
				newZiehung.setDatum(nextDate.getTime());
				
				int zahlen[] = new int[6];
				zahlen[0] = Integer.parseInt(removeNonDigits((String)wordList.get(startIndex+1))); 
				zahlen[1] = Integer.parseInt(removeNonDigits((String)wordList.get(startIndex+2)));
				zahlen[2] = Integer.parseInt(removeNonDigits((String)wordList.get(startIndex+3))); 
				zahlen[3] = Integer.parseInt(removeNonDigits((String)wordList.get(startIndex+4))); 
				zahlen[4] = Integer.parseInt(removeNonDigits((String)wordList.get(startIndex+5))); 
				zahlen[5] = Integer.parseInt(removeNonDigits((String)wordList.get(startIndex+6))); 
				newZiehung.setZahlen(zahlen);
				
				newZiehung.setZusatzZahl(Integer.parseInt(removeNonDigits((String)wordList.get(startIndex+7))));
				newZiehung.setJoker((String)wordList.get(startIndex+32));					
				return newZiehung;
			}
			else
				return null;
		} catch (Exception nfe) {
			//[TODO: Log]
			return null;
		}
			
	}
	
	public LottoGewinnquote extractLottoGewinnquote(Calendar nextDate) {
		LottoGewinnquote lgq = extractLottoGewinnquote(nextDate, 1);
		if (lgq == null)
			lgq = extractLottoGewinnquote(nextDate, 2);
		
		return lgq;
	}	
	
	private LottoGewinnquote extractLottoGewinnquote(Calendar nextDate, int no) {
	
		LottoGewinnquote newlgq;
		
		List wordList = readData(no);		
		if (wordList == null)
			return null;
		
		int startIndex = searchStartIndex(wordList);
		if (startIndex == -1)
			return null;
		
	
		try {
			if (newData((String)wordList.get(startIndex-1), nextDate)) {
				
				newlgq = new LottoGewinnquote();

				if (((String)wordList.get(startIndex+9)).equalsIgnoreCase("Jackpot")) {
					newlgq.setQuote(GewinnquoteType.JACKPOT, 
				    		Double.parseDouble(stringconv((String)wordList.get(startIndex+10))));
				}
				else {
					newlgq.setAnzahl(GewinnquoteType.RICHTIGE_6,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+9))));
					newlgq.setQuote(GewinnquoteType.RICHTIGE_6,
				   		Double.parseDouble(stringconv((String)wordList.get(startIndex+10))));
				}
				
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_5_PLUS,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+12))));
				newlgq.setQuote(GewinnquoteType.RICHTIGE_5_PLUS,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+13))));
				
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_5,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+15))));
				newlgq.setQuote(GewinnquoteType.RICHTIGE_5,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+16))));
				
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_4,
					Integer.parseInt(stringconv((String)wordList.get(startIndex+18))));
				newlgq.setQuote(GewinnquoteType.RICHTIGE_4,
					Double.parseDouble(stringconv((String)wordList.get(startIndex+19))));
				
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_3,
					 Integer.parseInt(stringconv((String)wordList.get(startIndex+21))));
				newlgq.setQuote(GewinnquoteType.RICHTIGE_3,
					 Double.parseDouble(stringconv((String)wordList.get(startIndex+22))));
				
				NextGewinnsumme.nextLottoGewinn = Double.parseDouble(stringconv((String)wordList.get(startIndex+29)));

				
				return newlgq;
			} else
				return null;
		} catch (Exception nfe) {
			//[TODO: Log]
			return null;
		}
	}

	public JokerGewinnquote extractJokerGewinnquote(Calendar nextDate) {
		JokerGewinnquote jgq = extractJokerGewinnquote(nextDate, 1);
		if (jgq == null)
			jgq = extractJokerGewinnquote(nextDate, 2);
		
		return jgq;
	}
	
	private JokerGewinnquote extractJokerGewinnquote(Calendar nextDate, int no) {
		JokerGewinnquote newjgq;

		List wordList = readData(no);		
		if (wordList == null)
			return null;
		
		int startIndex = searchStartIndex(wordList);
		if (startIndex == -1)
			return null;
		
		try {
			if (newData((String)wordList.get(startIndex-1), nextDate)) {

				newjgq = new JokerGewinnquote();

				if (((String)wordList.get(startIndex+34)).equalsIgnoreCase("Jackpot")) {
					newjgq.setQuote(GewinnquoteType.JACKPOT, 
				    		Double.parseDouble(stringconv((String)wordList.get(startIndex+35))));
				}
				else {
					newjgq.setAnzahl(GewinnquoteType.RICHTIGE_6,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+34))));
					newjgq.setQuote(GewinnquoteType.RICHTIGE_6,
				   		Double.parseDouble(stringconv((String)wordList.get(startIndex+35))));
				}
									
				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_5,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+37))));
				newjgq.setQuote(GewinnquoteType.RICHTIGE_5,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+38))));
				
				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_4,
					Integer.parseInt(stringconv((String)wordList.get(startIndex+40))));
				newjgq.setQuote(GewinnquoteType.RICHTIGE_4,
					Double.parseDouble(stringconv((String)wordList.get(startIndex+41))));
				
				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_3,
					 Integer.parseInt(stringconv((String)wordList.get(startIndex+43))));
				newjgq.setQuote(GewinnquoteType.RICHTIGE_3,
					 Double.parseDouble(stringconv((String)wordList.get(startIndex+44))));

				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_2,
					 Integer.parseInt(stringconv((String)wordList.get(startIndex+46))));
				newjgq.setQuote(GewinnquoteType.RICHTIGE_2,
					 Double.parseDouble(stringconv((String)wordList.get(startIndex+47))));

				NextGewinnsumme.nextJokerGewinn = Double.parseDouble(stringconv((String)wordList.get(startIndex+54)));
			
				
				return newjgq;
			} else
				return null;
		} catch (Exception nfe) {
			//[TODO: Log]
			return null;
		}

	}

	
	private List readData(int no) {
		BufferedReader inReader;
		
		try {
			if (AppProperties.getBooleanProperty("web.access", true)) {
				URL url = new URL(AppProperties.getStringProperty("url.teletext."+no));
				inReader = new BufferedReader(new TagFilterReader(new InputStreamReader(url.openStream())));
			} else {
				inReader = new BufferedReader(new TagFilterReader(new FileReader(AppProperties.getStringProperty("file.teletext"))));
			}			
			
			List wordList = makeWordList(inReader);
			inReader.close();
			
			return wordList;
		} catch (MalformedURLException e) {
			System.err.println(e);
			//[TODO: Log]
	      return null;
		} catch (IOException ioe) {
			System.err.println(ioe);
			//[TODO: Log]
	     return null;
		}

	}
		
		
	private int searchStartIndex(List list) {
		for (int i = 0; i < list.size(); i++) {				
			String str = (String)list.get(i);
			if (str.equalsIgnoreCase("LOTTO")) {
				return i;
			}
		}
		return -1;
	}
		
	private boolean newData(String dateStr, Calendar cal) throws Exception {
				
		int yyyy = Integer.parseInt(dateStr.substring(6));
		int mm   = Integer.parseInt(dateStr.substring(3,5))-1;
		int dd   = Integer.parseInt(dateStr.substring(0,2));
				
		if ( (dd == cal.get(Calendar.DATE)) && (mm == cal.get(Calendar.MONTH)) &&
			  (yyyy == cal.get(Calendar.YEAR)) ) 
     		return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
	
		AppProperties.setFileName("d:/javaprojects/private/Lotto/lotto.props");
		Extractor ex = new Extractor_Teletext();
		
		Calendar cal = new GregorianCalendar(1999, Calendar.OCTOBER, 2);
		Ziehung zie = ex.extractZiehung(cal);
		LottoGewinnquote lg = ex.extractLottoGewinnquote(cal);
		JokerGewinnquote jg = ex.extractJokerGewinnquote(cal);
		
		if (zie != null)
			zie.showSlotName = true;
		
		if (lg != null)	
			lg.showSlotName = true;
			
		if (jg != null)	
			jg.showSlotName = true;
		
		System.out.println(zie);
		System.out.println(lg);
		System.out.println(jg);
		System.out.println(NextGewinnsumme.nextLottoGewinn);
		System.out.println(NextGewinnsumme.nextJokerGewinn);

	}
	
}