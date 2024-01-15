package ch.ess.cal.service.impl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.MultiKeyMap;

import ch.ess.cal.model.Translation;
import ch.ess.cal.model.TranslationText;
import ch.ess.cal.persistence.HolidayDao;
import ch.ess.holiday.EasterHoliday;
import ch.ess.holiday.Holiday;
import ch.ess.holiday.SimpleHoliday;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:12 $ 
 * 
 * @spring.bean id="holidayRegistry"
 * 
 */
public class HolidayRegistry {

  private static Map<String, Holiday> builtinHolidays = null;

  private Map<String, Holiday> holidays;
  private Map<String, Map<String, String>> holidaysTranslations;

  private HolidayDao holidayDao;

  static {
    builtinHolidays = new HashMap<String, Holiday>();
    builtinHolidays.put("goodFriday", EasterHoliday.GOOD_FRIDAY);
    builtinHolidays.put("easterSunday", EasterHoliday.EASTER_SUNDAY);
    builtinHolidays.put("easterMonday", EasterHoliday.EASTER_MONDAY);
    builtinHolidays.put("ascension", EasterHoliday.ASCENSION);
    builtinHolidays.put("whitSunday", EasterHoliday.WHIT_SUNDAY);
    builtinHolidays.put("whitMonday", EasterHoliday.WHIT_MONDAY);
    builtinHolidays.put("christmasEve", SimpleHoliday.CHRISTMAS_EVE);
    builtinHolidays.put("christmas", SimpleHoliday.CHRISTMAS);
    builtinHolidays.put("stStephensDay", SimpleHoliday.ST_STEPHENS_DAY);
    builtinHolidays.put("newYearsEve", SimpleHoliday.NEW_YEARS_EVE);
    builtinHolidays.put("newYearsDay", SimpleHoliday.NEW_YEARS_DAY);
  }

  private HolidayRegistry() {
    holidays = new HashMap<String, Holiday>();
    holidaysTranslations = new HashMap<String, Map<String, String>>();
  }

  /**
   * @spring.property reflocal="holidayDao"
   */
  public void setHolidayDao(HolidayDao holidayDao) {
    this.holidayDao = holidayDao;
  }

  public void init() {

    holidays.clear();
    holidaysTranslations.clear();

    List<ch.ess.cal.model.Holiday> activeHolidays = holidayDao.findActiveHolidays();
    for (ch.ess.cal.model.Holiday holiday : activeHolidays) {
      addHoliday(holiday);
    }

  }

  public void addHoliday(ch.ess.cal.model.Holiday hol) {
    Holiday holiday;
    if (hol.getBuiltin() == null) {

      int startYear = 0;
      int endYear = 0;
      if (hol.getFromYear() != null) {
        startYear = hol.getFromYear().intValue();
      }

      if (hol.getToYear() != null) {
        endYear = hol.getToYear().intValue();
      }

      if (hol.getDayOfWeek() != null) {
        int dayOfWeek = hol.getDayOfWeek().intValue();
        if ((hol.getAfterDay() == null) || !hol.getAfterDay().booleanValue()) {
          dayOfWeek = -dayOfWeek;
        }
        holiday = new SimpleHoliday(hol.getMonthNo().intValue(), hol.getDayOfMonth().intValue(), dayOfWeek, hol.getId()
            .toString(), startYear, endYear);
      } else {
        holiday = new SimpleHoliday(hol.getMonthNo().intValue(), hol.getDayOfMonth().intValue(),
            hol.getId().toString(), startYear, endYear);
      }
    } else {
      holiday = builtinHolidays.get(hol.getBuiltin());
    }

    holidays.put(hol.getBuiltin(), holiday);

    Translation tr = hol.getTranslation();
    Set<TranslationText> translationTexts = tr.getTranslationTexts();
    Map<String, String> transMap = new HashMap<String, String>();

    for (TranslationText tt : translationTexts) {
      transMap.put(tt.getLocale(), tt.getText());
    }
    holidaysTranslations.put(hol.getBuiltin(), transMap);
  }

  public Map<Integer, String> getMonthHolidays(Locale locale, Calendar cal) {
    return getMonthHolidays(locale, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
  }

  public Map<Integer, String> getMonthHolidays(Locale locale, int month, int year) {
    Calendar startCal = new GregorianCalendar(year, month, 1);
    int lastDay = startCal.getActualMaximum(Calendar.DATE);
    Calendar endCal = new GregorianCalendar(year, month, lastDay);

    Map<Integer, String> resultMap = new HashMap<Integer, String>();

    for (Iterator<Map.Entry<String,Holiday>> it = holidays.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String,Holiday> entry = it.next();

      Holiday hol = entry.getValue();
      Calendar event = hol.firstBetween(startCal, endCal);
      if (event != null) {
        Map<String, String> translations = holidaysTranslations.get(entry.getKey());
        resultMap.put(new Integer(event.get(Calendar.DATE)), translations.get(locale.toString()));
      }

    }

    return resultMap;
  }

  public MultiKeyMap getYearHolidays(Locale locale, int year) {
    Calendar startCal = new GregorianCalendar(year, Calendar.JANUARY, 1);
    Calendar endCal = new GregorianCalendar(year, Calendar.DECEMBER, 31);

    return getYearHolidays(locale, startCal, endCal);

  }

  public MultiKeyMap getYearHolidays(Locale locale, Calendar startCal, Calendar endCal) {

    MultiKeyMap resultMap = new MultiKeyMap();

    for (Iterator<Map.Entry<String,Holiday>> it = holidays.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String,Holiday> entry = it.next();

      Holiday hol = entry.getValue();
      Calendar event = hol.firstBetween(startCal, endCal);
      if (event != null) {
        Map<String, String> translations = holidaysTranslations.get(entry.getKey());
        resultMap.put(new Integer(event.get(Calendar.YEAR)), new Integer(event.get(Calendar.MONTH)), new Integer(event
            .get(Calendar.DATE)), translations.get(locale.toString()));
      }

    }

    return resultMap;
  }
}
