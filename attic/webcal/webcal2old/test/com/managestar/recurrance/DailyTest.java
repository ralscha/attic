package com.managestar.recurrance;

import junit.framework.Test;
import junit.framework.TestSuite;
import ch.ess.cal.vcal.ruleparser.ParseException;
import ch.ess.cal.vcal.ruleparser.TokenMgrError;
import ch.ess.cal.vcal.ruleparser.VCalRuleParser;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;



/**
 * Unit test for dailiy recurrences
 */
public class DailyTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public DailyTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(DailyTest.class);
  }



  /**
   * Test DAILY frequency
   */
  public void testDaily() {
    
    try {
    
    testRule("FREQ=DAILY;BYMONTHDAY=1;COUNT=5",
             new String[] {
               "Mon Jul 01 19:54:40 GMT 2002",
               "Thu Aug 01 19:54:40 GMT 2002",
               "Sun Sep 01 19:54:40 GMT 2002",
               "Tue Oct 01 19:54:40 GMT 2002",
               "Fri Nov 01 19:54:40 GMT 2002",
             });  

    testRule("FREQ=DAILY;BYYEARDAY=22;COUNT=5",
             new String[] {
               "Wed Jan 22 19:54:40 GMT 2003",
               "Thu Jan 22 19:54:40 GMT 2004",
               "Sat Jan 22 19:54:40 GMT 2005",
               "Sun Jan 22 19:54:40 GMT 2006",
               "Mon Jan 22 19:54:40 GMT 2007",
             });  
   
    testRule("FREQ=DAILY;BYMONTH=2;COUNT=5",
             new String[] {
               "Sat Feb 01 19:54:40 GMT 2003",
               "Sun Feb 02 19:54:40 GMT 2003",
               "Mon Feb 03 19:54:40 GMT 2003",
               "Tue Feb 04 19:54:40 GMT 2003",
               "Wed Feb 05 19:54:40 GMT 2003",
             });      
                 
    
    testRule("FREQ=DAILY;COUNT=20;INTERVAL=3;BYHOUR=10;BYMINUTE=30;BYSECOND=0",
             new String[] {
               "Sat Jun 29 10:30:00 GMT 2002",
               "Tue Jul 02 10:30:00 GMT 2002",
               "Fri Jul 05 10:30:00 GMT 2002",
               "Mon Jul 08 10:30:00 GMT 2002",
               "Thu Jul 11 10:30:00 GMT 2002",
               "Sun Jul 14 10:30:00 GMT 2002",
               "Wed Jul 17 10:30:00 GMT 2002",
               "Sat Jul 20 10:30:00 GMT 2002",
               "Tue Jul 23 10:30:00 GMT 2002",
               "Fri Jul 26 10:30:00 GMT 2002",
               "Mon Jul 29 10:30:00 GMT 2002",
               "Thu Aug 01 10:30:00 GMT 2002",
               "Sun Aug 04 10:30:00 GMT 2002",
               "Wed Aug 07 10:30:00 GMT 2002",
               "Sat Aug 10 10:30:00 GMT 2002",
               "Tue Aug 13 10:30:00 GMT 2002",
               "Fri Aug 16 10:30:00 GMT 2002",
               "Mon Aug 19 10:30:00 GMT 2002",
               "Thu Aug 22 10:30:00 GMT 2002",
               "Sun Aug 25 10:30:00 GMT 2002",
             });    
    
    
    
    start = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    start.set(Calendar.YEAR, 1997);
    start.set(Calendar.MONTH, Calendar.SEPTEMBER);
    start.set(Calendar.DATE, 2);
    start.set(Calendar.HOUR_OF_DAY, 9);
    start.set(Calendar.MINUTE, 0);
    start.set(Calendar.SECOND, 0);  
    start.set(Calendar.MILLISECOND, 0);  
    
    testRule("FREQ=DAILY;UNTIL=19970917T000000Z",
             new String[] {
               "Tue Sep 02 09:00:00 GMT 1997",
               "Wed Sep 03 09:00:00 GMT 1997",
               "Thu Sep 04 09:00:00 GMT 1997",
               "Fri Sep 05 09:00:00 GMT 1997",
               "Sat Sep 06 09:00:00 GMT 1997",
               "Sun Sep 07 09:00:00 GMT 1997",
               "Mon Sep 08 09:00:00 GMT 1997",
               "Tue Sep 09 09:00:00 GMT 1997",
               "Wed Sep 10 09:00:00 GMT 1997",
               "Thu Sep 11 09:00:00 GMT 1997",
               "Fri Sep 12 09:00:00 GMT 1997",
               "Sat Sep 13 09:00:00 GMT 1997",
               "Sun Sep 14 09:00:00 GMT 1997",
               "Mon Sep 15 09:00:00 GMT 1997",
               "Tue Sep 16 09:00:00 GMT 1997",
             }); 
    String rule = VCalRuleParser.getICalRule("D1 19970917T000000Z");
    testRule(rule,
             new String[] {
               "Tue Sep 02 09:00:00 GMT 1997",
               "Wed Sep 03 09:00:00 GMT 1997",
               "Thu Sep 04 09:00:00 GMT 1997",
               "Fri Sep 05 09:00:00 GMT 1997",
               "Sat Sep 06 09:00:00 GMT 1997",
               "Sun Sep 07 09:00:00 GMT 1997",
               "Mon Sep 08 09:00:00 GMT 1997",
               "Tue Sep 09 09:00:00 GMT 1997",
               "Wed Sep 10 09:00:00 GMT 1997",
               "Thu Sep 11 09:00:00 GMT 1997",
               "Fri Sep 12 09:00:00 GMT 1997",
               "Sat Sep 13 09:00:00 GMT 1997",
               "Sun Sep 14 09:00:00 GMT 1997",
               "Mon Sep 15 09:00:00 GMT 1997",
               "Tue Sep 16 09:00:00 GMT 1997",
             }); 
                 
             
    testRule("FREQ=DAILY;UNTIL=19970917",
             new String[] {
               "Tue Sep 02 09:00:00 GMT 1997",
               "Wed Sep 03 09:00:00 GMT 1997",
               "Thu Sep 04 09:00:00 GMT 1997",
               "Fri Sep 05 09:00:00 GMT 1997",
               "Sat Sep 06 09:00:00 GMT 1997",
               "Sun Sep 07 09:00:00 GMT 1997",
               "Mon Sep 08 09:00:00 GMT 1997",
               "Tue Sep 09 09:00:00 GMT 1997",
               "Wed Sep 10 09:00:00 GMT 1997",
               "Thu Sep 11 09:00:00 GMT 1997",
               "Fri Sep 12 09:00:00 GMT 1997",
               "Sat Sep 13 09:00:00 GMT 1997",
               "Sun Sep 14 09:00:00 GMT 1997",
               "Mon Sep 15 09:00:00 GMT 1997",
               "Tue Sep 16 09:00:00 GMT 1997",
               "Tue Sep 17 09:00:00 GMT 1997",
             });     
    rule = VCalRuleParser.getICalRule("D1 19970917");
    testRule(rule,
             new String[] {
               "Tue Sep 02 09:00:00 GMT 1997",
               "Wed Sep 03 09:00:00 GMT 1997",
               "Thu Sep 04 09:00:00 GMT 1997",
               "Fri Sep 05 09:00:00 GMT 1997",
               "Sat Sep 06 09:00:00 GMT 1997",
               "Sun Sep 07 09:00:00 GMT 1997",
               "Mon Sep 08 09:00:00 GMT 1997",
               "Tue Sep 09 09:00:00 GMT 1997",
               "Wed Sep 10 09:00:00 GMT 1997",
               "Thu Sep 11 09:00:00 GMT 1997",
               "Fri Sep 12 09:00:00 GMT 1997",
               "Sat Sep 13 09:00:00 GMT 1997",
               "Sun Sep 14 09:00:00 GMT 1997",
               "Mon Sep 15 09:00:00 GMT 1997",
               "Tue Sep 16 09:00:00 GMT 1997",
               "Tue Sep 17 09:00:00 GMT 1997",
             });                   

    rule = VCalRuleParser.getICalRule("D1 #2");
    testRule(rule,
               new String[] {
                 "Tue Sep 02 09:00:00 GMT 1997",
                 "Wed Sep 03 09:00:00 GMT 1997",
               });                   

    rule = VCalRuleParser.getICalRule("D2 #3");
    testRule(rule,
             new String[] {
               "Tue Sep 02 09:00:00 GMT 1997",
               "Thu Sep 04 09:00:00 GMT 1997",
               "Sat Sep 06 09:00:00 GMT 1997",
             });                   



    } catch (ParseException e) {
      e.printStackTrace();
      fail(e.toString());
    } catch (TokenMgrError e) {
      e.printStackTrace();
      fail(e.toString());
    }  

  }
}
