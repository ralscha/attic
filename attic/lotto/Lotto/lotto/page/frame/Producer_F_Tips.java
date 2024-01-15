package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;
import com.odi.util.*;

//Properties
//numberOfTips   int

public class Producer_F_Tips extends PageProducer {

	private static final String template = "head_frame.template";
	private static final String htmlFile = "Tips.html";
	private static final String title = "Tips f&uuml;r die n&auml;chste Ausspielung";
	
	public void producePage(Ausspielungen auss, Properties props) {
		try {

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + template);

			PrintWriter pw = new PrintWriter(
                   			new BufferedWriter(new FileWriter(lottoFramePath + htmlFile)));
			tw.addVariable("title", title);
			tw.write(pw);

			int numberOfTips = 10;
			if (props != null) {
				Integer not = (Integer) props.get("numberOfTips");
				if (not != null)
					numberOfTips = not.intValue();
			}

			java.util.List tips = new TipMaker().createTips(auss, numberOfTips);

			pw.println("<h2 align=center>"+title+"</h2>");
			pw.println("<div align=center><center>");
			pw.println("<table border=1 width=\"50%\">");

			boolean first = true;
			boolean jump = false;

			for (int n = 0; n < tips.size(); n++) {
				int[] zahlen = ((Ziehung) tips.get(n)).getZahlen();

				if (first) {
					pw.println("<tr bgcolor=\"#BBCCCC\">");
					for (int j = 0; j < 6; j++)
						pw.println("<td align=center width=\"16%\"><b>" + zahlen[j] + "</b></td>");

					pw.println("</tr>");
					first = false;
				} else {
					if (jump) 
						pw.println("<tr bgcolor=\"#BBCCCC\">");
					else
						pw.println("<tr bgcolor=\"#EEEEEE\">");

					jump = !jump;
					
					for (int j = 0; j < 6; j++)
						pw.println("<td align=center><b>" + zahlen[j] + "</b></td>");
					
					pw.println("</tr>");
				}
			}

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