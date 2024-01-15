package ch.ess.calendar.tools;


import java.util.Locale;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ReminderServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  
  private ReminderRunner reminderRunner = null;

	public void destroy() {
    if (reminderRunner != null)
      reminderRunner.stop();
	}


	public void init() {

	  try {
	    Class.forName("com.codestudio.sql.PoolMan").newInstance();
	  } catch (Exception ex) {
	    System.out.println("Could Not Find the PoolMan Driver. " +
		         "Is PoolMan.jar in your CLASSPATH?");
	    System.exit(0);
	  }

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

    ch.ess.calendar.AppointmentRequest.setSmtp(smtp);
    ch.ess.calendar.AppointmentRequest.setSender(sender);

    reminderRunner = new ReminderRunner(smtp, sender, waittime);
    

	}


	public void doGet(HttpServletRequest request,
                  	HttpServletResponse response) throws java.io.IOException {
		response.sendError(HttpServletResponse.SC_NO_CONTENT);
	}

}
