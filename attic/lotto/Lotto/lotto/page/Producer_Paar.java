package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_Paar extends PageProducer {
			
	private static final String[] templates = {"paaralle.template", "paar42.template", "paar45.template", "paar97.template"};
	private static final String[] htmlFiles = {"paaralle.shtml", "paar42.shtml", "paar45.shtml", "paar97.shtml"};
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {
			
			int paar[][][] = new int[4][45][45];
			java.util.List tmpList[] = new ArrayList[4];
				
			paar[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getPaar();
			paar[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getPaar();
			paar[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getPaar();
			paar[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getPaar();

			for (int x = 0; x < 4; x++) {
				tmpList[x] = new ArrayList();
				for (int i = 1; i < 45; i++) {
					for (int j = i+1; j <= 45; j++)
						tmpList[x].add(new PairEntry(i, j, paar[x][i-1][j-1]));
				}
				Collections.sort(tmpList[x], new TopPairEntryComparator());
        	}

			for (int x = 0; x < 4; x++) {
        		TemplateWriter tw = new TemplateWriter();
        		tw.addFile(templatePath + templates[x]);
				
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[x])));
				tw.write(pw);
				
				
				for (int i = 0; i < tmpList[x].size(); i++) {
					
					pw.println("<tr>");
					
					pw.print("<td align=\"CENTER\">");
					pw.print(((PairEntry)tmpList[x].get(i)).getValue1());
					pw.println("</td>");
					
					pw.print("<td align=\"CENTER\">");
					pw.print(((PairEntry)tmpList[x].get(i)).getValue2());
					pw.println("</td>");
					
					pw.print("<td align=\"CENTER\">");
					pw.print(((PairEntry)tmpList[x].get(i)).getData());
					pw.println("</td>");
					
					pw.println("</tr>");
				}
				
				pw.println("</table>");
				pw.println("<!--#include file=\"include/tail.shtml\"-->");				
				
				pw.close();
			}			
			
		
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}