package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;


public class AusstehendProducer extends Producer {
	
	private static final String template = "head_auss.vm";
	private static final String htmlFile = "auss.shtml";
	
	public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		try {

			java.util.List tmpList = new ArrayList();

      int[] ausstehend = as.getAusstehend();
      
			for (int x = 0; x < 45; x++) {
				tmpList.add(new Entry(x+1, ausstehend[x]));
      }
      
			Collections.sort(tmpList);
            
      VelocityContext context = new VelocityContext();

      Template templ = Velocity.getTemplate(templatePath + template);

      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFile)));

      templ.merge(context, pw);

			pw.println("<br>");
			pw.println("<p class=\"subTitle\">Seit wann ausstehend?</p>");
			pw.println("<br>");
			pw.println("<table border=\"1\" class=\"tableContents\">");
			pw.println("<tr><th class=\"tableHeader\">Ausspielungen</th><th colspan=6 class=\"tableHeader\">Zahlen</th></tr>");

			int i = 0;
			int j;
			while (i < 39) {
				pw.print("<tr><th class=\"tableRowHeader\">");
				pw.print(((Entry)tmpList.get(i)).getData());
				pw.print("</th>");
				pw.print("<td align=\"CENTER\" class=\"text\">");
				pw.print(((Entry)tmpList.get(i)).getValue());
				pw.print("</td>");
				
				j = 1;
				while (((Entry)tmpList.get(i+1)).getData() == ((Entry)tmpList.get(i)).getData()) {
					i++; j++;
					pw.print("<td align=\"CENTER\" class=\"text\">");
					pw.print(((Entry)tmpList.get(i)).getValue());
					pw.print("</td>");

				}
				i++;				
				pw.print("<td colspan=\"");
				pw.print(String.valueOf(6-j));
				pw.println("\"></td></tr>");
        	}
			// Die letzten 6
			pw.print("	<tr><th class=\"tableRowHeader\">");
			pw.print(((Entry)tmpList.get(i)).getData());
			pw.println("</th>");
			
			for (; i < 45; i++) {
				pw.print("<td align=\"center\" class=\"text\">");
				pw.print(((Entry)tmpList.get(i)).getValue());
				pw.print("</td>");
			}
			
			pw.println("</tr></table>");
			pw.println("<!--#include file=\"include/tail.shtml\"-->");
			
			pw.close();
			
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}