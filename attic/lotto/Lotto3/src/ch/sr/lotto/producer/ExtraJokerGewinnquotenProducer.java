package ch.sr.lotto.producer;

import java.io.*;
import java.text.*;
import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class ExtraJokerGewinnquotenProducer extends Producer {

	//Properties
	//jahr   int   JJJJ (wenn fehlt, alle Jahre)
	//asc    true/false

	private DecimalFormat form;
	private DecimalFormat form2;

	public ExtraJokerGewinnquotenProducer() {
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

      ExtraJokerGainQuota[] jgqs = (ExtraJokerGainQuota[])db.get(ExtraJokerGainQuota.class, query);

      if (year > 0) {

        PrintWriter pw = writeHTMLHeader(year);
        writeHead(pw, jgqs[0]);
        
        for (int i = 0; i < jgqs.length; i++) {
          writeExtraJokerGewinnquote(pw, jgqs[i]);
        }
        

        
        writeTail(pw);
        pw.close();        
        
      } else {

        ExtraJokerGainQuota jgq = jgqs[0];
        int mjahr = jgq.getYear().intValue();

        PrintWriter pw = writeHTMLHeader(mjahr);
        writeHead(pw, jgq);
        writeExtraJokerGewinnquote(pw, jgq);

        for (int i = 1; i < jgqs.length; i++) {
          jgq = jgqs[i];
          if (jgq.getYear().intValue() != mjahr) {
            writeTail(pw);
            pw.close();

            mjahr = jgq.getYear().intValue();
            pw = writeHTMLHeader(mjahr);
            writeHead(pw, jgq);
          }
          writeExtraJokerGewinnquote(pw, jgq);
        }
        
        writeTail(pw);
        pw.close();
      }
      

		} catch (Exception fe) {
			fe.printStackTrace();
		}

	}

	private PrintWriter writeHTMLHeader(int year) throws IOException, FileNotFoundException {
		PrintWriter pw = new PrintWriter(
                   		new BufferedWriter(new FileWriter(quotenPath + "ejgq" + year + ".shtml")));

		pw.println("<html>");
		pw.println("<head>");
		pw.print("<title>Schweizer Zahlenlotto: ExtraJoker Gewinnquoten ");
		pw.print(year);
		pw.println("</title>");
		pw.println("<link rel=stylesheet type=\"text/css\" href=\"include/lotto.css\">");
		pw.println("</head>");
		pw.println("<!--#include file=\"include/bodyl.shtml\"-->");
		return pw;
	}

	private void writeHead(PrintWriter pw, ExtraJokerGainQuota jgq) {
		pw.println("<br>");
		pw.println("<p class=\"subTitle\">ExtraJoker Gewinnquoten ");
		pw.print(jgq.getYear());
		pw.println("</p>");
		pw.println("<br>");
		pw.println("<table border=\"1\" class=\"tableContentsSmall\">");
		pw.println("<tr>");
		pw.println("<th width=\"5%\" class=\"tableHeaderSmall\">Nr.</th>");
		pw.println("<th WIDTH=\"10%\" class=\"tableHeaderSmall\">Jackpot</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">6 Richtige</th>");
		pw.println("<TH WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">5 Richtige</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">4 Richtige</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">3 Richtige</th>");
		pw.println("<th WIDTH=\"17%\" COLSPAN=2 class=\"tableHeaderSmall\">2 Richtige</th>");
		pw.println("</tr>");
	}


	private void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("<!--#include file=\"include/tail.shtml\"-->");
	}
	
	private void writeExtraJokerGewinnquote(PrintWriter pw, ExtraJokerGainQuota jgq) {
		pw.println("<tr>");
   		pw.print("<td ALIGN=\"RIGHT\" class=\"tcsb\">");
   		pw.print(jgq.getNo());
   		pw.println("</td>");
   		
		pw.print("<td ALIGN=\"RIGHT\" class=\"tcs\">");
    if (jgq.getJackpot() != null) {
  		pw.print(form.format(jgq.getJackpot()));
    } else {
      pw.print("&nbsp;");
    }
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(jgq.getNum6()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(jgq.getQuota6()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(jgq.getNum5()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(jgq.getQuota5()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(jgq.getNum4()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(jgq.getQuota4()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(jgq.getNum3()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(jgq.getQuota3()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(jgq.getNum2()));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(jgq.getQuota2()));
		pw.println("</td>");

		pw.print("</tr>");
	}
}