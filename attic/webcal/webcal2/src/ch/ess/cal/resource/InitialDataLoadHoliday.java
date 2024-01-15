package ch.ess.cal.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import ch.ess.cal.db.Holiday;
import ch.ess.common.Constants;

import com.ibm.icu.util.Calendar;




public class InitialDataLoadHoliday {
  
  private static final Map TRANSLATIONS = new HashMap();
    
  static {
    TRANSLATIONS.put("shroveTuesday", getTranslationMap("Shrove Tuesday", "Shrove Tuesday"));    
    TRANSLATIONS.put("ashWednesday", getTranslationMap("Aschermittwoch", "Ash Wednesday"));    
    TRANSLATIONS.put("palmSunday", getTranslationMap("Palmsonntag", "Palm Sunday"));    
    TRANSLATIONS.put("maundyThursday", getTranslationMap("Gründonnerstag", "Maundy Thursday"));    
    TRANSLATIONS.put("goodFriday", getTranslationMap("Karfreitag", "Good Friday"));    
    TRANSLATIONS.put("easterSunday", getTranslationMap("Ostersonntag", "Easter Sunday"));    
    TRANSLATIONS.put("easterMonday", getTranslationMap("Ostermontag", "Easter Monday"));    
    TRANSLATIONS.put("ascension", getTranslationMap("Auffahrt", "Ascension"));    
    TRANSLATIONS.put("pentecost", getTranslationMap("Pfingsten", "Pentecost"));
    TRANSLATIONS.put("whitSunday", getTranslationMap("Pfingstsonntag", "Whit Sunday"));
    TRANSLATIONS.put("whitMonday", getTranslationMap("Pfingstmontag", "Whit Monday"));
    TRANSLATIONS.put("corpusChristi", getTranslationMap("Copus Christi", "Corpus Christi"));
  } 
  
