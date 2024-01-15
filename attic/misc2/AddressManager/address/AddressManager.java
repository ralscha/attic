/*
 * @(#)AddressManager.java	1.1 6/1999
 *
 * Johannes Plachy (JPlachy@qualityservice.com)
 *
 *
 * Erstellt die verschiedenen HTML Seiten und
 * behandlet die SQL spezifischen programmteile
 *
 * Version 1.0
 */

package address;


//import at.jps.tools.html.*;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;



public class AddressManager {
	/**
	 * String Konstanten
	 */

	private final static String AUTOR = "Johannes Plachy";
	private final static String TITLE = "Personal addressbook 1.1";
	private final static String MAIL2ADMIN = "JPlachy@qualityservice.com";
	private final static String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	* Meldungskennungen
	*/

	private static final int ID_NEW = 1;
	private static final int ID_EDIT = 2;
	private static final int ID_DEL = 3;
	private static final int ID_NOPWD = 4;

	/**
	* SQL Befehle die vorbreitet werden um schneller
	* und wiederholt verwendet werden zu koennen
	*/

	private PreparedStatement ivPstmtCount = null;
	private PreparedStatement ivPstmtInsert = null;
	private PreparedStatement ivPstmtUpdate = null;
	private PreparedStatement ivPstmtQuery = null;

	/**
	* SQL Verbindung lokal
	*/

	private Connection ivSQLconnection = null;

	/**
	* URL fue einzubauende Links auf servlet selbst
	*/

	private String ivServletURL = null;

	/**
	* constructor tut zZ nichts
	*/

	public AddressManager() {
	}

	/**
	 * @param ServletURL enthaelt pfad zum servlet selbst
	 * um maschinen und namensunabhängig referenzieren zu koennen.
	 */

	public void setServletURL(String ServletURL) {
		ivServletURL = ServletURL;
	}

	/**
	 * zur ueberpruefung ob die JDBC verbindung unterbrochen wurde
	 *
	 * @return true if still connected
	 *
	 */

	public boolean isConnected() {
		try {
			return ((ivSQLconnection != null) && (!ivSQLconnection.isClosed()));
		} catch (SQLException sqlx) {
			System.err.println("SQLException :"+sqlx);
		}
		return false;
	}

	/**
	 * baut die Verbindung zur SQL Datenbank auf
	 * richtet die wichtigsten SQLBefehle zur spaeteren verwendung her
	 *
	 * @param JDBCurlstr URL um eine Datenbank verbindung aufbauen zu koennen.
	 */

	public boolean init(String url, String user, String password) {
		try {
			ivSQLconnection = DriverManager.getConnection(url, user, password);

			// insert commando
			ivPstmtInsert = ivSQLconnection.prepareStatement("INSERT INTO ADDRESSES (FIRSTNAME,NAME,STREET,CITY,TEL,EMAIL,HOMEPAGEURL,COMMENTS, IDUSER) VALUES (?,?,?,?,?,?,?,?,?)");

			// update commando
			ivPstmtUpdate = ivSQLconnection.prepareStatement("UPDATE ADDRESSES SET FIRSTNAME=?, NAME=?, STREET=?, CITY=?, TEL=?, EMAIL=?, URL= ?, COMMENTS=? WHERE ID=?");

			// count commando
			ivPstmtCount = ivSQLconnection.prepareStatement("SELECT COUNT(*) FROM ADDRESSES WHERE IDUSER=?");

			// query commando
			ivPstmtQuery = ivSQLconnection.prepareStatement("SELECT NAME, FIRSTNAME, STREET, CITY, TEL, EMAIL, URL, COMMENTS, ID FROM ADDRESSES WHERE ID=?");

			return true;

		}
		catch (SQLException sqlx) {
			System.err.println("SQLException :"+sqlx);

		}

		return false;
	}

	/**
	 * baut die Verbindung zur SQL Datenbank ab
	 */
	public void close() {
		if (isConnected()) {
			try {
				ivSQLconnection.close();
			} catch (SQLException sqlx) {
			}
			finally { ivSQLconnection = null;
        			} }
	}

