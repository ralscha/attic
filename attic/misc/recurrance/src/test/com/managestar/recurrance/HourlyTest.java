package com.managestar.recurrance;


import java.util.*;

import junit.framework.*;

/**
 * Unit test for houly recurrences
 */
public class HourlyTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public HourlyTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(HourlyTest.class);
  }



  /**
   * Test HOURLY frequency
   */
  public void testHourly() {
    

    end = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    end.set(Calendar.YEAR, 2002);
    end.set(Calendar.MONTH, Calendar.JUNE);
    end.set(Calendar.DATE, 27);
    end.set(Calendar.HOUR_OF_DAY, 19);
    end.set(Calendar.MINUTE, 54);
    end.set(Calendar.SECOND, 40);

    testRule("FREQ=HOURLY;COUNT=50;INTERVAL=1;BYYEARDAY=177",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Wed Jun 26 20:54:40 GMT 2002",
               "Wed Jun 26 21:54:40 GMT 2002",
               "Wed Jun 26 22:54:40 GMT 2002",
               "Wed Jun 26 23:54:40 GMT 2002",
             });


    testRule("FREQ=HOURLY;COUNT=50;INTERVAL=2;BYDAY=TH",
             new String[] {
               "Thu Jun 27 01:54:40 GMT 2002",
               "Thu Jun 27 03:54:40 GMT 2002",
               "Thu Jun 27 05:54:40 GMT 2002",
               "Thu Jun 27 07:54:40 GMT 2002",
               "Thu Jun 27 09:54:40 GMT 2002",
               "Thu Jun 27 11:54:40 GMT 2002",
               "Thu Jun 27 13:54:40 GMT 2002",
               "Thu Jun 27 15:54:40 GMT 2002",
               "Thu Jun 27 17:54:40 GMT 2002",
               "Thu Jun 27 19:54:40 GMT 2002",
             });
             
             
    testRule("FREQ=HOURLY;COUNT=50;INTERVAL=2;BYMONTHDAY=26",             
          new String[] {
            "Thu Jun 26 19:54:40 GMT 2002",
            "Thu Jun 26 21:54:40 GMT 2002",
            "Thu Jun 26 23:54:40 GMT 2002",
          });             


    testRule("FREQ=HOURLY;UNTIL=20020626;INTERVAL=1;BYYEARDAY=177",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Wed Jun 26 20:54:40 GMT 2002",
               "Wed Jun 26 21:54:40 GMT 2002",
               "Wed Jun 26 22:54:40 GMT 2002",
               "Wed Jun 26 23:54:40 GMT 2002",
             });

    testRule("FREQ=HOURLY;UNTIL=20020626T225440Z;INTERVAL=1;BYYEARDAY=177",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Wed Jun 26 20:54:40 GMT 2002",
               "Wed Jun 26 21:54:40 GMT 2002",
               "Wed Jun 26 22:54:40 GMT 2002"
             }, false);
             
    testRule("FREQ=HOURLY;UNTIL=20020626T225439Z;INTERVAL=1;BYYEARDAY=177",
             new String[] {
               "Wed Jun 26 19:54:40 GMT 2002",
               "Wed Jun 26 20:54:40 GMT 2002",
               "Wed Jun 26 21:54:40 GMT 2002"
             }, false);             

    testRule("FREQ=HOURLY;COUNT=20;INTERVAL=2;BYMINUTE=0,15,30,45;",
             new String[] {
               "Wed Jun 26 21:00:40 GMT 2002",
               "Wed Jun 26 21:15:40 GMT 2002",
               "Wed Jun 26 21:30:40 GMT 2002",
               "Wed Jun 26 21:45:40 GMT 2002",
               "Wed Jun 26 23:00:40 GMT 2002",
               "Wed Jun 26 23:15:40 GMT 2002",
               "Wed Jun 26 23:30:40 GMT 2002",
               "Wed Jun 26 23:45:40 GMT 2002",
               "Thu Jun 27 01:00:40 GMT 2002",
               "Thu Jun 27 01:15:40 GMT 2002",
               "Thu Jun 27 01:30:40 GMT 2002",
               "Thu Jun 27 01:45:40 GMT 2002",
               "Thu Jun 27 03:00:40 GMT 2002",
               "Thu Jun 27 03:15:40 GMT 2002",
               "Thu Jun 27 03:30:40 GMT 2002",
               "Thu Jun 27 03:45:40 GMT 2002",
               "Thu Jun 27 05:00:40 GMT 2002",
               "Thu Jun 27 05:15:40 GMT 2002",
               "Thu Jun 27 05:30:40 GMT 2002",
               "Thu Jun 27 05:45:40 GMT 2002",
             });


    testRule("FREQ=HOURLY;COUNT=15;INTERVAL=2;BYMINUTE=0,15,30,45;BYSECOND=11",
             new String[] {
               "Wed Jun 26 21:00:11 GMT 2002",
               "Wed Jun 26 21:15:11 GMT 2002",
               "Wed Jun 26 21:30:11 GMT 2002",
               "Wed Jun 26 21:45:11 GMT 2002",
               "Wed Jun 26 23:00:11 GMT 2002",
               "Wed Jun 26 23:15:11 GMT 2002",
               "Wed Jun 26 23:30:11 GMT 2002",
               "Wed Jun 26 23:45:11 GMT 2002",
               "Thu Jun 27 01:00:11 GMT 2002",
               "Thu Jun 27 01:15:11 GMT 2002",
               "Thu Jun 27 01:30:11 GMT 2002",
               "Thu Jun 27 01:45:11 GMT 2002",
               "Thu Jun 27 03:00:11 GMT 2002",
               "Thu Jun 27 03:15:11 GMT 2002",
               "Thu Jun 27 03:30:11 GMT 2002",
             });


    end.set(Calendar.YEAR, 2002);
    end.set(Calendar.MONTH, Calendar.JULY);
    end.set(Calendar.DATE, 1);
    end.set(Calendar.HOUR_OF_DAY, 19);
    end.set(Calendar.MINUTE, 54);
    end.set(Calendar.SECOND, 40);
    
    testRule("FREQ=HOURLY;COUNT=50;INTERVAL=3;BYMONTH=7",
            new String[] {
              "Mon Jul 01 01:54:40 GMT 2002",
              "Mon Jul 01 04:54:40 GMT 2002",
              "Mon Jul 01 07:54:40 GMT 2002",
              "Mon Jul 01 10:54:40 GMT 2002",
              "Mon Jul 01 13:54:40 GMT 2002",
              "Mon Jul 01 16:54:40 GMT 2002",
              "Mon Jul 01 19:54:40 GMT 2002",
            });       


  }
}
