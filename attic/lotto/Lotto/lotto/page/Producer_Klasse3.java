package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_Klasse3 extends PageProducer {

	private static final String[] templates = {"klasse3alle.template", "klasse342.template", "klasse345.template",
    		"klasse397.template"};
	private static final String[] htmlFiles = {"klasse3alle.shtml", "klasse342.shtml", "klasse345.shtml",
    		"klasse397.shtml"};

	public void producePage(Ausspielungen auss, Properties props) {
		try {

			Map[] klasseMap = new Map[4];
			klasseMap[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungDreierKlassen();
			klasseMap[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungDreierKlassen();
			klasseMap[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungDreierKlassen();
			klasseMap[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungDreierKlassen();

			for (int i = 0; i < 4; i++) {
				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + templates[i]);

				int[] values = new int[5];
				for (int j = 0; j < values.length; j++) {
					values[j] = 0;
				}
				
				Integer help;
				help = (Integer)klasseMap[i].get(new Integer(2));
				if (help != null)
					values[0] = help.intValue();
				
				help = (Integer)klasseMap[i].get(new Integer(3));
				if (help != null)
					values[1] = help.intValue();
				
				help = (Integer)klasseMap[i].get(new Integer(4));
				if (help != null)
					values[2] = help.intValue();					

				help = (Integer)klasseMap[i].get(new Integer(5));
				if (help != null)
					values[3] = help.intValue();

				help = (Integer)klasseMap[i].get(new Integer(6));
				if (help != null)
					values[4] = help.intValue();

				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < values.length; j++) {
					tw.addVariable("k"+j, values[j]);
					pc.add(values[j]);
				}
				
				for (int j = 0; j < values.length; j++)
					tw.addVariable("pk"+j, pc.getPercentString(values[j]));

				tw.write(lottoPath + htmlFiles[i]);
			}
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}