package lotto.page;

import lotto.*;
import lotto.db.*;
import java.util.*;
import java.io.*;
import common.io.*;

public class Producer_Top extends PageProducer {
		
	private static final String template = "frtop.template";
	private static final String htmlFile = "frtop.html";

	public void producePage(Ausspielungen auss, Properties props) {
		try {
			Ziehung zie = auss.getLastZiehung();
			Calendar cal = new GregorianCalendar();
			cal.setTime(zie.getDatum());
			
			TemplateWriter tw = new TemplateWriter();
			tw.addFile(headerFile);
			tw.addFile(templatePath + template);
			
			tw.addVariable("title", "Schweizer Zahlenlotto");
			
			StringBuffer sb = new StringBuffer();
			sb.append(cal.get(Calendar.DAY_OF_MONTH));
			sb.append(". ");
			sb.append(MONTHS[cal.get(Calendar.MONTH)]);
			sb.append(" ");
			sb.append(cal.get(Calendar.YEAR));
				
			tw.addVariable("datelong", sb.toString());

			tw.write(lottoPath + htmlFile);		                           
		} catch (Exception fe) {
			//Log
		}
	}
	
}