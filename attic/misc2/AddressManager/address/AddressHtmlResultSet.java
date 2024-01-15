
package address;

import java.sql.*;


/**
 * AddressHtmlResultSet erweitert HtmlResultSet
 *
 *
 * @author Johannes Plachy
 * @version 1.00, 15/06/99
 */


public class AddressHtmlResultSet extends HtmlResultSet {

	private String ivServletURL;

	/**
	* Namen Email und URL der Addresse werden
	* als Hyperlink zurueckgeliefert
	*
	* @return String
	* @param Resultset
	* @param ServletURL fuer spaetere Links
	*/
	public AddressHtmlResultSet(ResultSet rs, String ServletURL) {
		super(rs);
		ivServletURL = ServletURL;
	}

	/**
	 * Namen Email und URL der Addresse werden
	 * als Hyperlink zurueckgeliefert
	 *
	 * @return String
	 * @param ColumnTitle zur Info
	 * @param CellText
	 * @param Resultset
	 * @param nrCols
	 */

	public String getHTMLStringForCell(String columnTitle, String cellText, ResultSet rs,
                                   	int nrCols) {
		try {
			if (columnTitle.equals("NAME")) {
				return "<a href=\""+ivServletURL + "?what=edit&id="+
       				rs.getObject(nrCols - 1) + "\">"+cellText + "</a></td>";
			} else if (columnTitle.equals("EMAIL")) {
				return "<a href=\"mailto:"+cellText + "\">"+cellText + "</a>";
			} else if (columnTitle.equals("URL")) {
				return "<a href=\""+cellText + "\">"+cellText + "</a>";
			}

			return cellText;
		} catch (SQLException e) {
			return "&nbsp;";
		}
	}

	/**
	 * letzten 2 spalten sind nur IDs
	 * also nicht zur Anzeige gedacht
	 *
	 * @return columns to hide
	 */
	public int getlastColumnsHiddenCount() {
		return 2;
	}

}
