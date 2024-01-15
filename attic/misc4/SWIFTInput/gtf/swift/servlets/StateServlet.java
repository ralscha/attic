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
import common.util.*;

public class StateServlet extends HttpServlet {

	private Branches branches;
	private List colourList;
	
	private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm");    

	private String servletPath;
	private Map descriptionMap;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		printHeader(out, "", "all", "ALL");
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int gtfNumber = 0;
		String submit = null;
		String value, name;
		String branchStr, colour;
		String gtfNumberStr = "";
		State stateObj;
		int colourValue = 0;
		branchStr = colour = null;
	
		Enumeration values = req.getParameterNames();
		while (values.hasMoreElements()) {
			name = (String) values.nextElement();
			value = req.getParameterValues(name)[0];
			if (name.equals("gtfno")) {
				try {
					gtfNumber = Integer.parseInt(value);
					gtfNumberStr = value;
				} catch (NumberFormatException nfe) {
					gtfNumber = 0;
					gtfNumberStr = "";
				}
			} else	if (name.equals("branches")) {
				branchStr = value;
			} else	if (name.equals("colour")) {
				colour = value;
				colourValue = 0;
				for (int i = 0; i < colourList.size(); i++) {
					if (colour.equals(colourList.get(i))) {
						colourValue = i + 1;
						break;
					}	
				}
			}
		}
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
	
		printHeader(out, gtfNumberStr, colour, branchStr);
		out.println("<P>");
		out.println("<table border=\"1\">");
		out.println("<TR>");
		out.println("<TH>Gtf Nr</TH>");
		out.println("<TH>Proc Center</TH>");
		out.println("<TH>Typ</TH>");
		out.println("<TH>Status</TH>");
		out.println("<TH>Datum</TH>");
		out.println("<TH>Beschreibung</TH>");
		out.println("</TR>");

		Branch branch = branches.getBranch(branchStr);

		List states = SWIFTService.searchStates(gtfNumber, branch, "GtfNo", SWIFTService.ASC);
		for (int i = 0; i < states.size(); i++) {
			stateObj = (State)states.get(i);
			StateDescription sd = (StateDescription)descriptionMap.get(stateObj.getStateNum());
			if ((colourValue == 4) || (colourValue == sd.getColour())) {
				switch (sd.getColour()) {
					case 1 :
						out.println("<TR bgcolor=\"Lime\">");
						break;
					case 2 :
						out.println("<TR bgcolor=\"Yellow\">");
						break;
					case 3 :
						out.println("<TR bgcolor=\"Red\">");
						break;
					default :
						out.println("<TR>");
				}
				out.println("<TD>" + stateObj.getGtfNo() + "</TD>");
				out.println("<TD>" + stateObj.getProcCenter() + "</TD>");
				if (stateObj.getMessageType().equalsIgnoreCase("S"))
					out.println("<TD>SWIFT</TD>");
				else
					out.println("<TD>TELEX</TD>");
				out.println("<TD>" + stateObj.getStateNum() + " " + stateObj.getStateTran() + "</TD>");
				out.println("<TD>" + datetimeFormat.format(new java.util.Date(stateObj.getStateTS().getTime())) + "</TD>");
				out.println("<TD>" + sd.getDescription() + "</TD>");
				out.println("</TR>");
			}
		}
		out.println("</TABLE>");
		out.println("</BODY>");
		out.println("</HTML>");
		out.close();
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	
		String datFile = getInitParameter("props.file");

		AppProperties.setFileName(datFile);
		servletPath = AppProperties.getStringProperty("state.servlet.path");
		branches = new Branches();

		colourList = new ArrayList();
		colourList.add("green");
		colourList.add("yellow");
		colourList.add("red");
		colourList.add("all");
	
		List sd = SWIFTService.getStateDescription();
		if (sd != null) {
			descriptionMap = new HashMap();
			for (int i = 0; i < sd.size(); i++) {
				StateDescription s = (StateDescription)sd.get(i);
				descriptionMap.put(s.getCode(), s);
			}
		}
	}
	
	public void printHeader(PrintWriter out, String value, String colour, String branch) {
		out.print("<HTML>");
		out.print("<HEAD><TITLE>SWIFT/Telex Status</TITLE></HEAD>");
		out.print("<BODY><FONT SIZE=\"+1\"><b>SWIFT/Telex Status</b></FONT>");
		out.print("<HR>");
		out.println("<FORM ACTION=\"/" + servletPath + "\" METHOD=\"POST\">");
		out.println("<P>Gtf Number&nbsp;&nbsp;<INPUT TYPE=\"TEXT\" NAME=\"gtfno\" VALUE=\"" + value + "\" SIZE=\"7\">");
		out.println("&nbsp;&nbsp;");
		out.println("<select name=\"branches\" size=\"1\">");
		Iterator it = branches.iterator();
		while (it.hasNext()) {
			Branch b = (Branch)it.next();
			if (b.getName().equals(branch))
				out.print("<option value=\"" + b.getHTMLName() + "\" selected>" + b.getHTMLName() + "</option>");
			else
				out.print("<option value=\"" + b.getHTMLName() + "\">" + b.getHTMLName() + "</option>");
		}
		out.print("</select>");
		out.println("&nbsp;&nbsp;");
		for (int i = 0; i < colourList.size(); i++) {
			String hs = (String) colourList.get(i);
			if (hs.equals(colour))
				out.println("<input type=\"Radio\" name=\"colour\" value=\"" + hs + "\" checked>" + hs);
			else
				out.println("<input type=\"Radio\" name=\"colour\" value=\"" + hs + "\">" + hs);
		}
		out.println("&nbsp;&nbsp;");
		out.println("<INPUT TYPE=\"SUBMIT\" NAME=\"Submit\" VALUE=\"Show\">");
		out.println("</FORM>");
		out.println("<HR>");
	}
}