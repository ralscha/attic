package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class PaarProducer extends Producer {

  private static final String[] templates =
    { "paaralle.vm", "paar42.vm", "paar45.vm", "paar97.vm" };
  private static final String[] htmlFiles =
    { "paaralle.shtml", "paar42.shtml", "paar45.shtml", "paar97.shtml" };

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      int paar[][][] = new int[4][45][45];
      java.util.List tmpList[] = new ArrayList[4];

      paar[0] = as.getAusspielungenStatistik(AusspielungenType.ALLE).getPaar();
      paar[1] = as.getAusspielungenStatistik(AusspielungenType.AUS42).getPaar();
      paar[2] = as.getAusspielungenStatistik(AusspielungenType.AUS45).getPaar();
      paar[3] = as.getAusspielungenStatistik(AusspielungenType.AB1997).getPaar();

      for (int x = 0; x < 4; x++) {
        tmpList[x] = new ArrayList();
        for (int i = 1; i < 45; i++) {
          for (int j = i + 1; j <= 45; j++)
            tmpList[x].add(new PairEntry(i, j, paar[x][i - 1][j - 1]));
        }
        Collections.sort(tmpList[x], new TopPairEntryComparator());
      }

      for (int x = 0; x < 4; x++) {
        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + templates[x]);


        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[x])));
        templ.merge(context, pw);

        for (int i = 0; i < tmpList[x].size(); i++) {

          pw.println("<tr>");

          pw.print("<td align=\"CENTER\">");
          pw.print(((PairEntry) tmpList[x].get(i)).getValue1());
          pw.println("</td>");

          pw.print("<td align=\"CENTER\">");
          pw.print(((PairEntry) tmpList[x].get(i)).getValue2());
          pw.println("</td>");

          pw.print("<td align=\"CENTER\">");
          pw.print(((PairEntry) tmpList[x].get(i)).getData());
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