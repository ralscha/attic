
package ch.ess.calendar;

import java.sql.*;
import java.util.*;

import ch.ess.calendar.db.*;
import ch.ess.calendar.session.*;

public class DayViewRequest {

	private String userid;
	private Calendar date;
	private List appList;
	private List allDayList;
	private List[] appArray = new List[24];
	private int[] affected = new int[24];
	private int maxCol;
	private boolean showDelete;
	private Users showUser;
	
	public DayViewRequest() {
		showUser = null;
		userid = null;
		date = new GregorianCalendar();
		allDayList = new ArrayList();
		maxCol = 0;
		
		for (int i = 0; i < 24; i++) {
   		appArray[i] = new ArrayList();
			affected[i] = 0;
   	}
		
		showDelete = false;
	}
	
	public void setAppCache(AppointmentsCache appcache, boolean logonOK, String loginUserid) throws InterruptedException {
		
	 	if (logonOK) {
			appList = appcache.getUserAppointments(userid, date, loginUserid);

			if (userid.equals(loginUserid))
				showDelete = true;
				
	 	} else {
			appList = appcache.getUserAppointments(userid, date);
	 	}
		

		
		if (appList != null) {
			Iterator it = appList.iterator();
		   while (it.hasNext()) {
				Appointments app = (Appointments)it.next();
				if (app.isAlldayevent()) {
					allDayList.add(app);
				} else {
					int starthour = app.getStartCalendar().get(Calendar.HOUR_OF_DAY);
			  
					Calendar endCal = app.getEndCalendar();
					int endhour = endCal.get(Calendar.HOUR_OF_DAY);
					int endminute = endCal.get(Calendar.MINUTE);
					if (endminute > 0) 
						endhour++;

					((List)appArray[starthour]).add(app);
					for (int j = starthour+1; j < endhour; j++) {
						affected[j]++;	
					}		
				}
		   }
			
	   	for (int i = 0; i < appArray.length; i++) {
				int s = ((List)appArray[i]).size() + affected[i];
				if (s > maxCol) {
					maxCol = s;
				}
			}
		}	
	}
	
	public boolean showDelete() {
		return showDelete;
	}
	
	public int getMaxCol() {
		return maxCol;
	}
	
	public int getAffected(int ix) {
		return affected[ix];
	}
		
	public int getAppArraySize() {
		return appArray.length;
	}
	
	public List getAppArray(int ix) {
		return (List)appArray[ix];
	}
	
	public List getAlldayappointments() {
		return allDayList;
	}
	
	public boolean hasAlldayappointments() {
		return (!allDayList.isEmpty());
	}
	
	public boolean hasTimedappointments() {
		return (maxCol > 0);
	}
		
  public void setDay(String day) {
    this.date.set(Calendar.DATE, Integer.parseInt(day));
  }

  public String getDay() {
    return String.valueOf(this.date.get(Calendar.DATE));
  }

  public void setMonth(String month) {
    this.date.set(Calendar.MONTH, Integer.parseInt(month));  
  }

  public void setYear(String year) {
    this.date.set(Calendar.YEAR, Integer.parseInt(year));
  }

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getUserid() {
    return this.userid;
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
		} else {
			return ("");
		}
	}
	
	public String getWeekday() {
		return ch.ess.calendar.util.Constants.WEEKDAYS[date.get(Calendar.DAY_OF_WEEK)];
	}

	public String getFormatedDate() {
		return ch.ess.calendar.util.Constants.dateFormat.format(date.getTime());
	}
}