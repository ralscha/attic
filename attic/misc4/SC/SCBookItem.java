
package gtf.tools;

import java.io.PrintWriter;

public class SCBookItem implements Comparable {
	
	private String gtfNo;
	private String accountNo;
	private String valueDate;
	private String amount;
	private String currency;
	private String caha;
	private String holderOID;
	private String credit, debit;
	private double am;
	
	public SCBookItem() {
		gtfNo = null;
		accountNo = null;
		valueDate = null;
		amount = null;
		currency = null;
		caha = null;
		holderOID = null;
		credit = null;
		debit = null;
		am = 0;
	}
	
	public void setTAmount(double am) {
		this.am = am;
	}
	
	public double getTAmount() {
		return am;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}
	
	public void setDebit(String debit) {
		this.debit = debit;
	}

	public void setGtfNo(String gtfNo) {
		this.gtfNo = gtfNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getAccountNo() {
		return accountNo;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCaha(String caha) {
		this.caha = caha;
	}

	public void setHolderOID(String holderOID) {
		this.holderOID = holderOID;
	}
	
	public static void writeHead(PrintWriter pw) {
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>SC Contingent Booking Summary</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<table border=\"1\">");
		pw.println("<tr>");
		pw.println("<th>GtfNo</th>");
		pw.println("<th>AccountNo</th>");
		pw.println("<th>Debit</th>");
		pw.println("<th>Credit</th>");
		pw.println("<th>Currency</th>");
		pw.println("<th>Amount</th>");
		pw.println("<th>ValueDate</th>");	
		pw.println("</tr>");
	}
		
	public static void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("</body>");
		pw.println("</html>");
	}
	
	public static void writeTotal(PrintWriter pw, String text, String total) {
		pw.println("<tr>");
		pw.println("<td colspan=\"5\" align=\"right\">TOTAL "+text+"</td>");
		pw.println("<td align=\"right\">"+total+"</td>");
		pw.println("<td>&nbsp;</td>");		
		pw.println("</tr>	");	
		
		pw.println("<tr>");
		pw.println("<td colspan=\"7\">&nbsp;</td>");
		pw.println("</tr>");
	}
	
	public void writeRow(PrintWriter pw) {
		pw.println("<tr>");
		pw.println("<td >"+caha+"-"+gtfNo+"</td>");
		pw.println("<td align=\"right\">"+accountNo+"</td>");
		pw.println("<td align=\"right\">"+debit+"</td>");
		pw.println("<td align=\"right\">"+credit+"</td>");
		pw.println("<td align=\"right\">"+currency+"</td>");
		pw.println("<td align=\"right\">"+amount+"</td>");
		pw.println("<td align=\"right\">"+valueDate+"</td>");
		
		pw.println("</tr>	");
	}
	
	public int compareTo(Object o) {
		int r;
		SCBookItem other = (SCBookItem)o;

		r = currency.compareTo(other.currency);
		if (r == 0) {
			r = accountNo.compareTo(other.accountNo);
			if (r == 0) {
				r = caha.compareTo(other.caha);
				if (r == 0) {
					r = gtfNo.compareTo(other.gtfNo);
				}
			}
		}
		return r;
	}
	
	
}