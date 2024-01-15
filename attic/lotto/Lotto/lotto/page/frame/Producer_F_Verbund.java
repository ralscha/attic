package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_F_Verbund extends PageProducer {
	
	private static final String header = "head_frame.template";
	private static final String[] templates = {"verbundalle_frame.template", "verbund42_frame.template", "verbund45_frame.template", "verbund97_frame.template"};
	private static final String[] htmlFiles = {"Folalle.html", "Fol42.html", "Fol45.html", "Folz97.html"};
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {
			
			Map[] verbundMap = new Map[4];
			verbundMap[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungVerbund();
			verbundMap[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungVerbund();
			verbundMap[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungVerbund();
			verbundMap[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungVerbund();

        for (int i = 0; i < 4; i++) {
        		TemplateWriter tw = new TemplateWriter();
        		tw.addFile(templatePath + header);
        		tw.addFile(templatePath + templates[i]);
				tw.addVariable("title", "Aufeinanderfolgende Zahlen "+TITLES[i]);
	
        	
        		Integer[] values = new Integer[11];
        		
        		values[0] = (Integer)verbundMap[i].get(VerbundType.NICHTS.getExternal());
	        	values[1] = (Integer)verbundMap[i].get(VerbundType.ZWILLING.getExternal());
				values[2] = (Integer)verbundMap[i].get(VerbundType.DRILLING.getExternal());
				values[3] = (Integer)verbundMap[i].get(VerbundType.VIERLING.getExternal());
				values[4] = (Integer)verbundMap[i].get(VerbundType.FUENFLING.getExternal());
				values[5] = (Integer)verbundMap[i].get(VerbundType.SECHSLING.getExternal());
				values[6] = (Integer)verbundMap[i].get(VerbundType.DOPPELZWILLING.getExternal());
				values[7] = (Integer)verbundMap[i].get(VerbundType.DOPPELDRILLING.getExternal());
				values[8] = (Integer)verbundMap[i].get(VerbundType.DRILLINGZWILLING.getExternal());
				values[9] = (Integer)verbundMap[i].get(VerbundType.DREIFACHZWILLING.getExternal());
				values[10] =(Integer)verbundMap[i].get(VerbundType.VIERLINGZWILLING.getExternal());
        		
        		for (int j = 0; j < values.length; j++) {
        			tw.addVariable("auf"+j, values[j]);        			
        		}
        		        	
        		tw.write(lottoFramePath + htmlFiles[i]);
			}			
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}