	/**
	 * anzeige des menues
	 *
	 * @param out outputstream fuer HTML output
	 * @param user UserId/Name des aktuellen Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	public void doIndex(PrintWriter out, User user) {
		if (user.getId() > -1) {
			writeHeader(out, "choose Letter", false);
			writeIndex(out, user);
			writeFooter(out);
		} else {
			writeMsg(out, ID_NOPWD);
		}
	}

	/**
	 * Anzeige der Login Maske
	 *
	 * @param out outputstream fuer HTML output
	 * @param count anzahl der offenen verbindungen
	 */

	public void doLoginForm(PrintWriter out, int count) {
		writeHeader(out, TITLE, false);
		writeLogonForm(out, count);
	}

	/**
	 * Anzeige der Login Maske
	 *
	 * @param out outputstream fuer HTML output
	 * @param Username
	 * @param Password zur ueberpruefung
	 */

	public int doLogin(PrintWriter out, String Username, String Password) {
		int iActualUserId = checkuser(Username, Password);

		if (iActualUserId > -1) {
			User tmpUser = new User(iActualUserId, Username);

			writeHeader(out, "choose Letter", false);
			writeIndex(out, tmpUser);
			writeFooter(out);
		} else {
			writeMsg(out, ID_NOPWD);
		}

		return iActualUserId;
	}

	/**
	 * Anzeige der Eingabemaske fuer neue Adresse
	 *
	 * @param out outputstream fuer HTML output
	 * @param user UserId/Name des aktuellen Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	public void doNewAddress(PrintWriter out, User user) {
		writeHeader(out, "new Address", false);
		writeIndex(out, user);
		writeEditForm(out, null);
		writeFooter(out);
	}

	/**
	 * Anzeige der Eingabemaske um eine Adresse
	 * bearbeiten zu koennen
	 * @param out outputstream fuer HTML output
	 * @param user UserId/name des aktuellen
	 * Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	public void doEditAddress(PrintWriter out, int id, User user) {
		Address tempAddress = getAddressFromId(id);

		if (tempAddress != null) {
			writeHeader(out, "edit Address", false);
			writeIndex(out, user);
			writeEditForm(out, tempAddress);
			writeFooter(out);
		} else {
			writeHeader(out, "Record could not be found", true);
			out.println("Maybe the desired record has been deleted in the meantime");
			writeFooter(out);
		}
	}

	/**
	 * Anzeige der Suchmaske
	 *
	 * @param out outputstream fuer HTML output
	 * @param user UserId/name des aktuellen Benutzers
	 */

	public void doSearchAddress(PrintWriter out, User user) {
		writeHeader(out, "Search Addresses", false);
		writeIndex(out, user);
		writeSearchForm(out);
		writeFooter(out);
	}

	/**
	 * Anzeige des Suchergebnisses
	 *
	 * @param out outputstream fuer HTML output
	 * @param user UserId/name des aktuellen Benutzers
	 */

	public void doSearchListAddress(PrintWriter out, User user, String searchString) {
		writeHeader(out, "Searchresults"+((searchString != null) ? "<"+searchString + ">" : ""),
            		false);
		writeIndex(out, user);
		writeSearchList(out, searchString, user.getId());
		writeFooter(out);
	}

