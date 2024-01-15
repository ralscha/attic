package lotto.page;

import lotto.*;
import java.util.*;
import java.io.*;
import java.text.*;
import common.io.*;

public class Producer_Quoten extends PageProducer {
		
	private static final String template = "quoten.template";
	private static final String htmlFile = "quoten.shtml";

	public void producePage(Ausspielungen auss, Properties props) {
		try {
			
			DecimalFormat form = new DecimalFormat("#,##0.00");
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			dfs.setGroupingSeparator('\'');
			form.setDecimalFormatSymbols(dfs);
			
			DecimalFormat form2 = new DecimalFormat("#,##0");
			form2.setDecimalFormatSymbols(dfs);
			
			Ziehung zie = auss.getLastZiehung();
			LottoGewinnquote lgq = zie.getLottoGewinnquote();
			JokerGewinnquote jgq = zie.getJokerGewinnquote();

			if ((lgq != null) && (jgq != null)) {
				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + template);
				
				if (lgq.getAnzahl(GewinnquoteType.RICHTIGE_6) == 0) {
					tw.addVariable("lanz6", "JACKPOT");
					tw.addVariable("lbet6", form.format(lgq.getQuote(GewinnquoteType.JACKPOT)));
				} else {
					tw.addVariable("lanz6", form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_6)));
					tw.addVariable("lbet6", form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_6)));
				}
				
				tw.addVariable("lanz5+", form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_5_PLUS)));
				tw.addVariable("lbet5+", form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_5_PLUS)));
				tw.addVariable("lanz5", form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_5)));
				tw.addVariable("lbet5", form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_5)));
				tw.addVariable("lanz4", form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_4)));
				tw.addVariable("lbet4", form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_4)));
				tw.addVariable("lanz3", form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_3)));
				tw.addVariable("lbet3", form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_3)));

				if (NextGewinnsumme.nextLottoGewinn > 0.0) {					 	
					tw.addVariable("lerwartetdatum", "Erwartete Gewinnsumme f&uuml;r 6 Richtige: "+
								dateFormat.format(NextGewinnsumme.nextDatum.getTime())+"<BR>");
					tw.addVariable("lerwartetsumme", "<B>"+form.format(NextGewinnsumme.nextLottoGewinn)+"</B>");
				} else {
					tw.addVariable("lerwartetdatum", "");
					tw.addVariable("lerwartetsumme", "");				
				}				
				
				if (jgq.getAnzahl(GewinnquoteType.RICHTIGE_6) == 0) {
					tw.addVariable("janz6", "JACKPOT");
					tw.addVariable("jbet6", form.format(jgq.getQuote(GewinnquoteType.JACKPOT)));
				} else {
					tw.addVariable("janz6", form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_6)));
					tw.addVariable("jbet6", form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_6)));
				}
				
				tw.addVariable("janz5", form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_5)));
				tw.addVariable("jbet5", form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_5)));
				tw.addVariable("janz4", form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_4)));
				tw.addVariable("jbet4", form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_4)));
				tw.addVariable("janz3", form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_3)));
				tw.addVariable("jbet3", form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_3)));
				tw.addVariable("janz2", form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_2)));
				tw.addVariable("jbet2", form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_2)));
				
				if (NextGewinnsumme.nextJokerGewinn > 0.0) {					 	
					tw.addVariable("jerwartetdatum", "Erwartete Gewinnsumme f&uuml;r 6 Richtige: "+
								dateFormat.format(NextGewinnsumme.nextDatum.getTime())+"<BR>");
					tw.addVariable("jerwartetsumme", "<B>"+form.format(NextGewinnsumme.nextJokerGewinn)+"</B>");
				} else {
					tw.addVariable("jerwartetdatum", "");
					tw.addVariable("jerwartetsumme", "");				
				}
				
				tw.write(quotenPath + htmlFile);
			}
			
		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
}