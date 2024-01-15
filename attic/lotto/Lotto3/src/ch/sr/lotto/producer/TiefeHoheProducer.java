package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class TiefeHoheProducer extends Producer {

  private static final String[] templates = { "htalle.vm", "ht42.vm", "ht45.vm", "ht97.vm" };
  private static final String[] htmlFiles =
    { "htalle.shtml", "ht42.shtml", "ht45.shtml", "ht97.shtml" };

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      int tiefe[];
      AusspielungenType[] types = AusspielungenType.getTypesAsArray();
      for (int i = 0; i < 4; i++) {
        tiefe = as.getAusspielungenStatistik(types[i]).getVerteilungTiefe();

        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + templates[i]);

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i])));



        PercentCalculator pc = new PercentCalculator();
        for (int j = 0; j < tiefe.length; j++)
          pc.add(tiefe[j]);

        for (int j = 0; j < tiefe.length; j++) {
          context.put("ht" + j, new Integer(tiefe[j]));
          context.put("htp" + j, pc.getPercentString(tiefe[j]));
        }
        
        templ.merge(context, pw);
        pw.close();      }

    } catch (Exception fe) {
      fe.printStackTrace();
      System.err.println(fe);
    }
  }
}