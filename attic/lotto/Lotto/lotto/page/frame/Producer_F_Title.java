package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_F_Title extends PageProducer {

	private static final String header = "head_frame.template";
	private static final String template = "top_frame.template";
	
	private static final String htmlFile = "frtop.html";
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + header);
			tw.addFile(templatePath + template);

			tw.addVariable("title", "Schweizer Zahlenlotto");

			Ziehung zie = auss.getLastZiehung();
			Date date = zie.getDatum();		
			Calendar d = new GregorianCalendar();
			d.setTime(date);
			
			tw.addVariable("datelong", d.get(Calendar.DAY_OF_MONTH)+". "+
			                           MONTHS[d.get(Calendar.MONTH)]+
			                           " "+(d.get(Calendar.YEAR)));

			tw.write(lottoFramePath + htmlFile);


		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}
