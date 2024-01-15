package lotto.page.frame;

import lotto.*;
import lotto.page.*;
import common.io.*;
import com.odi.util.*;
import java.io.*;
import java.util.*;

public class Producer_F_Ziehungen extends PageProducer {
		
	//Properties
	//jahr     int   JJJJ (wenn fehlt error!)
	//asc      true/false
	
	private static final String header = "head_frame.template";
	private static final String fileLottoBegin = "Z";
	private static final String fileJokerBegin = "j";

	private StringBuffer ds;
	
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

			ds = new StringBuffer();
			
			if (asc) {
				it = auss.getListIterator(0);

				if (it.hasNext())
					zie = (Ziehung) it.next();

				while (it.hasNext() && (zie.getJahr() < year)) {
					zie = (Ziehung) it.next();
				}
				if (zie.getJahr() != year)
					return;

				PrintWriter pw  = new PrintWriter(new BufferedWriter(new FileWriter(lottoFramePath + fileLottoBegin + zie.getJahr() + ".html")));
				PrintWriter pwj = new PrintWriter(new BufferedWriter(new FileWriter(lottoFramePath + fileJokerBegin + zie.getJahr() + ".html")));

				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + header);
				tw.addVariable("title", "Ziehungen " + zie.getJahr());
				tw.write(pw);
				
				tw = new TemplateWriter();
				tw.addFile(templatePath + header);
				tw.addVariable("title", "Joker " + zie.getJahr());
				tw.write(pwj);
			
				writeZiehungFirst(pw, zie);
				writeJokerHead(pwj, zie);
				writeJoker(pwj, zie);
			
				while(it.hasNext() && (zie.getJahr() == year)) {
					zie = (Ziehung)it.next();
					if (zie.getJahr() == year) {
						writeZiehungNext(pw, zie);
						writeJoker(pwj, zie);
					}
				}

				writeTail(pw);
				writeTail(pwj);
				pw.close();
				pwj.close();
				
			} else {
				it = auss.getListIterator(auss.getAusspielungenStatistik(AusspielungenType.ALLE).getAnzahlAusspielungen());
				if (it.hasPrevious())
					zie = (Ziehung)it.previous();

				while ((it.hasPrevious()) && (zie.getJahr() > year)) {
					zie = (Ziehung) it.previous();
					if (zie.getJahr() == year)
						break;
				}
				if (zie.getJahr() != year)
					return;

				PrintWriter pw  = new PrintWriter(new BufferedWriter(new FileWriter(lottoFramePath + fileLottoBegin + zie.getJahr() + ".html")));
				PrintWriter pwj = new PrintWriter(new BufferedWriter(new FileWriter(lottoFramePath + fileJokerBegin + zie.getJahr() + ".html")));

				TemplateWriter tw = new TemplateWriter();
				tw.addFile(templatePath + header);
				tw.addVariable("title", "Ziehungen " + zie.getJahr());
				tw.write(pw);
				
				tw = new TemplateWriter();
				tw.addFile(templatePath + header);
				tw.addVariable("title", "Joker " + zie.getJahr());
				tw.write(pwj);
			
				writeZiehungFirst(pw, zie);
				writeJokerHead(pwj, zie);
				writeJoker(pwj, zie);
				
				while(it.hasPrevious() && (zie.getJahr() == year)) {
					zie = (Ziehung)it.previous();
					if (zie.getJahr() == year) {
						writeZiehungNext(pw, zie);
						writeJoker(pwj, zie);
					}
				}

				writeTail(pw);
				writeTail(pwj);
				pw.close();
				pwj.close();
			}

		} catch (Exception fe) {
			System.err.println(fe);
		}

	}
	
	private void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("</center></div>");
		pw.println("</body>");
		pw.println("</html>");
	}
		
	private void writeZiehungFirst(PrintWriter pw, Ziehung zie) {
		ds.setLength(0);
		pw.println("<h2 align=center>Ziehungen " + zie.getJahr() + "</h2>");
		pw.println("<div align=center><center>");
		pw.println("<table border=1>");
		
		pw.println("<tr><th align=right width=\"7%\">Nr.</th><th align=center width=\"44%\">Datum</th><th colspan=6>Gewinnzahlen</th><th align=right><em>Zz</em></th></tr>");
		
		int[] zahlen = zie.getZahlen();
		pw.print("<tr><th align=right width=\"7%\">"+zie.getNr()+"</th>");
		
		if (zie.isWednesday())
			ds.append("MI,&nbsp;");
		else
			ds.append("SA,&nbsp;");
		
		ds.append(dateFormat.format(zie.getDatum()));
		
		pw.print("<TD align=center><TT>"+ds.toString()+"</TT></TD>");
		for (int i = 0; i < 6; i++) {
			pw.print("<td align=right width=\"7%\">"+zahlen[i]+" </td>");
		}
		pw.println("<td align=right width=\"7%\"><em>"+zie.getZusatzZahl()+" </em></td></tr>");

	}
	
	private void writeZiehungNext(PrintWriter pw, Ziehung zie) {
		ds.setLength(0);
		
		int[] zahlen = zie.getZahlen();
		pw.print("<tr><th align=right>"+zie.getNr()+"</th>");
		
		if (zie.isWednesday())
		   ds.append("MI,&nbsp;");
		else
		   ds.append("SA,&nbsp;");
		
		ds.append(dateFormat.format(zie.getDatum()));
		pw.print("<TD align=center><TT>"+ds.toString()+"</TT></TD>");
		
		for (int i = 0; i < 6; i++) {
			pw.print("<td align=right>"+zahlen[i]+" </td>");
		}
		pw.println("<td align=right><em>"+zie.getZusatzZahl()+" </em></td></tr>");

	}

	private void writeJokerHead(PrintWriter pw, Ziehung zie) {
		pw.println("<h2 align=center>Joker " + zie.getJahr() + "</h2>");
		pw.println("<div align=center><center>");
		pw.println("<table border=1>");
		pw.println("<tr bgcolor=\"#101010\"><th align=right width=\"8%\"><FONT color=\"#FFFFFF\">Nr.</font></th><th align=center width=\"27%\"><FONT color=\"#FFFFFF\">Datum</font></th><TH><font color=\"#FFFFFF\">Joker</font></th></tr>");
	}

	private void writeJoker(PrintWriter pw, Ziehung zie) {
		ds.setLength(0);
		pw.print("<tr><th align=right>"+zie.getNr()+"</th>");
		
		if (zie.isWednesday())
			ds.append("MI,&nbsp;");
		else
			ds.append("SA,&nbsp;");
		ds.append(dateFormat.format(zie.getDatum()));
		
		pw.print("<TD align=center><TT>"+ds.toString()+"</TT></TD>");
		pw.print("<TD align=center><B>");
		pw.print(zie.getJoker());
		pw.print("</B></TD></TR>");
	}

	
}