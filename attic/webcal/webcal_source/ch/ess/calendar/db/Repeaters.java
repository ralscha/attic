package ch.ess.calendar.db;

import java.util.*;
import java.text.*;
import cmp.business.BigDate;

public class Repeaters implements java.io.Serializable {
	
	//EVERY
	public final static int EVERY = 1;
	public final static int EVERYSECOND = 2;
	public final static int EVERYTHIRD = 3;
	public final static int EVERYFOURTH = 4;

	//EVERYPERIOD
	public final static int EVERYPERIOD_DAY = 1;
	public final static int EVERYPERIOD_WEEK = 2;
	public final static int EVERYPERIOD_MONTH = 3;
	public final static int EVERYPERIOD_YEAR = 4;
	public final static int EVERYPERIOD_MWF = 5; /* Mon, Wed, Fri */
	public final static int EVERYPERIOD_TT = 6; /* Tue, Thu */
	public final static int EVERYPERIOD_WORKWEEK = 7; /* Mon - Fr */
 	public final static int EVERYPERIOD_WEEKEND = 8; /* Sat + Sun */
	
	//REPEATON
	public final static int REPEATON_FIRST = 1;
	public final static int REPEATON_SECOND = 2;
	public final static int REPEATON_THIRD = 3;
	public final static int REPEATON_FOURTH = 4;
	public final static int REPEATON_LAST = 5;

	//REPEATONPERIOD
	public final static int REPEATONPERIOD_MONTH = 1;
	public final static int REPEATONPERIOD_2MONTH = 2;
	public final static int REPEATONPERIOD_3MONTH = 3;
	public final static int REPEATONPERIOD_4MONTH = 4;
	public final static int REPEATONPERIOD_6MONTH = 5;


	private int repeaterid;
	private int appointmentid;
	private java.sql.Timestamp until;
	private int every;
	private int everyperiod;
	private int repeaton;
	private int repeatonweekday;
	private int repeatonperiod;

	public Repeaters() {
		this.repeaterid = -1;
		this.appointmentid = -1;
		this.until = null;
		this.every = -1;
		this.everyperiod = -1;
		this.repeaton = -1;
		this.repeatonweekday = -1;
		this.repeatonperiod = -1;
	}

	public Repeaters(int repeaterid, int appointmentid, java.sql.Timestamp until, int every, int everyperiod, int repeaton, int repeatonweekday, int repeatonperiod) {
		this.repeaterid = repeaterid;
		this.appointmentid = appointmentid;
		this.until = until;
		this.every = every;
		this.everyperiod = everyperiod;
		this.repeaton = repeaton;
		this.repeatonweekday = repeatonweekday;
		this.repeatonperiod = repeatonperiod;
	}

	public boolean isEveryMode() {
		return ( (every != -1) && (everyperiod != -1) );
	}

	public int getRepeaterid() {
		return repeaterid;
	}

	public void setRepeaterid(int repeaterid) {
		this.repeaterid = repeaterid;
	}

	public int getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(int appointmentid) {
		this.appointmentid = appointmentid;
	}

	public java.sql.Timestamp getUntil() {
		return until;
	}
	
	public String getFormatedUntil() {
		return ch.ess.calendar.util.Constants.dateFormat.format(until);
	}

	public void setUntil(java.sql.Timestamp until) {
		this.until = until;
	}

	public void setUntil(String datestr) {
		this.until = new java.sql.Timestamp(getCalendar(datestr).getTime().getTime());
	}

	public int getEvery() {
		return every;
	}

	public void setEvery(int every) {
		this.every = every;
	}

	public int getEveryperiod() {
		return everyperiod;
	}

	public void setEveryperiod(int everyperiod) {
		this.everyperiod = everyperiod;
	}

	public int getRepeaton() {
		return repeaton;
	}

	public void setRepeaton(int repeaton) {
		this.repeaton = repeaton;
	}

	public int getRepeatonweekday() {
		return repeatonweekday;
	}

	public void setRepeatonweekday(int repeatonweekday) {
		this.repeatonweekday = repeatonweekday;
	}

	public int getRepeatonperiod() {
		return repeatonperiod;
	}

	public void setRepeatonperiod(int repeatonperiod) {
		this.repeatonperiod = repeatonperiod;
	}

	public boolean isAlways() {
		return (until == null);
	}

	public String toString() {
		return "Repeaters("+ repeaterid + " " + appointmentid + " " + until + " " + every + " " + everyperiod + " " + repeaton + " " + repeatonweekday + " " + repeatonperiod+")";
	}
	
