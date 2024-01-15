package lotto.page;

import lotto.*;
import com.odi.util.*;
import java.io.*;
import java.util.*;

public class Producer_Ziehungen extends PageProducer {
		
	//Properties
	//jahr     int   JJJJ (wenn fehlt, alle Jahre)
	//asc      true/false
	//withDate true/false
	//joker    true/false
		
	public void producePage(Ausspielungen auss, Properties props) {

		try {
			boolean asc = true;
			int year = -1;
			boolean withDate = true;
			boolean joker = false;
			
			if (props != null) {
				Boolean ascBoolean = (Boolean) props.get("asc");
				if (ascBoolean != null) {
					asc = ascBoolean.booleanValue();
				}
				Integer yearInteger = (Integer) props.get("year");
				if (yearInteger != null) {
					year = yearInteger.intValue();
				}
				Boolean wdBoolean = (Boolean)props.get("withDate");
				if (wdBoolean != null) {
					withDate = wdBoolean.booleanValue();
				}
				Boolean jokerBoolean = (Boolean)props.get("joker");
				if (jokerBoolean != null) {
					joker = jokerBoolean.booleanValue();
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
					writeHead(pw, zie, withDate, joker);
					
					writeZiehung(pw, zie, withDate, joker);
					while(it.hasNext() && (zie.getJahr() == year)) {
						zie = (Ziehung)it.next();
						if (zie.getJahr() == year) 
							writeZiehung(pw, zie, withDate, joker);
					}

					writeTail(pw);
					pw.close();
				} else {
					if (it.hasNext())
						zie = (Ziehung) it.next();
					if (zie == null) return;
					int mjahr = zie.getJahr();
				
					PrintWriter pw = writeHTMLHeader(mjahr);
					writeHead(pw, zie, withDate, joker);
					writeZiehung(pw, zie, withDate, joker);
						
					while(it.hasNext()) {
						zie = (Ziehung)it.next();				
						if (zie.getJahr() != mjahr) {
							writeTail(pw);
							pw.close();
				
							mjahr = zie.getJahr();
							pw = writeHTMLHeader(mjahr);
							writeHead(pw, zie, withDate, joker);
						}
						writeZiehung(pw, zie, withDate, joker);

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
					writeHead(pw, zie, withDate, joker);
					
					writeZiehung(pw, zie, withDate, joker);
					while(it.hasPrevious() && (zie.getJahr() == year)) {
						zie = (Ziehung)it.previous();
						if (zie.getJahr() == year)
							writeZiehung(pw, zie, withDate, joker);
					}

					writeTail(pw);
					pw.close();
				} else {
					if (zie == null) return;
					int mjahr = zie.getJahr();
					
					PrintWriter pw = writeHTMLHeader(mjahr);
					writeHead(pw, zie, withDate, joker);
					while(it.hasPrevious()) {
						zie = (Ziehung)it.previous();
						
						if (zie.getJahr() != mjahr) {
							writeTail(pw);
							pw.close();
							
							mjahr = zie.getJahr();
							pw = writeHTMLHeader(mjahr);
							writeHead(pw, zie, withDate, joker);
						}
						writeZiehung(pw, zie, withDate, joker);
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
		                  					new FileWriter(lottoPath + "Z" + year + ".shtml")));
		
		pw.println("<html>");
		pw.println("<head>");
		pw.print("<title>Schweizer Zahlenlotto: Ziehungen ");
		pw.print(year);
		pw.println("</title>");
		pw.println("<link rel=stylesheet type=\"text/css\" href=\"include/lotto.css\">");
		pw.println("</head>");
		pw.println("<!--#include file=\"include/body.shtml\"-->");
		return pw;
	}
	
	private void writeHead(PrintWriter pw, Ziehung zie, boolean withDate, boolean joker) {
		pw.println("<br>");
		pw.println("<p class=\"subTitle\">Ziehungen ");
		pw.print(zie.getJahr());
		pw.println("</p>");
		pw.println("<br>");
		pw.println("<table border=\"1\" class=\"tableContents\">");
		
		
		pw.println("<tr>");
		pw.println("<th align=\"RIGHT\" class=\"tableHeader\">Nr.</th>");
		pw.println("<th></th>");
		
		if (withDate) {
			pw.println("<th align=\"center\" class=\"tableHeader\">Datum</th>");
			pw.println("<th></th>");
		}
		pw.println("<th colspan=\"6\" class=\"tableHeader\">Gewinnzahlen</th>");
		pw.println("<th></th>");		
		pw.println("<th align=\"RIGHT\" class=\"tableHeader\">Zz</th>");
		
		
		if (joker) {
			pw.println("<th></th>");
			pw.println("<th align=\"center\" class=\"tableHeader\">Joker</th>");
		}
		
		pw.println("</tr>");
	}
	
	private void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("<!--#include file=\"include/tail.shtml\"-->");
	}
	
	private void writeZiehung(PrintWriter pw, Ziehung zie, boolean withDate, boolean joker) {
		pw.println("<tr>");
		
		pw.print("<th align=\"right\" class=\"textb\">");
		pw.print(zie.getNr());
		pw.println("</th>");
		pw.println("<td></td>");

		if (withDate) {
		
			pw.print("<TD align=\"center\" class=\"text\">");
			
			if (zie.isWednesday())
				pw.print("Mi,&nbsp;");
			else
				pw.print("Sa,&nbsp;");
			
			pw.print(dateFormat.format(zie.getDatum()));
			pw.println("</TD>");	
			pw.println("<td></td>");	
		}
		
		int zahlen[] = zie.getZahlen();
		for (int i = 0; i < zahlen.length; i++) {
			pw.print("<td align=\"right\" class=\"text\">");
			pw.print(zahlen[i]);
			pw.println("</td>");
		}
		pw.println("<td></td>");	
		
		pw.print("<td align=\"right\" class=\"tc1\">");
		pw.print(zie.getZusatzZahl());
		pw.println("</td>");
		
		if (joker) {
			pw.println("<td></td>");
			pw.print("<td align=\"center\" class=\"text\">");
			pw.print(zie.getJoker());
			pw.println("</td>");
		}

		pw.print("</tr>");
	}

}