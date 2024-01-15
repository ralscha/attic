package lotto.page;

import lotto.*;
import java.util.*;
import java.io.*;
import common.io.*;

public class Producer_Aktuelle extends PageProducer {
		
	private static final String template = "aktuelle.template";
	private static final String htmlFile = "aktuelle.shtml";

	public void producePage(Ausspielungen auss, Properties props) {
		try {
			Ziehung zie = auss.getLastZiehung();
			
			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + template);
									
			tw.addVariable("datelong", getLongDateString(zie.getDatum()));
			tw.addVariable("nr", zie.getNr());
			tw.addVariable("year", zie.getJahr());
			
			int zahlen[] = zie.getZahlen();
			tw.addVariable("zahl1", zahlen[0]);
			tw.addVariable("zahl2", zahlen[1]);
			tw.addVariable("zahl3", zahlen[2]);
			tw.addVariable("zahl4", zahlen[3]);
			tw.addVariable("zahl5", zahlen[4]);
			tw.addVariable("zahl6", zahlen[5]);
			
			tw.addVariable("zz", zie.getZusatzZahl());
			tw.addVariable("joker", zie.getJoker());
			
			tw.write(lottoPath + htmlFile);		                           
		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
	private String getLongDateString(Date date) {
		String helpStr = dateFormat.format(date);
		StringBuffer sb = new StringBuffer();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
			sb.append("Mittwoch, ");
		else
			sb.append("Samstag, ");
		
		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		sb.append(". ");
		sb.append(MONTHS[cal.get(Calendar.MONTH)]);
		sb.append(" ");
		sb.append(cal.get(Calendar.YEAR));

		return sb.toString();
	}
	
}