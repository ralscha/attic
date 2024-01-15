package lotto.page;

import lotto.*;
import java.util.*;
import java.io.*;
import java.text.*;
import common.io.*;

public class Producer_Aktuelle_WML extends PageProducer {
		
	private static final String template = "l_wml.template";
	private static final String htmlFile = "l.wml";

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

			TemplateWriter tw = new TemplateWriter();
			tw.addFile(templatePath + template);
									
			tw.addVariable("date", dateFormat.format(zie.getDatum()));
			tw.addVariable("nr", zie.getNr());
			tw.addVariable("year", zie.getJahr());
			
			int zahlen[] = zie.getZahlen();
			tw.addVariable("zahl1", zahlen[0]);
			tw.addVariable("zahl2", zahlen[1]);
			tw.addVariable("zahl3", zahlen[2]);
			tw.addVariable("zahl4", zahlen[3]);
			tw.addVariable("zahl5", zahlen[4]);
			tw.addVariable("zahl6", zahlen[5]);
			
			tw.addVariable("zz", zie.getZusatzZahl());
			tw.addVariable("joker", zie.getJoker());
			
      //Quoten
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
				tw.addVariable("lerwartetdatum", dateFormat.format(NextGewinnsumme.nextDatum.getTime()));
				tw.addVariable("lerwartetsumme", form.format(NextGewinnsumme.nextLottoGewinn));
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
				tw.addVariable("jerwartetdatum", dateFormat.format(NextGewinnsumme.nextDatum.getTime()));
				tw.addVariable("jerwartetsumme", form.format(NextGewinnsumme.nextJokerGewinn));
			} else {
				tw.addVariable("jerwartetdatum", "");
				tw.addVariable("jerwartetsumme", "");				
			}

			tw.write(quotenPath + htmlFile);		                           
		} catch (Exception fe) {
			System.err.println(fe);
		}

	}

	
}