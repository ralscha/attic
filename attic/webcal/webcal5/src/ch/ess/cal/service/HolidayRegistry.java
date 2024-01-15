package ch.ess.cal.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.map.MultiKeyMap;

import ch.ess.base.model.Translation;
import ch.ess.base.model.TranslationText;
import ch.ess.cal.dao.HolidayDao;
import ch.ess.holiday.EasterHoliday;
import ch.ess.holiday.Holiday;
import ch.ess.holiday.SimpleHoliday;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "holidayRegistry", autowire = Autowire.BYTYPE)
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
        startYear = hol.getFromYear();
      }

      if (hol.getToYear() != null) {
        endYear = hol.getToYear();
      }

      if (hol.getDayOfWeek() != null) {
        int dayOfWeek = hol.getDayOfWeek();
        if ((hol.getAfterDay() == null) || !hol.getAfterDay()) {
          dayOfWeek = -dayOfWeek;
        }
        holiday = new SimpleHoliday(hol.getMonthNo(), hol.getDayOfMonth(), dayOfWeek, hol.getId()
            .toString(), startYear, endYear);
      } else {
        holiday = new SimpleHoliday(hol.getMonthNo(), hol.getDayOfMonth(),
            hol.getId().toString(), startYear, endYear);
      }
    
      holidays.put(hol.getId().toString(), holiday);
    } else {
      holiday = builtinHolidays.get(hol.getBuiltin());
      holidays.put(hol.getBuiltin(), holiday);
    }
    
    

    

    Translation tr = hol.getTranslation();
    Set<TranslationText> translationTexts = tr.getTranslationTexts();
    Map<String, String> transMap = new HashMap<String, String>();

    for (TranslationText tt : translationTexts) {
      transMap.put(tt.getLocale(), tt.getText());
    }
    if (hol.getBuiltin() != null) {
      holidaysTranslations.put(hol.getBuiltin(), transMap);
    } else {
      holidaysTranslations.put(hol.getId().toString(), transMap);
    }
  }

  public Map<Integer, String> getMonthHolidays(Locale locale, Calendar cal) {
    return getMonthHolidays(locale, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
  }

  public Map<Integer, String> getMonthHolidays(Locale locale, int month, int year) {
    Calendar startCal = new GregorianCalendar(year, month, 1);
    int lastDay = startCal.getActualMaximum(Calendar.DATE);
    Calendar endCal = new GregorianCalendar(year, month, lastDay);

    Map<Integer, String> resultMap = new HashMap<Integer, String>();

    for (Entry<String, Holiday> entry : holidays.entrySet()) {
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

    for (Entry<String, Holiday> entry : holidays.entrySet()) {
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
