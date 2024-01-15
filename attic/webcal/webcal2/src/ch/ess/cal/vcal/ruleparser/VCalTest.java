package ch.ess.cal.vcal.ruleparser;

import java.util.Iterator;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.managestar.recurrance.Recurrance;

public class VCalTest {

  public static void main(String[] args) {
    
    try {
           
      
      Calendar start = new GregorianCalendar();
      start.set(Calendar.YEAR, 2003);
      start.set(Calendar.MONTH, Calendar.JANUARY);
      start.set(Calendar.DATE, 26);
      start.set(Calendar.HOUR_OF_DAY, 19);
      start.set(Calendar.MINUTE, 54);
      start.set(Calendar.SECOND, 40);
      start.set(Calendar.MILLISECOND, 0);
    

      Calendar end = new GregorianCalendar();
      end.set(Calendar.YEAR, 2004);
      end.set(Calendar.MONTH, Calendar.JANUARY);
      end.set(Calendar.DATE, 31);
      end.set(Calendar.HOUR_OF_DAY, 19);
      end.set(Calendar.MINUTE, 55);
      end.set(Calendar.SECOND, 40);
      end.set(Calendar.MILLISECOND, 0);
    
      String s = VCalRuleParser.getICalRule("D1 #10");    
      Recurrance r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("D1 20030301T000000Z");    
      r = new Recurrance(s, start, end);
            
      s = VCalRuleParser.getICalRule("D10 #5");    
      r = new Recurrance(s, start, end);
            

      s = VCalRuleParser.getICalRule("W1 #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("W1 20030301T000000Z");    
      r = new Recurrance(s, start, end);

      s = VCalRuleParser.getICalRule("W2 #0");    
      r = new Recurrance(s, start, end);

      s = VCalRuleParser.getICalRule("W1 TU TH #5");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("W2 MO WE FR 20030301T000000Z");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MP1 1+ FR #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MP1 1+ FR 20030301T000000Z");    
      r = new Recurrance(s, start, end);

      s = VCalRuleParser.getICalRule("MP2 1+ SU 1- SU #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MP6 2+ MO TU WE TH FR #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MP1 2- MO #6");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MD1 3- #0");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MD1 2 15 #10");    
      r = new Recurrance(s, start, end);

      s = VCalRuleParser.getICalRule("MD1 1 LD #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MD1 1 1- #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MD18 10 11 12 13 14 15 #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MD1 2- #5");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("YM1 6 7 #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("YM2 1 2 3 #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("YD3 1 100 200 #10");    
      r = new Recurrance(s, start, end);
      
      s = VCalRuleParser.getICalRule("MP1 1+ SA 20311231");    
      r = new Recurrance(s, start, end);
      
      System.out.println(s);

      
      
      
      for (Iterator it = r.getAllMatchingDates().iterator(); it.hasNext();) {
        Calendar element = (Calendar)it.next();
        System.out.println(element.getTime());
      }
      
      
      
      
      
      
      

      
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (TokenMgrError e) {
      e.printStackTrace();
    }
  }
}
