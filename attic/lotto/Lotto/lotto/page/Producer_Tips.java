package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;

//Properties
//numberOfTips   int

public class Producer_Tips extends PageProducer {
	
	private static final String template = "head_tips.template";
	private static final String htmlFile = "Tips.shtml";

	public void producePage(Ausspielungen auss, Properties props) {
		try {

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + template);

  			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFile)));
			tw.write(pw);

			int numberOfTips = 10;
			if (props != null) {
				Integer not = (Integer)props.get("numberOfTips");
				if (not != null)
					numberOfTips = not.intValue();
			}

			java.util.List tips = new TipMaker().createTips(auss, numberOfTips);
			
			pw.println("<br>");
			pw.println("<p class=\"subTitle\">Tips f&uuml;r die n&auml;chste Ausspielung</p>");
			pw.println("<br>");
			pw.println("<table width=\"50%\" border=\"1\" class=\"tableContents\">");

			for (int n = 0; n < tips.size(); n++) {
				int[] zahlen = ((Ziehung)tips.get(n)).getZahlen();
				
				pw.print("<tr>");
				for (int j = 0; j < 6; j++) {
					pw.print("<td align=center class=\"text\"><b>");
					pw.print(zahlen[j]);
					pw.print("</b></td>");
				}
				pw.println("</tr>");
				if (n < tips.size()-1)
					pw.println("<tr></tr>");
			}
			pw.println("</table>");
			pw.println("<!--#include file=\"include/tail.shtml\"-->");
			pw.close();

		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}