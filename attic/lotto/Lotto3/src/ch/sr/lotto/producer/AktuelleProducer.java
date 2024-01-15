package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class AktuelleProducer extends Producer {
		
	private static final String template = "aktuelle.vm";
	private static final String htmlFile = "aktuelle.shtml";

	public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		try {
      Draw draw = Draw.getLastDraw(db);

      VelocityContext context = new VelocityContext();
      Template templ = Velocity.getTemplate(templatePath + template);

      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + htmlFile)));

			context.put("datelong", getLongDateString(draw.getDrawdate()));
			context.put("nr", draw.getNo());
			context.put("year", draw.getYear());
			
			context.put("zahl1", draw.getNum1());
			context.put("zahl2", draw.getNum2());
			context.put("zahl3", draw.getNum3());
			context.put("zahl4", draw.getNum4());
			context.put("zahl5", draw.getNum5());
			context.put("zahl6", draw.getNum6());
			
			context.put("zz", draw.getAddnum());
			context.put("joker", draw.getJoker());
      context.put("extrajoker", draw.getExtrajoker());
			
      templ.merge(context, out);
      out.close();
		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
	private String getLongDateString(Date date) {
		//String helpStr = dateFormat.format(date);
		StringBuffer sb = new StringBuffer();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
			sb.append("Mittwoch, ");
		else
			sb.append("Samstag, ");
		
		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		sb.append(". ");
		sb.append(MONTHS[cal.get(Calendar.MONTH)]);
		sb.append(" ");
		sb.append(cal.get(Calendar.YEAR));

		return sb.toString();
	}
	
}