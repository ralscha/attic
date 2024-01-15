
package ch.ess.calendar;

import java.util.*;
import java.text.*;
import java.sql.*;
import ch.ess.calendar.db.*;

public class Summary {

	private int reqMonth;
	private int reqYear;

	private int prevMonth, prevYear, nextMonth, nextYear;

	private int lastday;
	private List userList;
	private Calendar[] days;

	private Calendar today;

	public Summary() {
		today = new GregorianCalendar();	
		reqMonth = today.get(Calendar.MONTH);
		reqYear  = today.get(Calendar.YEAR);
		computeNextAndPrev();
		lastday = -1;
		days = null;
	}
		
	public List getUserList() {
		return userList;
	}

	public void init() throws SQLException {
		UsersTable ut = new UsersTable();
		Iterator userit = ut.select(null, "firstname");
		
		userList = new ArrayList();
		while (userit.hasNext()) {
			Users user = (Users)userit.next();
			userList.add(user);
		}	
	}

	public void setMonth(int month) {
		reqMonth = month;
		computeNextAndPrev();
	}
	
	public int getMonth() {
		return reqMonth;
	}


	public void setYear(int year) {
    reqYear = year;
		computeNextAndPrev();
	}
	
	public int getYear() {
		return reqYear;
	}

	
	public int getNextMonth() {
		return nextMonth;
	}
	
	public int getNextYear() {
		return nextYear;
	}
	
	public int getPrevMonth() {
		return prevMonth;
	}
	
	public int getPrevYear() {
		return prevYear;
	}
	
	public String getRequestMonthName() {
		return ch.ess.calendar.util.Constants.MONTHNAMES[reqMonth];
	}
	
	public String getMonthName(int i) {
		return ch.ess.calendar.util.Constants.MONTHNAMES[i];
	}
	
	public int getLastDay() {
		if (lastday == -1)
			setLastDay();
			
		return lastday;
	}
	
	private void setLastDay() {
		lastday = ch.ess.calendar.util.Constants.MONTHDAYS[reqMonth];
		
		GregorianCalendar tmp = new GregorianCalendar(reqYear, reqMonth, 1);
		if (tmp.isLeapYear(reqYear) && (reqMonth == Calendar.FEBRUARY)) {
    		lastday++;
		}				
	}

	public Calendar getCalendar(int index) {
		if (days == null) {
			days = new Calendar[getLastDay()+1];
			days[0] = null;
			for (int i = 1; i < days.length; i++) {
				days[i] = new GregorianCalendar(reqYear, reqMonth, i);
			}
		}
		
		return days[index];
	}
	
	public String getFormatedCalendar(int index) {
		return ch.ess.calendar.util.Constants.dateFormat.format(getCalendar(index).getTime());
	}
	
	private void computeNextAndPrev() {
		if (reqMonth == Calendar.JANUARY) {
			prevMonth = Calendar.DECEMBER;
			prevYear = reqYear - 1;
		} else {
			prevMonth = reqMonth - 1;
			prevYear = reqYear;
		}

		if (reqMonth == Calendar.DECEMBER) {
			nextMonth = Calendar.JANUARY;
			nextYear = reqYear + 1;
		} else {
			nextMonth = reqMonth + 1;
			nextYear = reqYear;
		}
	}
	
	public int getTodayYear() {
		return today.get(Calendar.YEAR);
	}
	
	//Helper methods
	public boolean isToday(Calendar tmp) {
		return ( (today.get(Calendar.DATE) == tmp.get(Calendar.DATE)) &&
		         (today.get(Calendar.MONTH) == tmp.get(Calendar.MONTH)) &&
		         (today.get(Calendar.YEAR) == tmp.get(Calendar.YEAR)) );
	}
}