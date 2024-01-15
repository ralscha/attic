package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class VerbundProducer extends Producer {

  private static final String[] templates =
    { "Verbundalle.vm", "Verbund42.vm", "Verbund45.vm", "Verbund97.vm" };
  private static final String[] htmlFiles =
    { "Verbundalle.shtml", "Verbund42.shtml", "Verbund45.shtml", "Verbund97.shtml" };

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      Map[] verbundMap = new Map[4];
      verbundMap[0] = as.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungVerbund();
      verbundMap[1] = as.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungVerbund();
      verbundMap[2] = as.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungVerbund();
      verbundMap[3] = as.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungVerbund();

      for (int i = 0; i < 4; i++) {

        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + templates[i]);

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i])));



        Integer[] values = new Integer[11];

        values[0] = (Integer) verbundMap[i].get(VerbundType.NICHTS.getExternal());
        values[1] = (Integer) verbundMap[i].get(VerbundType.ZWILLING.getExternal());
        values[2] = (Integer) verbundMap[i].get(VerbundType.DRILLING.getExternal());
        values[3] = (Integer) verbundMap[i].get(VerbundType.VIERLING.getExternal());
        values[4] = (Integer) verbundMap[i].get(VerbundType.FUENFLING.getExternal());
        values[5] = (Integer) verbundMap[i].get(VerbundType.SECHSLING.getExternal());
        values[6] = (Integer) verbundMap[i].get(VerbundType.DOPPELZWILLING.getExternal());
        values[7] = (Integer) verbundMap[i].get(VerbundType.DOPPELDRILLING.getExternal());
        values[8] = (Integer) verbundMap[i].get(VerbundType.DRILLINGZWILLING.getExternal());
        values[9] = (Integer) verbundMap[i].get(VerbundType.DREIFACHZWILLING.getExternal());
        values[10] = (Integer) verbundMap[i].get(VerbundType.VIERLINGZWILLING.getExternal());

        PercentCalculator pc = new PercentCalculator();
        for (int j = 0; j < values.length; j++) {
          context.put("auf" + j, values[j]);
          pc.add(values[j]);
        }

        for (int j = 0; j < values.length; j++)
          context.put("per" + j, pc.getPercentString(values[j]));

        templ.merge(context, pw);
        pw.close();
      }
    } catch (Exception fe) {
      System.err.println(fe);
    }
  }
}