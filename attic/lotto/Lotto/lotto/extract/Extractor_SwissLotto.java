package lotto.extract;

import lotto.*;
import common.util.*;
import common.io.*;
import common.util.log.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Extractor_SwissLotto extends Extractor {


	public Ziehung extractZiehung(Calendar nextDate) {
   		Ziehung newZiehung;
		
		List wordList = readData(0);		
		if (wordList == null)
			return null;
		
		int startIndex = searchStartIndex(wordList);
		if (startIndex == -1)
			return null;

		/*
		if ((startIndex+18) >= wordList.size())
			return null;
		*/
		
		try {
			if ( newData(Integer.parseInt(removePoint((String)wordList.get(startIndex+1))),
		   			  	 getEnglishMonthNumber((String)wordList.get(startIndex+2)),
							 Integer.parseInt((String)wordList.get(startIndex+3)), nextDate) ) {
			
				newZiehung = new Ziehung();
				newZiehung.setDatum(nextDate.getTime());
			
				int zahlen[] = new int[6];
				zahlen[0] = Integer.parseInt((String)wordList.get(startIndex+6));  // 1. Zahl
				zahlen[1] = Integer.parseInt((String)wordList.get(startIndex+7));  // 2. Zahl
				zahlen[2] = Integer.parseInt((String)wordList.get(startIndex+8));  // 3. Zahl
				zahlen[3] = Integer.parseInt((String)wordList.get(startIndex+9));  // 4. Zahl
				zahlen[4] = Integer.parseInt((String)wordList.get(startIndex+10)); // 5. Zahl
				zahlen[5] = Integer.parseInt((String)wordList.get(startIndex+11)); // 6. Zahl
				newZiehung.setZahlen(zahlen);
			
			   newZiehung.setZusatzZahl(Integer.parseInt((String)wordList.get(startIndex+12))); // Zusatzzahl
			   newZiehung.setJoker((String)wordList.get(startIndex+14)); // Joker
			   
			   
				startIndex = searchStartIndex(wordList, "correct");
				if (startIndex != -1) {
					NextGewinnsumme.nextLottoGewinn = Double.parseDouble(stringconv((String)wordList.get(startIndex+5)));
					NextGewinnsumme.nextJokerGewinn = Double.parseDouble(stringconv((String)wordList.get(startIndex+8)));
					//System.out.println(NextGewinnsumme.nextLottoGewinn);
					//System.out.println(NextGewinnsumme.nextJokerGewinn);
				} 			 	
				return newZiehung;
			} else
				return null;
		} catch (NumberFormatException nfe) {
			System.out.println(nfe);			
		    //[TODO: Log]
			return null;
		}
	}

	public LottoGewinnquote extractLottoGewinnquote(Calendar nextDate) {

		LottoGewinnquote newlgq;
		
		List wordList = readData(1);		
		if (wordList == null)
			return null;
		
		
		int startIndex = searchStartIndex(wordList);
		if (startIndex == -1)
			return null;
			
		try {
			if ( newData(Integer.parseInt(removePoint((String)wordList.get(startIndex+1))),
		   			  	 getEnglishMonthNumber((String)wordList.get(startIndex+2)),
							 Integer.parseInt((String)wordList.get(startIndex+3)), nextDate) ) {
				
				startIndex = searchStartIndex(wordList, "5+");
				if (startIndex == -1)
					return null;
				
				startIndex += 4;
		
			
				newlgq = new LottoGewinnquote();

				if (((String)wordList.get(startIndex)).equalsIgnoreCase("Jackpot")) {
               newlgq.setQuote(GewinnquoteType.JACKPOT, 
						   Double.parseDouble(stringconv((String)wordList.get(startIndex+11))));
				} else {
					newlgq.setAnzahl(GewinnquoteType.RICHTIGE_6,
							Integer.parseInt(stringconv((String)wordList.get(startIndex))));
					newlgq.setQuote(GewinnquoteType.RICHTIGE_6,
				   		Double.parseDouble(stringconv((String)wordList.get(startIndex+11))));
				}
				
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_5_PLUS,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+2)))); //1
				newlgq.setQuote(GewinnquoteType.RICHTIGE_5_PLUS,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+13))));
				
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_5,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+4)))); //2
				newlgq.setQuote(GewinnquoteType.RICHTIGE_5,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+15))));
							
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_4,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+6)))); //3
				newlgq.setQuote(GewinnquoteType.RICHTIGE_4,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+17))));
							
				newlgq.setAnzahl(GewinnquoteType.RICHTIGE_3,
					 		Integer.parseInt(stringconv((String)wordList.get(startIndex+8)))); //4		
				newlgq.setQuote(GewinnquoteType.RICHTIGE_3,
					 		Double.parseDouble(stringconv((String)wordList.get(startIndex+19))));
					 		
									
				return newlgq;           
			} else
				return null;
		
		} catch (NumberFormatException nfe) {
			System.out.println(nfe);
			//[TODO Log]
			return null;
		}

	}


	public JokerGewinnquote extractJokerGewinnquote(Calendar nextDate) {

		JokerGewinnquote newjgq;
		
		List wordList = readData(2);		
		if (wordList == null)
			return null;
		
		
		int startIndex = searchStartIndex(wordList);
		if (startIndex == -1)
			return null;
			
		try {
			if ( newData(Integer.parseInt(removePoint((String)wordList.get(startIndex+1))),
		   			  	 getEnglishMonthNumber((String)wordList.get(startIndex+2)),
							 Integer.parseInt((String)wordList.get(startIndex+3)), nextDate) ) {

				startIndex = searchStartLastIndex(wordList, "Joker");
				if (startIndex == -1)
					return null;
				
				startIndex += 21;

				newjgq = new JokerGewinnquote();
				
				if (((String)wordList.get(startIndex)).equalsIgnoreCase("Jackpot")) {
               newjgq.setQuote(GewinnquoteType.JACKPOT, 
						   Double.parseDouble(stringconv((String)wordList.get(startIndex+11))));
				}
			
				else {
					newjgq.setAnzahl(GewinnquoteType.RICHTIGE_6,
							Integer.parseInt(stringconv((String)wordList.get(startIndex))));
					newjgq.setQuote(GewinnquoteType.RICHTIGE_6,
				   		Double.parseDouble(stringconv((String)wordList.get(startIndex+11))));
				}
								
				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_5,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+2))));
				newjgq.setQuote(GewinnquoteType.RICHTIGE_5,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+13))));
							
				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_4,
							Integer.parseInt(stringconv((String)wordList.get(startIndex+4))));
				newjgq.setQuote(GewinnquoteType.RICHTIGE_4,
							Double.parseDouble(stringconv((String)wordList.get(startIndex+15))));
							
				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_3,
					 		Integer.parseInt(stringconv((String)wordList.get(startIndex+6))));		
				newjgq.setQuote(GewinnquoteType.RICHTIGE_3,
					 		Double.parseDouble(stringconv((String)wordList.get(startIndex+17))));
					 		
				newjgq.setAnzahl(GewinnquoteType.RICHTIGE_2,
					 		Integer.parseInt(stringconv((String)wordList.get(startIndex+8))));		
				newjgq.setQuote(GewinnquoteType.RICHTIGE_2,
					 		Double.parseDouble(stringconv((String)wordList.get(startIndex+19))));
			
									
				return newjgq;           
			} else
				return null;
		
		} catch (NumberFormatException nfe) {
			System.out.println(nfe);
			//[TODO Log]
			return null;
		}

	}


	private List readData(int t) {
		BufferedReader inReader;
		
		try {
			if (AppProperties.getBooleanProperty("web.access", true)) {
				URL url = null;
				switch(t) {
					case 0: 
						url = new 	URL(AppProperties.getStringProperty("url.swisslotto.ziehung"));
						break;
					case 1:
						url = new 	URL(AppProperties.getStringProperty("url.swisslotto.lottogewinnquote"));
						break;
					case 2:
						url = new 	URL(AppProperties.getStringProperty("url.swisslotto.jokergewinnquote"));
						break;
				}				
				inReader = new BufferedReader(new InputStreamReader(url.openStream()));
			} else {
				String fileName = null;
				switch(t) {					
					case 0:
						fileName = AppProperties.getStringProperty("file.swisslotto.ziehung");
						break;
					case 1:
						fileName = AppProperties.getStringProperty("file.swisslotto.lottogewinnquote");
						break;
					case 2:
						fileName = AppProperties.getStringProperty("file.swisslotto.jokergewinnquote");
						break;
				}
				inReader = new BufferedReader(new FileReader(fileName));
			}			
			
			List wordList = makeWordListHTMLParser(inReader);
			inReader.close();

      /*
			Iterator it = wordList.iterator();
			int i = 0;
			while(it.hasNext()) {
				System.out.println((i++) + " " + (String)it.next());
			}
			*/

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
			if ((((String)list.get(i)).equalsIgnoreCase("Wednesday")) ||
				 (((String)list.get(i)).equalsIgnoreCase("Saturday"))) {
				return i;
			}
		}
		return -1;
	}

	private int searchStartIndex(List list, String cstr) {
		for (int i = 0; i < list.size(); i++) {				
			if (((String)list.get(i)).equalsIgnoreCase(cstr)) {
				return i;
			}
		}
		return -1;
	}

  private int searchStartLastIndex(List list, String cstr) {
    for (int i = list.size()-1; i >= 0; i--) {
	    if (((String)list.get(i)).equalsIgnoreCase(cstr)) {
				return i;
			}      
    }
    return -1;
  }

	private boolean newData(int dd, int mm, int yyyy, Calendar cal) {
								
		if ( (dd == cal.get(Calendar.DATE)) && (mm == cal.get(Calendar.MONTH)) &&
			  (yyyy == cal.get(Calendar.YEAR)) ) 
     		return true;
		else
			return false;
	}
	
	
	public static void main(String[] args) {
	
		AppProperties.setFileName("d:/javaprojects/private/Lotto/lotto.props");
		Extractor ex = new Extractor_SwissLotto();
		
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
	}

	
}