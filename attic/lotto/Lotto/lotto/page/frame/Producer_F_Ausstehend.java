package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;


public class Producer_F_Ausstehend extends PageProducer {
	
	private static final String header = "head_frame.template";
	private static final String htmlFile = "Auss.html";
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {

			java.util.List tmpList = new ArrayList();
			int[] ausstehend = auss.getAusstehend();

			for (int x = 0; x < 45; x++)
				tmpList.add(new Entry(x+1, ausstehend[x]));

			Collections.sort(tmpList);

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + header);
			tw.addVariable("title", "Seit wann ausstehend?");
			

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lottoFramePath + htmlFile)));			
			tw.write(pw);

			pw.println("<h2 align=center>Seit wann ausstehend?</h2>");
			pw.println("<div align=center><center>");
			pw.println("<table border=1>");
			pw.println("<tr bgcolor=\"#101010\"><th width=\"14%\"><FONT color=\"#FFFFFF\">Ausspielungen</font></th><th colspan=6><FONT color=\"#FFFFFF\">Zahlen</font></th></tr>");

			int i = 0;
			int j;
			
			while (i < 39) {
				pw.print("<tr><th bgcolor=\"#BBCCCC\">" + ((Entry)tmpList.get(i)).getData() + " </th>");
				pw.print("<td bgcolor=\"#EEEEEE\" align=center>" + ((Entry)tmpList.get(i)).getValue() + " </td>");

				j = 1;
				while (((Entry)tmpList.get(i+1)).getData() == ((Entry)tmpList.get(i)).getData()) {
		        	i++; j++;
        			pw.print("<td bgcolor=\"#EEEEEE\" align=center>" + ((Entry)tmpList.get(i)).getValue() + " </td>");
        		}
				
				i++;
        		for (; j < 6; j++)
					pw.print("<td align=center>&#160; </td>");

				pw.println("</tr>");
        	}

			// Die letzten 6
        	pw.print("<tr><th bgcolor=\"#BBCCCC\">" + ((Entry)tmpList.get(i)).getData() + " </th>");
			for (; i < 45; i++)
				pw.print("<td bgcolor=\"#EEEEEE\" align=center>" + ((Entry)tmpList.get(i)).getValue() + " </td>");

			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</center></div>");
			pw.println("</body>");
			pw.println("</html>");

        	pw.close();		
			
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
}