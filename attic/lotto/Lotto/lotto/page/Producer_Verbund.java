package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_Verbund extends PageProducer {
			
	private static final String[] templates = {"Verbundalle.template", "Verbund42.template", "Verbund45.template", "Verbund97.template"};
	private static final String[] htmlFiles = {"Verbundalle.shtml", "Verbund42.shtml", "Verbund45.shtml", "Verbund97.shtml"};
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {
			
			Map[] verbundMap = new Map[4];
			verbundMap[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungVerbund();
			verbundMap[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungVerbund();
			verbundMap[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungVerbund();
			verbundMap[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungVerbund();

        for (int i = 0; i < 4; i++) {
        		TemplateWriter tw = new TemplateWriter();
        		tw.addFile(templatePath + templates[i]);
				
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

        		PercentCalculator pc = new PercentCalculator();        	
        		for (int j = 0; j < values.length; j++) {
        			tw.addVariable("auf"+j, values[j]);
        			pc.add(values[j]);
        		}
        		
        		for (int j = 0; j < values.length; j++) 
        			tw.addVariable("per"+j, pc.getPercentString(values[j]));		        	
        	
        		tw.write(lottoPath + htmlFiles[i]);
			}			
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}