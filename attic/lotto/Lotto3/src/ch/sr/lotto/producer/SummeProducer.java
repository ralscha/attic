package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class SummeProducer extends Producer {

  private static final String[] templates =
    { "summealle.vm", "summe42.vm", "summe45.vm", "summe97.vm" };
  private static final String[] htmlFiles =
    { "summealle.shtml", "summe42.shtml", "summe45.shtml", "summe97.shtml" };

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      Map[] summeMap = new Map[4];
      summeMap[0] = as.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungSumme();
      summeMap[1] = as.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungSumme();
      summeMap[2] = as.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungSumme();
      summeMap[3] = as.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungSumme();

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
        for (int j = 0; j < 50; j++) {
          help = (Integer) summeMap[i].get(new Integer(j));
          if (help != null)
            values[0] += help.intValue();
        }
        for (int j = 50; j < 100; j++) {
          help = (Integer) summeMap[i].get(new Integer(j));
          if (help != null)
            values[1] += help.intValue();
        }
        for (int j = 100; j < 150; j++) {
          help = (Integer) summeMap[i].get(new Integer(j));
          if (help != null)
            values[2] += help.intValue();
        }
        for (int j = 150; j < 200; j++) {
          help = (Integer) summeMap[i].get(new Integer(j));
          if (help != null)
            values[3] += help.intValue();
        }
        for (int j = 200; j < 260; j++) {
          help = (Integer) summeMap[i].get(new Integer(j));
          if (help != null)
            values[4] += help.intValue();
        }

        PercentCalculator pc = new PercentCalculator();
        for (int j = 0; j < values.length; j++) {
          context.put("s" + j, new Integer(values[j]));
          pc.add(values[j]);
        }

        for (int j = 0; j < values.length; j++)
          context.put("sp" + j, pc.getPercentString(values[j]));

        templ.merge(context, pw);
        pw.close();
      }
    } catch (Exception fe) {
      System.err.println(fe);
    }
  }
}