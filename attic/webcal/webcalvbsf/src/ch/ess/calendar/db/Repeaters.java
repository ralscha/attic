package ch.ess.calendar.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;
import cmp.business.BigDate;
import java.text.*;
/**
 * Class Repeaters.
 */
public class Repeaters {

  //EVERY
  public final static Long EVERY = new Long(1);
  public final static Long EVERYSECOND = new Long(2);
  public final static Long EVERYTHIRD = new Long(3);
  public final static Long EVERYFOURTH = new Long(4);
  private final static int EVERYint = 1;
  private final static int EVERYSECONDint = 2;
  private final static int EVERYTHIRDint = 3;
  private final static int EVERYFOURTHint = 4;

  //EVERYPERIOD
  public final static Long EVERYPERIOD_DAY = new Long(1);
  public final static Long EVERYPERIOD_WEEK = new Long(2);
  public final static Long EVERYPERIOD_MONTH = new Long(3);
  public final static Long EVERYPERIOD_YEAR = new Long(4);
  public final static Long EVERYPERIOD_MWF = new Long(5); /* Mon, Wed, Fri */
  public final static Long EVERYPERIOD_TT = new Long(6); /* Tue, Thu */
  public final static Long EVERYPERIOD_WORKWEEK = new Long(7); /* Mon - Fr */
  public final static Long EVERYPERIOD_WEEKEND = new Long(8); /* Sat + Sun */
  private final static int EVERYPERIOD_DAYint = 1;
  private final static int EVERYPERIOD_WEEKint = 2;
  private final static int EVERYPERIOD_MONTHint = 3;
  private final static int EVERYPERIOD_YEARint = 4;
  private final static int EVERYPERIOD_MWFint = 5; /* Mon, Wed, Fri */
  private final static int EVERYPERIOD_TTint = 6; /* Tue, Thu */
  private final static int EVERYPERIOD_WORKWEEKint= 7; /* Mon - Fr */
  private final static int EVERYPERIOD_WEEKENDint = 8; /* Sat + Sun */
  
  //REPEATON
  public final static Long REPEATON_FIRST = new Long(1);
  public final static Long REPEATON_SECOND = new Long(2);
  public final static Long REPEATON_THIRD = new Long(3);
  public final static Long REPEATON_FOURTH = new Long(4);
  public final static Long REPEATON_LAST = new Long(5);
  private final static int REPEATON_FIRSTint = 1;
  private final static int REPEATON_SECONDint = 2;
  private final static int REPEATON_THIRDint = 3;
  private final static int REPEATON_FOURTHint = 4;
  private final static int REPEATON_LASTint = 5;

  //REPEATONPERIOD
  public final static Long REPEATONPERIOD_MONTH = new Long(1);
  public final static Long REPEATONPERIOD_2MONTH = new Long(2);
  public final static Long REPEATONPERIOD_3MONTH = new Long(3);
  public final static Long REPEATONPERIOD_4MONTH = new Long(4);
  public final static Long REPEATONPERIOD_6MONTH = new Long(5);
  private final static int REPEATONPERIOD_MONTHint = 1;
  private final static int REPEATONPERIOD_2MONTHint = 2;
  private final static int REPEATONPERIOD_3MONTHint = 3;
  private final static int REPEATONPERIOD_4MONTHint = 4;
  private final static int REPEATONPERIOD_6MONTHint = 5;
  

  public final static Long NOTHING = new Long(-1);
  
    /* attributes */
    private Long repeaterid;
    private OReference appointment;
    private Date until;
    private Long every;
    private Long everyperiod;
    private Long repeaton;
    private Long repeatonweekday;
    private Long repeatonperiod;

    /** Creates a new Repeaters */    
    public Repeaters() {
    }
    
    
    /** Accessor for attribute repeaterid */
    public Long getRepeaterid() {
        return repeaterid;
    }
    
    /** Accessor for attribute until */
    public Date getUntil() {
        return until;
    }
    
    /** Accessor for attribute every */
    public Long getEvery() {
        return every;
    }
    
    public boolean isEveryMode() {
      return ( (!every.equals(NOTHING)) && (!everyperiod.equals(NOTHING)) );
    }    
    
    /** Accessor for attribute everyperiod */
    public Long getEveryperiod() {
        return everyperiod;
    }
    
    /** Accessor for attribute repeaton */
    public Long getRepeaton() {
        return repeaton;
    }
    
    /** Accessor for attribute repeatonweekday */
    public Long getRepeatonweekday() {
        return repeatonweekday;
    }
    
    /** Accessor for attribute repeatonperiod */
    public Long getRepeatonperiod() {
        return repeatonperiod;
    }
    
