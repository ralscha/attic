package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import java.text.*;
import common.io.*;
import com.odi.util.*;


public class Producer_Ausstehend_WML extends PageProducer {
	
	private static final String template = "stat_wml.template";
	private static final String htmlFile = "stat.wml";
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {

			java.util.List tmpList = new ArrayList();
			int[] ausstehend = auss.getAusstehend();

			for (int x = 0; x < 45; x++)
				tmpList.add(new Entry(x+1, ausstehend[x]));

			Collections.sort(tmpList);

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + template);

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(quotenPath + htmlFile)));
			
			tw.write(pw);
      
      DecimalFormat form = new DecimalFormat("00");

      Ziehung zie = auss.getLastZiehung();
      pw.println("<p>");
      pw.println(dateFormat.format(zie.getDatum()));
      pw.println("<br/>Anzahl Zieh.--Zahlen<br/>");

			int i = 0;
			int j;
			while (i < 39) {
				pw.print(form.format(((Entry)tmpList.get(i)).getData()));
				pw.print("--");
				pw.print(form.format(((Entry)tmpList.get(i)).getValue()));
        pw.print(" ");
				
				j = 1;
				while (((Entry)tmpList.get(i+1)).getData() == ((Entry)tmpList.get(i)).getData()) {
					i++; j++;
					pw.print(form.format(((Entry)tmpList.get(i)).getValue()));
					pw.print(" ");

				}
        pw.println("<br/>");
				i++;				
      }
			// Die letzten 6
      pw.print(form.format(((Entry)tmpList.get(i)).getData()));
      pw.print("--");
			
			for (; i < 45; i++) {
				pw.print(form.format(((Entry)tmpList.get(i)).getValue()));
        pw.print(" ");
			}
			
			pw.println("</p>");
			pw.println("</card>");
			pw.println("</wml>");

			
			pw.close();
			
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}