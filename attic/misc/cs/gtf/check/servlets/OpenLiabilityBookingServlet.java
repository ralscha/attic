package gtf.check.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import common.util.*;
import gtf.common.*;
import ViolinStrings.*;

public class OpenLiabilityBookingServlet extends HttpServlet {

	
	private final static String selectLCSQLString = "SELECT DISTINCT DOSSIER_NUMBER FROM GTFLC.DOSSIER WHERE OID IN "+
	                                              "(SELECT DOSSIER_OID FROM GTFLC.LIABILITY_BOOKING WHERE IS_EFFECTED = 0)";

	private final static String selectDCSQLString = "SELECT DISTINCT DOSSIER_NUMBER FROM GTFLC.DC_DOSSIER WHERE OID IN "+
	                                              "(SELECT DOSSIER_OID FROM GTFLC.DC_LIABILITY_BOOK WHERE IS_EFFECTED = 0)";

	private final static String selectRCSQLString = "SELECT DISTINCT DOSSIER_NUMBER FROM GTFLC.RC_DOSSIER WHERE OID IN "+
	                                              "(SELECT DOSSIER_OID FROM GTFLC.RC_LIABILITY_BOOK WHERE IS_EFFECTED = 0)";
	
	private final static String selectLOISQLString = "SELECT DISTINCT DOSSIER_NUMBER FROM GTFLC.LOI_DOSSIER WHERE OID IN "+
	                                              "(SELECT DOSSIER_OID FROM GTFLC.LOI_LIABILITY_BOOK WHERE IS_EFFECTED = 0)";

	
	
	public void doPost(HttpServletRequest req,
                   	HttpServletResponse res) throws ServletException, IOException {
                   		

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		String branch = null;		
		String type = null;
                   		
		Enumeration values = req.getParameterNames();
		while (values.hasMoreElements()) {
			String name = (String) values.nextElement();
			String value = req.getParameterValues(name)[0];
			
			if ("Branch".equals(name.trim())) {
				branch = value;
			} else if ("TYPE".equalsIgnoreCase(name.trim())) {
				type = value;
			}
		}

                   		
		try {
   			
			printHead(out);
			
			ServiceCenter sc = new ServiceCenter(branch);
                   		
        	out.print("<p>");
			out.print("<i>");
			out.print(sc.getName());
			out.println("</i>");
			out.flush();
			
			Connection conn = sc.getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs = null;
			if (type.equalsIgnoreCase("LC")) 
				rs = stmt.executeQuery(selectLCSQLString);
			else if (type.equalsIgnoreCase("DC"))
				rs = stmt.executeQuery(selectDCSQLString);
			else if (type.equalsIgnoreCase("RC"))
				rs = stmt.executeQuery(selectRCSQLString);
			else if (type.equalsIgnoreCase("LOI"))
				rs = stmt.executeQuery(selectLOISQLString);
			
			if (rs.next()) {
				out.println("<table border=\"1\">");
				out.println("<tr>");	
				out.println("<th>"+type+" Dossier</th>");
				out.println("</tr>");
								
				printRow(rs, out);
				while (rs.next()) {	
					printRow(rs, out);
				}

				out.println("</table>");
			}
			printTail(out);
			
			rs.close();
			stmt.close();
			sc.closeConnection();
			
			out.close();
		
		} catch (Exception e) {
			log(e.toString());
		}

	}

	private void printRow(ResultSet rs, PrintWriter out) throws SQLException {
		int dossierNo = rs.getInt(1);
		
		out.println("<tr>");					
		out.print("<td>");
		out.print(Strings.rightJustify(String.valueOf(dossierNo), 7, ' ')	);					
		out.println("</td>");					
		out.println("</tr>");	
	}	
	
	private void printHead(PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Open LC Bookings</title>");
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