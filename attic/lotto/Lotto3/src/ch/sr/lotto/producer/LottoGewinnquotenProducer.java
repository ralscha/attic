package ch.sr.lotto.producer;

import java.io.*;
import java.text.*;
import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class LottoGewinnquotenProducer extends Producer {

	//Properties
	//jahr   int   JJJJ (wenn fehlt, alle Jahre)
	//asc    true/false

	private DecimalFormat form;
	private DecimalFormat form2;

	public LottoGewinnquotenProducer() {
		super();

		form = new DecimalFormat("#,##0.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);

		form2 = new DecimalFormat("#,##0");
		form2.setDecimalFormatSymbols(dfs);
	}

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		
		try {
			boolean asc = true;
			int year = -1;
			
			if (props != null) {
				Boolean ascBoolean = (Boolean) props.get("asc");
				if (ascBoolean != null) {
					asc = ascBoolean.booleanValue();
				}
				Integer yearInteger = (Integer) props.get("year");
				if (yearInteger != null) {
					year = yearInteger.intValue();
				}
			}
											
      OQuery query = new OQuery();

      if (year > 0) {
        query.add(year, "year");
      }

      if (asc) {
        query.addOrder("year", OQuery.ASC);
        query.addOrder("no", OQuery.ASC);
      } else {
        query.addOrder("year", OQuery.DESC);
        query.addOrder("no", OQuery.DESC);
      }

      LottoGainQuota[] lgqs = (LottoGainQuota[])db.get(LottoGainQuota.class, query);

      if (year > 0) {

        PrintWriter pw = writeHTMLHeader(year);
        writeHead(pw, lgqs[0]);
        
        for (int i = 0; i < lgqs.length; i++) {
          writeLottoGewinnquote(pw, lgqs[i]);
        }
        

        
        writeTail(pw);
        pw.close();        
        
      } else {

        LottoGainQuota jgq = lgqs[0];
        int mjahr = jgq.getYear().intValue();

        PrintWriter pw = writeHTMLHeader(mjahr);
        writeHead(pw, jgq);
        writeLottoGewinnquote(pw, jgq);

        for (int i = 1; i < lgqs.length; i++) {
          jgq = lgqs[i];
          if (jgq.getYear().intValue() != mjahr) {
            writeTail(pw);
            pw.close();

            mjahr = jgq.getYear().intValue();
            pw = writeHTMLHeader(mjahr);
            writeHead(pw, jgq);
          }
          writeLottoGewinnquote(pw, jgq);
        }
        
        writeTail(pw);
        pw.close();
      }

		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
	private PrintWriter writeHTMLHeader(int year) throws IOException, FileNotFoundException {
		PrintWriter pw = new PrintWriter( new BufferedWriter(
		                  					new FileWriter(quotenPath + "gq" + year + ".shtml")));
		
		pw.println("<html>");
		pw.println("<head>");
		pw.print("<title>Schweizer Zahlenlotto: Lotto Gewinnquoten ");
		pw.print(year);
		pw.println("</title>");
		pw.println("<link rel=stylesheet type=\"text/css\" href=\"include/lotto.css\">");
		pw.println("</head>");
		pw.println("<!--#include file=\"include/bodyl.shtml\"-->");
		return pw;
	}
	
	private void writeHead(PrintWriter pw, LottoGainQuota lgq) {
		pw.println("<br>");
		pw.println("<p class=\"subTitle\">Lotto Gewinnquoten ");
		pw.print(lgq.getYear());
		pw.println("</p>");
		pw.println("<br>");
		pw.println("<table border=\"1\" class=\"tableContentsSmall\">");
		pw.println("<tr>");
		pw.println("<th width=\"5%\" class=\"tableHeaderSmall\">Nr.</th>");
		pw.println("<th WIDTH=\"10%\" class=\"tableHeaderSmall\">Jackpot</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">6 Richtige</th>");
		pw.println("<TH WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">5 Richtige +</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">5 Richtige</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">4 Richtige</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">3 Richtige</th>");
		pw.println("</tr>");
	}
	
	private void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("<!--#include file=\"include/tail.shtml\"-->");
	}
	
	private void writeLottoGewinnquote(PrintWriter pw, LottoGainQuota lgq) {
		pw.println("<tr>");
   		pw.print("<td ALIGN=\"RIGHT\" class=\"tcsb\">");
   		pw.print(lgq.getNo());
   		pw.println("</td>");

		pw.print("<td ALIGN=\"RIGHT\" class=\"tcs\">");
    if (lgq.getJackpot() != null) {
  		pw.print(form.format(lgq.getJackpot()));
    } else {
      pw.print("&nbsp;");
    }
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getNum6()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuota6()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getNum5plus()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuota5plus()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getNum5()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuota5()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getNum4()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuota4()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getNum3()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuota3()));
		pw.println("</td>");

		pw.print("</tr>");
	}
}