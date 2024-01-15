package com.managestar.recurrance;


import junit.framework.Test;
import junit.framework.TestSuite;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;



/**
 * Unit test for minutely recurrences 
 */
public class MinutelyTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public MinutelyTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(MinutelyTest.class);
  }



  /**
   * Test MINUTELY frequency
   */
  public void testMinutely() {

    end = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    end.set(Calendar.YEAR, 2002);
    end.set(Calendar.MONTH, Calendar.JUNE);
    end.set(Calendar.DATE, 27);
    end.set(Calendar.HOUR_OF_DAY, 19);
    end.set(Calendar.MINUTE, 54);
    end.set(Calendar.SECOND, 40);


    testRule("FREQ=MINUTELY;COUNT=5;INTERVAL=15;BYDAY=TH",
            new String[] {
              "Thu Jun 27 00:09:40 GMT 2002",
              "Thu Jun 27 00:24:40 GMT 2002",
              "Thu Jun 27 00:39:40 GMT 2002",
              "Thu Jun 27 00:54:40 GMT 2002",
              "Thu Jun 27 01:09:40 GMT 2002",
            });
    
    testRule("FREQ=MINUTELY;COUNT=5;INTERVAL=15;BYDAY=WE",
            new String[] {
              "Wed Jun 26 19:54:40 GMT 2002",
              "Wed Jun 26 20:09:40 GMT 2002",
              "Wed Jun 26 20:24:40 GMT 2002",
              "Wed Jun 26 20:39:40 GMT 2002",
              "Wed Jun 26 20:54:40 GMT 2002",
            });    
    
    testRule("FREQ=MINUTELY;COUNT=5;INTERVAL=15;BYMONTHDAY=27",
            new String[] {
              "Thu Jun 27 00:09:40 GMT 2002",
              "Thu Jun 27 00:24:40 GMT 2002",
              "Thu Jun 27 00:39:40 GMT 2002",
              "Thu Jun 27 00:54:40 GMT 2002",
              "Thu Jun 27 01:09:40 GMT 2002",
            });
    
    testRule("FREQ=MINUTELY;COUNT=5;INTERVAL=15;BYMONTHDAY=26",
            new String[] {
              "Wed Jun 26 19:54:40 GMT 2002",
              "Wed Jun 26 20:09:40 GMT 2002",
              "Wed Jun 26 20:24:40 GMT 2002",
              "Wed Jun 26 20:39:40 GMT 2002",
              "Wed Jun 26 20:54:40 GMT 2002",
            });       

    testRule("FREQ=MINUTELY;COUNT=5;INTERVAL=15;BYYEARDAY=178",
            new String[] {
              "Thu Jun 27 00:09:40 GMT 2002",
              "Thu Jun 27 00:24:40 GMT 2002",
              "Thu Jun 27 00:39:40 GMT 2002",
              "Thu Jun 27 00:54:40 GMT 2002",
              "Thu Jun 27 01:09:40 GMT 2002",
            });
    
    testRule("FREQ=MINUTELY;COUNT=5;INTERVAL=15;BYYEARDAY=177",
            new String[] {
              "Wed Jun 26 19:54:40 GMT 2002",
              "Wed Jun 26 20:09:40 GMT 2002",
              "Wed Jun 26 20:24:40 GMT 2002",
              "Wed Jun 26 20:39:40 GMT 2002",
              "Wed Jun 26 20:54:40 GMT 2002",
            });       

    testRule("FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;",
             new String[] {
               "Thu Jun 27 00:09:40 GMT 2002",
               "Thu Jun 27 00:24:40 GMT 2002",
               "Thu Jun 27 00:39:40 GMT 2002",
               "Thu Jun 27 00:54:40 GMT 2002",
               "Thu Jun 27 02:09:40 GMT 2002",
               "Thu Jun 27 02:24:40 GMT 2002",
               "Thu Jun 27 02:39:40 GMT 2002",
               "Thu Jun 27 02:54:40 GMT 2002",
               "Thu Jun 27 04:09:40 GMT 2002",
               "Thu Jun 27 04:24:40 GMT 2002",
               "Thu Jun 27 04:39:40 GMT 2002",
               "Thu Jun 27 04:54:40 GMT 2002",
               "Thu Jun 27 06:09:40 GMT 2002",
               "Thu Jun 27 06:24:40 GMT 2002",
               "Thu Jun 27 06:39:40 GMT 2002",
               "Thu Jun 27 06:54:40 GMT 2002",
               "Thu Jun 27 08:09:40 GMT 2002",
               "Thu Jun 27 08:24:40 GMT 2002",
               "Thu Jun 27 08:39:40 GMT 2002",
               "Thu Jun 27 08:54:40 GMT 2002",
             });

    testRule("FREQ=MINUTELY;UNTIL=20020627T042440Z;INTERVAL=15;BYHOUR=0,2,4,6,8;",
             new String[] {
               "Thu Jun 27 00:09:40 GMT 2002",
               "Thu Jun 27 00:24:40 GMT 2002",
               "Thu Jun 27 00:39:40 GMT 2002",
               "Thu Jun 27 00:54:40 GMT 2002",
               "Thu Jun 27 02:09:40 GMT 2002",
               "Thu Jun 27 02:24:40 GMT 2002",
               "Thu Jun 27 02:39:40 GMT 2002",
               "Thu Jun 27 02:54:40 GMT 2002",
               "Thu Jun 27 04:09:40 GMT 2002",
               "Thu Jun 27 04:24:40 GMT 2002"
             });


    testRule("FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=-5MIN",
             new String[] {
               "Thu Jun 27 00:04:40 GMT 2002",
               "Thu Jun 27 00:19:40 GMT 2002",
               "Thu Jun 27 00:34:40 GMT 2002",
               "Thu Jun 27 00:49:40 GMT 2002",
               "Thu Jun 27 02:04:40 GMT 2002",
               "Thu Jun 27 02:19:40 GMT 2002",
               "Thu Jun 27 02:34:40 GMT 2002",
               "Thu Jun 27 02:49:40 GMT 2002",
               "Thu Jun 27 04:04:40 GMT 2002",
               "Thu Jun 27 04:19:40 GMT 2002",
               "Thu Jun 27 04:34:40 GMT 2002",
               "Thu Jun 27 04:49:40 GMT 2002",
               "Thu Jun 27 06:04:40 GMT 2002",
               "Thu Jun 27 06:19:40 GMT 2002",
               "Thu Jun 27 06:34:40 GMT 2002",
               "Thu Jun 27 06:49:40 GMT 2002",
               "Thu Jun 27 08:04:40 GMT 2002",
               "Thu Jun 27 08:19:40 GMT 2002",
               "Thu Jun 27 08:34:40 GMT 2002",
               "Thu Jun 27 08:49:40 GMT 2002",
             });


    testRule("FREQ=MINUTELY;COUNT=10;INTERVAL=15;BYHOUR=0,2,4,6,8;BYSECOND=7;OFFSET=-5MIN",
             new String[] {
               "Thu Jun 27 00:04:07 GMT 2002",
               "Thu Jun 27 00:19:07 GMT 2002",
               "Thu Jun 27 00:34:07 GMT 2002",
               "Thu Jun 27 00:49:07 GMT 2002",
               "Thu Jun 27 02:04:07 GMT 2002",
               "Thu Jun 27 02:19:07 GMT 2002",
               "Thu Jun 27 02:34:07 GMT 2002",
               "Thu Jun 27 02:49:07 GMT 2002",
               "Thu Jun 27 04:04:07 GMT 2002",
               "Thu Jun 27 04:19:07 GMT 2002",
             });


    testRule("FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=3HOUR",
             new String[] {
               "Thu Jun 27 03:09:40 GMT 2002",
               "Thu Jun 27 03:24:40 GMT 2002",
               "Thu Jun 27 03:39:40 GMT 2002",
               "Thu Jun 27 03:54:40 GMT 2002",
               "Thu Jun 27 05:09:40 GMT 2002",
               "Thu Jun 27 05:24:40 GMT 2002",
               "Thu Jun 27 05:39:40 GMT 2002",
               "Thu Jun 27 05:54:40 GMT 2002",
               "Thu Jun 27 07:09:40 GMT 2002",
               "Thu Jun 27 07:24:40 GMT 2002",
               "Thu Jun 27 07:39:40 GMT 2002",
               "Thu Jun 27 07:54:40 GMT 2002",
               "Thu Jun 27 09:09:40 GMT 2002",
               "Thu Jun 27 09:24:40 GMT 2002",
               "Thu Jun 27 09:39:40 GMT 2002",
               "Thu Jun 27 09:54:40 GMT 2002",
               "Thu Jun 27 11:09:40 GMT 2002",
               "Thu Jun 27 11:24:40 GMT 2002",
               "Thu Jun 27 11:39:40 GMT 2002",
               "Thu Jun 27 11:54:40 GMT 2002",
             });

    testRule("FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=3HOUR",
             new String[] {
               "Thu Jun 27 03:09:40 GMT 2002",
               "Thu Jun 27 03:24:40 GMT 2002",
               "Thu Jun 27 03:39:40 GMT 2002",
               "Thu Jun 27 03:54:40 GMT 2002",
               "Thu Jun 27 05:09:40 GMT 2002",
               "Thu Jun 27 05:24:40 GMT 2002",
               "Thu Jun 27 05:39:40 GMT 2002",
               "Thu Jun 27 05:54:40 GMT 2002",
               "Thu Jun 27 07:09:40 GMT 2002",
               "Thu Jun 27 07:24:40 GMT 2002",
               "Thu Jun 27 07:39:40 GMT 2002",
               "Thu Jun 27 07:54:40 GMT 2002",
               "Thu Jun 27 09:09:40 GMT 2002",
               "Thu Jun 27 09:24:40 GMT 2002",
               "Thu Jun 27 09:39:40 GMT 2002",
               "Thu Jun 27 09:54:40 GMT 2002",
               "Thu Jun 27 11:09:40 GMT 2002",
               "Thu Jun 27 11:24:40 GMT 2002",
               "Thu Jun 27 11:39:40 GMT 2002",
               "Thu Jun 27 11:54:40 GMT 2002",
             });



    testRule("FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=-4DATE",
             new String[] {
               "Thu Jun 23 00:09:40 GMT 2002",
               "Thu Jun 23 00:24:40 GMT 2002",
               "Thu Jun 23 00:39:40 GMT 2002",
               "Thu Jun 23 00:54:40 GMT 2002",
               "Thu Jun 23 02:09:40 GMT 2002",
               "Thu Jun 23 02:24:40 GMT 2002",
               "Thu Jun 23 02:39:40 GMT 2002",
               "Thu Jun 23 02:54:40 GMT 2002",
               "Thu Jun 23 04:09:40 GMT 2002",
               "Thu Jun 23 04:24:40 GMT 2002",
               "Thu Jun 23 04:39:40 GMT 2002",
               "Thu Jun 23 04:54:40 GMT 2002",
               "Thu Jun 23 06:09:40 GMT 2002",
               "Thu Jun 23 06:24:40 GMT 2002",
               "Thu Jun 23 06:39:40 GMT 2002",
               "Thu Jun 23 06:54:40 GMT 2002",
               "Thu Jun 23 08:09:40 GMT 2002",
               "Thu Jun 23 08:24:40 GMT 2002",
               "Thu Jun 23 08:39:40 GMT 2002",
               "Thu Jun 23 08:54:40 GMT 2002",
             });


    end.set(Calendar.YEAR, 2002);
    end.set(Calendar.MONTH, Calendar.JULY);
    end.set(Calendar.DATE, 1);
    end.set(Calendar.HOUR_OF_DAY, 19);
    end.set(Calendar.MINUTE, 54);
    end.set(Calendar.SECOND, 40);
    
    testRule("FREQ=MINUTELY;COUNT=5;INTERVAL=15;BYSECOND=10;BYMONTH=7",
            new String[] {
              "Mon Jul 01 00:09:10 GMT 2002",
              "Mon Jul 01 00:24:10 GMT 2002",
              "Mon Jul 01 00:39:10 GMT 2002",
              "Mon Jul 01 00:54:10 GMT 2002",
              "Mon Jul 01 01:09:10 GMT 2002",                            
            });

  }
}