	/**
	 * Anzeige aller Adressen eines Registers oder alle
	 *
	 * @param out outputstream fuer HTML output
	 * @param FilterString Filterkriterium
	 * @param user UserId/name des aktuellen Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	public void doListAddress(PrintWriter out, String FilterString, User user) {
		writeHeader(out, "List of Addresses", false);
		writeIndex(out, user);
		writeLetterList(out, FilterString, user.getId());
		writeFooter(out);
	}

	/**
	 * speichert einen geaenderten Adressrecord
	 * oder loescht einen vorhandenen
	 *
	 * @param out outputstream fuer HTML output
	 * @param req wird verwendet um die uebergebenen parameter abzufragen
	 * @param Password zur nochmaligen verifizierung des besitzers
	 * @param id ist der interne zaehler des Datensatzes
	 * @param iActualUserId UserId des aktuellen Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	public void doModifyAddress(PrintWriter out, HttpServletRequest req, String Password,
                            	int id, User user, boolean doDelete) {
		if (checkpwd(Password, user.getId())) {
			// look for fields

			if (doDelete) {
				if (deleteRecord(id)) {
					writeHeader(out, "record deleted", true);
					writeMsg(out, ID_DEL);
					writeFooter(out);
				}
			} else {
				Address a;

				if (id == 0) {
					// new entry
					a = new Address();
				} else {
					a = getAddressFromId(id);
				}

				String tmp = req.getParameter("name");

				if (tmp != null)
					a.setName(tmp);

				tmp = req.getParameter("firstname");
				if (tmp != null)
					a.setFirstName(tmp);

				tmp = req.getParameter("street");
				if (tmp != null)
					a.setStreet(tmp);

				tmp = req.getParameter("city");
				if (tmp != null)
					a.setCity(tmp);

				tmp = req.getParameter("tel");
				if (tmp != null)
					a.setTel(tmp);

				tmp = req.getParameter("email");
				if (tmp != null)
					a.setEmail(tmp);

				tmp = req.getParameter("url");
				if (tmp != null)
					a.setURL(tmp);

				tmp = req.getParameter("comments");
				if (tmp != null)
					a.setComments(tmp);

				boolean ok = false;

				if (id == 0) {
					ok = executeCommand(a, false, user.getId());
				} else {
					ok = executeCommand(a, true, user.getId());
				}

				writeHeader(out, "Edit Address", true);
				writeMsg(out, (id != 0) ? ID_EDIT : ID_NEW);
				writeFooter(out);
			}
		} else {
			writeHeader(out, "Illegal access prohibited", true);
			out.println("Illegal access prohibited");
			writeFooter(out);
		}
	}

	/**
	 * entfernt alle sonderzeichen und whitespaces
	 * aus dem uebergebenen string
	 *
	 * @param txt der zu bearbeiten ist
	 * @return String ohne leerzeichen etc
	 */

	private static String removeWhiteSpaces(String txt) {
		StringBuffer temp = new StringBuffer(txt);

		int len = temp.length();

		for (int i = 0; i < len; i++) {
			if (Character.isLetterOrDigit(temp.charAt(i)) == false) {
				temp.deleteCharAt(i);
				len--;
			}
		}
		return temp.toString();
	}

	/**
	 * fehlerseite anzeigen
	 *
	 * @param out outputstream fuer HTML output
	 */

	public void showErrorPage(PrintWriter out) {
		writeHeader(out, "JDBC Connection error", false);
		out.println("a connection to the Address database could not be openen please contact your serviceprovider");
		writeFooter(out);
	}

	/**
	 * fehlerseite anzeigen
	 *
	 * @param out outputstream fuer HTML output
	 */
	public void showIllegalAccess(PrintWriter out) {
		writeHeader(out, "Illegal access", false);
		out.println("intrusion logged !\n Please login again - your session may be timed out - please login again");
		writeFooter(out);
	}

	/**
	 * fuehrt ein uebergebenes SQL kommando aus
	 *
	 * @param stmt SQL statement
	 * @param address zu verwendender datensatz
	 * @param iActualUserId Userid
	 * @exception SQLException wird durchgereicht
	 */

	private void executeCommand(PreparedStatement stmt, Address address,
                            	int iActualUserId) throws SQLException {
		stmt.clearParameters();

		stmt.setString(1, address.getFirstName());
		stmt.setString(2, address.getName());
		stmt.setString(3, address.getStreet());
		stmt.setString(4, address.getCity());
		stmt.setString(5, address.getTel());
		stmt.setString(6, address.getEmail());
		stmt.setString(7, address.getURL());
		stmt.setString(8, address.getComments());
		stmt.setInt(9, iActualUserId);

		stmt.executeUpdate();
	}

	/**
	 * initialisiert SQL statement mit daten des zu
	 * aendernden Datensatzes und fuehrt SQL Kommando danach aus
	 *
	 * @param address zu verwendender datensatz
	 * @param update aendern oder loschen
	 * @param iActualUserId Userid
	 */

	boolean executeCommand(Address address, boolean update, int iActualUserId) {
		// synchronized (ivPstmtInsert)
		boolean ok = false;

		try {
			if (update) {
				executeCommand(ivPstmtUpdate, address, address.getId());
			} else {
				executeCommand(ivPstmtInsert, address, iActualUserId);
			}
			ok = true;
		} catch (SQLException sqlx) {
			System.err.println("SQLException :"+sqlx);
		}
		return ok;
	}

