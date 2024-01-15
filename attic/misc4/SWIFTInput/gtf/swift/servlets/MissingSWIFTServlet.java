package gtf.swift.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import gtf.util.*;
import gtf.swift.*;
import common.util.*;

public class MissingSWIFTServlet extends HttpServlet {

	private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss");    
	private Calendar lastUpdate;

	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<HTML><HEAD><TITLE>Fehlende SWIFT Input Messages</TITLE></HEAD>");
		out.println("<BODY><FONT SIZE=\"+1\"><b>Fehlende SWIFT Input Messages </b></FONT>");	
		out.print("<HR>");
	
		List missingTOSN = SWIFTService.getMissingSWIFT();
	
		if (missingTOSN.size() > 0) {
			out.println("SWIFT(s) mit folgender FOSN fehlen:<p>");
			for (int i = 0; i < missingTOSN.size(); i++)
				out.print("<b>" + (String)missingTOSN.get(i) + "</b><br>");
			out.println("<p>");
			out.println("Repetition mit SW41 an Transaktion LC55 (nicht an Drucker!)<br>");
			out.println("SWIFT-Support: " + AppProperties.getStringProperty("swift.support.tel"));
		} else {
			out.println("Es fehlt kein SWIFT");
		}
		
		
		out.print("<HR>");
		out.print("</BODY></HTML>");
		out.close();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
			String datFile = getInitParameter("props.file");			
			AppProperties.setFileName(datFile);
	}
}