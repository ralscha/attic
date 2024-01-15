package lotto.page;

import lotto.*;
import java.util.Properties;
import java.io.*;
import java.text.*;
import common.io.*;
import COM.odi.util.*;

public class Producer_Joker extends PageProducer {

	//Properties
	//jahr   int   JJJJ (wenn fehlt, alle Jahre)
	//asc    true/false


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
				if (it.hasNext())
					zie = (Ziehung) it.next();

				if (year > 0) {

					while ((zie.getJahr() < year) && it.hasNext()) {
						zie = (Ziehung) it.next();
					}
					if (zie.getJahr() != year)
						return;

					PrintWriter pw = writeHead(year);	

					do {
						writeJoker(pw, zie);
						zie = (Ziehung) it.next();
					} while (zie.getJahr() == year);

					writeTail(pw);
					pw.close();
				} else {
					if (zie == null)
						return;
					int mjahr = zie.getJahr();

					PrintWriter pw = writeHead(mjahr);
					
					while (it.hasNext()) {
						zie = (Ziehung) it.next();

						if (zie.getJahr() != mjahr) {
							writeTail(pw);
							pw.close();

							mjahr = zie.getJahr();
							pw = writeHead(mjahr);
						}
						writeJoker(pw, zie);
					}
					writeTail(pw);
					pw.close();

				}

			} else {
				it = auss.getListIterator( auss.getAusspielungenStatistik(
                             				AusspielungenType.ALLE).getAnzahlAusspielungen());
				if (it.hasPrevious())
					zie = (Ziehung) it.previous();

				if (year > 0) {
					while ((it.hasPrevious()) && (zie.getJahr() > year)) {
						zie = (Ziehung) it.previous();
						if (zie.getJahr() == year)
							break;
					}
					if (zie.getJahr() != year)
						return;

					PrintWriter pw = writeHead(year);

					do {
						writeJoker(pw, zie);
						zie = (Ziehung) it.previous();
					} while (zie.getJahr() == year);

					writeTail(pw);
					pw.close();
				} else {
					if (zie == null)
						return;
					int mjahr = zie.getJahr();

					PrintWriter pw = writeHead(mjahr);
					
					while (it.hasPrevious()) {
						zie = (Ziehung) it.previous();

						if (zie.getJahr() != mjahr) {
							writeTail(pw);
							pw.close();

							mjahr = zie.getJahr();
							pw = writeHead(mjahr);
						}
						writeJoker(pw, zie);
					}
					writeTail(pw);
					pw.close();

				}

			}


		} catch (Exception fe) {
			System.err.println(fe);
		}

	}


	private PrintWriter writeHead(int year) throws IOException, FileNotFoundException {
		PrintWriter pw = new PrintWriter(
                   		new BufferedWriter(new FileWriter(quotenPath + "\\j" + year + ".html")));

		TemplateWriter tw = new TemplateWriter();
	//	tw.addFile(headerFile);
		tw.addVariable("title", "Joker " + year);
		tw.write(pw);

		pw.println("<h2 align=center>Joker " + year + "</h2>");
		pw.println("<div align=center><center>");
		pw.println("<table border=1>");
		pw.println("<tr bgcolor=\"#101010\"><th align=right width=\"8%\"><FONT color=\"#FFFFFF\">Nr.</font></th><th align=center width=\"27%\"><FONT color=\"#FFFFFF\">Datum</font></th><TH><font color=\"#FFFFFF\">Joker</font></th></tr>");

		return pw;
	}

	private void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("</center></div>");
		pw.println("</body>");
		pw.println("</html>");
	}

	private void writeJoker(PrintWriter pw, Ziehung zie) {
		StringBuffer sb = new StringBuffer();

		pw.print("<tr><th align=right>");
		pw.print(zie.getNr());
		pw.println("</th>");

		if (zie.isWednesday())
			sb.append("MI,&nbsp;");
		else
			sb.append("SA,&nbsp;");
		sb.append(dateFormat.format(zie.getDatum()));

		pw.print("<TD align=center><TT>");
		pw.print(sb.toString());
		pw.print("</TT></TD>");
		pw.print("<TD align=center><B>");
		pw.print(zie.getJoker());
		pw.print("</B></TD></TR>");

	}
}