	/**
	 * ueberprueft username & password auf plausibilitaet
	 * un liefert userid zurueck
	 *
	 * @param UserName
	 * @param PassWord
	 * @return UserID
	 */

	int checkuser(String UserName, String PassWord) {
		if ((UserName == null) || (PassWord == null)) {
			return -1;
		} else {
			UserName = removeWhiteSpaces(UserName);
			PassWord = removeWhiteSpaces(PassWord);

			try {
				Statement stmt = ivSQLconnection.createStatement();

				ResultSet rs =
  				stmt.executeQuery("SELECT USERNAME, PASSWORD, ID FROM ADDRESSUSER WHERE USERNAME='" +
                    				UserName + "' AND PASSWORD='"+PassWord + "'");

				if (rs.next()) {
					return rs.getInt(3);
				} else {
					Statement stmt1 = ivSQLconnection.createStatement();

					ResultSet rs1 =
  					stmt1.executeQuery("SELECT USERNAME FROM ADDRESSUSER WHERE USERNAME='"+
                     					UserName + "'");

					if (rs1.next()) {
						return -1; // username already in use ! but password doenot match
					}

					// neuen eintrag anlegen
					{ String sql =
    					"INSERT INTO ADDRESSUSER (USERNAME,PASSWORD) VALUES ('"+UserName + "','"+
    					PassWord + "')";

  					stmt.execute(sql);
					} return checkuser(UserName, PassWord);
				}
			}
			catch (SQLException sqlx) {
				System.err.println("SQLException in checkusr :"+sqlx);

			}
			return -1;
		}
	}

	/**
	 * ueberprueft ob ein eingegebenes password
	 * zu der aktuellen UserID passt
	 *
	 * @param PassWord
	 * @param iActualUserId des aktuellen Benutzers
	 * @return true if ok
	 */

	boolean checkpwd(String PassWord, int iActualUserId) {
		if (PassWord == null) {
			return false;
		} else {
			try {
				Statement stmt = ivSQLconnection.createStatement();

				ResultSet rs =
  				stmt.executeQuery("SELECT PASSWORD, ID FROM ADDRESSUSER WHERE PASSWORD='"+
                    				PassWord + "' AND ID="+Integer.toString(iActualUserId, 10));

				boolean pwdexists = rs.next();

				stmt.close();

				return pwdexists;
			} catch (SQLException sqlx) {
				System.err.println("SQLException in checkpwd :"+sqlx);

			}
			return false;
		}
	}

	/**
	 * holt die Anzahl der Datensaetze des aktuellen Benutzers
	 * zu der aktuellen UserID passt
	 *
	 * @param iActualUserId des aktuellen Benutzers
	 * @return anzahl der Datensaetze
	 */

	int getAddressCount(int iActualUserId) {
		try {
			ivPstmtCount.clearParameters();

			ivPstmtCount.setInt(1, iActualUserId);

			ivPstmtCount.execute();

			ResultSet rs = ivPstmtCount.getResultSet();

			if (rs.next()) {
				return rs.getInt(1);
			} else
				return 0;
		}
		catch (SQLException sqlx) {
			System.err.println("SQLException in getAddressCount:"+sqlx);
		}

		return -1;
	}

	/**
	 * loescht den Datensatz mit der passenden ID
	 *
	 * @param Id des DS
	 * @return true wenn alles guitgegangen ist
	 */

	boolean deleteRecord(int Id) {
		try {
			Statement stmt = ivSQLconnection.createStatement();

			String sql = "DELETE FROM ADDRESSES WHERE ID="+Integer.toString(Id, 10);

			stmt.execute(sql);

			stmt.close();

			return true;
		} catch (SQLException sx) {
			System.err.println("SQLException in deleteRecord :"+sx);
		}
		return false;
	}

	/**
	 * holt den Datensatz mit der passenden ID
	 *
	 * @param Id des DS
	 * @return true wenn alles guitgegangen ist
	 */

