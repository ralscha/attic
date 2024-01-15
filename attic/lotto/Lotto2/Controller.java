import lotto.*;
import lotto.html.*;
import lotto.util.*;
import lotto.extract.*;
import COM.odi.util.*;
import COM.odi.*;
import java.text.*;
import java.util.*;

public class Controller {
    
    private Calendar nextDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss");    
    private int nextNr, nextJahr;
        
    public Controller(String args[]) {
                
        //Suchen des nächsten Termins        
        DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
        DbManager.startReadTransaction();
        Ausspielungen auss = DbManager.getAusspielungen();        
        Ziehung zie = auss.getLast();
        Calendar lastDate = zie.getDate();                        
        
        nextNr = zie.getNr() + 1;
        nextJahr = zie.getJahr();
        nextDate = calculateNextDate(lastDate);                    
    	
        if (nextJahr != nextDate.get(Calendar.YEAR)) {
           nextJahr = nextDate.get(Calendar.YEAR);
           nextNr   = 1;
        }        

        DbManager.commitTransaction();                
        DbManager.shutdown();        
        
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Letzte Ziehung   : " + dateFormat.format(lastDate.getTime()));
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Nächste Ziehung : " + dateFormat.format(nextDate.getTime()));
 
        new LottoRunner(this);
    	
    }
    
/*
    public void setWaitTime() {
        DbManager.initialize(AppProperties.getInstance().getProperty("database"), false);
        DbManager.startReadTransaction();
        
        Ausspielungen auss = DbManager.getAusspielungen();
        Ziehung lastZie = auss.getLast();
        Calendar lastDate = lastZie.getDate();   
            
        nextNr = lastZie.getNr() + 1;
        
    	  nextJahr = lastZie.getJahr();
        nextDate = calculateNextDate(lastDate);                    
    	
        
    		if (nextJahr != nextDate.get(Calendar.YEAR)) {
           nextJahr = nextDate.get(Calendar.YEAR);
           nextNr   = 1;
        }        
        
    	
        DbManager.commitTransaction();                
        DbManager.shutdown();        
           
        waitDate.setTime(nextDate.getTime());            
        alarm.setTime(waitDate.getTime());           
        DiskLog.getInstance().log(DiskLog.INFO, getClass().getName(), "Warten auf : " + dateFormat.format(waitDate.getTime()));
    }
  */  
    public int getNextNr() {
        return nextNr;
    }
    
    public int getNextJahr() {
        return nextJahr;
    }    
        
    public Calendar getNextDate() {
        return nextDate;
    }
        
    public Calendar calculateNextDate(Calendar lDate) {
        Calendar nDate = new GregorianCalendar();
        nDate.setTime(lDate.getTime());
        
        if (lDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            //Samstag
            nDate.add(Calendar.DATE, +3);
            nDate.set(Calendar.AM_PM, Calendar.PM);
            nDate.set(Calendar.HOUR, 6);
            nDate.set(Calendar.MINUTE, 00);
            nDate.set(Calendar.SECOND, 00);
            nDate.setTimeZone(TimeZone.getTimeZone("ECT"));
        } else {
            //Mittwoch    
            nDate.add(Calendar.DATE, +4);
            nDate.set(Calendar.AM_PM, Calendar.PM);
            nDate.set(Calendar.HOUR, 9);
            nDate.set(Calendar.MINUTE, 00);
            nDate.set(Calendar.SECOND, 00);
            nDate.setTimeZone(TimeZone.getTimeZone("ECT"));
        }        
        return nDate;
    }
        		
    public static void main(String args[]) {
        new Controller(args);
    }
}