	private Calendar getCalendar(String datestr) {
		Calendar tmpdate = new GregorianCalendar();
		Date date = null;
		try {		
			date = ch.ess.calendar.util.Constants.dateFormat.parse(datestr);
		} catch (ParseException pe) { 
			return null;
		}
		tmpdate.setTime(date);
		
		return  new GregorianCalendar(tmpdate.get(Calendar.YEAR),
				                        tmpdate.get(Calendar.MONTH),
												tmpdate.get(Calendar.DATE));
		
	}
	
	
	public boolean inRange(Calendar startCal, Calendar testCal) {
		
		if (!isAlways()) {	
			Calendar untilCal = new GregorianCalendar();
			untilCal.setTime(until);
			untilCal.set(Calendar.HOUR_OF_DAY, 0);
			untilCal.set(Calendar.MINUTE, 0);
			untilCal.set(Calendar.SECOND, 0);
			untilCal.set(Calendar.MILLISECOND, 0);
		
			if (testCal.after(untilCal))
				return false;
		}
	
		int startDay = startCal.get(Calendar.DATE);
		int startMonth = startCal.get(Calendar.MONTH);
		int startYear = startCal.get(Calendar.YEAR);
		int startWeekday = startCal.get(Calendar.DAY_OF_WEEK);
		
		int testDay = testCal.get(Calendar.DATE);
		int testMonth = testCal.get(Calendar.MONTH);
		int testYear = testCal.get(Calendar.YEAR);
		int testWeekday = testCal.get(Calendar.DAY_OF_WEEK);
	
		if (isEveryMode()) {
			int period = 0;
			if (getEvery() == EVERY) {
				period = 1;
			} else if (getEvery() == EVERYSECOND) {
				period = 2;
			} else if (getEvery() == EVERYTHIRD) {
				period = 3;
			} else if (getEvery() == EVERYFOURTH) {
				period = 4;
			}
		
		
			if (getEveryperiod() == EVERYPERIOD_DAY) {			
				int diffDays = getOrdinal(testYear, testMonth, testDay)
										- getOrdinal(startYear, startMonth, startDay);				
				return (diffDays % period == 0);			
			} else if (getEveryperiod() == EVERYPERIOD_WEEK) {
				int diffDays = getOrdinal(testYear, testMonth, testDay)
									- getOrdinal(startYear, startMonth, startDay);
				period = period * 7;							
				return (diffDays % period == 0);				
			} else if (getEveryperiod() == EVERYPERIOD_MONTH) {
				int tmpMonth = (testYear - startYear) * 12 + testMonth;
				return ( (startDay == testDay) && ((tmpMonth - startMonth) % period == 0) );
			} else if (getEveryperiod() == EVERYPERIOD_YEAR) {
				return ( (startDay == testDay) && (startMonth == testMonth) &&
				         ((testYear - startYear) % period == 0) );
			} else if (getEveryperiod() == EVERYPERIOD_MWF) {
				if (weekOk(startCal, testCal, period)) {
					return ((testWeekday == Calendar.MONDAY) || 
					       (testWeekday == Calendar.WEDNESDAY) ||
							 (testWeekday == Calendar.FRIDAY)) ;
				} else {
					return false;
				}
			} else if (getEveryperiod() == EVERYPERIOD_TT) {
				if (weekOk(startCal, testCal, period)) {
					return ((testWeekday == Calendar.TUESDAY) || 
					       (testWeekday == Calendar.THURSDAY));
				} else {
					return false;
				}
			} else if (getEveryperiod() == EVERYPERIOD_WORKWEEK) {
				if (weekOk(startCal, testCal, period)) {
					return ((testWeekday == Calendar.MONDAY) || 
					       (testWeekday == Calendar.TUESDAY) ||
					       (testWeekday == Calendar.WEDNESDAY) ||
					       (testWeekday == Calendar.THURSDAY) ||
							 (testWeekday == Calendar.FRIDAY)) ;
				} else {
					return false;
				}
			} else if (getEveryperiod() == EVERYPERIOD_WEEKEND) {		
				if (weekOk(startCal, testCal, period)) {
					return ((testWeekday == Calendar.SATURDAY) || 
							 (testWeekday == Calendar.SUNDAY)) ;
				} else {
					return false;
				}
			}
		} else { // Repeat on
			int testOrdinal = getOrdinal(testYear, testMonth, testDay);

			if (getRepeatonperiod() == REPEATONPERIOD_MONTH) {
				int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton(), getBigDateWeekday(getRepeatonweekday()),
														testYear, testMonth+1);			
				return (testnthOrdinal == testOrdinal);
			} else if (getRepeatonperiod() == REPEATONPERIOD_2MONTH) {
				int tmpMonth = (testYear - startYear) * 12 + testMonth;
				if ((tmpMonth - startMonth) % 2 == 0) {
					int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton(), getBigDateWeekday(getRepeatonweekday()),
															testYear, testMonth+1);			
					return (testnthOrdinal == testOrdinal);
				} else {
					return false;
				}
			} else if (getRepeatonperiod() == REPEATONPERIOD_3MONTH) {
				int tmpMonth = (testYear - startYear) * 12 + testMonth;
				if ((tmpMonth - startMonth) % 3 == 0) {
					int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton(), getBigDateWeekday(getRepeatonweekday()),
															testYear, testMonth+1);			
					return (testnthOrdinal == testOrdinal);
				} else {
					return false;
				}
			} else if (getRepeatonperiod() == REPEATONPERIOD_4MONTH) {
				int tmpMonth = (testYear - startYear) * 12 + testMonth;
				if ((tmpMonth - startMonth) % 4 == 0) {
					int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton(), getBigDateWeekday(getRepeatonweekday()),
															testYear, testMonth+1);			
					return (testnthOrdinal == testOrdinal);
				} else {
					return false;
				}
			
			} else if (getRepeatonperiod() == REPEATONPERIOD_6MONTH) {		
				int tmpMonth = (testYear - startYear) * 12 + testMonth;
				if ((tmpMonth - startMonth) % 6 == 0) {
					int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton(), getBigDateWeekday(getRepeatonweekday()),
															testYear, testMonth+1);			
					return (testnthOrdinal == testOrdinal);
				} else {
					return false;
				}			
	   	} 
			
		}
		
		return false;
	}
	
	private int getOrdinal(int year, int month, int day) {
		BigDate bd = new BigDate(year, month + 1, day);
		return bd.getOrdinal();
	}
	
	private boolean weekOk(Calendar ostartCal, Calendar stestCal, int period) {
		Calendar startCal = (Calendar)ostartCal.clone();
		Calendar testCal  = (Calendar)stestCal.clone();
		
		int startWeekday = startCal.get(Calendar.DAY_OF_WEEK);
		int testWeekday = testCal.get(Calendar.DAY_OF_WEEK);
		
		int firstDayOfWeek = startCal.getFirstDayOfWeek();
		if (startWeekday < firstDayOfWeek) {
			startWeekday += 7;
		}
		if (testWeekday < firstDayOfWeek) {
			testWeekday += 7;
		}
				
		startCal.add(Calendar.DATE, -(startWeekday - startCal.getFirstDayOfWeek()));
		testCal.add(Calendar.DATE, -(testWeekday - startCal.getFirstDayOfWeek()));
		
		int diffDays = getOrdinal(testCal.get(Calendar.YEAR), testCal.get(Calendar.MONTH),
		                          testCal.get(Calendar.DATE))
							- getOrdinal(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH),
									     startCal.get(Calendar.DATE));
		period = period * 7;							
		return (diffDays % period == 0);	
	}
	
	private int getBigDateWeekday(int weekday) {
		switch (weekday) {
			case Calendar.SUNDAY : return 0;
			case Calendar.MONDAY : return 1;
			case Calendar.TUESDAY : return 2;
			case Calendar.WEDNESDAY : return 3;
			case Calendar.THURSDAY : return 4;
			case Calendar.FRIDAY : return 5;
			case Calendar.SATURDAY : return 6;															
			default : return -1;
		}
	}
	
	public String getDescription() {
		StringBuffer sb = new StringBuffer();
		if (isEveryMode()) {
			sb.append("Repeat ");
			switch (getEvery()) {
				case EVERY : sb.append("Every "); break;
				case EVERYSECOND : sb.append("Every Second "); break;
				case EVERYTHIRD : sb.append("Every Third "); break;
				case EVERYFOURTH : sb.append("Every Fourth "); break;
			}
			switch (getEveryperiod()) {
				case EVERYPERIOD_DAY : sb.append("Day"); break;
				case EVERYPERIOD_WEEK : sb.append("Week"); break;
				case EVERYPERIOD_MONTH : sb.append("Month"); break;
				case EVERYPERIOD_YEAR : sb.append("Year"); break;
				case EVERYPERIOD_MWF : sb.append("Mon, Wed, Fri"); break;
				case EVERYPERIOD_TT : sb.append("Tue, Thu"); break;
				case EVERYPERIOD_WORKWEEK : sb.append("Mon - Fri"); break;
				case EVERYPERIOD_WEEKEND : sb.append("Sat + Sun"); break;
			}
			return sb.toString();
		} else {
			sb.append("Repeat on the ");
			switch (getRepeaton()) {
				case REPEATON_FIRST : sb.append("First "); break;
				case REPEATON_SECOND : sb.append("Second "); break;
				case REPEATON_THIRD : sb.append("Third "); break;
				case REPEATON_FOURTH : sb.append("Fourth "); break;
				case REPEATON_LAST : sb.append("Last "); break;				
			}
			sb.append(ch.ess.calendar.util.Constants.WEEKDAYS[getRepeatonweekday()]);
			sb.append(" of the month every ");
			
			switch (getRepeatonperiod()) {
				case REPEATONPERIOD_MONTH : sb.append("Month "); break;				
				case REPEATONPERIOD_2MONTH : sb.append("2 Month "); break;				
				case REPEATONPERIOD_3MONTH : sb.append("3 Month "); break;				
				case REPEATONPERIOD_4MONTH : sb.append("4 Month "); break;				
				case REPEATONPERIOD_6MONTH : sb.append("6 Month "); break;																				
			}
			return sb.toString();
		}
		
	}
}
