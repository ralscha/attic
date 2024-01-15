package lotto.page;

import lotto.*;
import java.util.*;
import java.io.*;
import java.text.*;
import common.io.*;
import com.odi.util.*;

public class Producer_LottoGewinnquoten extends PageProducer {

	//Properties
	//jahr   int   JJJJ (wenn fehlt, alle Jahre)
	//asc    true/false

	private DecimalFormat form;
	private DecimalFormat form2;

	public Producer_LottoGewinnquoten() {
		super();

		form = new DecimalFormat("#,##0.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);

		form2 = new DecimalFormat("#,##0");
		form2.setDecimalFormatSymbols(dfs);
	}

	public void producePage(Ausspielungen auss, Properties props) {
		
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
											
			Ziehung zie = null;
			ListIterator it;

			if (asc) {
				it = auss.getListIterator(0);

				if (year > 0) {

					if (it.hasNext())
						zie = (Ziehung) it.next();

					while (it.hasNext() && (zie.getJahr() < year)) {
						zie = (Ziehung) it.next();
					}
					if (zie.getJahr() != year)
						return;

					PrintWriter pw = writeHTMLHeader(year);
					writeHead(pw, zie);

					LottoGewinnquote lgq = zie.getLottoGewinnquote();
					if (lgq != null)
						writeLottoGewinnquote(pw, lgq, zie);

					while(it.hasNext() && (zie.getJahr() == year)) {
						zie = (Ziehung)it.next();
						if (zie.getJahr() == year) {
							lgq = zie.getLottoGewinnquote();
							if (lgq != null)
								writeLottoGewinnquote(pw, lgq, zie);
						}
					}

					writeTail(pw);
					pw.close();
				} else {
					if (it.hasNext())
						zie = (Ziehung) it.next();
					if (zie == null) return;
					int mjahr = zie.getJahr();
				
					PrintWriter pw = writeHTMLHeader(mjahr);
					writeHead(pw, zie);
					LottoGewinnquote lgq = zie.getLottoGewinnquote();
					if (lgq != null)
						writeLottoGewinnquote(pw, lgq, zie);

					
					while(it.hasNext()) {
						zie = (Ziehung)it.next();				
						if (zie.getJahr() != mjahr) {
							writeTail(pw);
							pw.close();
				
							mjahr = zie.getJahr();
							pw = writeHTMLHeader(mjahr);
							writeHead(pw, zie);
						}
						lgq = zie.getLottoGewinnquote();
						if (lgq != null)
							writeLottoGewinnquote(pw, lgq, zie);
					}
					writeTail(pw);
					pw.close();
						
				}
				
			} else {
				it = auss.getListIterator(auss.getAusspielungenStatistik(AusspielungenType.ALLE).getAnzahlAusspielungen());
				if (it.hasPrevious())
					zie = (Ziehung)it.previous();

				if (year > 0) {
					while ((it.hasPrevious()) && (zie.getJahr() > year)) {
						zie = (Ziehung) it.previous();
						if (zie.getJahr() == year)
							break;
					}
					if (zie.getJahr() != year)
						return;

					PrintWriter pw = writeHTMLHeader(year);
					writeHead(pw, zie);
					
					LottoGewinnquote lgq = zie.getLottoGewinnquote();
					if (lgq != null)
						writeLottoGewinnquote(pw, lgq, zie);

					while(it.hasPrevious() && (zie.getJahr() == year)) {
						zie = (Ziehung)it.previous();
						if (zie.getJahr() == year) {
							lgq = zie.getLottoGewinnquote();
							if (lgq != null)
								writeLottoGewinnquote(pw, lgq, zie);
						}
					}

					writeTail(pw);
					pw.close();
				} else {
					if (zie == null) return;
					int mjahr = zie.getJahr();
					
					PrintWriter pw = writeHTMLHeader(mjahr);
					writeHead(pw, zie);
					while(it.hasPrevious()) {
						zie = (Ziehung)it.previous();
						
						if (zie.getJahr() != mjahr) {
							writeTail(pw);
							pw.close();
							
							mjahr = zie.getJahr();
							pw = writeHTMLHeader(mjahr);
							writeHead(pw, zie);
						}
						LottoGewinnquote lgq = zie.getLottoGewinnquote();
						if (lgq != null)
							writeLottoGewinnquote(pw, lgq, zie);
					}
					writeTail(pw);
					pw.close();

				}

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
	
	private void writeHead(PrintWriter pw, Ziehung zie) {
		pw.println("<br>");
		pw.println("<p class=\"subTitle\">Lotto Gewinnquoten ");
		pw.print(zie.getJahr());
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
	
	private void writeLottoGewinnquote(PrintWriter pw, LottoGewinnquote lgq, Ziehung zie) {
		pw.println("<tr>");
   		pw.print("<td ALIGN=\"RIGHT\" class=\"tcsb\">");
   		pw.print(zie.getNr());
   		pw.println("</td>");

		pw.print("<td ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuote(GewinnquoteType.JACKPOT)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_6)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_6)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_5_PLUS)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_5_PLUS)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_5)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_5)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_4)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_4)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcsa\">");
		pw.print(form2.format(lgq.getAnzahl(GewinnquoteType.RICHTIGE_3)));
		pw.println("</td>");

		pw.print("	<TD ALIGN=\"RIGHT\" class=\"tcs\">");
		pw.print(form.format(lgq.getQuote(GewinnquoteType.RICHTIGE_3)));
		pw.println("</td>");

		pw.print("</tr>");
	}
}