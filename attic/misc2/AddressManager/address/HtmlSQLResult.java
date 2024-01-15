/*
 * @(#)HtmlSQLResult.java	1.1 6/1999
 *
 * Johannes Plachy (JPlachy@qualityservice.com)
 *
 *
 * generiert HTML Tabelle
 * aus generischem Resultset
 *
 * Version 1.0
 */

package address;


import java.sql.*;

public class HtmlSQLResult {
	private String ivSql;
	private Connection ivCon;

	public HtmlSQLResult(String sql, Connection con) {
		ivSql = sql;
		ivCon = con;
	}

	/**
	 * erzeugt aus einem Resultset
	 * eine HTML Tabelle in einem String
	 *
	 * @return HTML tabelle
	 */

	public String toString() { // can be called at most once
		StringBuffer out = new StringBuffer();

		try {
			Statement stmt = ivCon.createStatement();

			// if there is a resultset to work with ...
			if (stmt.execute(ivSql)) {
				HtmlResultSet hrst = new HtmlResultSet(stmt.getResultSet());

				out.append(hrst.toString());
			} else {
				// There's a count to shown
				out.append("<B>Records Affected:</B> " + stmt.getUpdateCount());
			}
		}
		catch (SQLException e) {
			out.append("</TABLE><H1>ERROR:</H1> " + e.getMessage());
			while ((e = e.getNextException()) != null) {
				out.append("\n"+e.getMessage());
			}
		}
		return out.toString();
	}
}
