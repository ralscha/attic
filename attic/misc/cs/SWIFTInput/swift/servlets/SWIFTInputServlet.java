package gtf.swift.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import gtf.util.*;
import gtf.swift.*;
import gtf.swift.util.*;
import gtf.swift.state.*;
import gtf.swift.input.*;
import gtf.swift.mt.*;
import common.util.*;


public class SWIFTInputServlet extends HttpServlet {

	private List dates;
	private List cols;
	
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");    
	private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm");    

	private final static String[] PARMSTR = {"from", "to", "branch", "orderby", "order"};
	private final static String ASCORDER = "ASC";
	private final static String DESCORDER = "DESC";

	private final static String COL_TOSN = "TOSN";
	private final static String COL_MT = "MT";
	private final static String COL_SENDER = "Sender";
	private final static String COL_PRIORITY = "Priority";
	private final static String COL_DUPLICATE = "Duplicate";
	private final static String COL_RECEIVER = "Receiver";
	private final static String COL_RECEIPTDATE = "Receipt Date";

	private Calendar lastUpdate;
	private Branches branches;

	private String servletPath, servletMsgPath;
	

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		dates = SWIFTService.getDates();
			
		if ((dates != null) && (dates.size() > 0))  {
			printHeader(out, (String)dates.get(dates.size() - 1), (String)dates.get(dates.size() - 1), "ALL", (String)cols.get(0), DESCORDER);
		} else {
			out.print("<HTML>");
			out.print("<HEAD><TITLE>SWIFT Input</TITLE></HEAD>");
			out.print("<BODY><FONT SIZE=\"+1\"><b>SWIFT Input</b></FONT>");
			out.print("<HR>");
			out.print("NO SWIFTS");
		}
		out.print("</BODY>");
		out.print("</HTML>");	
		out.close();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String hStr;
		Calendar fromDate = new GregorianCalendar();
		Calendar toDate = new GregorianCalendar();
		String branch = null;
		String order = null;
		String by = null;
		String toDateStr = null;
		String fromDateStr = null;
		String name, value;
		dates = SWIFTService.getDates();
	
		Enumeration values = req.getParameterNames();
		while (values.hasMoreElements()) {
			name = (String) values.nextElement();
			value = req.getParameterValues(name)[0];
			if (name.equals(PARMSTR[0])) {
				ParsePosition pos = new ParsePosition(0);
				fromDateStr = value;
				Date help = dateFormat.parse(value, pos);
				fromDate.setTime(help);
			} else	if (name.equals(PARMSTR[1])) {
				ParsePosition pos = new ParsePosition(0);
				toDateStr = value;
				Date help = dateFormat.parse(value, pos);
				toDate.setTime(help);
			} else	if (name.equals(PARMSTR[2]))
				branch = value;
			else if (name.equals(PARMSTR[3]))
				order = value;
			else if (name.equals(PARMSTR[4]))
				by = value;
		}
		
		if (fromDate.after(toDate)) {
			Calendar h = fromDate;
			fromDate = toDate;
			toDate = h;
			String s = fromDateStr;
			fromDateStr = toDateStr;
			toDateStr = s;
		}
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		printHeader(out, fromDateStr, toDateStr, branch, order, by);
		printTableHeader(out, order, by);
		List headers = searchData(fromDate, toDate, branch, order, by);
		if (headers != null) {
			for (int i = 0; i < headers.size(); i++) {
				SWIFTHeader shObj = (SWIFTHeader)headers.get(i);
				out.print("<TR>");
				out.print("<TD><A HREF=\"/"+servletMsgPath+"?TOSN=" + shObj.getTOSN() + "\" TARGET=\"Msg\">" + shObj.getTOSN() + "</A></TD>");
				out.print("<TD>" + shObj.getMessageType() + "</TD>");
				out.print("<TD ALIGN=\"LEFT\">" + shObj.getSenderAddress() + "</TD>");
				if (shObj.getPriority().equalsIgnoreCase("U"))
					out.print("<TD ALIGN=\"CENTER\">urgent</TD>");
				else
					out.print("<TD ALIGN=\"CENTER\">normal</TD>");
				if (shObj.getDuplicate().equalsIgnoreCase("Y"))
					out.print("<TD ALIGN=\"CENTER\">yes</TD>");
				else
					out.print("<TD ALIGN=\"CENTER\">no</TD>");
				out.print("<TD ALIGN=\"LEFT\">" + shObj.getReceiverAddress() + "</TD>");
				out.print("<TD>" + shObj.getProcCenter() + "</TD>");
				out.print("<TD>" + datetimeFormat.format(new Date(shObj.getReceiveTS().getTime())) + "</TD>");
				out.print("</TR>");
			}
		} else {
			out.print("<b>Nothing found</b>");
		}
		out.print("</TABLE></BODY></HTML>");
		out.close();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	
		String datFile = getInitParameter("props.file");
		AppProperties.setFileName(datFile);
		servletPath = AppProperties.getStringProperty("swift.input.servlet.path");
		servletMsgPath = AppProperties.getStringProperty("swift.msg.servlet.path");
				
		branches = new Branches();
		
