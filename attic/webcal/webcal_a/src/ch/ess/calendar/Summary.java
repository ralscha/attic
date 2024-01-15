
package ch.ess.calendar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import ch.ess.calendar.db.Users;
import ch.ess.calendar.db.UsersTable;

public class Summary {

	private int reqMonth;
	private int reqYear;

	private int prevMonth, prevYear, nextMonth, nextYear;

	private int lastday;
	private List userList;
	private Calendar[] days;

	private Calendar today;
  
  
  private List weekColspan;
  private List weekNos;

	public Summary() {
		today = new GregorianCalendar();	
		reqMonth = today.get(Calendar.MONTH);
		reqYear  = today.get(Calendar.YEAR);
		computeNextAndPrev();
		lastday = -1;
		days = null;

	}

  
  private void calcWeekno() {
    
    weekColspan = new ArrayList();
    weekNos = new ArrayList();
    
    Calendar cal = new GregorianCalendar(reqYear, reqMonth, 1);
    cal.setMinimalDaysInFirstWeek(4);
    
    int length = cal.getActualMaximum(Calendar.DATE);
    
    int mweekno = cal.get(Calendar.WEEK_OF_YEAR);
    int colspan = 0;

    for (int i = 1; i <= length; i++) {
            
      int weekno = cal.get(Calendar.WEEK_OF_YEAR);
      
      if (weekno == mweekno) {
        colspan++;
      } else {
        weekColspan.add(new Integer(colspan));
        weekNos.add(new Integer(mweekno));
        mweekno = weekno;
        colspan = 1;
      }

      cal.add(Calendar.DATE, +1);

    }

    if (colspan > 0) {
      weekColspan.add(new Integer(colspan));
      weekNos.add(new Integer(mweekno));
    }  
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
  
  
	
	public List getWeekColspan() {
    return weekColspan;
  }
  

  public void setWeekColspan(List weekColspan) {
    this.weekColspan = weekColspan;
  }
  

  public List getWeekNos() {
    return weekNos;
  }
  

  public void setWeekNos(List weekNos) {
    this.weekNos = weekNos;
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
    calcWeekno();
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