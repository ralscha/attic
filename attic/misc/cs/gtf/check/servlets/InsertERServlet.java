package gtf.check.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import common.util.*;
import gtf.db.*;
import gtf.common.*;
import ViolinStrings.*;

public class InsertERServlet extends HttpServlet {
	
	
	public void doPost(HttpServletRequest req,
                   	HttpServletResponse res) throws ServletException, IOException {
                   		

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		        
		
		String branch = req.getParameter("branch");
		if ((branch == null) || (branch.trim().length() == 0)) {
			res.sendError(res.SC_BAD_REQUEST, "No Branch specified.");
			return;
		}
		
		String isocode = req.getParameter("isocode");
		if ((isocode == null) || (isocode.trim().length() == 0)) {
			res.sendError(res.SC_BAD_REQUEST, "No ISO Code specified.");
			return;
		}
		
		
		String buyrateastr = req.getParameter("buyratea");
		if ((buyrateastr == null) || (buyrateastr.trim().length() == 0)) {
			res.sendError(res.SC_BAD_REQUEST, "No Buy Rate A specified.");
			return;
		}
		
		String buyratebstr = req.getParameter("buyrateb");
		if ((buyratebstr == null) || (buyratebstr.trim().length() == 0)) {
			res.sendError(res.SC_BAD_REQUEST, "No Buy Rate B specified.");
			return;
		}
		
		String sellrateastr = req.getParameter("sellratea");
		if ((sellrateastr == null) || (sellrateastr.trim().length() == 0)) {
			res.sendError(res.SC_BAD_REQUEST, "No Sell Rate A specified.");
			return;
		}
		
		String sellratebstr = req.getParameter("sellrateb");
		if ((sellratebstr == null) || (sellratebstr.trim().length() == 0)) {
			res.sendError(res.SC_BAD_REQUEST, "No Sell Rate B specified.");
			return;
		}

		String middleratestr = req.getParameter("middlerate");
		if ((middleratestr == null) || (middleratestr.trim().length() == 0)) {
			res.sendError(res.SC_BAD_REQUEST, "No Middle Rate specified.");
			return;
		}
		
		BigDecimal buyratea;
		BigDecimal buyrateb;
		BigDecimal sellratea;
		BigDecimal sellrateb;
		BigDecimal middlerate;
		
		try {
			buyratea = new BigDecimal(buyrateastr);
			buyrateb = new BigDecimal(buyratebstr);
			sellratea = new BigDecimal(sellrateastr);
			sellrateb = new BigDecimal(sellratebstr);
			middlerate = new BigDecimal(middleratestr);			
		} catch (NumberFormatException nfe) {
			res.sendError(res.SC_BAD_REQUEST, "rate is not numeric");
			return;
		}
		
                   		
		try {
							
			ServiceCenter sc = new ServiceCenter(branch);
                   		
			Connection conn = sc.getConnection();
		
			EXCHANGE_RATE ex = new EXCHANGE_RATE(isocode, 
							new java.sql.Date(new java.util.Date().getTime()),
							"1", buyratea, buyrateb, middlerate,
							sellratea, sellrateb);
			
			EXCHANGE_RATETable exrt = new EXCHANGE_RATETable(conn);

			exrt.insert(ex);			
			ex.setRATE_FIXING_NUMBER("2");
			exrt.insert(ex);
			ex.setRATE_FIXING_NUMBER("3");
			exrt.insert(ex);
			ex.setRATE_FIXING_NUMBER("4");
			exrt.insert(ex);
			
			conn.commit();
			sc.closeConnection();
			
			out.println("<html><head><title>Insert Exchange Rate OK</title>");
			out.println("</head><body><FONT SIZE=\"+1\"><b>Insert Exchange Rate OK</b></FONT>");
			out.println("<hr>Insert Exchange Rate successful:<br><br>");
			out.println("Branch: "+branch+"<br>IsoCode: "+isocode+"<br></body></html>");
			
			out.close();
		
		} catch (Exception e) {
			res.sendError(res.SC_INTERNAL_SERVER_ERROR, "Exception in Servlet"+e.toString());
			log(e.toString());
		}

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String propsFile = getInitParameter(gtf.common.Constants.P_PROPS_FILE);
		AppProperties.setFileName(propsFile);	
	
	}
}