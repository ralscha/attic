
package gtf.check.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import common.util.*;
import gtf.common.*;
import ViolinStrings.Strings;

public class PendingPaymentsServlet extends HttpServlet {


	private final static String selectSQLString = "SELECT BRANCH,BRANCH_GROUP,DOSSIER_NUMBER, GTFLC.SETTLEMENT.SETTLE_DATE "+
														  "FROM ((GTFLC.DOSSIER LEFT JOIN GTFLC.SETTLEMENT ON GTFLC.DOSSIER.OID = "+
														  "GTFLC.SETTLEMENT.DOSSIER_OID) LEFT JOIN GTFLC.SETTLEMENT_BALANCE ON GTFLC.SETTLEMENT.OID "+
														  "= GTFLC.SETTLEMENT_BALANCE.SETTLEMENT_OID) LEFT JOIN GTFLC.PAYMENT ON "+
														  "GTFLC.SETTLEMENT_BALANCE.OID = GTFLC.PAYMENT.SETTLE_BAL_OID "+
														  "WHERE (((GTFLC.PAYMENT.AMOUNT) Is Not Null) AND ((GTFLC.PAYMENT.EFFECTED) Is Null "+
														  "Or (GTFLC.PAYMENT.EFFECTED)=0) AND ((GTFLC.SETTLEMENT.APPROVAL_STATUS)='F') AND ((GTFLC.SETTLEMENT.PROVISIONAL)=0) and  ((GTFLC.PAYMENT.VALUE_DATE)>'1998-02-01')) "+
														  "group by BRANCH,BRANCH_GROUP,DOSSIER_NUMBER,GTFLC.SETTLEMENT.SETTLE_DATE "+
														  "ORDER BY BRANCH,BRANCH_GROUP,DOSSIER_NUMBER,GTFLC.SETTLEMENT.SETTLE_DATE";


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
		out.println("<title>Pending LC Payments</title>");
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