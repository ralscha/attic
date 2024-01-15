

package ch.sr.nerd;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginHandler extends HttpServlet {
	public void doPost(HttpServletRequest req,
                   	HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		// Get the user's name and password
		String passwd = req.getParameter("passwd");

		// Check the name and password for validity
		if (!allowUser(passwd)) {
			out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
			out.println("<BODY>Your login and password are  invalid.<BR>");
			out.println("You may want to <A HREF=/login.html>  try again</A>");
			out.println("</BODY></HTML>");
		} else { // Valid login. Make a note in the session object.


			HttpSession session = req.getSession(true);
			session.setAttribute("logon.isDone", "OK");
			// just a marker object

			// Try redirecting the client to the page he first tried
			// to access
			try {
				String target = (String) session.getAttribute("login.target");
				log(target);
				if (target != null)
					res.sendRedirect(target);
				return;
			} catch (Exception ignored) {
				// Couldn't redirect to the target. Redirect to the
				// site's home page.
				res.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":" +
                 				req.getServerPort());
			}
		}
	}

	protected boolean allowUser(String passwd) {
		return ("idiot".equals(passwd));
	}
}