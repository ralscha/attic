package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_Quersumme extends PageProducer {

	private static final String[] templates = {"queralle.template", "quer42.template", "quer45.template",
    		"quer97.template"};
	private static final String[] htmlFiles = {"queralle.shtml", "quer42.shtml", "quer45.shtml",
    		"quer97.shtml"};

	public void producePage(Ausspielungen auss, Properties props) {
		try {

			Map[] summeMap = new Map[4];
			summeMap[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungQuersumme();
			summeMap[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungQuersumme();
			summeMap[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungQuersumme();
			summeMap[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungQuersumme();

			for (int i = 0; i < 4; i++) {
				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + templates[i]);

				int[] values = new int[6];
				for (int j = 0; j < values.length; j++) {
					values[j] = 0;
				}
				
				Integer help;
				for (int j = 0; j < 20; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[0] += help.intValue();
				}
				for (int j = 20; j < 30; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[1] += help.intValue();
				}
				for (int j = 30; j < 40; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[2] += help.intValue();					
				}
				for (int j = 40; j < 50; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[3] += help.intValue();
				}
				for (int j = 50; j < 60; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[4] += help.intValue();
				}

				for (int j = 60; j < 70; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[5] += help.intValue();
				}

				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < values.length; j++) {
					tw.addVariable("q"+j, values[j]);
					pc.add(values[j]);
				}
				
				for (int j = 0; j < values.length; j++)
					tw.addVariable("pq"+j, pc.getPercentString(values[j]));

				tw.write(lottoPath + htmlFiles[i]);
			}
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}