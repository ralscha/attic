package com.managestar.recurrance;




import java.util.*;

import junit.framework.*;

/**
 * Unit test for monthly recurrences
 */
public class MonthlyTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public MonthlyTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(MonthlyTest.class);
  }



  /**
   * Test MONTHLY frequency
   */
  public void testMonthly() {
    

    
    
    testRule("FREQ=MONTHLY;COUNT=5;INTERVAL=2",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Mon Aug 26 19:54:40 GMT 2002",
               "Sat Oct 26 19:54:40 GMT 2002",
               "Thu Dec 26 19:54:40 GMT 2002",
               "Wed Feb 26 19:54:40 GMT 2003"
             });   
          

    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20030226",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Mon Aug 26 19:54:40 GMT 2002",
               "Sat Oct 26 19:54:40 GMT 2002",
               "Thu Dec 26 19:54:40 GMT 2002",
               "Wed Feb 26 19:54:40 GMT 2003"
             }, false);  


    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20030225",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Mon Aug 26 19:54:40 GMT 2002",
               "Sat Oct 26 19:54:40 GMT 2002",
               "Thu Dec 26 19:54:40 GMT 2002"
             }, false);  

    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20020626",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
             }, false);  

    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20020225",
             new String[] {
             }, false);  
             

    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20030226T195440Z",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Mon Aug 26 19:54:40 GMT 2002",
               "Sat Oct 26 19:54:40 GMT 2002",
               "Thu Dec 26 19:54:40 GMT 2002",
               "Wed Feb 26 19:54:40 GMT 2003"
             }, false);  
    
    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20030226T195439Z",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Mon Aug 26 19:54:40 GMT 2002",
               "Sat Oct 26 19:54:40 GMT 2002",
               "Thu Dec 26 19:54:40 GMT 2002"
             }, false);      
         
    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20020626T195440Z",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002"
             }, false);      
                      
    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20020626T195441Z",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002"
             }, false);
                   
    testRule("FREQ=MONTHLY;INTERVAL=2;UNTIL=20020626T195439Z",
             new String[] {               
             }, false);      
             
             

    testRule("FREQ=MONTHLY;COUNT=5;INTERVAL=2;BYYEARDAY=122",
             new String[] {
               "Fri May 02 21:54:40 CEST 2003",
               "Sat May 01 21:54:40 CEST 2004",
               "Mon May 02 21:54:40 CEST 2005",
               "Tue May 02 21:54:40 CEST 2006",
               "Wed May 02 21:54:40 CEST 2007"
             });   



    testRule("FREQ=MONTHLY;INTERVAL=7;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0",
             new String[] {
               "Sun Jan 26 00:00:00 GMT 2003",
               "Sun Jan 26 12:00:00 GMT 2003",
               "Tue Aug 26 00:00:00 GMT 2003",
               "Tue Aug 26 12:00:00 GMT 2003",
               "Fri Mar 26 00:00:00 GMT 2004",
               "Fri Mar 26 12:00:00 GMT 2004",
               "Tue Oct 26 00:00:00 GMT 2004",
               "Tue Oct 26 12:00:00 GMT 2004",
               "Thu May 26 00:00:00 GMT 2005",
               "Thu May 26 12:00:00 GMT 2005",
               "Mon Dec 26 00:00:00 GMT 2005",
               "Mon Dec 26 12:00:00 GMT 2005",
               "Wed Jul 26 00:00:00 GMT 2006",
               "Wed Jul 26 12:00:00 GMT 2006",
               "Mon Feb 26 00:00:00 GMT 2007",
               "Mon Feb 26 12:00:00 GMT 2007"
             }); 


    testRule("FREQ=MONTHLY;COUNT=10;BYDAY=1FR",
             new String[] {
               "Fri Jul 05 19:54:40 GMT 2002",
               "Fri Aug 02 19:54:40 GMT 2002",
               "Fri Sep 06 19:54:40 GMT 2002",
               "Fri Oct 04 19:54:40 GMT 2002",
               "Fri Nov 01 19:54:40 GMT 2002",
               "Fri Dec 06 19:54:40 GMT 2002",
               "Fri Jan 03 19:54:40 GMT 2003",
               "Fri Feb 07 19:54:40 GMT 2003",
               "Fri Mar 07 19:54:40 GMT 2003",
               "Fri Apr 04 19:54:40 GMT 2003",
             }); 

    testRule("FREQ=MONTHLY;INTERVAL=2;COUNT=10;BYDAY=1SU,-1SU",
             new String[] {
               "Sun Jun 30 19:54:40 GMT 2002",
               "Sun Aug 04 19:54:40 GMT 2002",
               "Sun Aug 25 19:54:40 GMT 2002",
               "Sun Oct 06 19:54:40 GMT 2002",
               "Sun Oct 27 19:54:40 GMT 2002",
               "Sun Dec 01 19:54:40 GMT 2002",
               "Sun Dec 29 19:54:40 GMT 2002",
               "Sun Feb 02 19:54:40 GMT 2003",
               "Sun Feb 23 19:54:40 GMT 2003",
               "Sun Apr 06 19:54:40 GMT 2003",
             }); 

    testRule("FREQ=MONTHLY;COUNT=6;BYDAY=-2MO",
             new String[] {
               "Mon Jul 22 19:54:40 GMT 2002",
               "Mon Aug 19 19:54:40 GMT 2002",
               "Mon Sep 23 19:54:40 GMT 2002",
               "Mon Oct 21 19:54:40 GMT 2002",
               "Mon Nov 18 19:54:40 GMT 2002",
               "Mon Dec 23 19:54:40 GMT 2002",
             }); 

    testRule("FREQ=MONTHLY;BYMONTHDAY=-3;COUNT=10",
             new String[] {
               "Fri Jun 28 19:54:40 GMT 2002",
               "Mon Jul 29 19:54:40 GMT 2002",
               "Thu Aug 29 19:54:40 GMT 2002",
               "Sat Sep 28 19:54:40 GMT 2002",
               "Tue Oct 29 19:54:40 GMT 2002",
               "Thu Nov 28 19:54:40 GMT 2002",
               "Sun Dec 29 19:54:40 GMT 2002",
               "Wed Jan 29 19:54:40 GMT 2003",
               "Wed Feb 26 19:54:40 GMT 2003",
               "Sat Mar 29 19:54:40 GMT 2003",
             }); 

    testRule("FREQ=MONTHLY;COUNT=10;BYMONTHDAY=2,15",
             new String[] {
               "Tue Jul 02 19:54:40 GMT 2002",
               "Mon Jul 15 19:54:40 GMT 2002",
               "Fri Aug 02 19:54:40 GMT 2002",
               "Thu Aug 15 19:54:40 GMT 2002",
               "Mon Sep 02 19:54:40 GMT 2002",
               "Sun Sep 15 19:54:40 GMT 2002",
               "Wed Oct 02 19:54:40 GMT 2002",
               "Tue Oct 15 19:54:40 GMT 2002",
               "Sat Nov 02 19:54:40 GMT 2002",
               "Fri Nov 15 19:54:40 GMT 2002",
             }); 

    testRule("FREQ=MONTHLY;COUNT=10;BYMONTHDAY=1,-1",
             new String[] {
               "Sun Jun 30 19:54:40 GMT 2002",
               "Mon Jul 01 19:54:40 GMT 2002",
               "Wed Jul 31 19:54:40 GMT 2002",
               "Thu Aug 01 19:54:40 GMT 2002",
               "Sat Aug 31 19:54:40 GMT 2002",
               "Sun Sep 01 19:54:40 GMT 2002",
               "Mon Sep 30 19:54:40 GMT 2002",
               "Tue Oct 01 19:54:40 GMT 2002",
               "Thu Oct 31 19:54:40 GMT 2002",
               "Fri Nov 01 19:54:40 GMT 2002",
             }); 

    testRule("FREQ=MONTHLY;INTERVAL=18;COUNT=10;BYMONTHDAY=10,11,12,13,14",
             new String[] {
               "Wed Dec 10 19:54:40 GMT 2003",
               "Thu Dec 11 19:54:40 GMT 2003",
               "Fri Dec 12 19:54:40 GMT 2003",
               "Sat Dec 13 19:54:40 GMT 2003",
               "Sun Dec 14 19:54:40 GMT 2003",
               "Fri Jun 10 19:54:40 GMT 2005",
               "Sat Jun 11 19:54:40 GMT 2005",
               "Sun Jun 12 19:54:40 GMT 2005",
               "Mon Jun 13 19:54:40 GMT 2005",
               "Tue Jun 14 19:54:40 GMT 2005",
             }); 

    testRule("FREQ=MONTHLY;BYDAY=FR;BYMONTHDAY=13;COUNT=10",
             new String[] {
               "Fri Sep 13 19:54:40 GMT 2002",
               "Fri Dec 13 19:54:40 GMT 2002",
               "Fri Jun 13 19:54:40 GMT 2003",
               "Fri Feb 13 19:54:40 GMT 2004",
               "Fri Aug 13 19:54:40 GMT 2004",
               "Fri May 13 19:54:40 GMT 2005",
               "Fri Jan 13 19:54:40 GMT 2006",
               "Fri Oct 13 19:54:40 GMT 2006",
               "Fri Apr 13 19:54:40 GMT 2007",
             });

    testRule("FREQ=MONTHLY;BYDAY=SA;BYMONTHDAY=7,8,9,10,11,12,13;COUNT=30",
             new String[] {
               "Sat Jul 13 19:54:40 GMT 2002",
               "Sat Aug 10 19:54:40 GMT 2002",
               "Sat Sep 07 19:54:40 GMT 2002",
               "Sat Oct 12 19:54:40 GMT 2002",
               "Sat Nov 09 19:54:40 GMT 2002",
               "Sat Dec 07 19:54:40 GMT 2002",
               "Sat Jan 11 19:54:40 GMT 2003",
               "Sat Feb 08 19:54:40 GMT 2003",
               "Sat Mar 08 19:54:40 GMT 2003",
               "Sat Apr 12 19:54:40 GMT 2003",
               "Sat May 10 19:54:40 GMT 2003",
               "Sat Jun 07 19:54:40 GMT 2003",
               "Sat Jul 12 19:54:40 GMT 2003",
               "Sat Aug 09 19:54:40 GMT 2003",
               "Sat Sep 13 19:54:40 GMT 2003",
               "Sat Oct 11 19:54:40 GMT 2003",
               "Sat Nov 08 19:54:40 GMT 2003",
               "Sat Dec 13 19:54:40 GMT 2003",
               "Sat Jan 10 19:54:40 GMT 2004",
               "Sat Feb 07 19:54:40 GMT 2004",
               "Sat Mar 13 19:54:40 GMT 2004",
               "Sat Apr 10 19:54:40 GMT 2004",
               "Sat May 08 19:54:40 GMT 2004",
               "Sat Jun 12 19:54:40 GMT 2004",
               "Sat Jul 10 19:54:40 GMT 2004",
               "Sat Aug 07 19:54:40 GMT 2004",
               "Sat Sep 11 19:54:40 GMT 2004",
               "Sat Oct 09 19:54:40 GMT 2004",
               "Sat Nov 13 19:54:40 GMT 2004",
               "Sat Dec 11 19:54:40 GMT 2004",
             });


    String rule = "FREQ=MONTHLY;INTERVAL=1";
    Recurrance r = new Recurrance(rule, start, end);
    assertEquals(r.getAllMatchingDates().size(), 60);

    r = new Recurrance(RecurranceRuleXml.convertRRuleToXml(rule), start, end);
    assertEquals(r.getAllMatchingDates().size(), 60);



    end = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    end.set(Calendar.YEAR, 2002);
    end.set(Calendar.MONTH, Calendar.AUGUST);
    end.set(Calendar.DATE, 26);
    end.set(Calendar.HOUR_OF_DAY, 19);
    end.set(Calendar.MINUTE, 54);
    end.set(Calendar.SECOND, 40);
    end.set(Calendar.MILLISECOND, 0);
    
    
    testRule("FREQ=MONTHLY;BYDAY=TU,WE,TH;BYSETPOS=1,-1",
             new String[] {
                "Wed Jun 26 21:54:40 CEST 2002",
                "Thu Jun 27 21:54:40 CEST 2002",
                "Tue Jul 02 21:54:40 CEST 2002",
                "Wed Jul 31 21:54:40 CEST 2002",
                "Thu Aug 01 21:54:40 CEST 2002",
                "Thu Aug 22 21:54:40 CEST 2002"
             }, false);
             
      
                            

  }
}
