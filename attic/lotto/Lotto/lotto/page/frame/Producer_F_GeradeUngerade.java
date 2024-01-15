package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;

public class Producer_F_GeradeUngerade extends PageProducer {
			
	private static final String header = "head_frame.template";		
	private static final String[] templates = {"gualle_frame.template", "gu42_frame.template", "gu45_frame.template", "gu97_frame.template"};		
	private static final String[] htmlFiles = {"GUalle.html", "GU42.html", "GU45.html", "GUz97.html"};   	
			
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {
		
			int hger[];			
			AusspielungenType[] types = AusspielungenType.getTypesAsArray();
			for (int i = 0; i < 4; i++) {
				hger = auss.getAusspielungenStatistik(types[i]).getVerteilungHaeufigkeitGerade();
        		TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + header);
        		tw.addFile(templatePath + templates[i]);

				
				tw.addVariable("title", "Gerade / Ungerade " + TITLES[i]);
				for (int j = 0; j < 7; j++)
					tw.addVariable("g"+j,hger[j]);
				
				tw.write(lottoFramePath + htmlFiles[i]);
			}

			/*************************************************************************************/

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + header);
			tw.addFile(templatePath + "gutotal_frame.template");
			tw.addVariable("title", "Gerade / Ungerade Total");
			
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
			
		
			tw.write(lottoFramePath + "GUtotal.html");

		} catch (Exception fe) {
			fe.printStackTrace();
			System.err.println(fe);
		}
	}	
}