
package gtf.tools;

import java.io.PrintWriter;

public class SCFBookItem implements Comparable {
	
	private String gtfNo;
	private String accountNoDebit;
	private String accountNoCredit;
	private String valueDate;
	private String amount;
	private String currency;
	private String caha;
	private String holderOID;
	private String credit, debit;
	private double am;
	
	public SCFBookItem() {
		gtfNo = null;
		accountNoDebit = null;
		accountNoCredit = null;
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
	
	public void setAccountNoDebit(String accountNo) {
		this.accountNoDebit = accountNo;
	}
	
	public String getAccountNoDebit() {
		return accountNoDebit;
	}

	public void setAccountNoCredit(String accountNo) {
		this.accountNoCredit = accountNo;
	}
	
	public String getAccountNoCredit() {
		return accountNoCredit;
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
		pw.println("<title>SC Firm Booking Summary</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<table border=\"1\">");
		pw.println("<tr>");
		pw.println("<th>GtfNo</th>");
		pw.println("<th>DebitAccountNo</th>");
		pw.println("<th>CreditAccountNo</th>");
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
		pw.println("<td colspan=\"6\" align=\"right\">TOTAL "+text+"</td>");
		pw.println("<td align=\"right\">"+total+"</td>");
		pw.println("<td>&nbsp;</td>");		
		pw.println("</tr>	");	
		
		pw.println("<tr>");
		pw.println("<td colspan=\"8\">&nbsp;</td>");
		pw.println("</tr>");
	}
	
	public void writeRow(PrintWriter pw) {
		pw.println("<tr>");
		pw.println("<td >"+caha+"-"+gtfNo+"</td>");
		pw.println("<td align=\"right\">"+accountNoDebit+"</td>");
		pw.println("<td align=\"right\">"+accountNoCredit+"</td>");
		pw.println("<td align=\"right\">"+debit+"</td>");
		pw.println("<td align=\"right\">"+credit+"</td>");
		pw.println("<td align=\"right\">"+currency+"</td>");
		pw.println("<td align=\"right\">"+amount+"</td>");
		pw.println("<td align=\"right\">"+valueDate+"</td>");
		
		pw.println("</tr>	");
	}
	
	public int compareTo(Object o) {
		int r;
		SCFBookItem other = (SCFBookItem)o;

		r = currency.compareTo(other.currency);
		if (r == 0) {
			
			String c1 = accountNoDebit;
			String c2 = other.accountNoDebit;
			
			if (c1.startsWith("9400", 4))
				c1 = accountNoCredit;
			if (c2.startsWith("9400", 4))
				c2 = other.accountNoCredit;	
			
			r = c1.compareTo(c2);
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