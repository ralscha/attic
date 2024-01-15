package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_EndziffernSumme extends PageProducer {

	private static final String[] templates = {"summeendalle.template", "summeend42.template", "summeend45.template",
    		"summeend97.template"};
	private static final String[] htmlFiles = {"summeendalle.shtml", "summeend42.shtml", "summeend45.shtml",
    		"summeend97.shtml"};

	public void producePage(Ausspielungen auss, Properties props) {
		try {

			Map[] summeMap = new Map[4];
			summeMap[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungEndZiffernSumme();
			summeMap[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungEndZiffernSumme();
			summeMap[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungEndZiffernSumme();
			summeMap[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungEndZiffernSumme();

			for (int i = 0; i < 4; i++) {
				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + templates[i]);

				int[] values = new int[7];
				for (int j = 0; j < values.length; j++) {
					values[j] = 0;
				}
				
				Integer help;
				for (int j = 0; j < 15; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[0] += help.intValue();
				}
				for (int j = 15; j < 20; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[1] += help.intValue();
				}
				for (int j = 20; j < 25; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[2] += help.intValue();					
				}
				for (int j = 25; j < 30; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[3] += help.intValue();
				}
				for (int j = 30; j < 35; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[4] += help.intValue();
				}
				for (int j = 35; j < 40; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[5] += help.intValue();
				}
				for (int j = 40; j < 60; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[6] += help.intValue();
				}


				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < values.length; j++) {
					tw.addVariable("se"+j, values[j]);
					pc.add(values[j]);
				}
				
				for (int j = 0; j < values.length; j++)
					tw.addVariable("pse"+j, pc.getPercentString(values[j]));

				tw.write(lottoPath + htmlFiles[i]);
			}
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}