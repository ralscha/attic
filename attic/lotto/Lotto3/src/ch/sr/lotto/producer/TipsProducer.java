package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

//Properties
//numberOfTips   int

public class TipsProducer extends Producer {

  private static final String template = "head_tips.vm";
  private static final String htmlFile = "Tips.shtml";

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      VelocityContext context = new VelocityContext();
      Template templ = Velocity.getTemplate(templatePath + template);

      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFile)));

      templ.merge(context, pw);

      int numberOfTips = 10;
      if (props != null) {
        Integer not = (Integer) props.get("numberOfTips");
        if (not != null)
          numberOfTips = not.intValue();
      }

      java.util.List tips = new TipMaker().createTips(as, numberOfTips);

      pw.println("<br>");
      pw.println("<p class=\"subTitle\">Tips f&uuml;r die n&auml;chste Ausspielung</p>");
      pw.println("<br>");
      pw.println("<table width=\"50%\" border=\"1\" class=\"tableContents\">");

      for (int n = 0; n < tips.size(); n++) {
        int[] zahlen = (int[]) tips.get(n);

        pw.print("<tr>");
        for (int j = 0; j < 6; j++) {
          pw.print("<td align=center class=\"text\"><b>");
          pw.print(zahlen[j]);
          pw.print("</b></td>");
        }
        pw.println("</tr>");
        if (n < tips.size() - 1)
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