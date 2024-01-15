package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import java.util.*;
import java.io.*;
import java.text.*;
import common.io.*;
import com.odi.util.*;

public class Producer_F_JokerGewinnquoten extends PageProducer {

	//Properties
	//jahr   int   JJJJ (wenn fehlt error)
	//asc    true/false

	private DecimalFormat form;
	private DecimalFormat form2;
	
	private static final String header = "head_frame.template";
	private static final String fileBegin = "jgq";

	public Producer_F_JokerGewinnquoten() {
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

			if (year == -1) throw new IllegalArgumentException("year property is missing");
			
			Ziehung zie = null;
			ListIterator it;


			if (asc) {
				it = auss.getListIterator(0);
				if (it.hasNext())
					zie = (Ziehung) it.next();


				while (it.hasNext() && ((zie.getJahr() < year))) {
					zie = (Ziehung) it.next();
				}
				if (zie.getJahr() != year)
					return;

				PrintWriter pw  = new PrintWriter(new BufferedWriter(new FileWriter(quotenFramePath + fileBegin + zie.getJahr() + ".html")));
		
				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + header);
				tw.addVariable("title", "Joker Gewinnquoten " + zie.getJahr());
				tw.write(pw);

				writeHead(pw, zie);


				JokerGewinnquote jgq = zie.getJokerGewinnquote();
				if (jgq != null)
					writeJokerGewinnquote(pw, jgq, zie);

				while(it.hasNext() && (zie.getJahr() == year)) {
					zie = (Ziehung)it.next();
					if (zie.getJahr() == year) {
						jgq = zie.getJokerGewinnquote();
						if (jgq != null)
							writeJokerGewinnquote(pw, jgq, zie);
					}
				}

				writeTail(pw);
				pw.close();

			} else {
				it = auss.getListIterator( auss.getAusspielungenStatistik(
                             				AusspielungenType.ALLE).getAnzahlAusspielungen());
				if (it.hasPrevious())
					zie = (Ziehung) it.previous();

				while ((it.hasPrevious()) && (zie.getJahr() > year)) {
					zie = (Ziehung) it.previous();
					if (zie.getJahr() == year)
						break;
				}
				if (zie.getJahr() != year)
					return;

				PrintWriter pw  = new PrintWriter(new BufferedWriter(new FileWriter(quotenFramePath + fileBegin + zie.getJahr() + ".html")));
		
				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + header);
				tw.addVariable("title", "Joker Gewinnquoten " + zie.getJahr());
				tw.write(pw);
				
				writeHead(pw, zie);

				JokerGewinnquote jgq = zie.getJokerGewinnquote();
				if (jgq != null)
					writeJokerGewinnquote(pw, jgq, zie);

				while(it.hasPrevious() && (zie.getJahr() == year)) {
					zie = (Ziehung)it.previous();
					if (zie.getJahr() == year) {
						jgq = zie.getJokerGewinnquote();
						if (jgq != null)
							writeJokerGewinnquote(pw, jgq, zie);
					}
				}

				writeTail(pw);
				pw.close();
			}


		} catch (Exception fe) {
			System.err.println(fe);
		}

	}


	private void writeHead(PrintWriter pw, Ziehung zie) {
		pw.println("<h2 align=center>Joker Gewinnquoten " + zie.getJahr() + "</h2>");
		pw.println("<div align=center><center>");
		pw.println("<table border=1>");
		
		pw.println("<tr bgcolor=\"#101010\">");
		pw.println("   <th WIDTH=\"5%\"><FONT color=\"#FFFFFF\">Nr.</font></th>");
		pw.println("	<th WIDTH=\"10%\"><FONT color=\"#FFFFFF\">Jackpot</font></th>");
		pw.println("	<th WIDTH=\"17%\" COLSPAN=2><FONT color=\"#FFFFFF\">6 Endziffern</font></th>");
		pw.println("	<th WIDTH=\"17%\" COLSPAN=2><FONT color=\"#FFFFFF\">5 Endziffern</font></th>");
		pw.println("	<th WIDTH=\"17%\" COLSPAN=2><FONT color=\"#FFFFFF\">4 Endziffern</font></th>");
		pw.println("	<th WIDTH=\"17%\" COLSPAN=2><FONT color=\"#FFFFFF\">3 Endziffern</font></th>");
		pw.println("	<th WIDTH=\"17%\" COLSPAN=2><FONT color=\"#FFFFFF\">2 Endziffern</font></th>");
		pw.println("</tr>");
	}


	private void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("</center></div>");
		pw.println("</body>");
		pw.println("</html>");
	}
	
	private void writeJokerGewinnquote(PrintWriter pw, JokerGewinnquote jgq, Ziehung zie) {
		pw.println("<tr>");
		pw.println("  <td ALIGN=\"RIGHT\">"+zie.getNr()+"</td>");
		pw.println("	<td ALIGN=\"RIGHT\">"+form.format(jgq.getQuote(GewinnquoteType.JACKPOT))+"</td>");
		pw.println("	<TD ALIGN=\"RIGHT\" bgcolor=\"#EEEEEE\">"+form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_6))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\">"+form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_6))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\" bgcolor=\"#EEEEEE\">"+form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_5))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\">"+form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_5))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\" bgcolor=\"#EEEEEE\">"+form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_4))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\">"+form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_4))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\" bgcolor=\"#EEEEEE\">"+form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_3))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\">"+form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_3))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\" bgcolor=\"#EEEEEE\">"+form2.format(jgq.getAnzahl(GewinnquoteType.RICHTIGE_2))+"</TD>");
		pw.println("	<TD ALIGN=\"RIGHT\">"+form.format(jgq.getQuote(GewinnquoteType.RICHTIGE_2))+"</TD>");
		pw.println("</TR>");
	}
}