		cols = new ArrayList();
		cols.add(COL_TOSN);
		cols.add(COL_MT);
		cols.add(COL_SENDER);
		cols.add(COL_PRIORITY);
		cols.add(COL_DUPLICATE);
		cols.add(COL_RECEIVER);
		cols.add(COL_RECEIPTDATE);
			
	}

	public void printHeader(PrintWriter out, String from, String to, String branch, String order, String by) {
		String hStr;
		out.print("<HTML>");
		out.print("<HEAD><TITLE>SWIFT Input</TITLE></HEAD>");
		out.print("<BODY><FONT SIZE=\"+1\"><b>SWIFT Input</b></FONT>");
		out.print("<HR>");
		out.print("<FORM ACTION=\"/" + servletPath + "\" METHOD=\"POST\">");
		out.print("<table><tr><th align=\"LEFT\">from</th><th align=\"LEFT\">to</th><th align=\"LEFT\">branch</th>");
		out.print("<th colspan=\"2\" align=\"LEFT\">order by</th><th>&nbsp;</th></tr>");
		out.print("<tr>");
		out.print("<td><select name=\"" + PARMSTR[0] + "\" size=\"1\">");
		for (int i = 0; i < dates.size(); i++) {
			hStr = (String)dates.get(i);
			if (hStr.equals(from))
				out.print("<option value=\"" + hStr + "\" selected>" + hStr + "</option>");
			else
				out.print("<option value=\"" + hStr + "\">" + hStr + "</option>");
		}
		out.print("</select></td>");
		out.print("<td><select name=\"" + PARMSTR[1] + "\" size=\"1\">");
		for (int i = 0; i < dates.size(); i++) {
			hStr = (String)dates.get(i);
			if (hStr.equals(to))
				out.print("<option value=\"" + hStr + "\" selected>" + hStr + "</option>");
			else
				out.print("<option value=\"" + hStr + "\">" + hStr + "</option>");
		}
		out.print("</select></td>");
		out.print("<td><select name=\"" + PARMSTR[2] + "\" size=\"1\">");
		Iterator it = branches.iterator();
		while (it.hasNext()) {
			Branch b = (Branch)it.next();
			hStr = b.getName();
			if (hStr.equals(branch))
				out.print("<option value=\"" + b.getHTMLName() + "\" selected>" + b.getHTMLName() + "</option>");
			else
				out.print("<option value=\"" + b.getHTMLName() + "\">" + b.getHTMLName() + "</option>");
		}
		out.print("</select></td>");
		out.print("<td><select name=\"" + PARMSTR[3] + "\" size=\"1\">");
		for (int i = 0; i < cols.size(); i++) {
			hStr = (String)cols.get(i);
			if (!hStr.equals(COL_RECEIPTDATE)) {
				if (hStr.equals(order))
					out.print("<option value=\"" + hStr + "\" selected>" + hStr + "</option>");
				else
					out.print("<option value=\"" + hStr + "\">" + hStr + "</option>");
			}
		}
		out.print("</select></td><td><select name=\"" + PARMSTR[4] + "\" size=\"1\">");
		if (by.equals(ASCORDER)) {
			out.print("<option value=\"" + ASCORDER + "\" selected>ascending</option>");
			out.print("<option value=\"" + DESCORDER + "\">descending</option></select>");
		} else {
			out.print("<option value=\"" + ASCORDER + "\">ascending</option>");
			out.print("<option value=\"" + DESCORDER + "\" selected>descending</option></select>");
		}
		out.print("</td><td><INPUT TYPE=\"SUBMIT\" VALUE=\"Show\"></td></tr></table>");
		out.print("</FORM><HR>");
	}

	public void printTableHeader(PrintWriter out, String ordercol, String by) {
		out.print("<TABLE CELLPADDING=3 WIDTH=0 BORDER=1 ALIGN=\"LEFT\">");
		out.print("<TR>");
		for (int i = 0; i < cols.size(); i++) {
			String hStr = (String)cols.get(i);
			if (hStr.equals("Receiver"))
				out.print("<TH COLSPAN=\"2\" ALIGN=\"LEFT\">");
			else
				out.print("<TH ALIGN=\"LEFT\">");
			out.print(hStr);
			if (hStr.equals(ordercol)) {
				if (by.equals(ASCORDER))
					out.print("&nbsp;<img src=\"/icons/up.gif\" width=13 height=13 border=0>");
				else
					out.print("&nbsp;<img src=\"/icons/down.gif\" width=13 height=13 border=0>");
			}
			out.print("</TH>");
		}
		out.print("</TR>");
	}
	
	private List searchData(Calendar fromDate, Calendar toDate, String branchStr, String orderBy, String dir) {
		
		fromDate.set(Calendar.HOUR_OF_DAY, 0);
		fromDate.set(Calendar.MINUTE, 0);
		fromDate.set(Calendar.SECOND, 0);
		toDate.set(Calendar.HOUR_OF_DAY, 23);
		toDate.set(Calendar.MINUTE, 59);
		toDate.set(Calendar.SECOND, 59);
		Branch branch = branches.getBranch(branchStr);

		if (branch == null)
			return null;
		int d;
		if (dir.equals(ASCORDER))
			d = SWIFTService.ASC;
		else
			d = SWIFTService.DESC;
		String orderClause = null;
		if (orderBy.equals(COL_TOSN))
			orderClause = "TOSN";
		if (orderBy.equals(COL_MT))
			orderClause = "MessageType";
		if (orderBy.equals(COL_SENDER))
			orderClause = "SenderAddress";
		if (orderBy.equals(COL_PRIORITY))
			orderClause = "Priority";
		if (orderBy.equals(COL_DUPLICATE))
			orderClause = "Duplicate";
		if (orderBy.equals(COL_RECEIVER))
			orderClause = "ReceiverAddress";
		if (orderBy.equals(COL_RECEIPTDATE))
			orderClause = "ReceiveTS";
		return (SWIFTService.searchSWIFTs(fromDate, toDate, branch, orderClause, d));
	}
}