	Address getAddressFromId(int Id) {
		Address a = new Address();

		try {
			ivPstmtQuery.clearParameters();

			ivPstmtQuery.setInt(1, Id);

			ivPstmtQuery.execute();

			ResultSet rs = ivPstmtQuery.getResultSet();

			if (rs.next())// es gibt zumindest ein ergbnis
			{
				String tmp;

				tmp = rs.getString("name");
				if (tmp != null)
					a.setName(tmp);

				tmp = rs.getString("firstname");
				if (tmp != null)
					a.setFirstName(tmp);

				tmp = rs.getString("street");
				if (tmp != null)
					a.setStreet(tmp);

				tmp = rs.getString("city");
				if (tmp != null)
					a.setCity(tmp);

				tmp = rs.getString("tel");
				if (tmp != null)
					a.setTel(tmp);

				tmp = rs.getString("email");
				if (tmp != null)
					a.setEmail(tmp);

				tmp = rs.getString("url");
				if (tmp != null)
					a.setURL(tmp);

				tmp = rs.getString("comments");
				if (tmp != null)
					a.setComments(tmp);

				a.setId(rs.getInt("id"));
			}
		} catch (SQLException sx) {
			System.err.println("SQLException in getAddressFromId :"+sx);
		}

		return a;
	}

	/**
	 * erstellt HTML ausgabe der Adressliste
	 *
	 * @param out outputstream fuer HTML output
	 * @param Filterstring des Registers
	 * @param iActualUserId UserId des aktuellen Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	void writeLetterList(PrintWriter out, String filterString, int iActualUserId) {
		// select all from
		try {
			Statement stmt = ivSQLconnection.createStatement();

			char letter = filterString.charAt(0);

			if (letter == '*') {
				letter = '%';
			}

			String QueryString =
  			"SELECT NAME, FIRSTNAME, STREET, CITY, TEL, EMAIL, HOMEPAGEURL, COMMENTS, ID, IDUSER FROM ADDRESSES WHERE NAME LIKE '"+
  			letter + "%' AND IDUSER=" +Integer.toString(iActualUserId, 10) + " ORDER BY NAME,FIRSTNAME";

			ResultSet rs = stmt.executeQuery(QueryString);

			HtmlResultSet hrs = new AddressHtmlResultSet(rs, ivServletURL);

			out.println(hrs.toString());

			stmt.close();
		} catch (SQLException sx) {
			System.err.println("SQLException in writeLetterList :"+sx);
		}
		catch (Exception x) {
			System.err.println("Exception in writeLetterList :"+x);
		}
	}

	/**
	 * erstellt HTML ausgabe der Adressliste Suchergebnis
	 *
	 * @param out outputstream fuer HTML output
	 * @param Filterstring des Registers
	 * @param iActualUserId UserId des aktuellen Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	void writeSearchList(PrintWriter out, String searchString, int iActualUserId) {
		if ((searchString == null) || (searchString.length() == 0)) {
			out.println(" Nothing to search");
			return;
		}

		try {
			Statement stmt = ivSQLconnection.createStatement();

			String QueryString =
  			"SELECT NAME, FIRSTNAME, STREET, CITY, TEL, EMAIL, HOMEPAGEURL, COMMENTS, ID, IDUSER FROM ADDRESSES WHERE (NAME LIKE '%"+
  			searchString + "%' OR FIRSTNAME LIKE '%"+searchString + "%' OR STREET LIKE '%"+
  			searchString + "%') AND IDUSER="+Integer.toString(iActualUserId, 10) +
  			" ORDER BY NAME,FIRSTNAME";

			ResultSet rs = stmt.executeQuery(QueryString);

			HtmlResultSet hrs = new AddressHtmlResultSet(rs, ivServletURL);

			out.println(hrs.toString());

			stmt.close();
		} catch (SQLException sx) {

			System.err.println("SQLException in writeSearchList :"+sx);
		}
		catch (Exception x) {
			System.err.println("Exception in writeSearchList :"+x);
		}
	}



	/**
	 * erstellt HTML ausgabe des registers
	 *
	 * @param out outputstream fuer HTML output
	 * @param iActualUserId UserId des aktuellen Benutzers fuer die Anzeige der vorhandenen Eintraege
	 */

