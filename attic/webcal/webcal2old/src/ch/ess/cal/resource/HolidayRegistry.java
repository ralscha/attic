package ch.ess.cal.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import ch.ess.cal.db.Holiday;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.EasterHoliday;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.SimpleHoliday;

public class HolidayRegistry {
  
  private final static HolidayRegistry instance = new HolidayRegistry();
  
  private Map builtinHolidays = null;
  private Map holidays;  
  
  private HolidayRegistry() {
    holidays = new HashMap();    
    builtinHolidays = new HashMap();
    initializeBuiltin();
  }
  
  public static void init() throws HibernateException {
         
    Session sess = null;
    Transaction tx = null;
  
    try {  
      sess = HibernateManager.open();
        
      tx = sess.beginTransaction(); 
            
      List resultList = sess.find("from Holiday as hol where hol.builtin = true and hol.active = true");  
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Holiday hol = (Holiday)it.next();                
        addHoliday(hol);
      }
  
      tx.commit();
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
      throw e;
    } finally {
      HibernateManager.finallyHandling(sess);
    } 
        
  }

  public static void addHoliday(Holiday hol) {
    com.ibm.icu.util.Holiday holiday;
    if (hol.getMonth() != null) {
      holiday = new SimpleHoliday(hol.getMonth().intValue(), hol.getDayOfMonth(), 
                                  hol.getDayOfWeek(), hol.getName());
    } else {
      holiday = (com.ibm.icu.util.Holiday)instance.builtinHolidays.get(hol.getName());
    }
    
    instance.holidays.put(hol.getName(), holiday);
  }
  
  
  public synchronized static void removeHoliday(String name) {
    instance.holidays.remove(name);
  }
  
  
  public static Map getMonthHolidays(Calendar cal) {
    return getMonthHolidays(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
  }
  
  public static Map getMonthHolidays(int month, int year) {
    Calendar startCal = new GregorianCalendar(year, month, 1);
    Calendar endCal = new GregorianCalendar(year, month+1, 1);
    Date startDate = startCal.getTime();
    Date endDate = endCal.getTime();
    
    Map resultMap = new HashMap();
    
    for (Iterator it = instance.holidays.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      
      com.ibm.icu.util.Holiday hol = (com.ibm.icu.util.Holiday)entry.getValue();
      Date event = hol.firstBetween(startDate, endDate);
      if (event != null) {
        Calendar cal = new GregorianCalendar();        
        cal.setTime(event);
        resultMap.put(new Integer(cal.get(Calendar.DATE)), entry.getKey());
      }
      
    }
    
    return resultMap;
  }
  
  
  public static Map getBuiltinHolidays() {
    return instance.builtinHolidays;
  }
  
  private void initializeBuiltin() {
        
    builtinHolidays = new HashMap();
    builtinHolidays.put("shroveTuesday", new EasterHoliday(-47, "Shrove Tuesday")); 
    builtinHolidays.put("ashWednesday", new EasterHoliday(-46, "Ash Wednesday")); 
    builtinHolidays.put("palmSunday", EasterHoliday.PALM_SUNDAY);
    builtinHolidays.put("maundyThursday", EasterHoliday.MAUNDY_THURSDAY);  
    builtinHolidays.put("goodFriday", EasterHoliday.GOOD_FRIDAY);
    builtinHolidays.put("easterSunday", EasterHoliday.EASTER_SUNDAY);  
    builtinHolidays.put("easterMonday", EasterHoliday.EASTER_MONDAY); 
    builtinHolidays.put("ascension", EasterHoliday.ASCENSION);
    builtinHolidays.put("pentecost", EasterHoliday.PENTECOST); 
    builtinHolidays.put("whitSunday", EasterHoliday.WHIT_SUNDAY);  
    builtinHolidays.put("whitMonday", EasterHoliday.WHIT_MONDAY); 
    builtinHolidays.put("corpusChristi", EasterHoliday.CORPUS_CHRISTI);       
  }

}
