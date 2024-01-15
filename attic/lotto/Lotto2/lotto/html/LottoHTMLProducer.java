package lotto.html;

import lotto.*;
import lotto.util.*;
import java.util.*;
import java.io.*;
import com.objectspace.jgl.*;
import com.objectspace.jgl.algorithms.*;
import java.text.*;
import COM.odi.util.*;

public class LottoHTMLProducer {

    private String templatePath;

    private Ausspielungen ausspielungen;
	private String path;
	private String quotenPath;

    private static String MONTHS[] = {"Januar", "Februar", "M&auml;rz", "April", "Mai", "Juni",
                               "Juli", "August", "September", "Oktober", "November", "Dezember"};

	private static String TITLES[] = {"alle Ziehungen", "ab Ziehung Nr. 14/1979", "ab Ziehung Nr. 1/1986",
                          "ab Ziehung Nr. 1/1997"};
    private static String DESCRIPTIONS[] = {" ", " (Einf&uuml;hrung von 41 und 42)", " (Einf&uuml;hrung von 43, 44 und 45)",
                          " (Einf&uuml;hrung Mittwochsziehung)"};

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public LottoHTMLProducer(String path, String quotenPath, Ausspielungen ausspielungen) {
        this.path = path;
        this.quotenPath = quotenPath;
        this.templatePath = AppProperties.getInstance().getProperty("templatePath");
        this.ausspielungen = ausspielungen;
	}

	
    public void writeQuoten(double nextLottoGewinn, double nextJokerGewinn, Calendar nextDate) {

        DecimalFormat form = new DecimalFormat("#,##0.00");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('\'');
        form.setDecimalFormatSymbols(dfs);

        DecimalFormat form2 = new DecimalFormat("#,##0");
        form2.setDecimalFormatSymbols(dfs);

        try {
            Ziehung zie = ausspielungen.getLast();

            LottoGewinnquote lgq = zie.getLottoGewinnquote();
            JokerGewinnquote jgq = zie.getJokerGewinnquote();

            if ((lgq != null) && (jgq != null)) {
                HTMLProducer hp = new HTMLProducer(templatePath + "/header.template",templatePath + "/quoten.template");

                hp.addVariable("title", "Gewinnquoten");

                if (lgq.getAnzahl(LottoGewinnquote.R6) == 0) {
                    hp.addVariable("lanz6", "JACKPOT");
                    hp.addVariable("lbet6", form.format(lgq.getQuote(LottoGewinnquote.JACKPOT)));
                } else {
                    hp.addVariable("lanz6", form2.format(lgq.getAnzahl(LottoGewinnquote.R6)));
                    hp.addVariable("lbet6", form.format(lgq.getQuote(LottoGewinnquote.R6)));

                }
                hp.addVariable("lanz5+", form2.format(lgq.getAnzahl(LottoGewinnquote.R5P)));
                hp.addVariable("lbet5+", form.format(lgq.getQuote(LottoGewinnquote.R5P)));
                hp.addVariable("lanz5", form2.format(lgq.getAnzahl(LottoGewinnquote.R5)));
                hp.addVariable("lbet5", form.format(lgq.getQuote(LottoGewinnquote.R5)));
                hp.addVariable("lanz4", form2.format(lgq.getAnzahl(LottoGewinnquote.R4)));
                hp.addVariable("lbet4", form.format(lgq.getQuote(LottoGewinnquote.R4)));
                hp.addVariable("lanz3", form2.format(lgq.getAnzahl(LottoGewinnquote.R3)));
                hp.addVariable("lbet3", form.format(lgq.getQuote(LottoGewinnquote.R3)));
                hp.addVariable("lerwartetdatum", dateFormat.format(nextDate.getTime()));
                hp.addVariable("lerwartetsumme", form.format(nextLottoGewinn));


                if (jgq.getAnzahl(JokerGewinnquote.R6) == 0) {
                    hp.addVariable("janz6", "JACKPOT");
                    hp.addVariable("jbet6", form.format(jgq.getQuote(JokerGewinnquote.JACKPOT)));
                } else {
                    hp.addVariable("janz6", form2.format(jgq.getAnzahl(JokerGewinnquote.R6)));
                    hp.addVariable("jbet6", form.format(jgq.getQuote(JokerGewinnquote.R6)));

                }
                hp.addVariable("janz5", form2.format(jgq.getAnzahl(JokerGewinnquote.R5)));
                hp.addVariable("jbet5", form.format(jgq.getQuote(JokerGewinnquote.R5)));
                hp.addVariable("janz4", form2.format(jgq.getAnzahl(JokerGewinnquote.R4)));
                hp.addVariable("jbet4", form.format(jgq.getQuote(JokerGewinnquote.R4)));
                hp.addVariable("janz3", form2.format(jgq.getAnzahl(JokerGewinnquote.R3)));
                hp.addVariable("jbet3", form.format(jgq.getQuote(JokerGewinnquote.R3)));
                hp.addVariable("janz2", form2.format(jgq.getAnzahl(JokerGewinnquote.R2)));
                hp.addVariable("jbet2", form.format(jgq.getQuote(JokerGewinnquote.R2)));
                hp.addVariable("jerwartetdatum", dateFormat.format(nextDate.getTime()));
                hp.addVariable("jerwartetsumme", form.format(nextJokerGewinn));

                hp.printHTML(quotenPath + "\\quoten.html");
            }
        }
        catch (FileNotFoundException fe) {
            DiskLog.getInstance().log(DiskLog.FATAL, getClass().getName(), "writeQuoten() : " + fe);
        }

    }



}