	void writeIndex(PrintWriter out, User user) {
		int NrOfElements = getAddressCount(user.getId());

		out.println("<div align=\"left\"><table border=\"0\">");
		out.println("<tr><th colspan=\"1\" ID=\"TableHeaderStyle\">&nbsp;&nbsp;Number of Entries in private Addressbook of User '"+
            		user.getUsername() + "': "+ Integer.toString(NrOfElements, 10) + "&nbsp;&nbsp;</th></tr>");
		out.println("</table></div>");
		out.println("<p>");

		out.println("<table border=\"0\"><tr>");

		for (int i = 0; i < 26; i++) {
			out.println("<td><a href=\""+ivServletURL + "?what=list&ix="+ABC.charAt(i) + "\">"+
            			ABC.charAt(i) + "</a></td>");
		}

		out.println("<td>&nbsp;<a href=\""+ivServletURL + "?what=list&ix=*\">ALL</a></td>");
		out.println("<td>&nbsp;&nbsp;&nbsp;<a href=\""+ivServletURL + "?what=new\">New-Entry</a></td>");
		out.println("<td>&nbsp;&nbsp;&nbsp;<a href=\""+ivServletURL + "?what=searchmask\">Search</a></td>");
		out.println("<td>&nbsp;&nbsp;&nbsp;<a href=\""+ivServletURL + "?what=logout\">Logout</a></td>");
		out.println("</tr></table>");
		out.println("</p><br>");
	}

	/**
	 * erstellt HTML Seitenkopf
	 * und bindet stylesheet ein
	 * @param out outputstream fuer HTML output
	 * @param Title Titel
	 * @param autoforward springt zum Index zurueck wenn gewuenscht
	 */

	void writeHeader(PrintWriter out, String Title, boolean autoforward) {
		out.println("<html>");
		out.println("<head>");

		out.println("<META NAME=\"author\" CONTENT=\""+AUTOR + "\">");
		out.println("<META NAME=\"Addressbook\" CONTENT=\""+TITLE + "\">");
		out.println("<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\">");

		if (autoforward) {
			out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"1; URL="+ivServletURL + "?what=index\">");
		}

