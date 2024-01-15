package lotto.page;

import lotto.*;
import java.util.*;
import java.io.*;
import common.io.*;

public class Producer_Date extends PageProducer {
		
	private static final String htmlFile = "date.dat";

	public void producePage(Ausspielungen auss, Properties props) {
		try {
			Ziehung zie = auss.getLastZiehung();
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(includePath + htmlFile)));
			pw.println(getMiddleDateString(zie.getDatum()));
			pw.close();
		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
	private String getMiddleDateString(Date date) {
		StringBuffer sb = new StringBuffer();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);

		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		sb.append(". ");
		sb.append(MONTHS[cal.get(Calendar.MONTH)]);
		sb.append(" ");
		sb.append(cal.get(Calendar.YEAR));

		return sb.toString();
	}
	
}