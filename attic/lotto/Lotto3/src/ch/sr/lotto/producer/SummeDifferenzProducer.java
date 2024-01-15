package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;


public class SummeDifferenzProducer extends Producer {

	private static final String[] templates = {"summediffalle.vm", "summediff42.vm", "summediff45.vm",
    		"summediff97.vm"};
	private static final String[] htmlFiles = {"summediffalle.shtml", "summediff42.shtml", "summediff45.shtml",
    		"summediff97.shtml"};

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		try {

			Map[] summeMap = new Map[4];
			summeMap[0] = as.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungSummeDifferenzen();
			summeMap[1] = as.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungSummeDifferenzen();
			summeMap[2] = as.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungSummeDifferenzen();
			summeMap[3] = as.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungSummeDifferenzen();

			for (int i = 0; i < 4; i++) {

        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + templates[i]);

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i])));


				int[] values = new int[7];
				for (int j = 0; j < values.length; j++) {
					values[j] = 0;
				}
				
				Integer help;
				for (int j = 0; j < 15; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[0] += help.intValue();
				}
				for (int j = 15; j < 20; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[1] += help.intValue();
				}
				for (int j = 20; j < 25; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[2] += help.intValue();					
				}
				for (int j = 25; j < 30; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[3] += help.intValue();
				}
				for (int j = 30; j < 35; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[4] += help.intValue();
				}
				for (int j = 35; j < 40; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[5] += help.intValue();
				}
				for (int j = 40; j < 60; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[6] += help.intValue();
				}


				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < values.length; j++) {
					context.put("sd"+j, new Integer(values[j]));
					pc.add(values[j]);
				}
				
				for (int j = 0; j < values.length; j++)
					context.put("psd"+j, pc.getPercentString(values[j]));

        templ.merge(context, pw);
        pw.close();			}
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}