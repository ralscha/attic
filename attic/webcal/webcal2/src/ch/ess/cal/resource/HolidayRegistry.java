package ch.ess.cal.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import ch.ess.cal.db.Holiday;
import ch.ess.common.db.HibernateSession;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.EasterHoliday;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.SimpleHoliday;

public class HolidayRegistry {

  private final static HolidayRegistry instance = new HolidayRegistry();

  private static Map builtinHolidays = null;
  
  private Map holidays;
  private Map holidaysLocale;
  
  static {
    initializeBuiltin();
  }
  
  private HolidayRegistry() {
    holidays = new HashMap();
    holidaysLocale = new HashMap();
    
    builtinHolidays = new HashMap();
    
  }

  public static void init() throws HibernateException {

    Transaction tx = null;

    try {
      Session sess = HibernateSession.currentSession();

      tx = sess.beginTransaction();

      List resultList = sess.find("from Holiday as hol where hol.builtin = true and hol.active = true");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Holiday hol = (Holiday)it.next();
        addHoliday(hol);
      }

      tx.commit();
    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      throw e;
    } finally {
      HibernateSession.closeSession();
    }

  }

  public static void addHoliday(Holiday hol) {
    com.ibm.icu.util.Holiday holiday;
    if (hol.getMonth() != null) {
      holiday = new SimpleHoliday(hol.getMonth().intValue(), hol.getDayOfMonth(), hol.getDayOfWeek(), hol.getInternalKey());
    } else {
      holiday = (com.ibm.icu.util.Holiday)builtinHolidays.get(hol.getInternalKey());
    }

    instance.holidays.put(hol.getInternalKey(), holiday);
    instance.holidaysLocale.put(hol.getInternalKey(), hol.getTranslations());
  }

  public synchronized static void removeHoliday(Holiday hol) {
    instance.holidays.remove(hol.getInternalKey());
  }

  public static Map getMonthHolidays(Locale locale, Calendar cal) {
    return getMonthHolidays(locale, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
  }

  public static Map getMonthHolidays(Locale locale, int month, int year) {
    Calendar startCal = new GregorianCalendar(year, month, 1);
    Calendar endCal = new GregorianCalendar(year, month + 1, 1);
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
        Map translations = (Map)instance.holidaysLocale.get(entry.getKey());        
        resultMap.put(new Integer(cal.get(Calendar.DATE)), translations.get(locale));
      }

    }

    return resultMap;
  }

  public static Map getBuiltinHolidays() {
    return builtinHolidays;
  }

  private static void initializeBuiltin() {

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
