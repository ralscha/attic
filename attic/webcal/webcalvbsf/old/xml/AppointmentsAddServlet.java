
package ch.ess.calendar.xml;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import ch.ess.calendar.session.*;

public class AppointmentsAddServlet extends HttpServlet {

	private File tmpDir = null;

	public void doPost(HttpServletRequest req, 
					   HttpServletResponse res) throws ServletException, IOException {
	
		res.setContentType("text/plain");
		PrintWriter out = res.getWriter();

		// Get Authorization header
		String auth = req.getHeader("Authorization");

		try {
			// Do we allow that user?
			if (!allowUser(auth)) {
				// Not allowed, so report he's unauthorized
				res.setHeader("WWW-Authenticate", "BASIC realm=\"users\"");
				res.sendError(res.SC_UNAUTHORIZED);
			} else {
				InputStream requestIS = req.getInputStream();
				
				File fout = File.createTempFile("appointment", ".xml", tmpDir);
				FileOutputStream fos = new FileOutputStream(fout);
				
				ch.ess.calendar.util.StreamCopier.copy(requestIS, fos);
						
				Lax lax = new Lax();

				AppointmentsHandler appHandler = new AppointmentsHandler();
				lax.addHandler(appHandler);

				lax.parseDocument(true, lax, fout);
				fos.close();
				fout.delete();		
			}
		} catch (Exception e) {
			out.println(e);
			res.sendError(res.SC_INTERNAL_SERVER_ERROR);			
		}	
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String dirStr = getInitParameter("tmpDir");	
		tmpDir = new File(dirStr);
	}
	
	
	// This method checks the user information sent in the Authorization
	// header against the database of users maintained in the users Hashtable.
	protected boolean allowUser(String auth) throws Exception {
		if (auth == null) return false;  // no auth

		if (!auth.toUpperCase().startsWith("BASIC ")) 
			return false;  // we only do BASIC

		// Get encoded user and password, comes after "BASIC "
		String userpassEncoded = auth.substring(6);

		// Decode it, using any base 64 decoder
		ch.ess.calendar.util.Base64 dec = new ch.ess.calendar.util.Base64();

		String userpassDecoded = new String(dec.decode(userpassEncoded.toCharArray()));

		int pos = userpassDecoded.indexOf(":");
		String user = userpassDecoded.substring(0, pos);
		String pass = userpassDecoded.substring(pos+1);
		
		
		Login login = new Login();
		login.setUserid(user);
		login.setPassword(pass);
		
		if (login.isLogonOK()) 
			return true;
		else
			return false;
	}
	
}

    		
    		