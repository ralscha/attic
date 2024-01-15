
import java.math.BigDecimal;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

public class CrapaHTMLPage {

	private String fileName;
	private String lastKst, lastLE;
	private String branch;

	private static final DecimalFormat format, numberFormat;

	static {
		format = new DecimalFormat("#,##0.00");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		format.setDecimalFormatSymbols(dfs);

		numberFormat = new DecimalFormat("#,##0");
		numberFormat.setDecimalFormatSymbols(dfs);
	}

	private PrintWriter out = null;
	
	public CrapaHTMLPage(PrintWriter out, String branch, int year, int month) throws IOException {

		this.out = out;
		lastKst = null;
		lastLE = null;
		
		this.branch = branch;
		writeHead(year, month);
	}

	private void writeHead(int year, int month) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Crapa "+year+Strings.rightJustify(String.valueOf(month),2,'0') + "</title>");
		//out.println("<link rel=stylesheet type=\"text/css\" href=\"file:///D|/projects/Crapa/crapa.css\">");
		out.println("<link rel=stylesheet type=\"text/css\" href=\"/gtf/crapa.css\">");
		out.println("</head>");
		out.println("<body>");
		out.print("<i>");
		out.print(branch);
		out.print(" ");
		out.print(Branches.getName(branch));
		out.print(" / ");
		out.print(month);
		out.print(".");
		out.print(year);
		out.print("</i>");
		out.println("<table class=\"text\" width=\"90%\">");
		out.println("<tr>");
		out.println("<td class=\"text\" colspan=\"6\"><hr></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th class=\"textb\" align=\"LEFT\">Kst</th>");
		out.println("<th class=\"textb\" align=\"LEFT\">Legal Entity</th>");
		out.println("<th class=\"textb\" align=\"LEFT\">Code</th>");
		out.println("<th class=\"textb\" align=\"LEFT\">Beschreibung</th>");
		out.println("<th class=\"textb\" align=\"RIGHT\">Anzahl</th>");
		out.println("<th class=\"textb\" align=\"RIGHT\">Betrag</th>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td class=\"text\" colspan=\"6\"><hr></td>");
		out.println("</tr>");
	}

	private void writeTail() {
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}

	public void addLine(String kst, String legalEntity, int crapaCode, int number,
                    	BigDecimal total) {


		if (kst.equals(lastKst) && legalEntity.equals(lastLE)) {
			out.println("<tr>");
			out.println("<td class=\"text\"></td>");
			out.println("<td class=\"text\"></td>");
		} else {

			if (lastKst != null) {
				out.println("<tr>");
				out.println("<td class=\"text\" colspan=\"6\">&nbsp;</td>");
				out.println("</tr>");
			}

			out.println("<tr>");
			out.println("<td class=\"text\">"+kst + "</td>");
			out.println("<td class=\"text\">"+legalEntity + "</td>");

			lastKst = kst;
			lastLE = legalEntity;
		}

		out.println("<td class=\"text\">"+crapaCode + "</td>");
		out.println("<td class=\"text\">"+CrapaCodeDescription.getDescription(crapaCode) + "</td>");
		out.println("<td class=\"text\" align=\"RIGHT\">"+numberFormat.format(number) + "</td>");
		out.println("<td class=\"text\" align=\"RIGHT\">"+format.format(total) + "</td>");
		out.println("</tr>");
	}

	public void addTotalLine(BigDecimal total) {
		out.println("<tr>");
		out.println("<td class=\"text\" colspan=\"6\"><hr></td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td class=\"text\"></td>");
		out.println("<td class=\"text\"></td>");
		out.println("<td class=\"text\"></td>");
		out.println("<td class=\"text\" align=\"RIGHT\"><b>Total</b></td>");
		out.println("<td class=\"text\"></td>");
		out.println("<td class=\"text\" align=\"RIGHT\"><b>"+format.format(total) + "</b></td>");
		out.println("</tr>");
	}

	public void close() {
		writeTail();
		out.close();
	}

}