
package gtf.check.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import common.util.*;
import gtf.common.*;
import ViolinStrings.Strings;

public class PendingDCPaymentsServlet extends HttpServlet {


	private final static String selectSQLString = "SELECT BRANCH,BRANCH_GROUP,DOSSIER_NUMBER, GTFLC.DC_SETTLEMENT.SETTLE_DATE " +
														  "FROM (((GTFLC.DC_DOSSIER LEFT JOIN GTFLC.DC_SETTLEMENT ON GTFLC.DC_DOSSIER.OID = GTFLC.DC_SETTLEMENT.DOSSIER_OID) LEFT JOIN GTFLC.DC_SETTLE_BALANCE ON GTFLC.DC_SETTLEMENT.OID = GTFLC.DC_SETTLE_BALANCE.SETTLEMENT_OID) LEFT JOIN GTFLC.DC_PAYMENT ON GTFLC.DC_SETTLE_BALANCE.OID = GTFLC.DC_PAYMENT.SETTLE_BAL_OID) LEFT JOIN GTFLC.DC_AUDIT_RECORD ON GTFLC.DC_SETTLEMENT.UOW_OID = GTFLC.DC_AUDIT_RECORD.DOSSIER_ITEM_OID " +
														  "WHERE (((GTFLC.DC_PAYMENT.EFFECTED) Is Null) AND ((GTFLC.DC_AUDIT_RECORD.ROLE_CODE)='2A' Or (GTFLC.DC_AUDIT_RECORD.ROLE_CODE)='OA') AND ((GTFLC.DC_PAYMENT.AMOUNT) Is Not Null)) " +
														  "GROUP BY BRANCH,BRANCH_GROUP,DOSSIER_NUMBER, GTFLC.DC_SETTLEMENT.SETTLE_DATE " +
														  "ORDER BY BRANCH,BRANCH_GROUP,DOSSIER_NUMBER, GTFLC.DC_SETTLEMENT.SETTLE_DATE";

	public void doPost(HttpServletRequest req,
                   	HttpServletResponse res) throws ServletException, IOException {
                   		

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
                   		
		try {
   			
			printHead(out);
			
			ServiceCenters scs = new ServiceCenters();
			ServiceCenter[] sc = scs.getAllServiceCenters();
			
			for (int x = 0; x < sc.length; x++) {
				
				if (sc[x].getShortName().equalsIgnoreCase("CSBS")) continue;
				
				out.print("<p>");
				out.print("<i>");
				out.print(sc[x].getName());
				out.println("</i>");
				out.flush();
				
				Connection conn = sc[x].getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(selectSQLString);
				
				if (rs.next()) {
					out.println("<table border=\"1\">");
					out.println("<tr>");	
					out.println("<th>Dossier</th>");
					out.println("<th>Settle Date</th>");
					out.println("</tr>");
					printRow(rs, out);						
					
					while (rs.next()) {											
						printRow(rs, out);	
					}
					out.println("</table>");
				}
				out.flush();
				
				rs.close();
				stmt.close();
				sc[x].closeConnection();
			}
			
			printTail(out);
		} catch (Exception e) {
			log(e.toString());
		}
		
		out.close();
	}

	private void printRow(ResultSet rs, PrintWriter out) throws SQLException {
		String branch = rs.getString(1);
		String branchgroup = rs.getString(2);
		int dossierNo = rs.getInt(3);
		
		out.println("<tr>");					
		out.print("<td>");
		out.print(branch.trim());
		out.print(" ");
		out.print(branchgroup.trim());
		out.print("-");
		out.print(Strings.rightJustify(String.valueOf(dossierNo), 7, '0')	);					
		out.println("</td>");					
		out.println("<td>"+rs.getString(4)+"</td>");
		out.println("</tr>");	
	}
	
	private void printHead(PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Pending DC Payments</title>");
		out.println("<link rel=stylesheet type=\"text/css\" href=\"/gtf/crapa.css\">");
		out.println("</head>");
		out.println("<body>");
	}
	
	private void printTail(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		String propsFile = getInitParameter(gtf.common.Constants.P_PROPS_FILE);
		AppProperties.setFileName(propsFile);	
	
	}
}