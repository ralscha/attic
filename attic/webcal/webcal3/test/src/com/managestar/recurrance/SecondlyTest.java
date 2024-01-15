package com.managestar.recurrance;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for secondly recurrences 
 */
public class SecondlyTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public SecondlyTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(SecondlyTest.class);
  }

  /**
   * Test SECONDLY frequency
   */
  public void testSecondly() {

    end = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    end.set(Calendar.YEAR, 2002);
    end.set(Calendar.MONTH, Calendar.JUNE);
    end.set(Calendar.DATE, 27);
    end.set(Calendar.HOUR_OF_DAY, 2);
    end.set(Calendar.MINUTE, 0);
    end.set(Calendar.SECOND, 0);

    testRule("FREQ=SECONDLY;COUNT=5;INTERVAL=59;BYDAY=TH", new String[]{"Thu Jun 27 00:00:30 GMT 2002",
        "Thu Jun 27 00:01:29 GMT 2002", "Thu Jun 27 00:02:28 GMT 2002", "Thu Jun 27 00:03:27 GMT 2002",
        "Thu Jun 27 00:04:26 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=5;INTERVAL=59;BYDAY=WE", new String[]{"Wed Jun 26 19:54:40 GMT 2002",
        "Wed Jun 26 19:55:39 GMT 2002", "Wed Jun 26 19:56:38 GMT 2002", "Wed Jun 26 19:57:37 GMT 2002",
        "Wed Jun 26 19:58:36 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=5;INTERVAL=15;BYMONTHDAY=27", new String[]{"Thu Jun 27 00:00:10 GMT 2002",
        "Thu Jun 27 00:00:25 GMT 2002", "Thu Jun 27 00:00:40 GMT 2002", "Thu Jun 27 00:00:55 GMT 2002",
        "Thu Jun 27 00:01:10 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=5;INTERVAL=15;BYMONTHDAY=26", new String[]{"Wed Jun 26 19:54:40 GMT 2002",
        "Wed Jun 26 19:54:55 GMT 2002", "Wed Jun 26 19:55:10 GMT 2002", "Wed Jun 26 19:55:25 GMT 2002",
        "Wed Jun 26 19:55:40 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=5;INTERVAL=15;BYYEARDAY=178", new String[]{"Thu Jun 27 00:00:10 GMT 2002",
        "Thu Jun 27 00:00:25 GMT 2002", "Thu Jun 27 00:00:40 GMT 2002", "Thu Jun 27 00:00:55 GMT 2002",
        "Thu Jun 27 00:01:10 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=5;INTERVAL=15;BYYEARDAY=177", new String[]{"Wed Jun 26 19:54:40 GMT 2002",
        "Wed Jun 26 19:54:55 GMT 2002", "Wed Jun 26 19:55:10 GMT 2002", "Wed Jun 26 19:55:25 GMT 2002",
        "Wed Jun 26 19:55:40 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;", new String[]{"Thu Jun 27 00:00:10 GMT 2002",
        "Thu Jun 27 00:00:25 GMT 2002", "Thu Jun 27 00:00:40 GMT 2002", "Thu Jun 27 00:00:55 GMT 2002",
        "Thu Jun 27 00:01:10 GMT 2002", "Thu Jun 27 00:01:25 GMT 2002", "Thu Jun 27 00:01:40 GMT 2002",
        "Thu Jun 27 00:01:55 GMT 2002", "Thu Jun 27 00:02:10 GMT 2002", "Thu Jun 27 00:02:25 GMT 2002",
        "Thu Jun 27 00:02:40 GMT 2002", "Thu Jun 27 00:02:55 GMT 2002", "Thu Jun 27 00:03:10 GMT 2002",
        "Thu Jun 27 00:03:25 GMT 2002", "Thu Jun 27 00:03:40 GMT 2002", "Thu Jun 27 00:03:55 GMT 2002",
        "Thu Jun 27 00:04:10 GMT 2002", "Thu Jun 27 00:04:25 GMT 2002", "Thu Jun 27 00:04:40 GMT 2002",
        "Thu Jun 27 00:04:55 GMT 2002",});

    testRule("FREQ=SECONDLY;UNTIL=20020627T000125Z;INTERVAL=15;BYHOUR=0,2,4,6,8;", new String[]{
        "Thu Jun 27 00:00:10 GMT 2002", "Thu Jun 27 00:00:25 GMT 2002", "Thu Jun 27 00:00:40 GMT 2002",
        "Thu Jun 27 00:00:55 GMT 2002", "Thu Jun 27 00:01:10 GMT 2002", "Thu Jun 27 00:01:25 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=10;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=-5MIN", new String[]{
        "Wed Jun 26 23:55:10 GMT 2002", "Wed Jun 26 23:55:25 GMT 2002", "Wed Jun 26 23:55:40 GMT 2002",
        "Wed Jun 26 23:55:55 GMT 2002", "Wed Jun 26 23:56:10 GMT 2002", "Wed Jun 26 23:56:25 GMT 2002",
        "Wed Jun 26 23:56:40 GMT 2002", "Wed Jun 26 23:56:55 GMT 2002", "Wed Jun 26 23:57:10 GMT 2002",
        "Wed Jun 26 23:57:25 GMT 2002"});

    testRule("FREQ=SECONDLY;COUNT=10;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=3HOUR", new String[]{
        "Wed Jun 27 03:00:10 GMT 2002", "Wed Jun 27 03:00:25 GMT 2002", "Wed Jun 27 03:00:40 GMT 2002",
        "Wed Jun 27 03:00:55 GMT 2002", "Wed Jun 27 03:01:10 GMT 2002", "Wed Jun 27 03:01:25 GMT 2002",
        "Wed Jun 27 03:01:40 GMT 2002", "Wed Jun 27 03:01:55 GMT 2002", "Wed Jun 27 03:02:10 GMT 2002",
        "Wed Jun 27 03:02:25 GMT 2002",});

    testRule("FREQ=SECONDLY;COUNT=10;INTERVAL=15;BYHOUR=0,2,4,6,8;OFFSET=-4DATE", new String[]{
        "Wed Jun 23 00:00:10 GMT 2002", "Wed Jun 23 00:00:25 GMT 2002", "Wed Jun 23 00:00:40 GMT 2002",
        "Wed Jun 23 00:00:55 GMT 2002", "Wed Jun 23 00:01:10 GMT 2002", "Wed Jun 23 00:01:25 GMT 2002",
        "Wed Jun 23 00:01:40 GMT 2002", "Wed Jun 23 00:01:55 GMT 2002", "Wed Jun 23 00:02:10 GMT 2002",
        "Wed Jun 23 00:02:25 GMT 2002",

    });

    start.set(Calendar.YEAR, 2002);
    start.set(Calendar.MONTH, Calendar.JUNE);
    start.set(Calendar.DATE, 30);
    start.set(Calendar.HOUR_OF_DAY, 23);
    start.set(Calendar.MINUTE, 54);
    start.set(Calendar.SECOND, 40);

    end.set(Calendar.YEAR, 2002);
    end.set(Calendar.MONTH, Calendar.JULY);
    end.set(Calendar.DATE, 1);
    end.set(Calendar.HOUR_OF_DAY, 00);
    end.set(Calendar.MINUTE, 01);
    end.set(Calendar.SECOND, 30);

    testRule("FREQ=SECONDLY;COUNT=5;INTERVAL=15;BYMONTH=7", new String[]{"Mon Jul 01 00:00:10 GMT 2002",
        "Mon Jul 01 00:00:25 GMT 2002", "Mon Jul 01 00:00:40 GMT 2002", "Mon Jul 01 00:00:55 GMT 2002",
        "Mon Jul 01 00:01:10 GMT 2002",});

  }
}