		out.println("<title>"+Title + "</title>");
		//      out.println("<LINK REL =\"stylesheet\" TYPE=\"text/css\" HREF=\"/address.css\" TITLE=\"Style\">");
		out.println("</head>");
		out.println("<BODY>");
	}

	/**
	 * erstellt HTML Seitenende
	 *
	 * @param out outputstream fuer HTML output
	 */


	void writeFooter(PrintWriter out) {
		out.println("<p><a href=\"mailto:"+MAIL2ADMIN + "\">send feedback</a></p>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * erstellt simplen Messagetext
	 *
	 * @param out outputstream fuer HTML output
	 */

	void writeMsg(PrintWriter out, int iMsg) {
		switch (iMsg) {
			case ID_NEW:
				out.println("<p>Record added successfully</p>");
				break;
			case ID_EDIT:
				out.println("<p>Modification commited successfully</p>");
				break;
			case ID_DEL:
				out.println("<p>Record deleted successfully</p>");
				break;
			case ID_NOPWD:
				out.println("<p>either USERNAME is already used by another person or you mistyped your PASSWORD, please login with USERNAME and PASSWORD</p>");
				break;
		}
	}


	/**
	  * erstellt HTML Seite der Suchmaske um
	  * einen datensatz suchen zu koennen
	  *
	  * @param out outputstream fuer HTML output
	  * @param address die zu bearbeitende Adresse
	  */

	void writeSearchForm(PrintWriter out) {
		// out.println("<font color=\"#FFF000\">");

		out.println("<p>Search for Addresses that contain the following Textstring</p>");

		out.println("<form method=\"POST\" action=\""+ivServletURL + "\">");
		out.println("<div align=\"left\"><table border=\"0\" ID=\"TableEditStyle\">");
		out.println("<tr><th colspan=\"2\" ID=\"TableEditHeader\">Address</th></tr>");
		out.println("<tr>");
		out.println("<td><div align=\"left\">TextString:</td>");
		out.println("<td><input type=\"text\" name=\"searchpattern\" "+ "value=\"\" size=\"30\"></td>");
		out.println("</tr>");
		out.println("</table></div>");
		out.println("<p>&nbsp;</p>");
		out.println("<input type=\"hidden\" name=\"what\" value=\"search\" size=\"10\">");
		out.println("<input type=\"submit\" value=\"Submit\" name=\"Submit\">");

		out.println("</form>");
	}




	/**
	 * erstellt HTML Seite der Eingabemaske um
	 * einen datensatz editieren zu koennen
	 *
	 * @param out outputstream fuer HTML output
	 * @param address die zu bearbeitende Adresse
	 */

	void writeEditForm(PrintWriter out, Address address) {
		boolean bIsNew = (address == null);

		// out.println("<font color=\"#FFF000\">");

		if (bIsNew) {
			out.println("<p>Enter new Address</p>");
		} else {
			out.println("<p>Edit Address</p>");
		}

		// out.println("</font>");

		out.println("<form method=\"POST\" action=\""+ivServletURL + "\">");
		out.println("<div align=\"left\"><table border=\"0\" ID=\"TableEditStyle\">");
		out.println("<tr><th colspan=\"2\" ID=\"TableEditHeader\">Address</th></tr>");
		out.println("<tr>");
		out.println("<td><div align=\"left\">First name:</td>");
		out.println("<td><input type=\"text\" name=\"firstname\" "+
            		(bIsNew ? "":" value=\""+address.getFirstName() + "\" ") + "size=\"20\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Name: </td>");
		out.println("<td><input type=\"text\" name=\"name\" "+
            		(bIsNew ? "":" value=\""+address.getName() + "\" ") + "size=\"40\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Address:</td>");
		out.println("<td><input type=\"text\" name=\"street\" "+
            		(bIsNew ? "":" value=\""+address.getStreet() + "\" ") + "size=\"40\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>City:</td>");
		out.println("<td><input type=\"text\" name=\"city\" "+
            		(bIsNew ? "":" value=\""+address.getCity() + "\" ") + "size=\"20\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Tel:</td>");
		out.println("<td><input type=\"text\" name=\"tel\" "+
            		(bIsNew ? "":" value=\""+address.getTel() + "\" ") + "size=\"20\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Email:</td>");
		out.println("<td><input type=\"text\" name=\"email\" "+
            		(bIsNew ? "":" value=\""+address.getEmail() + "\" ") + "size=\"40\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>URL:</td>");
		out.println("<td><input type=\"text\" name=\"url\" "+
            		(bIsNew ? "":" value=\""+address.getURL() + "\" ") + "size=\"40\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Comments:</td>");
		out.println("<td><textarea rows=\"2\" name=\"comments\" cols=\"40\">"+
            		(bIsNew ? "": address.getComments()) + "</textarea></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Password:</td>");
		out.println("<td><input type=\"password\" name=\"password\" size=\"20\"></td>");
		out.println("</tr>");
		out.println("</table></div>");
		out.println("<p>&nbsp;</p>");
		out.println("<input type=\"hidden\" name=\"what\" value=\"modified\" size=\"10\">");
		out.println("<input type=\"hidden\" name=\"id\" "+
            		(bIsNew ? "value=\"0\"" : "value=\""+address.getId() + "\"") + " size=\"10\">");
		out.println("<input type=\"submit\" value=\"Submit\" name=\"Submit\">");
		out.println("&nbsp;<input type=\"submit\" value=\"delete\" name=\"delete\">");

		out.println("</form>");
	}

	/**
	 * erstellt HTML Seite der Loginmaske
	 *
	 * @param out outputstream fuer HTML output
	 * @param count anzahl der offenen verbindungen
	 */

	void writeLogonForm(PrintWriter out, int count) {
		out.println("<p>Welcome !</p>");
		// out.println("<p>there are currently "+Integer.toString(count,10)+" Users online</p>");
		out.println("<p>Please login to use/create your own personal addressbook</p>");
		out.println("</font>");

		out.println("<form method=\"POST\" action=\""+ivServletURL + "\">");
		out.println("<div ");
		out.println("align=\"left\"><table border=\"0\">");
		out.println("<tr>");
		out.println("<td><div align=\"left\">Username:</td>");
		out.println("<td><input type=\"text\" name=\"username\" size=\"20\"></td>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>Password:</td>");
		out.println("<td><input type=\"password\" name=\"password\" size=\"20\"></td>");
		out.println("</tr>");
		out.println("</table></div>");
		out.println("<p>&nbsp;</p>");
		out.println("<input type=\"hidden\" name=\"what\" value=\"login\" size=\"10\">");
		out.println("<input type=\"submit\" value=\"Submit\" name=\"Submit\">");
		out.println("</form><br>");

	}

	//---------------------------------------------------

}
