package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

//Properties
//byHaeufigkeit   true/false

public class HaeufigkeitProducer extends Producer {

  private static final String[] templates =
    { "Hhalle.vm", "Hh42.vm", "Hh45.vm", "Hh97.vm", "Hzalle.vm", "Hz42.vm", "Hz45.vm", "Hz97.vm" };
  private static final String[] htmlFiles =
    {
      "Hhalle.shtml",
      "Hh42.shtml",
      "Hh45.shtml",
      "Hh97.shtml",
      "Hzalle.shtml",
      "Hz42.shtml",
      "Hz45.shtml",
      "Hz97.shtml" };

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {
      boolean orderByHaeufigkeit = true;
      if (props != null)
        orderByHaeufigkeit = ((Boolean) props.get("orderByHaeufigkeit")).booleanValue();

      java.util.List tmpList[] = new ArrayList[4];
      int haeuf[][] = new int[4][45];

      haeuf[0] = as.getAusspielungenStatistik(AusspielungenType.ALLE).getHaeufigkeit();
      haeuf[1] = as.getAusspielungenStatistik(AusspielungenType.AUS42).getHaeufigkeit();
      haeuf[2] = as.getAusspielungenStatistik(AusspielungenType.AUS45).getHaeufigkeit();
      haeuf[3] = as.getAusspielungenStatistik(AusspielungenType.AB1997).getHaeufigkeit();

      if (orderByHaeufigkeit) {
        for (int n = 0; n < 4; n++) {
          tmpList[n] = new ArrayList();
          for (int x = 0; x < 45; x++) {
            tmpList[n].add(new Entry(x + 1, haeuf[n][x]));
          }
          Collections.sort(tmpList[n]);
        }
      }

      for (int i = 0; i < 4; i++) {

        VelocityContext context = new VelocityContext();

        if (orderByHaeufigkeit) {

          Template templ = Velocity.getTemplate(templatePath + templates[i]);

          PrintWriter pw =
            new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i])));

          for (int j = 0; j < 22; j++) {
            context.put("z" + (j + 1), new Integer(((Entry) tmpList[i].get(j)).getValue()));
            context.put("h" + (j + 1), new Integer(((Entry) tmpList[i].get(j)).getData()));
            context.put("z" + (j + 24), new Integer(((Entry) tmpList[i].get(j + 23)).getValue()));
            context.put("h" + (j + 24), new Integer(((Entry) tmpList[i].get(j + 23)).getData()));
          }

          context.put("z23", new Integer(((Entry) tmpList[i].get(22)).getValue()));
          context.put("h23", new Integer(((Entry) tmpList[i].get(22)).getData()));

          templ.merge(context, pw);
          pw.close();
        } else {

          Template templ = Velocity.getTemplate(templatePath + templates[i + 4]);

          PrintWriter pw =
            new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i + 4])));

          for (int j = 0; j < 22; j++) {
            context.put("z" + (j + 1), new Integer(j + 1));
            context.put("h" + (j + 1), new Integer(haeuf[i][j]));
            context.put("z" + (j + 24), new Integer(j + 24));
            context.put("h" + (j + 24), new Integer(haeuf[i][j + 23]));
          }
          context.put("z23", new Integer(23));
          context.put("h23", new Integer(haeuf[i][22]));

          templ.merge(context, pw);
          pw.close();

        }
      }

    } catch (Exception fe) {
      System.err.println(fe);
    }
  }
}