  private static Map getTranslationMap(String german, String english) {
    Map m = new HashMap();
    m.put(Constants.GERMAN_LOCALE, german);
    m.put(Constants.ENGLISH_LOCALE, english);
    return m;
  } 
  

  
  public static void initialLoad() throws HibernateException {
    //Holidays
    Map bh = HolidayRegistry.getBuiltinHolidays();
    for (Iterator it = bh.keySet().iterator(); it.hasNext();) {
      String key = (String)it.next();
      Holiday h = new Holiday();
      h.setInternalKey(key);
      Map m = (Map)TRANSLATIONS.get(key);
      String name = "";
      for (Iterator itm = m.values().iterator(); itm.hasNext();) {
        String element = (String)itm.next();
        if (name.length() > 0) {
          name += ";";
        }
        name += element;
      }
      h.setTranslations(m);
      h.setBuiltin(true);
      h.setActive(true);
      h.persist();               
    }
        
            
    Map translations;
    int ix = 0;
    
    //Holiday US
    Holiday h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));    
    h.setMonth(new Integer(Calendar.JANUARY));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Martin Luther King Day");
    translations.put(Constants.GERMAN_LOCALE, "Martin Luther King Day");
    h.setTranslations(translations);
    h.persist();
        
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.FEBRUARY));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Presidents' Day");
    translations.put(Constants.GERMAN_LOCALE, "Presidents' Day");
    h.setTranslations(translations);    
    h.persist();
        
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(8);
    h.setDayOfWeek(Calendar.SUNDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Mother's Day");
    translations.put(Constants.GERMAN_LOCALE, "Muttertag");
    h.setTranslations(translations);    
    h.persist();        
        
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(31);
    h.setDayOfWeek(-Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Memorial Day");
    translations.put(Constants.GERMAN_LOCALE, "Memorial Day");
    h.setTranslations(translations);    
    h.persist();          
                
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.JUNE));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.SUNDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Father's Day");
    translations.put(Constants.GERMAN_LOCALE, "Vattertag");
    h.setTranslations(translations);    
    h.persist();       
      
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.JULY));
    h.setDayOfMonth(4);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Independence Day");
    translations.put(Constants.GERMAN_LOCALE, "Independence Day");
    h.setTranslations(translations);    
    h.persist();  

    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.SEPTEMBER));
    h.setDayOfMonth(1);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Labor Day");
    translations.put(Constants.GERMAN_LOCALE, "Labor Day");
    h.setTranslations(translations);    
    h.persist();       
               
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(2);
    h.setDayOfWeek(Calendar.TUESDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Election Day");
    translations.put(Constants.GERMAN_LOCALE, "Election Day");
    h.setTranslations(translations);    
    h.persist();          
        
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(8);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Columbus Day");
    translations.put(Constants.GERMAN_LOCALE, "Columbus Day");
    h.setTranslations(translations);    
    h.persist(); 
        
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(31);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Halloween");
    translations.put(Constants.GERMAN_LOCALE, "Halloween");
    h.setTranslations(translations);    
    h.persist(); 
        
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(11);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Veterans' Day");
    translations.put(Constants.GERMAN_LOCALE, "Veterans' Day");
    h.setTranslations(translations);    
    h.persist();  
        
    h = new Holiday();
    h.setCountry("US");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(22);
    h.setDayOfWeek(Calendar.THURSDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Thanksgiving");
    translations.put(Constants.GERMAN_LOCALE, "Thanksgiving");
    h.setTranslations(translations);    
    h.persist(); 
                                
    h = new Holiday();
    h.setCountry("FR");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Labor Day");
    translations.put(Constants.GERMAN_LOCALE, "Tag der Arbeit");
    h.setTranslations(translations);    
    h.persist();  
    
    h = new Holiday();
    h.setCountry("FR");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(8);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Victory Day");
    translations.put(Constants.GERMAN_LOCALE, "Victory Day");
    h.setTranslations(translations);    
    h.persist();     
    
    h = new Holiday();
    h.setCountry("FR");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.JULY));
    h.setDayOfMonth(14);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Bastille Day");
    translations.put(Constants.GERMAN_LOCALE, "Bastille Day");
    h.setTranslations(translations);    
    h.persist(); 

    h = new Holiday();
    h.setCountry("FR");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(11);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Armistice Day");
    translations.put(Constants.GERMAN_LOCALE, "Armistice Day");
    h.setTranslations(translations);    
    h.persist();  
    
    h = new Holiday();
    h.setCountry("DE");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.JUNE));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.WEDNESDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Memorial Day");
    translations.put(Constants.GERMAN_LOCALE, "Memorial Day");
    h.setTranslations(translations);    
    h.persist(); 
    
    h = new Holiday();
    h.setCountry("DE");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(3);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Unity Day");
    translations.put(Constants.GERMAN_LOCALE, "Tag der dt. Einheit");
    h.setTranslations(translations);    
    h.persist();  
   
    h = new Holiday();
    h.setCountry("DE");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(18);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Day of Prayer and Repentance");
    translations.put(Constants.GERMAN_LOCALE, "Buss- und Bettag");
    h.setTranslations(translations);    
    h.persist();
    
    
    h = new Holiday();
    h.setCountry("IT");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.APRIL));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Liberation Day");
    translations.put(Constants.GERMAN_LOCALE, "Liberation Day");
    h.setTranslations(translations);    
    h.persist();   
   
    h = new Holiday();
    h.setCountry("IT");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Labor Day");
    translations.put(Constants.GERMAN_LOCALE, "Tag der Arbeit");
    h.setTranslations(translations);    
    h.persist();  
    
    h = new Holiday();
    h.setCountry("AT");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "National Holiday");
    translations.put(Constants.GERMAN_LOCALE, "Nationalfeiertag");
    h.setTranslations(translations);    
    h.persist();  
    
    h = new Holiday();
    h.setCountry("AT");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(31);
    h.setDayOfWeek(-Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "National Holiday");
    translations.put(Constants.GERMAN_LOCALE, "Nationalfeiertag");
    h.setTranslations(translations);    
    h.persist();  
    
    h = new Holiday();
    h.setCountry("JP");
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.FEBRUARY));
    h.setDayOfMonth(11);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "National Foundation Day");
    translations.put(Constants.GERMAN_LOCALE, "National Foundation Day");
    h.setTranslations(translations);    
    h.persist();   



    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.JANUARY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "New Year's Day");
    translations.put(Constants.GERMAN_LOCALE, "Neujahrstag");
    h.setTranslations(translations);    
    h.persist(); 
    

    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.JANUARY));
    h.setDayOfMonth(6);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Epiphany");
    translations.put(Constants.GERMAN_LOCALE, "v");
    h.setTranslations(translations);    
    h.persist(); 
    
    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "May Day");
    translations.put(Constants.GERMAN_LOCALE, "1. Mai");
    h.setTranslations(translations);    
    h.persist(); 
    
    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.AUGUST));
    h.setDayOfMonth(15);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Assumption");
    translations.put(Constants.GERMAN_LOCALE, "Maria Himmelfahrt");
    h.setTranslations(translations);    
    h.persist(); 
        

    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "All Saints' Day");
    translations.put(Constants.GERMAN_LOCALE, "Allerheiligen");
    h.setTranslations(translations);    
    h.persist();


    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(2);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "All Souls' Day");
    translations.put(Constants.GERMAN_LOCALE, "Allerseelen");
    h.setTranslations(translations);    
    h.persist();
     

    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(8);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Immaculate Conception");
    translations.put(Constants.GERMAN_LOCALE, "Maria Empfängnis");
    h.setTranslations(translations);    
    h.persist();   


    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(24);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Christmas Eve");
    translations.put(Constants.GERMAN_LOCALE, "Heilig Abend");
    h.setTranslations(translations);    
    h.persist();
        
    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(25);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Christmas");
    translations.put(Constants.GERMAN_LOCALE, "Weihnachten");
    h.setTranslations(translations);    
    h.persist();
          
    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(26);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "Boxing Day");
    translations.put(Constants.GERMAN_LOCALE, "Boxing Day");
    h.setTranslations(translations);    
    h.persist();
    
    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(26);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "St. Stephen's Day");
    translations.put(Constants.GERMAN_LOCALE, "Stefanstag");
    h.setTranslations(translations);    
    h.persist();
    
    h = new Holiday();
    h.setCountry(null);
    h.setInternalKey(String.valueOf(ix++));
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(31);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    translations = new HashMap();
    translations.put(Constants.ENGLISH_LOCALE, "New Year's Eve");
    translations.put(Constants.GERMAN_LOCALE, "Silvester");
    h.setTranslations(translations);    
    h.persist();
        
  }

}
