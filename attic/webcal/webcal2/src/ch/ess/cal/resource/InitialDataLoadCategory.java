package ch.ess.cal.resource;

import java.util.HashMap;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import ch.ess.cal.db.Category;
import ch.ess.common.Constants;




public class InitialDataLoadCategory {
  
  public static void initialLoad() throws HibernateException {
    //categories
    Category newCat = new Category();
    newCat.setColour("FF9900");
    newCat.setBwColour("000000");
    newCat.setIcalKey("BUSINESS");
    newCat.setUnknown(false); 
    newCat.setTranslations(getTranslationMap("Geschäft", "Business"));
    newCat.persist();
        
    newCat = new Category();
    newCat.setColour("0000FF");
    newCat.setBwColour("101010");
    newCat.setIcalKey("APPOINTMENT");
    newCat.setUnknown(false);     
    newCat.setTranslations(getTranslationMap("Verabredung", "Appointment"));           
    newCat.persist();        

    newCat = new Category();
    newCat.setColour("FFFF00");
    newCat.setBwColour("202020");
    newCat.setIcalKey("EDUCATION");
    newCat.setUnknown(false);                
    newCat.setTranslations(getTranslationMap("Ausbildung", "Education"));
    newCat.persist();        

    newCat = new Category();
    newCat.setColour("FF0000");
    newCat.setBwColour("303030");
    newCat.setIcalKey("HOLIDAY");
    newCat.setUnknown(false); 
    newCat.setTranslations(getTranslationMap("Feiertag", "Holiday"));        
    newCat.persist(); 

    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("404040");
    newCat.setIcalKey("MEETING");
    newCat.setUnknown(false); 
    newCat.setTranslations(getTranslationMap("Sitzung", "Meeting"));    
    newCat.persist();         

    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("505050");
    newCat.setIcalKey("MISCELLANEOUS");
    newCat.setUnknown(true);        
    newCat.setTranslations(getTranslationMap("Verschiedenes", "Miscellaneous"));         
    newCat.persist();         
            

    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("606060");
    newCat.setIcalKey("PERSONAL");
    newCat.setUnknown(false);  
    newCat.setTranslations(getTranslationMap("Privat", "Personal"));               
    newCat.persist();         

    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("707070");
    newCat.setIcalKey("PHONE CALL");
    newCat.setUnknown(false);    
    newCat.setTranslations(getTranslationMap("Telefonanruf", "Phone Call"));             
    newCat.persist();         
        
    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("808080");
    newCat.setIcalKey("SICK DAY");
    newCat.setUnknown(false);  
    newCat.setTranslations(getTranslationMap("Krank", "Sick Day"));               
    newCat.persist();         
        

    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("909090");
    newCat.setIcalKey("SPECIAL OCCASSION");
    newCat.setUnknown(false);           
    newCat.setTranslations(getTranslationMap("Spezielle Gelegenheit", "Special Occassion"));      
    newCat.persist();         
        
    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("A0A0A0");
    newCat.setIcalKey("TRAVEL");
    newCat.setUnknown(false);
    newCat.setTranslations(getTranslationMap("Reisen", "Travel"));
    newCat.persist();       
        
    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setBwColour("B0B0B0");
    newCat.setIcalKey("VACATION");
    newCat.setUnknown(false);  
    newCat.setTranslations(getTranslationMap("Urlaub", "Vacation"));               
    newCat.persist();         
        
  }

  private static Map getTranslationMap(String german, String english) {
    Map m = new HashMap();
    m.put(Constants.GERMAN_LOCALE, german);
    m.put(Constants.ENGLISH_LOCALE, english);
    return m;
  } 

}
