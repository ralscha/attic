
package ch.ess.calendar.xml;

import org.xml.sax.*;
import ch.ess.calendar.db.*;
import java.sql.*;
import java.util.*;

public class AppointmentsHandler {

	private String userid = null;
	private boolean allday = false;
	private boolean priv = false;
	private String start = null; /* dd.MM.yyyy HH:mm:ss */
	private String end = null;
	private String subject = null;
	private String body = null;
	private String location = null;
	private String importance = null;
	private String categories = null;
	private String reminderminutesbefore = null;
	private String reminderemail = null;

	public void startoutlook(AttributeList list) {
		userid = list.getValue("userid");
	}	

	public void endappointment() {
    try {		
			AppointmentsTable appTable = new AppointmentsTable();
			
			//next id 
			int nextid = appTable.getMaxid();
			String queryid = "select max(appointmentid) as maxid from Appointments";

			Appointments app = new Appointments();
			app.setAppointmentid(nextid);
			app.setUserid(userid);
			app.setAlldayevent(allday);
			app.setPrivate(priv);			
			app.setSubject(subject);
			
			if (body != null)
				app.setBody(body);
			
			if (importance != null)
				app.setImportance(Integer.parseInt(importance));
				
			if (location != null)
				app.setLocation(location);
			
			//TODO: Categories
			
			if (start.length() == 19) { // Date + Time 
				app.setStartdate(start.substring(0, 10), start.substring(11, 16));
			} else if (start.length() == 10) { // End
			   app.setStartdate(start.substring(0,10));
			} else {
				System.err.println("Error in Startdate");
				return;
			}
			
			if (end.length() == 19) { // Date + Time 
				app.setEnddate(end.substring(0, 10), end.substring(11, 16));
			} else if (end.length() == 10) { // End
				app.setEnddate(end.substring(0, 10));
			} else {
				System.err.println("Error in Enddate");
				return;
			}

			
			appTable.insert(app);
		} catch (Exception e) {
			System.err.println(e);     
    }
	}

	public void startappointment(AttributeList list) {
		String allDayStr = list.getValue("allday");
		if ((allDayStr != null) && (allDayStr.equals("true"))) {
			allday = true;
		}

		String privStr = list.getValue("private");
		if ((privStr != null) && (privStr.equals("true"))) {
			priv = true;
		}
	}

	public void textOfstart(String str) {
		start = str;
	}

	public void textOfend(String str) {
		end = str;
	}

	public void textOfsubject(String str) {
		subject = str;
	}

	public void textOfbody(String str) {
		body = str;
	}
	
	public void textOflocation(String str) {
		location = str;
	}

	public void textOfimportance(String str) {
		importance = str;
	}

	public void textOfcategories(String str) {
		categories = str;
	}

	public void textOfminutesbefore(String str) {
		reminderminutesbefore = str;
	}

	public void textOfemail(String str) {
		reminderemail = str;
	}

}

