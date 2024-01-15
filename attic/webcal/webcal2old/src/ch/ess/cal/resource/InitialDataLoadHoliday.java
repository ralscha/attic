package ch.ess.cal.resource;

import java.util.Iterator;
import java.util.Map;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import ch.ess.cal.db.Holiday;

import com.ibm.icu.util.Calendar;




public class InitialDataLoadHoliday {
  
  public static void load(Session sess) throws HibernateException {
    //Holidays
    Map bh = HolidayRegistry.getBuiltinHolidays();
    for (Iterator it = bh.keySet().iterator(); it.hasNext();) {
      String key = (String)it.next();
      Holiday h = new Holiday();
      h.setName(key);
      h.setBuiltin(true);
      h.setActive(true);
      sess.save(h);          
    }
        
    //Holiday US
    Holiday h = new Holiday();
    h.setCountry("US");
    h.setName("martinLutherKingDay");
    h.setMonth(new Integer(Calendar.JANUARY));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);
        
    h = new Holiday();
    h.setCountry("US");
    h.setName("presidentsDay");
    h.setMonth(new Integer(Calendar.FEBRUARY));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);
        
    h = new Holiday();
    h.setCountry("US");
    h.setName("mothersDay");
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(8);
    h.setDayOfWeek(Calendar.SUNDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);        
        
    h = new Holiday();
    h.setCountry("US");
    h.setName("memorialDay");
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(31);
    h.setDayOfWeek(-Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);          
                
    h = new Holiday();
    h.setCountry("US");
    h.setName("fathersDay");
    h.setMonth(new Integer(Calendar.JUNE));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.SUNDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);          

    h = new Holiday();
    h.setCountry("US");
    h.setName("independenceDay");
    h.setMonth(new Integer(Calendar.JULY));
    h.setDayOfMonth(4);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);  

    h = new Holiday();
    h.setCountry("US");
    h.setName("laborDay");
    h.setMonth(new Integer(Calendar.SEPTEMBER));
    h.setDayOfMonth(1);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);          
               
    h = new Holiday();
    h.setCountry("US");
    h.setName("electionDay");
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(2);
    h.setDayOfWeek(Calendar.TUESDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);             
        
    h = new Holiday();
    h.setCountry("US");
    h.setName("columbusDay");
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(8);
    h.setDayOfWeek(Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);    
        
    h = new Holiday();
    h.setCountry("US");
    h.setName("halloween");
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(31);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h); 
        
    h = new Holiday();
    h.setCountry("US");
    h.setName("veteransDay");
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(11);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);  
        
    h = new Holiday();
    h.setCountry("US");
    h.setName("thanksgiving");
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(22);
    h.setDayOfWeek(Calendar.THURSDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h); 
    

    h = new Holiday();
    h.setCountry("FR");
    h.setName("laborDay");
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);     
    
    h = new Holiday();
    h.setCountry("FR");
    h.setName("vitoryDay");
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(8);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);        
    
    h = new Holiday();
    h.setCountry("FR");
    h.setName("bastilleDay");
    h.setMonth(new Integer(Calendar.JULY));
    h.setDayOfMonth(14);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);    

    h = new Holiday();
    h.setCountry("FR");
    h.setName("armisticeDay");
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(11);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);     
    
    h = new Holiday();
    h.setCountry("DE");
    h.setName("memorialDay");
    h.setMonth(new Integer(Calendar.JUNE));
    h.setDayOfMonth(15);
    h.setDayOfWeek(Calendar.WEDNESDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);    
    
    h = new Holiday();
    h.setCountry("DE");
    h.setName("unityDay");
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(3);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);     
   
    h = new Holiday();
    h.setCountry("DE");
    h.setName("dayOfPrayerAndRepentance");
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(18);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);   
    
    h = new Holiday();
    h.setCountry("IT");
    h.setName("liberationDay");
    h.setMonth(new Integer(Calendar.APRIL));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);      
   
    h = new Holiday();
    h.setCountry("IT");
    h.setName("laborDay");
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);     
    
    h = new Holiday();
    h.setCountry("AT");
    h.setName("nationalHoliday");
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);     
    
    h = new Holiday();
    h.setCountry("AT");
    h.setName("nationalHoliday");
    h.setMonth(new Integer(Calendar.OCTOBER));
    h.setDayOfMonth(31);
    h.setDayOfWeek(-Calendar.MONDAY);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);     
    
    h = new Holiday();
    h.setCountry("JP");
    h.setName("nationalFoundationDay");
    h.setMonth(new Integer(Calendar.FEBRUARY));
    h.setDayOfMonth(11);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(false);
    sess.save(h);      



    h = new Holiday();
    h.setCountry(null);
    h.setName("newYearsDay");
    h.setMonth(new Integer(Calendar.JANUARY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h); 
    

    h = new Holiday();
    h.setCountry(null);
    h.setName("epiphany");
    h.setMonth(new Integer(Calendar.JANUARY));
    h.setDayOfMonth(6);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h); 
    
    h = new Holiday();
    h.setCountry(null);
    h.setName("mayDay");
    h.setMonth(new Integer(Calendar.MAY));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h); 
    
    h = new Holiday();
    h.setCountry(null);
    h.setName("assumption");
    h.setMonth(new Integer(Calendar.AUGUST));
    h.setDayOfMonth(15);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h); 
        

    h = new Holiday();
    h.setCountry(null);
    h.setName("allSaintsDay");
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(1);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);   


    h = new Holiday();
    h.setCountry(null);
    h.setName("allSoulsDay");
    h.setMonth(new Integer(Calendar.NOVEMBER));
    h.setDayOfMonth(2);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);   
     

    h = new Holiday();
    h.setCountry(null);
    h.setName("immaculateConception");
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(8);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);      


    h = new Holiday();
    h.setCountry(null);
    h.setName("christmasEve");
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(24);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);
        
    h = new Holiday();
    h.setCountry(null);
    h.setName("christmas");
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(25);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);

        
    h = new Holiday();
    h.setCountry(null);
    h.setName("boxingDay");
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(26);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);
    
    h = new Holiday();
    h.setCountry(null);
    h.setName("stStephensDay");
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(26);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);
    
    h = new Holiday();
    h.setCountry(null);
    h.setName("newYearsEve");
    h.setMonth(new Integer(Calendar.DECEMBER));
    h.setDayOfMonth(31);
    h.setDayOfWeek(0);
    h.setBuiltin(true);
    h.setActive(true);
    sess.save(h);
        
  }

}
