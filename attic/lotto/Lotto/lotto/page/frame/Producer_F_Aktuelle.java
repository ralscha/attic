package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import java.util.*;
import java.io.*;
import common.io.*;

public class Producer_F_Aktuelle extends PageProducer {
		
	private static final String header = "head_frame.template";	
	private static final String template = "aktuelle_frame.template";
	private static final String htmlFile = "aktuelle.html";

	public void producePage(Ausspielungen auss, Properties props) {
		try {
			Ziehung zie = auss.getLastZiehung();
			
			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + header);
			tw.addFile(templatePath + template);
			
			
			Date date = zie.getDatum();
			Calendar d = new GregorianCalendar();
			d.setTime(date);
			
			
			tw.addVariable("title", "Aktuelle Ausspielung");
			tw.addVariable("datelong", d.get(Calendar.DAY_OF_MONTH)+". "+
			                        MONTHS[d.get(Calendar.MONTH)]+
			                        " "+(d.get(Calendar.YEAR)));
			tw.addVariable("nr", zie.getNr());
			tw.addVariable("year", zie.getJahr() );
			
			int zahlen[] = zie.getZahlen();
			
			tw.addVariable("zahl1", zahlen[0]);
			tw.addVariable("zahl2", zahlen[1]);
			tw.addVariable("zahl3", zahlen[2]);
			tw.addVariable("zahl4", zahlen[3]);
			tw.addVariable("zahl5", zahlen[4]);
			tw.addVariable("zahl6", zahlen[5]);
			tw.addVariable("zz", zie.getZusatzZahl());
			tw.addVariable("joker", zie.getJoker());
	
			tw.write(lottoFramePath + htmlFile);		                           
		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
}