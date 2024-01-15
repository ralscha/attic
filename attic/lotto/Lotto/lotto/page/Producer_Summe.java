package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_Summe extends PageProducer {

	private static final String[] templates = {"summealle.template", "summe42.template", "summe45.template",
    		"summe97.template"};
	private static final String[] htmlFiles = {"summealle.shtml", "summe42.shtml", "summe45.shtml",
    		"summe97.shtml"};

	public void producePage(Ausspielungen auss, Properties props) {
		try {

			Map[] summeMap = new Map[4];
			summeMap[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungSumme();
			summeMap[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungSumme();
			summeMap[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungSumme();
			summeMap[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungSumme();

			for (int i = 0; i < 4; i++) {
				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + templates[i]);

				int[] values = new int[5];
				for (int j = 0; j < values.length; j++) {
					values[j] = 0;
				}
				
				Integer help;
				for (int j = 0; j < 50; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[0] += help.intValue();
				}
				for (int j = 50; j < 100; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[1] += help.intValue();
				}
				for (int j = 100; j < 150; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[2] += help.intValue();					
				}
				for (int j = 150; j < 200; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[3] += help.intValue();
				}
				for (int j = 200; j < 260; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[4] += help.intValue();
				}

				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < values.length; j++) {
					tw.addVariable("s"+j, values[j]);
					pc.add(values[j]);
				}
				
				for (int j = 0; j < values.length; j++)
					tw.addVariable("sp"+j, pc.getPercentString(values[j]));

				tw.write(lottoPath + htmlFiles[i]);
			}
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}