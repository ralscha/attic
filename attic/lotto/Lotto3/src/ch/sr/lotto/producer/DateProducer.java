package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class DateProducer extends Producer {
		
	private static final String htmlFile = "date.dat";

	public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		try {
      Draw draw = Draw.getLastDraw(db);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(includePath + htmlFile)));
			pw.println(getMiddleDateString(draw.getDrawdate()));
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