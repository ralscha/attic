
package gtf.control;

import java.io.*;
import java.util.*;
import java.text.*;
import com.ibm.math.BigDecimal;

public class LiabCheckHTMLPage {
	
	private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    
 	private static DecimalFormat form = null;
 	
 
	static {
		form = new DecimalFormat("#,##0.000");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);
	}
	

	private PrintWriter out = null;
	
	public LiabCheckHTMLPage(PrintWriter out, String title) throws IOException {
		this.out = out;
		writeHead(title);
	}

	private void writeHead(String title) {
		out.println("<html>");
		out.println("<head>");
		out.print("<title>");
		out.print(title);
		out.println("</title>");
		out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"Mon, 06 Jan 1990 00:00:01 GMT\">");
		out.println("</head>");
		out.println("<body>");
		out.print("<FONT SIZE=\"+1\"><b>");
		out.print(title);
		out.println("</b></FONT>");
		out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
		
		Calendar today = Calendar.getInstance();
		out.print(lastUpdateFormat.format(today.getTime()));
		out.println("</font>");
		out.println("<hr>");
		out.println("<p>");		
	}

	private void writeTail() {
		out.println("</body>");
		out.println("</html>");
	}

	public void writeSeparator() {
		out.println("<p>");
		out.println("<hr>");
		out.println("<p>");
	}

	public void writeOverviewTableHead() {
		out.println("<table width=\"60%\" border=\"1\">");
		out.println("<tr>");
		out.println("<th>BU</th>");
		out.println("<th>W&auml;hrung</th>");
		out.println("<th align=\"RIGHT\">Einzelkonto</th>");
		out.println("<th align=\"RIGHT\">Sammelkonto</th>");
		out.println("<th align=\"RIGHT\">Differenz</th>");
		out.println("</tr>");
	}

	public void writeOverviewItem(String bu, String iso, 
					BigDecimal singleAmount, BigDecimal totalAmount, 
					BigDecimal difference) {
		out.println("<tr>");
		out.print("<td align=\"CENTER\">");
		out.print(bu);
		out.println("</td>");
		
		out.print("<td align=\"CENTER\">");
		out.print(iso);
		out.println("</td>");
				
		out.print("<td align=\"RIGHT\">");
		out.print(form.format(singleAmount.doubleValue()));
		out.println("</td>");
		
		out.print("<td align=\"RIGHT\">");
		out.print(form.format(totalAmount.doubleValue()));
		out.println("</td>");
		
		if (difference.compareTo(BigDecimal.ZERO) != 0) 
			out.print("<td align=\"RIGHT\" style=\"font-weight: bold; color: #00FF00;\">");
		else
			out.print("<td align=\"RIGHT\">");
		
		out.print(form.format(difference.doubleValue()));
		out.println("</td>");
		
		out.println("</tr>");		
	}

	public void writeCurrencyTableHead(String iso) {
		out.println("<table width=\"60%\" border=\"1\">");
		out.println("<tr>");
		out.print("<th colspan=\"4\" align=\"LEFT\">");
		out.print(iso);
		out.println("</th>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<th>Bankstelle</th>");
		out.println("<th align=\"RIGHT\">Einzelkonto</th>");
		out.println("<th align=\"RIGHT\">Sammelkonto</th>");
		out.println("<th align=\"RIGHT\">Differenz</th>");
		out.println("</tr>");
	}
	
	public void writeCurrencyItem(String bkst, BigDecimal singleTotal, BigDecimal totalTotal, 
										BigDecimal diff) {
		out.println("<tr>");
		out.print("<td align=\"CENTER\">");
		out.print(bkst);
		out.println("</td>");
		
		
		out.print("<td align=\"RIGHT\">");
		out.print(form.format(singleTotal.doubleValue()));
		out.println("</td>");
		
		out.print("<td align=\"RIGHT\">");
		out.print(form.format(totalTotal.doubleValue()));
		out.println("</td>");
		
		if (diff != null) {
			out.print("<td align=\"RIGHT\" style=\"font-weight: bold; color: #00FF00;\">");
			out.print(form.format(diff.doubleValue()));
			out.println("</td>");
		} else {
			out.println("<td>&nbsp;</td>");
		}
		
		out.println("</tr>");
	}
	
	public void writeCurrencyItem(String bkst, BigDecimal amount, boolean total) {
		writeCurrencyItem(bkst, amount, total, null);
	}	
	
	public void writeCurrencyItem(String bkst, BigDecimal amount, boolean total, BigDecimal diff) {
		out.println("<tr>");
		out.print("<td align=\"CENTER\">");
		out.print(bkst);
		out.println("</td>");
		
		if (total) {
			out.println("<td>&nbsp;</td>");
			out.print("<td align=\"RIGHT\">");
			out.print(form.format(amount.doubleValue()));
			out.println("</td>");
		} else {
			out.print("<td align=\"RIGHT\">");
			out.print(form.format(amount.doubleValue()));
			out.println("</td>");
			out.println("<td>&nbsp;</td>");
		}
		
		if (diff != null) {
			out.print("<td align=\"RIGHT\" style=\"font-weight: bold; color: #00FF00;\">");
			out.print(form.format(diff.doubleValue()));
			out.println("</td>");
		} else {
			out.println("<td>&nbsp;</td>");
		}
		
		out.println("</tr>");
	}
	
	
	
	public void writeTableTail() {
		out.println("</table>");
	}
	
	public void close() {
		writeTail();
		out.close();
	}

}