    /** Mutator for attribute repeaterid */
    public void setRepeaterid(Long newRepeaterid) {
        repeaterid = newRepeaterid;
    }
    
    /** Mutator for attribute until */
    public void setUntil(Date newUntil) {
        until = newUntil;
    }


  public void setUntil(String datestr) {
    this.until = new java.sql.Timestamp(getCalendar(datestr).getTime().getTime());
  }    
    
    public boolean isAlways() {
      return (until == null);
    }
    
    /** Mutator for attribute every */
    public void setEvery(Long newEvery) {
        every = newEvery;
    }
    
    /** Mutator for attribute everyperiod */
    public void setEveryperiod(Long newEveryperiod) {
        everyperiod = newEveryperiod;
    }
    
    /** Mutator for attribute repeaton */
    public void setRepeaton(Long newRepeaton) {
        repeaton = newRepeaton;
    }
    
    /** Mutator for attribute repeatonweekday */
    public void setRepeatonweekday(Long newRepeatonweekday) {
        repeatonweekday = newRepeatonweekday;
    }
    
    /** Mutator for attribute repeatonperiod */
    public void setRepeatonperiod(Long newRepeatonperiod) {
        repeatonperiod = newRepeatonperiod;
    }
    
    /** Accessor for reference appointment */
    public Appointments getAppointment() throws BODBException {
        return (Appointments)appointment.get();
    }
    
    /** Mutator for reference appointment */
    public void setAppointment(Appointments newAppointment)
      throws BODBException, BOReferenceNotUpdatedException {
        appointment.set(newAppointment);
    }
    
