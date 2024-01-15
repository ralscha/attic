package lotto.extract;

import lotto.*;
import java.util.*;
import java.io.*;
import adc.parser.*;

public abstract class Extractor {

	private static final String MONTHS[] = {
		"Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober",
		"November", "Dezember"
	};

	private static final String MONTHSEN[] = {
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
		"November", "December"
	};


	public abstract Ziehung extractZiehung(Calendar nextDate);
	public abstract LottoGewinnquote extractLottoGewinnquote(Calendar nextDate);
	public abstract JokerGewinnquote extractJokerGewinnquote(Calendar nextDate);


   //Helper methods 
   
	protected List makeWordList(BufferedReader r) throws IOException {
		String line;
		StringTokenizer st;
		
		List list = new ArrayList();
		int i = 0;

		while ((line = r.readLine()) != null) {
		    st = new StringTokenizer(line.trim());
		    while (st.hasMoreTokens()) {
		        list.add(st.nextToken());
		    }
		}
		
		return list;
	}
	
	protected List makeWordListHTMLParser(BufferedReader r) throws IOException {
		List list = new ArrayList();
		HtmlStreamTokenizer tok = new HtmlStreamTokenizer(r);
		
		while (tok.nextToken() != HtmlStreamTokenizer.TT_EOF) {
			if (tok.getTokenType() == HtmlStreamTokenizer.TT_TEXT)
				list.add(tok.getStringValue().toString());
		}
		return list;
	}
	
	protected String removeNonDigits(String digit) {
		StringBuffer sb = new StringBuffer();
		char[] chars = digit.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isDigit(chars[i]))
				sb.append(chars[i]);
		}
		return sb.toString();
	}
	
	protected String removePoint(String in) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			if (in.charAt(i) != '.')
				sb.append(in.charAt(i));
		}
		return sb.toString();
	}

   protected int getMonthNumber(String monthname) {
		for (int i = 0; i < MONTHS.length; i++) {
			if (monthname.equalsIgnoreCase(MONTHS[i])) {
				return i;
			}
		}
		return -1;
	}
	
	protected int getEnglishMonthNumber(String monthname) {
		for (int i = 0; i < MONTHSEN.length; i++) {
			if (monthname.equalsIgnoreCase(MONTHSEN[i])) {
				return i;
			}
		}
		return -1;
	}

	protected String stringconv(String in) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			if (Character.isDigit(in.charAt(i)) || (in.charAt(i) == '.'))
				sb.append(in.charAt(i));
		}		
		return sb.toString();
	}

}