package ch.ess.calendar.db;

import java.text.*;
import java.util.*;

public class Appointments implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  
  
  private int appointmentid;
	private String userid;
	private int categoryid;
	private java.sql.Timestamp startdate;
	private java.sql.Timestamp enddate;
	private String body;
	private String location;
	private String subject;
	private String alldayevent;
	private int importance;
	private String priv;

	private transient List reminderList;
	private transient List repeaterList;



	public Appointments() {
		this.appointmentid = 0;
		this.userid = null;
		this.categoryid = 0;
		this.startdate = null;
		this.enddate = null;
		this.body = "";
		this.location = "";
		this.subject = null;
		this.alldayevent = "Y";
		this.importance = 0;
		this.priv = "N";
	}

	public Appointments(int appointmentid, String userid, int categoryid, java.sql.Timestamp startdate, java.sql.Timestamp enddate, String body, String location, String subject, String alldayevent, int importance, String priv) {
		this.appointmentid = appointmentid;
		this.userid = userid;
		this.categoryid = categoryid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.body = body;
		this.location = location;
		this.subject = subject;
		this.alldayevent = alldayevent;
		this.importance = importance;
		this.priv = priv;
		
		this.reminderList = null;
		this.repeaterList = null;
	}

	public int getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(int appointmentid) {
		this.appointmentid = appointmentid;
	}

	public boolean isGroup() {
		return userid.equals("group");
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	
	public String getFormatedStartTime() {
		return ch.ess.calendar.util.Constants.timeFormat.format(startdate);
	}
	
	public String getFormatedStartDate() {
		return ch.ess.calendar.util.Constants.dateFormat.format(startdate);
	}

	public java.sql.Timestamp getStartdate() {
		return startdate;
	}
	
	public Calendar getStartCalendar() {
		GregorianCalendar startCal = new GregorianCalendar();
		startCal.setTime(startdate);
		return startCal;
	}

	public void setStartdate(java.sql.Timestamp startdate) {
		this.startdate = startdate;
	}

	public void setStartdate(String datestr) {
		setStartdate(datestr, null);
	}

	public void setStartdate(String datestr, String timestr) {						
		this.startdate = new java.sql.Timestamp(getCalendar(datestr, timestr).getTime().getTime());
	}

	public String getFormatedEndTime() {
		return ch.ess.calendar.util.Constants.timeFormat.format(enddate);
	}
	
	public String getFormatedEndDate() {
		return ch.ess.calendar.util.Constants.dateFormat.format(enddate);
	}

	public java.sql.Timestamp getEnddate() {
		return enddate;
	}

	public Calendar getEndCalendar() {
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(enddate);
		return endCal;
	}

	public void setEnddate(java.sql.Timestamp enddate) {
		this.enddate = enddate;
	}
	
	public void setEnddate(String datestr) {
		setEnddate(datestr, null);
	}

	public void setEnddate(String datestr, String timestr) {						
		this.enddate = new java.sql.Timestamp(getCalendar(datestr, timestr).getTime().getTime());
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		if ((body != null) && (body.trim().equals("")))
			this.body = null;
		else	
			this.body = body;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAlldayevent() {
		return alldayevent;
	}

	public void setAlldayevent(String alldayevent) {
		this.alldayevent = alldayevent;
	}

	public boolean isAlldayevent() {		
		if ("Y".equalsIgnoreCase(alldayevent)) {
			return true;
    }
	  return false;	
	}
	
	public void setAlldayevent(boolean flag) {
		if (flag)
			this.alldayevent = "Y";
		else
			this.alldayevent = "N";	
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public String getPrivate() {
		return priv;
	}

	public boolean isPrivate() {		
		if ("Y".equalsIgnoreCase(priv)) {
			return true;
    }		
	  return false;	
	}

	public void setPrivate(String priv) {
		this.priv = priv;
	}
	
	public void setPrivate(boolean flag) {
		if (flag)
			this.priv = "Y";
		else
			this.priv = "N";	
	}


	public String toString() {
		return "Appointments("+ appointmentid + " " + userid + " " + categoryid + " " + startdate + " " + enddate + " " + body + " " + location + " " + subject + " " + alldayevent + " " + importance + " " + priv+")";
	}
	
	private Calendar getCalendar(String datestr, String timestr) {
		Calendar tmpdate = new GregorianCalendar();
		Date date = null;
		try {		
			date = ch.ess.calendar.util.Constants.dateFormat.parse(datestr);
		} catch (ParseException pe) { 
      //no action
    }
		tmpdate.setTime(date);
		
		Calendar tmptime = null;
		if (timestr != null) {
			Date time = null;
			try { 
				time = ch.ess.calendar.util.Constants.timeFormat.parse(timestr); 
			} catch (ParseException pe) { 
			  System.err.println(pe);
			}
			tmptime = new GregorianCalendar();
			tmptime.setTime(time);
		}
		
		
		Calendar cal;
		if (tmptime != null) {
			cal = new GregorianCalendar(tmpdate.get(Calendar.YEAR),
					                           tmpdate.get(Calendar.MONTH),
														tmpdate.get(Calendar.DATE),
														tmptime.get(Calendar.HOUR_OF_DAY),
														tmptime.get(Calendar.MINUTE));
		} else {
			cal = new GregorianCalendar(tmpdate.get(Calendar.YEAR),
				                           tmpdate.get(Calendar.MONTH),
													tmpdate.get(Calendar.DATE));
		
		}
		return cal;
	}
	
	public List getReminders() {
		return reminderList;
	}
	
	public void addReminder(Reminders reminder) {
		if (reminderList == null) 
			reminderList = new ArrayList();
		
		reminderList.add(reminder);	
	}
	
	public boolean hasReminders() {
		return ((reminderList != null) && (!reminderList.isEmpty()));		
	}
	
	public List getRepeaters() {
		return repeaterList;
	}
	
	public void addRepeater(Repeaters repeater) {
		if (repeaterList == null) 
			repeaterList = new ArrayList();
		
		repeaterList.add(repeater);	
	}
	
	public boolean hasRepeaters() {
		return ((repeaterList != null) && (!repeaterList.isEmpty()));
	}
	
	public boolean inRange(Calendar cal) {
		Calendar testCal = (Calendar)cal.clone();		
		testCal.set(Calendar.HOUR_OF_DAY, 0);
		testCal.set(Calendar.MINUTE, 0);
		testCal.set(Calendar.SECOND, 0);
		testCal.set(Calendar.MILLISECOND, 0);
	
		Calendar startCal = new GregorianCalendar();
		startCal.setTime(startdate);		
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		startCal.set(Calendar.MILLISECOND, 0);
		
		Calendar endCal = new GregorianCalendar();
		endCal.setTime(enddate);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		endCal.set(Calendar.MILLISECOND, 0);
		
		if ((repeaterList == null) || (repeaterList.isEmpty())) {
			return ( (testCal.after(startCal) || testCal.equals(startCal)) &&
		         (testCal.before(endCal) || testCal.equals(endCal)) );
					
		} 
		if (testCal.after(startCal) || testCal.equals(startCal)) {
			Iterator it = repeaterList.iterator();
			boolean inRange = false;
			while (it.hasNext()) {
				Repeaters repeater = (Repeaters)it.next();
				inRange = inRange || repeater.inRange(startCal, cal);	
			}			
			return inRange;
		} 
	  return false;		
		
		
	}
}
