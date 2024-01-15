package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;

public class GeradeUngeradeProducer extends Producer {

  private static final String[] templates = { "gualle.vm", "gu42.vm", "gu45.vm", "gu97.vm" };
  private static final String[] htmlFiles =
    { "GUalle.shtml", "GU42.shtml", "GU45.shtml", "GU97.shtml" };

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
    try {

      int hger[];
      AusspielungenType[] types = AusspielungenType.getTypesAsArray();
      for (int i = 0; i < 4; i++) {

        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + templates[i]);

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i])));
          
          
        hger = as.getAusspielungenStatistik(types[i]).getVerteilungHaeufigkeitGerade();

        PercentCalculator pc = new PercentCalculator();
        for (int j = 0; j < hger.length; j++)
          pc.add(hger[j]);

        for (int j = 0; j < hger.length; j++) {
          context.put("g" + j, new Integer(hger[j]));
          context.put("p" + j, pc.getPercentString(hger[j]));
        }

        templ.merge(context, pw);
        pw.close();
      }

      /*************************************************************************************/

        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + "gutotal.vm");

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + "GUtotal.shtml")));
          
        

      int galle = as.getAusspielungenStatistik(AusspielungenType.ALLE).getTotalGerade();
      int ualle = as.getAusspielungenStatistik(AusspielungenType.ALLE).getTotalUngerade();
      int g1479 = as.getAusspielungenStatistik(AusspielungenType.AUS42).getTotalGerade();
      int u1479 = as.getAusspielungenStatistik(AusspielungenType.AUS42).getTotalUngerade();
      int g186 = as.getAusspielungenStatistik(AusspielungenType.AUS45).getTotalGerade();
      int u186 = as.getAusspielungenStatistik(AusspielungenType.AUS45).getTotalUngerade();
      int g1997 = as.getAusspielungenStatistik(AusspielungenType.AB1997).getTotalGerade();
      int u1997 = as.getAusspielungenStatistik(AusspielungenType.AB1997).getTotalUngerade();

      context.put("galle", new Integer(galle));
      context.put("ualle", new Integer(ualle));
      context.put("g1479", new Integer(g1479));
      context.put("u1479", new Integer(u1479));
      context.put("g186", new Integer(g186));
      context.put("u186", new Integer(u186));
      context.put("g1997", new Integer(g1997));
      context.put("u1997", new Integer(u1997));

      PercentCalculator pc = new PercentCalculator();
      pc.add(galle);
      pc.add(ualle);
      context.put("pgalle", pc.getPercentString(galle));
      context.put("pualle", pc.getPercentString(ualle));

      pc.clear();
      pc.add(g1479);
      pc.add(u1479);
      context.put("pg1479", pc.getPercentString(g1479));
      context.put("pu1479", pc.getPercentString(u1479));

      pc.clear();
      pc.add(g186);
      pc.add(u186);
      context.put("pg186", pc.getPercentString(g186));
      context.put("pu186", pc.getPercentString(u186));

      pc.clear();
      pc.add(g1997);
      pc.add(u1997);
      context.put("pg1997", pc.getPercentString(g1997));
      context.put("pu1997", pc.getPercentString(u1997));

      templ.merge(context, pw);
      pw.close();

    } catch (Exception fe) {
      fe.printStackTrace();
      System.err.println(fe);
    }
  }
}