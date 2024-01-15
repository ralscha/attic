/*
 * @(#)AddressServlet.java	1.32 99/08/21
 *
 * Johannes Plachy (JPlachy@qualityservice.com)
 *
 *
 * Version 1.1
 */

package address;


import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * AddressServlet erweitert HttpServlet
 *
 *
 * @author Johannes Plachy
 * @version 1.00, 15/06/99
 */

public class AddressServlet extends HttpServlet {
	/**
	  * alle SQL/HTML specifischen Aufgaben werden an auAddressManager delegiert
	  */

	private AddressManager ivAddressManager = null;
	private String dburl = null;
	private String user = null;
	private String password = null;

	/**
	* liefert Information ueber Servlet selbst zurueck
	*
	* @return Beschreibung
	*/

	public String getServletInfo() {
		return "Adressdatenbank v1.1";
	}


	/**
	 * Parameterauswertung fuer Drivename / URL / Database / UserInfo
	 *
	 * @param ServletConfig config Parameters for initialisation
	 *
	 * @exception ServletException if init fails
	 */

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {

			String driver = config.getInitParameter("jdbc.driver");
			dburl = config.getInitParameter("jdbc.url");
			user = config.getInitParameter("user");
			password = config.getInitParameter("password");

		System.out.println(driver);
		System.out.println(dburl);
			
			Class.forName(driver).newInstance();

			ivAddressManager = new AddressManager();
			ivAddressManager.init(dburl, user, password);
		}
		catch (Exception x) {
			System.err.println("Exception :"+x);
		}
	}

	/**
	 * servlet wird aus dem speicher genommen.
	 * Jetzt ist der Zeitpunkt gekommen etwaige verbindungen abzubauen
	 */

	private boolean ivShuttingDown;
	private int ivServiceCounter = 0;
	private Object ivCounterMutex = new Object();

	public void destroy() {
		super.destroy();

		synchronized (ivCounterMutex) {
			/* Check to see whether there are still service methods running,
			  * and if there are, tell them to stop. */

			if (numRequests() > 0) {
				setShuttingDown(true);
			}

			/* Wait for the all of the service methods to stop.  */
			while (numRequests() > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}

		// JDBC verbindung abbauen
		ivAddressManager.close();

	}

	//Access methods for serviceCounter
	private void enteringServiceMethod() {
		synchronized (ivCounterMutex) {
			ivServiceCounter++;
		}
	}

	private void leavingServiceMethod() {
		synchronized (ivCounterMutex) {
			ivServiceCounter--;

			if (ivServiceCounter == 0 && isShuttingDown())
				notifyAll();
		}
	}

	private int numRequests() {
		synchronized (ivCounterMutex) {
			return ivServiceCounter;
		}
	}

	//Access methods for shuttingDown
	protected void setShuttingDown(boolean flag) {
		ivShuttingDown = flag;
	}

	protected boolean isShuttingDown() {
		return ivShuttingDown;
	}


	/**
	 * die methode service wird nur ueberschrieben um
	 * darueber buchzufuehren wann und ob Client abfragen
	 * gerade behandlet werden.
	 *
	 */

	protected void service(HttpServletRequest req,
                       	HttpServletResponse resp) throws ServletException, IOException {
		// increment pending-request-count
		enteringServiceMethod();

		try {
			super.service(req, resp);
		}
		finally { // deccrement pending-request-count
  		leavingServiceMethod();
		} }



	/**
	 * der Einfachheit halber werden beide requests (HTTP GET & POST) in doGet bearbeitet
	 *
	 *
	 * @param req anfrage
	 * @param res antwort
	 *
	 * @exception ServletException, IOException
	 */

	public void doPost(HttpServletRequest req,
                   	HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	/**
	 * bearbeitet request die an den Server gerichtet werden, und antwortet mit verschiedenen Seiten
	 *
	 *
	 * @param req anfrage
	 * @param res antwort
	 *
	 * @exception ServletException, IOException
	 */

	public void doGet(HttpServletRequest req,
                  	HttpServletResponse res) throws ServletException, IOException {

		// prinzipiell html
		res.setContentType("text/html");

		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession(true);

		// trennen unbenutzter verbindungen oder
		// absichtliches ausloggen
		if (!session.isNew()) {
			String tmpCommand = req.getParameter("what");

			if (sessionShouldBeInvalidated(session) || (tmpCommand != null) &&
    				(tmpCommand.equals("logout"))) {
				System.err.println(" Session will be terminated: "+session.toString());

				session.invalidate();
				session = req.getSession(true);
			}
		}

		// check for JDBC timeout
		if (!ivAddressManager.isConnected()) {
			if (!ivAddressManager.init(dburl, user, password)) {
				ivAddressManager.showErrorPage(out);
			}
		}

		if (ivAddressManager.isConnected()) {
			// retrieve command and UserId for next step

			String command = req.getParameter("what");

			// get session userid
			User user = null;

			try {
				user = (User) session.getValue("user");
			} catch (IllegalStateException isx) {
				// !!!!!!!
			}

			if ((command != null) && (command.equals("login"))) {
				int iUserId = ivAddressManager.doLogin(out, req.getParameter("username"),
                                       				req.getParameter("password"));

				// new user arrived
				if (iUserId > -1) {
					// wichtig !
					// speichern der UserId im session context
					session.putValue("user", new User(iUserId, req.getParameter("username")));
				}
			} else if ((command == null) || (user == null)) {
				// AddressManager mit Serverinfo initialisieren
				ivAddressManager.setServletURL(getServletURL(req));

				ivAddressManager.doLoginForm(out, getSessionCount(session));
			} else // buchstabe ausgesucht

				if (command.equals("index")) {
					ivAddressManager.doIndex(out, user);
				} else // buchstabe ausgesucht
					if (command.equals("new")) {
						ivAddressManager.doNewAddress(out, user);
					} else if (command.equals("edit")) {
						// holen der adressID zum editieren
						int id = Integer.parseInt(req.getParameter("id"));

						ivAddressManager.doEditAddress(out, id, user);
					} else if (command.equals("list")) {
						String FilterString = req.getParameter("ix");

						ivAddressManager.doListAddress(out, FilterString, user);
					} else if (command.equals("modified")) {
						String Password = req.getParameter("password");
						int id = Integer.parseInt(req.getParameter("id"));
						String del = req.getParameter("delete");
						boolean doDelete = ((del != null) && (del.equals("delete")));

						ivAddressManager.doModifyAddress(out, req, Password, id, user,
                                 						doDelete);
					} else // suchmaske anzeigen
						if (command.equals("searchmask")) {
							ivAddressManager.doSearchAddress(out, user);
						} else // suchergebnisse anzeigen
							if (command.equals("search")) {
								String searchString = req.getParameter("searchpattern");

								ivAddressManager.doSearchListAddress(out, user, searchString);
							}
		} else {
			// no JDBC connection could be opened
			ivAddressManager.showErrorPage(out);
		}
	}

	/**
	 * baut eine URL aus verfuegbarer Serverinfo zusammen, um sich selbst (servlet)
	 * server und namensunabhängig referenzieren zu koennen.
	 *
	 * @param req
	 * @return kompletten URL String
	 */

	private String getServletURL(HttpServletRequest req) {
		
      String servletURL = "http://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+req.getServletPath();
      
      	return servletURL;
		
	}

	/**
	 * ueberprueft die inaktive zeit der aktuellen session um sie moeglicherweise zu schliessen
	 */

	boolean sessionShouldBeInvalidated(HttpSession session) {
		java.util.Date dayAgo =
  		new java.util.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
		java.util.Date hourAgo =
  		new java.util.Date(System.currentTimeMillis() - 60 * 60 * 1000);
		java.util.Date created = new java.util.Date(session.getCreationTime());
		java.util.Date accessed = new java.util.Date(session.getLastAccessedTime());

		if (created.before(dayAgo) || accessed.before(hourAgo)) {
			return true;
		} else
			return false;
	}


	/**
	 * liefert die anzahl der offenen verbindungen zurueck
	 */

	int getSessionCount(HttpSession session) {
		HttpSessionContext context = session.getSessionContext();

		Enumeration enumeration = context.getIds();

		int iCount = 0;

		while (enumeration.hasMoreElements()) {
			iCount++;
			enumeration.nextElement();
		}
		return iCount;
	}
}












