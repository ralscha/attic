package ch.sr.lotto.producer;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class AusstehendWMLProducer extends Producer {

  private static final String template = "stat_wml.vm";
  private static final String htmlFile = "stat.wml";

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      java.util.List tmpList = new ArrayList();

      int[] ausstehend = as.getAusstehend();

      for (int x = 0; x < 45; x++) {
        tmpList.add(new Entry(x + 1, ausstehend[x]));
      }

      Collections.sort(tmpList);

      VelocityContext context = new VelocityContext();

      Template templ = Velocity.getTemplate(templatePath + template);

      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(quotenPath + htmlFile)));

      templ.merge(context, pw);

      DecimalFormat form = new DecimalFormat("00");

      Draw draw = Draw.getLastDraw(db);
      pw.println("<p>");
      pw.println(dateFormat.format(draw.getDrawdate()));
      pw.println("<br/>Anzahl Zieh.--Zahlen<br/>");

      int i = 0;
      int j;
      while (i < 39) {
        pw.print(form.format(((Entry) tmpList.get(i)).getData()));
        pw.print("--");
        pw.print(form.format(((Entry) tmpList.get(i)).getValue()));
        pw.print(" ");

        j = 1;
        while (((Entry) tmpList.get(i + 1)).getData() == ((Entry) tmpList.get(i)).getData()) {
          i++;
          j++;
          pw.print(form.format(((Entry) tmpList.get(i)).getValue()));
          pw.print(" ");

        }
        pw.println("<br/>");
        i++;
      }
      // Die letzten 6
      pw.print(form.format(((Entry) tmpList.get(i)).getData()));
      pw.print("--");

      for (; i < 45; i++) {
        pw.print(form.format(((Entry) tmpList.get(i)).getValue()));
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