package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class Klasse5Producer extends Producer {

  private static final String[] templates =
    { "klasse5alle.vm", "klasse542.vm", "klasse545.vm", "klasse597.vm" };
  private static final String[] htmlFiles =
    { "klasse5alle.shtml", "klasse542.shtml", "klasse545.shtml", "klasse597.shtml" };

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      Map[] klasseMap = new Map[4];
      klasseMap[0] =
        as.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungFuenferKlassen();
      klasseMap[1] =
        as.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungFuenferKlassen();
      klasseMap[2] =
        as.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungFuenferKlassen();
      klasseMap[3] =
        as.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungFuenferKlassen();

      for (int i = 0; i < 4; i++) {
        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + templates[i]);

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i])));

        int[] values = new int[5];
        for (int j = 0; j < values.length; j++) {
          values[j] = 0;
        }

        Integer help;
        help = (Integer) klasseMap[i].get(new Integer(2));
        if (help != null)
          values[0] = help.intValue();

        help = (Integer) klasseMap[i].get(new Integer(3));
        if (help != null)
          values[1] = help.intValue();

        help = (Integer) klasseMap[i].get(new Integer(4));
        if (help != null)
          values[2] = help.intValue();

        help = (Integer) klasseMap[i].get(new Integer(5));
        if (help != null)
          values[3] = help.intValue();

        help = (Integer) klasseMap[i].get(new Integer(6));
        if (help != null)
          values[4] = help.intValue();

        PercentCalculator pc = new PercentCalculator();
        for (int j = 0; j < values.length; j++) {
          context.put("k" + j, new Integer(values[j]));
          pc.add(values[j]);
        }

        for (int j = 0; j < values.length; j++)
          context.put("pk" + j, pc.getPercentString(values[j]));

        templ.merge(context, pw);
        pw.close();
      }
    } catch (Exception fe) {
      System.err.println(fe);
    }
  }
}