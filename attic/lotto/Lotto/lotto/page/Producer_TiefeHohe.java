package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_TiefeHohe extends PageProducer {
			
			
	private static final String[] templates = {"htalle.template", "ht42.template", "ht45.template", "ht97.template"};		
	private static final String[] htmlFiles = {"htalle.shtml", "ht42.shtml", "ht45.shtml", "ht97.shtml"};   	
			
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {
		
			int tiefe[];			
			AusspielungenType[] types = AusspielungenType.getTypesAsArray();
			for (int i = 0; i < 4; i++) {
				tiefe = auss.getAusspielungenStatistik(types[i]).getVerteilungTiefe();
        		TemplateWriter tw = new TemplateWriter();
        		tw.addFile(templatePath + templates[i]);
				
				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < tiefe.length; j++) 
					pc.add(tiefe[j]);

				for (int j = 0; j < tiefe.length; j++) {
				  tw.addVariable("ht"+j, tiefe[j]);
				  tw.addVariable("htp"+j, pc.getPercentString(tiefe[j]));
				}
				
				tw.write(lottoPath + htmlFiles[i]);
			}

		} catch (Exception fe) {
			fe.printStackTrace();
			System.err.println(fe);
		}
	}	
}