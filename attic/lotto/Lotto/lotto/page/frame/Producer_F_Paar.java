package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_F_Paar extends PageProducer {
			
	private static final String header = "head_frame.template";	
	private static final String[] templates = {"paaralle_frame.template", "paar42_frame.template", "paar45_frame.template", "paar97_frame.template"};
	private static final String[] htmlFiles = {"paaralle.html", "paar42.html", "paar45.html", "paarz97.html"};
	
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
				tw.addFile(templatePath + header);
        		tw.addFile(templatePath + templates[x]);
				tw.addVariable("title", "Paare "+TITLES[x]);
				
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lottoFramePath + htmlFiles[x])));
				tw.write(pw);
				
				pw.println("<PRE>");
				pw.println("  <B>Zahl1  Zahl2   Ausspielungen</B>");
				
				StringBuffer sb = new StringBuffer();
				
				for (int i = 0; i < tmpList[x].size(); i++) {
					sb.setLength(0);
					int z1 = ((PairEntry)tmpList[x].get(i)).getValue1();
					int z2 = ((PairEntry)tmpList[x].get(i)).getValue2();
					int d  = ((PairEntry)tmpList[x].get(i)).getData();
					
					if (z1 < 10) sb.append("    ");
					else         sb.append("   ");
					sb.append(z1);
					
					if (z2 < 10) sb.append("      ");
					else         sb.append("     ");
					sb.append(z2);
					
					if (d < 100) sb.append(" ");
					if (d < 10)  sb.append(" ");
					sb.append("         ");
					sb.append(d);
					
					pw.println(sb.toString());

				}
				
				pw.println("</PRE>");
				pw.println("</body></html>");
				
				pw.close();
			}			
			

			
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}