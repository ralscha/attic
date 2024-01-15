
package address;


import java.sql.*;


public class HtmlResultSet {

	private ResultSet ivRs;

	public HtmlResultSet(ResultSet rs) {
		ivRs = rs;
	}

	public String toString() { 

		StringBuffer out = new StringBuffer();

		try {
			boolean isEmpty = false;

			try {
				if (!ivRs.isBeforeFirst()) {
					out.append("no entries found \n");
					isEmpty = true;
				}
			} catch (AbstractMethodError amex) {
				// Funktion ist leider nicht implementiert (JDBC2)
				// macht nix ...
			}

			if (!isEmpty) {
				ResultSetMetaData rsmd = ivRs.getMetaData();

				int totalcols = rsmd.getColumnCount();
				int numcols = totalcols - getlastColumnsHiddenCount();

				// Start a table to display the result set
				out.append("<table ID=\"TableStyle\">\n");

				// Title the table with the result set's column labels

				out.append("<TR >");
				for (int i = 1; i <= numcols; i++) {
					out.append("<TH ID=\"TableHeaderStyle\">&nbsp;"+
           					rsmd.getColumnLabel(i) + "&nbsp;</TH>");
				}
				out.append("</TR>\n");

				while (ivRs.next()) {
					out.append("<TR>"); // start a new row
					for (int i = 1; i <= numcols; i++) {
						out.append("<TD ID=\"TableData\">"); // start a new data element

						Object obj = ivRs.getObject(i);

						if (obj != null) {
							String CellText = getHTMLStringForCell(rsmd.getColumnLabel(i),
                                       							obj.toString(), ivRs, totalcols);

							if ((CellText != null) && (CellText.length() > 0)) {
								out.append(CellText);
							} else
								out.append("&nbsp;");
						} else {
							out.append("&nbsp;");
						}
						out.append("</TD>\n");
					}
					out.append("</TR>\n");
				}

				// End the table
				out.append("</TABLE>\n");
			}
		}
		catch (SQLException e) {
			out.append("</TABLE><H1>ERROR:</H1> " + e.getMessage() + "\n");
		}

		return out.toString();
	}

	/**
	 * Diese methode stellt eine moeglichkeit dar
	 * den Text der in einer Zelle erscheinen soll
	 * zu modifizieren
	 * @return String
	 * @param ColumnTitle zur Info
	 * @param CellText
	 * @param Resultset
	 * @param nrCols
	 */

	public String getHTMLStringForCell(String ColumnTitle, String CellText, ResultSet rs,
                                   	int nrCols) {
		return CellText;
	}

	/**
	 * dabei wird angenommern das zu verbergende Spalten die letzten
	 * sind die im ResultSetMetaData stehen.
	 * die anzahl der von hinten wegzulassender spalten kann hier angegeben werden
	 * @return anzahl der spalten
	 */

	public int getlastColumnsHiddenCount() {
		return 0;
	}
}
