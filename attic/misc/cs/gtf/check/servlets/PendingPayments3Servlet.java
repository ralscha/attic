
package gtf.check.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import common.util.*;
import gtf.common.*;
import ViolinStrings.Strings;

public class PendingPayments3Servlet extends HttpServlet {


	private final static String selectSQLString = "SELECT BRANCH, BRANCH_GROUP, GTFLC.DOSSIER.DOSSIER_NUMBER "+
		"FROM (GTFLC.DOSSIER LEFT JOIN GTFLC.SETTLEMENT ON GTFLC.DOSSIER.OID = GTFLC.SETTLEMENT.DOSSIER_OID) LEFT JOIN GTFLC.AUDIT_RECORD ON GTFLC.SETTLEMENT.OID = GTFLC.AUDIT_RECORD.DOSSIER_ITEM_OID "+
		"WHERE (((GTFLC.SETTLEMENT.PROVISIONAL)=0) AND ((GTFLC.SETTLEMENT.APPROVAL_STATUS)<>'F')) "+
		"GROUP BY BRANCH, BRANCH_GROUP, GTFLC.DOSSIER.DOSSIER_NUMBER "+
		"HAVING ( timestampdiff(16, CAST (CURRENT TIMESTAMP-Max(RECORDING_TS) AS CHAR(22)) ) >3) "+
		"ORDER BY GTFLC.DOSSIER.DOSSIER_NUMBER";


	public void doPost(HttpServletRequest req,
                   	HttpServletResponse res) throws ServletException, IOException {


		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		try {

			printHead(out);

			String branch = null;

			Enumeration values = req.getParameterNames();
			while (values.hasMoreElements()) {
				String name = (String) values.nextElement();
				String value = req.getParameterValues(name)[0];

				if ("Branch".equals(name.trim())) {
					branch = value;
				}
			}

			ServiceCenter sc = new ServiceCenter(branch);


			out.print("<p>");
			out.print("<i>");
			out.print(sc.getName());
			out.println("</i>");
			out.flush();

			Connection conn = sc.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQLString);

			if (rs.next()) {
				out.println("<table border=\"1\">");
				out.println("<tr>");
				out.println("<th>Dossier</th>");
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
			sc.closeConnection();


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
		out.print(Strings.rightJustify(String.valueOf(dossierNo), 7, '0'));
		out.println("</td>");
		out.println("</tr>");
	}
	private void printHead(PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Pending LC Payments (more than 3 days)</title>");
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