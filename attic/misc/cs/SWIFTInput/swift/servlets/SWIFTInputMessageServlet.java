package gtf.swift.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import java.util.*;
import gtf.util.*;
import gtf.swift.*;
import gtf.swift.util.*;
import gtf.swift.state.*;
import gtf.swift.input.*;
import gtf.swift.mt.*;
import common.util.*;


public class SWIFTInputMessageServlet extends HttpServlet {

	private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm");    
	private final static SimpleDateFormat dateFormat     = new SimpleDateFormat("dd.MM.yyyy");    
	

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String adr;
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		String tosn = req.getParameterValues("TOSN")[0];
		if (tosn == null)
			return;
			
		out.print("<HTML><HEAD>");
		out.print("<TITLE>SWIFT Input</TITLE>");
		out.print("</HEAD><BODY>");
	
		SWIFTHeader shObj = SWIFTService.getSWIFTHeader(tosn);
		
		if (shObj != null) {
			SWIFT_MT7 swiftMT = SWIFT_MT7.getSWIFTMT(shObj.getMessageType());
			out.print("<FONT SIZE=\"+1\"><b>SWIFT Input</b> (TOSN " + tosn + "&nbsp;&nbsp;ID ");
			out.print(shObj.getSessionNumber() + " " + shObj.getSequenceNumber() + ")</FONT>");
			String addrSender = shObj.getSenderAddress(); 
			out.print("<table><tr><td colspan=\"3\"><hr></td></tr><TR><TD>Sender</TD><TD><B>");
			out.print(addrSender);
			out.print("</B></td><td>Dispatch date: <b>");
			out.print(dateFormat.format(new java.util.Date(shObj.getSendDate().getTime())));
			out.print("</b></TD></TR>");
			out.print("<TR><td>&nbsp;</td><td colspan=\"2\">"); 
			adr = SWIFTService.getAddressFromSWIFT(addrSender.substring(0, 8) + addrSender.substring(9));
			if (adr != null)
				out.print(adr);
			out.print("</td></tr><tr><td colspan=\"3\"><hr></td></tr>");
			String addrReceiver = shObj.getReceiverAddress();
			out.print("<TR><TD>Receiver&nbsp;</TD><TD><B>");
			out.print(addrReceiver);
			out.print("</B> (");
			out.print(shObj.getProcCenter());
			out.print(")</td><td>Receipt date: <b>");
			out.print(datetimeFormat.format(new java.util.Date(shObj.getReceiveTS().getTime())));
			out.print("</b></TD></TR><tr><td>&nbsp;</td>");
			out.print("<td colspan=\"2\">");
			adr = SWIFTService.getAddressFromSWIFT(addrReceiver);
			if (adr != null)
				out.print(adr);
			out.print("</td></TR><tr><td colspan=\"3\"><hr></td>");
			out.print("</tr><TR><TD></TD><TD>Priority: <B>");
			if (shObj.getPriority().equals("U"))
				out.print("urgent");
			else
				out.print("normal");
			out.print("</B></TD><td>Possible duplicate: <B>");
			if (shObj.getDuplicate().equals("Y"))
				out.print("yes");
			else
				out.print("no");
			out.print("</B></td></tr><tr><td colspan=\"3\"><hr></td></tr></TABLE><p>");
			if (swiftMT != null)
				out.print("<FONT SIZE=\"+1\">" + swiftMT.getMTName() + " (MT " + shObj.getMessageType() + ")</FONT>");
			else
				out.print("<FONT SIZE=\"+1\">MT " + shObj.getMessageType() + "</FONT>");
			out.print("<PRE>");
			StringBuffer line = new StringBuffer();
			String fTag;
	
			List ml = SWIFTService.getSWIFTMessage(tosn);
	
			for (int i = 0; i < ml.size(); i++) {
				MessageLine mlObj = (MessageLine)ml.get(i);
				line.setLength(0);
				fTag = mlObj.getFieldTag().trim();
				if ((fTag != null) && (fTag.length() != 0)) {
					line.append("\n   <b>").append(fTag);
					if (fTag.length() == 2)
						line.append("   ");
					else
						line.append("  ");
					if (swiftMT != null) {
						line.append(swiftMT.getTagDescription(fTag)).append("</b>\n");
						line.append("        ");
						line.append(mlObj.getMsgLine());
					} else {
						line.append("</b>").append(mlObj.getMsgLine());
					}
				} else {
					line.append("        ");
					line.append(mlObj.getMsgLine());
				}
				out.println(line.toString());
			}
			out.print("</PRE>");
		} else {
			out.print("<FONT SIZE=\"+2\"><B>SWIFT " + tosn + " NOT FOUND</B></FONT>");
		}
		out.print("</BODY></HTML>");
		out.close();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	
		String datFile = getInitParameter("props.file");	
		AppProperties.setFileName(datFile);
	}
}