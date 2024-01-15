
package ch.ess.calendar;

import java.sql.*;
import java.util.*;

import ch.ess.calendar.db.*;
import ch.ess.calendar.session.*;

public class WeekViewRequest {

	private String userid;
	private Calendar date;	
	private boolean showDelete;
	private Users showUser;
	private List[] appAlldayList;
	private List[] appTimedList;
		
	private Calendar[] weekCal;
  private boolean setmonth, setyear;
	
	public WeekViewRequest() {
		showUser = null;
		userid = null;
		date = new GregorianCalendar();
		appAlldayList = new ArrayList[7];
		appTimedList  = new ArrayList[7];
		weekCal = new Calendar[7];
		setWeek();

		setmonth = setyear = false;
		showDelete = false;
	}
	
	public void setAppCache(AppointmentsCache appcache, boolean logonOK, String loginUserid) throws InterruptedException {		
	 	if (logonOK) {
			for (int i = 0; i < 7; i++) {
				appAlldayList[i] = appcache.getUserAppointments(userid, weekCal[i], loginUserid, AppointmentsCache.APPOINTMENTS_ALLDAY);
				appTimedList[i]  = appcache.getUserAppointments(userid, weekCal[i], loginUserid, AppointmentsCache.APPOINTMENTS_TIMED);
			}

			if (userid.equals(loginUserid))
				showDelete = true;
				
	 	} else {
			for (int i = 0; i < 7; i++) {
				appAlldayList[i] = appcache.getUserAppointments(userid, weekCal[i], AppointmentsCache.APPOINTMENTS_ALLDAY);
				appTimedList[i]  = appcache.getUserAppointments(userid, weekCal[i], AppointmentsCache.APPOINTMENTS_TIMED);
			}
	 	}
					  
	}
	
	public boolean showDelete() {
		return showDelete;
	}
		
  public void setDay(String day) {
    this.date.set(Calendar.DATE, Integer.parseInt(day));
    
    if (setmonth && setyear)
      setWeek();
  }

  public String getDay() {
    return String.valueOf(this.date.get(Calendar.DATE));
  }

  public void setMonth(String month) {
    this.date.set(Calendar.MONTH, Integer.parseInt(month));  
    setmonth = true;

    if (setmonth && setyear)
      setWeek();
  }

  public void setYear(String year) {
    this.date.set(Calendar.YEAR, Integer.parseInt(year));
    setyear = true;
    
    if (setmonth && setyear)
      setWeek();
  }
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getUserid() {
		return userid;
	}

	public void init() throws SQLException {
		UsersTable ut = new UsersTable();
		Iterator userit = ut.select("userid = '" + userid + "'");
		if (userit.hasNext()) {
			showUser = (Users)userit.next();
		} else {
			showUser = null;
		}	
	}
	
	public String getNameString() {
		if (showUser != null) {
			return (showUser.getFirstname() + " " + showUser.getName());
		} 
	  return ("");		
	}
	
	public int getWeekno() {
		return date.get(Calendar.WEEK_OF_YEAR);
	}

	public String getFormatedDate(int ix) {
		// ix 0 - 6
		return ch.ess.calendar.util.Constants.dateFormat.format(weekCal[ix].getTime());	
	}
	
	public Calendar getCal(int ix) {
		return weekCal[ix];	
	}
	
	public boolean isToday(int ix) {
		Calendar today = new GregorianCalendar();
	
		int day = weekCal[ix].get(Calendar.DATE);
		int month = weekCal[ix].get(Calendar.MONTH);
		int year = weekCal[ix].get(Calendar.YEAR);
		
		return ( (day == today.get(Calendar.DATE)) &&
		         (month == today.get(Calendar.MONTH)) &&
					(year == today.get(Calendar.YEAR)) ) ;
	}
	
	public String getWeekday(int ix) {
		return ch.ess.calendar.util.Constants.WEEKDAYS[weekCal[ix].get(Calendar.DAY_OF_WEEK)];
	}	
	
	public List getAlldayAppointments(int ix) {
		return appAlldayList[ix];
	}
	
	public List getTimedAppointments(int ix) {
		return appTimedList[ix];
	}
		
	public void setWeek() {
		int firstDayOfWeek = date.getFirstDayOfWeek();
		
		int firstDay = date.get(Calendar.DAY_OF_WEEK) - firstDayOfWeek;
		if (firstDay < 0) 
			firstDay += 7;
		
		Calendar firstdate = (Calendar)date.clone();			 
	   firstdate.add(Calendar.DAY_OF_MONTH, -firstDay);
		
		for (int i = 0; i < 7; i++) {
			weekCal[i] = (Calendar)firstdate.clone();
			firstdate.add(Calendar.DAY_OF_MONTH, +1);			
		}
	}
}