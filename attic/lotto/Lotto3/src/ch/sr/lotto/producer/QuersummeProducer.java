package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;


public class QuersummeProducer extends Producer {

	private static final String[] templates = {"queralle.vm", "quer42.vm", "quer45.vm",
    		"quer97.vm"};
	private static final String[] htmlFiles = {"queralle.shtml", "quer42.shtml", "quer45.shtml",
    		"quer97.shtml"};

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		try {

			Map[] summeMap = new Map[4];
			summeMap[0] = as.getAusspielungenStatistik(AusspielungenType.ALLE).getVerteilungQuersumme();
			summeMap[1] = as.getAusspielungenStatistik(AusspielungenType.AUS42).getVerteilungQuersumme();
			summeMap[2] = as.getAusspielungenStatistik(AusspielungenType.AUS45).getVerteilungQuersumme();
			summeMap[3] = as.getAusspielungenStatistik(AusspielungenType.AB1997).getVerteilungQuersumme();

			for (int i = 0; i < 4; i++) {
        VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + templates[i]);

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFiles[i])));
          
        

				int[] values = new int[6];
				for (int j = 0; j < values.length; j++) {
					values[j] = 0;
				}
				
				Integer help;
				for (int j = 0; j < 20; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[0] += help.intValue();
				}
				for (int j = 20; j < 30; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[1] += help.intValue();
				}
				for (int j = 30; j < 40; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[2] += help.intValue();					
				}
				for (int j = 40; j < 50; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[3] += help.intValue();
				}
				for (int j = 50; j < 60; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[4] += help.intValue();
				}

				for (int j = 60; j < 70; j++) {
					help = (Integer)summeMap[i].get(new Integer(j));
					if (help != null)
						values[5] += help.intValue();
				}

				PercentCalculator pc = new PercentCalculator();
				for (int j = 0; j < values.length; j++) {
					context.put("q"+j, new Integer(values[j]));
					pc.add(values[j]);
				}
				
				for (int j = 0; j < values.length; j++)
					context.put("pq"+j, pc.getPercentString(values[j]));

				templ.merge(context, pw);
        pw.close();
			}
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}