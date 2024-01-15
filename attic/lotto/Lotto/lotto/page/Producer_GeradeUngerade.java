package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_GeradeUngerade extends PageProducer {
			
			
	private static final String[] templates = {"gualle.template", "gu42.template", "gu45.template", "gu97.template"};		
	private static final String[] htmlFiles = {"GUalle.shtml", "GU42.shtml", "GU45.shtml", "GU97.shtml"};   	
			
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {
		
			int hger[];			
			AusspielungenType[] types = AusspielungenType.getTypesAsArray();
			for (int i = 0; i < 4; i++) {
				hger = auss.getAusspielungenStatistik(types[i]).getVerteilungHaeufigkeitGerade();
        		TemplateWriter tw = new TemplateWriter();
        		tw.addFile(templatePath + templates[i]);
				
				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < hger.length; j++) 
					pc.add(hger[j]);

				for (int j = 0; j < hger.length; j++) {
				  tw.addVariable("g"+j, hger[j]);
				  tw.addVariable("p"+j, pc.getPercentString(hger[j]));
				}
				
				tw.write(lottoPath + htmlFiles[i]);
			}

			/*************************************************************************************/

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + "gutotal.template");

			int galle = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getTotalGerade();
			int ualle = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getTotalUngerade();
			int g1479 = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getTotalGerade();
			int u1479 = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getTotalUngerade();
			int g186  = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getTotalGerade();
			int u186  = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getTotalUngerade();
			int g1997 = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getTotalGerade();
			int u1997 = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getTotalUngerade();
				
			tw.addVariable("galle", galle);
			tw.addVariable("ualle", ualle);
			tw.addVariable("g1479", g1479);
			tw.addVariable("u1479", u1479);
			tw.addVariable("g186", g186);
			tw.addVariable("u186",  u186);
			tw.addVariable("g1997", g1997);
			tw.addVariable("u1997", u1997);
			
			PercentCalculator pc = new PercentCalculator();
			pc.add(galle);
			pc.add(ualle);
			tw.addVariable("pgalle", pc.getPercentString(galle));
			tw.addVariable("pualle", pc.getPercentString(ualle));
			
			pc.clear();
			pc.add(g1479);
			pc.add(u1479);			
			tw.addVariable("pg1479", pc.getPercentString(g1479));
			tw.addVariable("pu1479", pc.getPercentString(u1479));
			
			pc.clear();
			pc.add(g186);
			pc.add(u186);
			tw.addVariable("pg186", pc.getPercentString(g186));
			tw.addVariable("pu186",  pc.getPercentString(u186));
			
			pc.clear();
			pc.add(g1997);
			pc.add(u1997);
			tw.addVariable("pg1997", pc.getPercentString(g1997));
			tw.addVariable("pu1997", pc.getPercentString(u1997));	
			
			tw.write(lottoPath + "GUtotal.shtml");

		} catch (Exception fe) {
			fe.printStackTrace();
			System.err.println(fe);
		}
	}	
}