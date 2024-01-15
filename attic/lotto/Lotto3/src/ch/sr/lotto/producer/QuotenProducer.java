package ch.sr.lotto.producer;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class QuotenProducer extends Producer {
		
	private static final String template = "quoten.vm";
	private static final String htmlFile = "quoten.shtml";

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		try {
			
			DecimalFormat form = new DecimalFormat("#,##0.00");
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			dfs.setGroupingSeparator('\'');
			form.setDecimalFormatSymbols(dfs);
			
			DecimalFormat form2 = new DecimalFormat("#,##0");
			form2.setDecimalFormatSymbols(dfs);
			
			Draw draw = Draw.getLastDraw(db);

      OQuery query = new OQuery();
      query.add(draw.getNo().intValue(), "no");
      query.add(draw.getYear().intValue(), "year");

      LottoGainQuota[] lgq = (LottoGainQuota[]) db.get(LottoGainQuota.class, query);

      query.reset();
      query.add(draw.getNo().intValue(), "no");
      query.add(draw.getYear().intValue(), "year");

      JokerGainQuota[] jgq = (JokerGainQuota[]) db.get(JokerGainQuota.class, query);

      query.reset();
      query.add(draw.getNo().intValue(), "no");
      query.add(draw.getYear().intValue(), "year");

      ExtraJokerGainQuota[] ejgq = (ExtraJokerGainQuota[]) db.get(ExtraJokerGainQuota.class, query);


			if ((lgq != null) && (jgq != null) && (ejgq != null)) {

      VelocityContext context = new VelocityContext();
        Template templ = Velocity.getTemplate(templatePath + template);

        PrintWriter pw =
          new PrintWriter(new BufferedWriter(new FileWriter(quotenPath + htmlFile)));


				if (lgq[0].getJackpot() != null) {
					context.put("lanz6", "JACKPOT");
					context.put("lbet6", form.format(lgq[0].getJackpot()));
				} else {
					context.put("lanz6", form2.format(lgq[0].getNum6()));
					context.put("lbet6", form.format(lgq[0].getQuota6()));
				}
				
				context.put("lanz5plus", form2.format(lgq[0].getNum5plus()));
				context.put("lbet5plus", form.format(lgq[0].getQuota5plus()));
				context.put("lanz5", form2.format(lgq[0].getNum5()));
				context.put("lbet5", form.format(lgq[0].getQuota5()));
				context.put("lanz4", form2.format(lgq[0].getNum4()));
				context.put("lbet4", form.format(lgq[0].getQuota4()));
				context.put("lanz3", form2.format(lgq[0].getNum3()));
				context.put("lbet3", form.format(lgq[0].getQuota3()));
        
        Double nextLottoQuota = (Double)props.get("nextLottoQuota");
        Double nextJokerQuota = (Double)props.get("nextJokerQuota");
        Double nextExtraJokerQuota = (Double)props.get("nextExtraJokerQuota");
        
        Date nextDate = (Date)props.get("nextDate");
        
				if (nextLottoQuota != null) {					 	
					context.put("lerwartetdatum", "Erwartete Gewinnsumme f&uuml;r 6 Richtige: "+
								dateFormat.format(nextDate)+"<BR>");
					context.put("lerwartetsumme", "<B>"+form.format(nextLottoQuota)+"</B>");
				} else {
					context.put("lerwartetdatum", "");
					context.put("lerwartetsumme", "");				
				}				
				
				if (jgq[0].getJackpot() != null) {
					context.put("janz6", "JACKPOT");
					context.put("jbet6", form.format(jgq[0].getJackpot()));
				} else {
					context.put("janz6", form2.format(jgq[0].getNum6()));
					context.put("jbet6", form.format(jgq[0].getQuota6()));
				}
				
				context.put("janz5", form2.format(jgq[0].getNum5()));
				context.put("jbet5", form.format(jgq[0].getQuota5()));
				context.put("janz4", form2.format(jgq[0].getNum4()));
				context.put("jbet4", form.format(jgq[0].getQuota4()));
				context.put("janz3", form2.format(jgq[0].getNum3()));
				context.put("jbet3", form.format(jgq[0].getQuota3()));
				context.put("janz2", form2.format(jgq[0].getNum2()));
				context.put("jbet2", form.format(jgq[0].getQuota2()));
				
				if (nextJokerQuota != null) {					 	
					context.put("jerwartetdatum", "Erwartete Gewinnsumme f&uuml;r 6 Richtige: "+
								dateFormat.format(nextDate)+"<BR>");
					context.put("jerwartetsumme", "<B>"+form.format(nextJokerQuota)+"</B>");
				} else {
					context.put("jerwartetdatum", "");
					context.put("jerwartetsumme", "");				
				}
        
        //ExtraJoker
        if (ejgq[0].getJackpot() != null) {
          context.put("ejanz6", "JACKPOT");
          context.put("ejbet6", form.format(ejgq[0].getJackpot()));
        } else {
          context.put("ejanz6", form2.format(ejgq[0].getNum6()));
          context.put("ejbet6", form.format(ejgq[0].getQuota6()));
        }

        context.put("ejanz5", form2.format(ejgq[0].getNum5()));
        context.put("ejbet5", form.format(ejgq[0].getQuota5()));
        context.put("ejanz4", form2.format(ejgq[0].getNum4()));
        context.put("ejbet4", form.format(ejgq[0].getQuota4()));
        context.put("ejanz3", form2.format(ejgq[0].getNum3()));
        context.put("ejbet3", form.format(ejgq[0].getQuota3()));
        context.put("ejanz2", form2.format(ejgq[0].getNum2()));
        context.put("ejbet2", form.format(ejgq[0].getQuota2()));

        if (nextExtraJokerQuota != null) {
          context.put("ejerwartetdatum", "Erwartete Gewinnsumme f&uuml;r 6 Richtige: "+
                dateFormat.format(nextDate)+"<BR>");
          context.put("ejerwartetsumme", "<B>"+form.format(nextExtraJokerQuota)+"</B>");
        } else {
          context.put("ejerwartetdatum", "");
          context.put("ejerwartetsumme", "");
        }
        
        
				
        templ.merge(context, pw);
        pw.close();
			}
			
		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
}