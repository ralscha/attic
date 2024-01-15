package ch.ess.calendar.tools;


import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public final class ReminderServlet extends HttpServlet {

  private ReminderRunner reminderRunner = null;

	public void destroy() {
    if (reminderRunner != null)
      reminderRunner.stop();
	}


	public void init() throws ServletException {

  	int waittime;
	  String smtp;
	  String sender;

		//set default locale		
		Locale.setDefault(Locale.UK);

  	if (ch.ess.calendar.util.CheckLicense.isDemo()) 
      return;

		String value = getServletConfig().getInitParameter("waittime");
		try {
			waittime = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			waittime = 60;
		}

    smtp = getServletConfig().getInitParameter("smtp");
    sender = getServletConfig().getInitParameter("sender");

    reminderRunner = new ReminderRunner(smtp, sender, waittime);

	}


	public void doGet(HttpServletRequest request,
                  	HttpServletResponse response) throws java.io.IOException, ServletException {
		response.sendError(HttpServletResponse.SC_NO_CONTENT);
	}

}