  public String getFormatedUntil() {
    return ch.ess.calendar.util.Constants.dateFormat.format(until);
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
      if (getEvery().equals(EVERY)) {
        period = 1;
      } else if (getEvery().equals(EVERYSECOND)) {
        period = 2;
      } else if (getEvery().equals(EVERYTHIRD)) {
        period = 3;
      } else if (getEvery().equals(EVERYFOURTH)) {
        period = 4;
      }
    
    
      if (getEveryperiod().equals(EVERYPERIOD_DAY)) {      
        int diffDays = getOrdinal(testYear, testMonth, testDay)
                    - getOrdinal(startYear, startMonth, startDay);        
        return (diffDays % period == 0);      
      } else if (getEveryperiod().equals(EVERYPERIOD_WEEK)) {
        int diffDays = getOrdinal(testYear, testMonth, testDay)
                  - getOrdinal(startYear, startMonth, startDay);
        period = period * 7;              
        return (diffDays % period == 0);        
      } else if (getEveryperiod().equals(EVERYPERIOD_MONTH)) {
        int tmpMonth = (testYear - startYear) * 12 + testMonth;
        return ( (startDay == testDay) && ((tmpMonth - startMonth) % period == 0) );
      } else if (getEveryperiod().equals(EVERYPERIOD_YEAR)) {
        return ( (startDay == testDay) && (startMonth == testMonth) &&
                 ((testYear - startYear) % period == 0) );
      } else if (getEveryperiod().equals(EVERYPERIOD_MWF)) {
        if (weekOk(startCal, testCal, period)) {
          return ((testWeekday == Calendar.MONDAY) || 
                 (testWeekday == Calendar.WEDNESDAY) ||
               (testWeekday == Calendar.FRIDAY)) ;
        } else {
          return false;
        }
      } else if (getEveryperiod().equals(EVERYPERIOD_TT)) {
        if (weekOk(startCal, testCal, period)) {
          return ((testWeekday == Calendar.TUESDAY) || 
                 (testWeekday == Calendar.THURSDAY));
        } else {
          return false;
        }
      } else if (getEveryperiod().equals(EVERYPERIOD_WORKWEEK)) {
        if (weekOk(startCal, testCal, period)) {
          return ((testWeekday == Calendar.MONDAY) || 
                 (testWeekday == Calendar.TUESDAY) ||
                 (testWeekday == Calendar.WEDNESDAY) ||
                 (testWeekday == Calendar.THURSDAY) ||
               (testWeekday == Calendar.FRIDAY)) ;
        } else {
          return false;
        }
      } else if (getEveryperiod().equals(EVERYPERIOD_WEEKEND)) {   
        if (weekOk(startCal, testCal, period)) {
          return ((testWeekday == Calendar.SATURDAY) || 
               (testWeekday == Calendar.SUNDAY)) ;
        } else {
          return false;
        }
      }
    } else { // Repeat on
      int testOrdinal = getOrdinal(testYear, testMonth, testDay);

      if (getRepeatonperiod().equals(REPEATONPERIOD_MONTH)) {
        int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton().intValue(), getBigDateWeekday(getRepeatonweekday()),
                            testYear, testMonth+1);     
        return (testnthOrdinal == testOrdinal);
      } else if (getRepeatonperiod().equals(REPEATONPERIOD_2MONTH)) {
        int tmpMonth = (testYear - startYear) * 12 + testMonth;
        if ((tmpMonth - startMonth) % 2 == 0) {
          int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton().intValue(), getBigDateWeekday(getRepeatonweekday()),
                              testYear, testMonth+1);     
          return (testnthOrdinal == testOrdinal);
        } else {
          return false;
        }
      } else if (getRepeatonperiod().equals(REPEATONPERIOD_3MONTH)) {
        int tmpMonth = (testYear - startYear) * 12 + testMonth;
        if ((tmpMonth - startMonth) % 3 == 0) {
          int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton().intValue(), getBigDateWeekday(getRepeatonweekday()),
                              testYear, testMonth+1);     
          return (testnthOrdinal == testOrdinal);
        } else {
          return false;
        }
      } else if (getRepeatonperiod().equals(REPEATONPERIOD_4MONTH)) {
        int tmpMonth = (testYear - startYear) * 12 + testMonth;
        if ((tmpMonth - startMonth) % 4 == 0) {
          int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton().intValue(), getBigDateWeekday(getRepeatonweekday()),
                              testYear, testMonth+1);     
          return (testnthOrdinal == testOrdinal);
        } else {
          return false;
        }
      
      } else if (getRepeatonperiod().equals(REPEATONPERIOD_6MONTH)) {    
        int tmpMonth = (testYear - startYear) * 12 + testMonth;
        if ((tmpMonth - startMonth) % 6 == 0) {
          int testnthOrdinal = BigDate.ordinalOfnthXXXDay(getRepeaton().intValue(), getBigDateWeekday(getRepeatonweekday()),
                              testYear, testMonth+1);     
          return (testnthOrdinal == testOrdinal);
        } else {
          return false;
        }     
      } 
      
    }
    
    return false;
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
  
  private int getBigDateWeekday(Long weekday) {
    switch (weekday.intValue()) {
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
      switch (getEvery().intValue()) {
        case EVERYint : sb.append("Every "); break;
        case EVERYSECONDint : sb.append("Every Second "); break;
        case EVERYTHIRDint : sb.append("Every Third "); break;
        case EVERYFOURTHint : sb.append("Every Fourth "); break;
      }
      switch (getEveryperiod().intValue()) {
        case EVERYPERIOD_DAYint : sb.append("Day"); break;
        case EVERYPERIOD_WEEKint : sb.append("Week"); break;
        case EVERYPERIOD_MONTHint : sb.append("Month"); break;
        case EVERYPERIOD_YEARint : sb.append("Year"); break;
        case EVERYPERIOD_MWFint : sb.append("Mon, Wed, Fri"); break;
        case EVERYPERIOD_TTint : sb.append("Tue, Thu"); break;
        case EVERYPERIOD_WORKWEEKint : sb.append("Mon - Fri"); break;
        case EVERYPERIOD_WEEKENDint : sb.append("Sat + Sun"); break;
      }
      return sb.toString();
    } else {
      sb.append("Repeat on the ");
      switch (getRepeaton().intValue()) {
        case REPEATON_FIRSTint : sb.append("First "); break;
        case REPEATON_SECONDint : sb.append("Second "); break;
        case REPEATON_THIRDint : sb.append("Third "); break;
        case REPEATON_FOURTHint : sb.append("Fourth "); break;
        case REPEATON_LASTint : sb.append("Last "); break;       
      }
      sb.append(ch.ess.calendar.util.Constants.WEEKDAYS[getRepeatonweekday().intValue()]);
      sb.append(" of the month every ");
      
      switch (getRepeatonperiod().intValue()) {
        case REPEATONPERIOD_MONTHint : sb.append("Month "); break;       
        case REPEATONPERIOD_2MONTHint : sb.append("2 Month "); break;        
        case REPEATONPERIOD_3MONTHint : sb.append("3 Month "); break;        
        case REPEATONPERIOD_4MONTHint : sb.append("4 Month "); break;        
        case REPEATONPERIOD_6MONTHint : sb.append("6 Month "); break;                                        
      }
      return sb.toString();
    }
    
  }
    
  private static int getOrdinal(int year, int month, int day) {
    BigDate bd = new BigDate(year, month + 1, day);
    return bd.getOrdinal();
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
    
}
