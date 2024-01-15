package com.managestar.recurrance;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for weekly recurrences
 */
public class WeeklyTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public WeeklyTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(WeeklyTest.class);
  }

  /**
   * Test MONTHLY frequency
   */
  public void testWeekly() {

    testRule("FREQ=WEEKLY;INTERVAL=3;WKST=SU;BYDAY=TU,TH;COUNT=30", new String[]{"Thu Jun 27 19:54:40 GMT 2002",
        "Tue Jul 16 19:54:40 GMT 2002", "Thu Jul 18 19:54:40 GMT 2002", "Tue Aug 06 19:54:40 GMT 2002",
        "Thu Aug 08 19:54:40 GMT 2002", "Tue Aug 27 19:54:40 GMT 2002", "Thu Aug 29 19:54:40 GMT 2002",
        "Tue Sep 17 19:54:40 GMT 2002", "Thu Sep 19 19:54:40 GMT 2002", "Tue Oct 08 19:54:40 GMT 2002",
        "Thu Oct 10 19:54:40 GMT 2002", "Tue Oct 29 19:54:40 GMT 2002", "Thu Oct 31 19:54:40 GMT 2002",
        "Tue Nov 19 19:54:40 GMT 2002", "Thu Nov 21 19:54:40 GMT 2002", "Tue Dec 10 19:54:40 GMT 2002",
        "Thu Dec 12 19:54:40 GMT 2002", "Thu Jan 02 19:54:40 GMT 2003", "Tue Jan 21 19:54:40 GMT 2003",
        "Thu Jan 23 19:54:40 GMT 2003", "Tue Feb 11 19:54:40 GMT 2003", "Thu Feb 13 19:54:40 GMT 2003",
        "Tue Mar 04 19:54:40 GMT 2003", "Thu Mar 06 19:54:40 GMT 2003", "Tue Mar 25 19:54:40 GMT 2003",
        "Thu Mar 27 19:54:40 GMT 2003", "Tue Apr 15 19:54:40 GMT 2003", "Thu Apr 17 19:54:40 GMT 2003",
        "Tue May 06 19:54:40 GMT 2003", "Thu May 08 19:54:40 GMT 2003",});

    testRule("FREQ=WEEKLY;INTERVAL=1;COUNT=10;WKST=SU;BYDAY=TU,TH", new String[]{"Thu Jun 27 19:54:40 GMT 2002",
        "Tue Jul 02 19:54:40 GMT 2002", "Thu Jul 04 19:54:40 GMT 2002", "Tue Jul 09 19:54:40 GMT 2002",
        "Thu Jul 11 19:54:40 GMT 2002", "Tue Jul 16 19:54:40 GMT 2002", "Thu Jul 18 19:54:40 GMT 2002",
        "Tue Jul 23 19:54:40 GMT 2002", "Thu Jul 25 19:54:40 GMT 2002", "Tue Jul 30 19:54:40 GMT 2002",});

    testRule("FREQ=WEEKLY;INTERVAL=2;WKST=SU;BYDAY=MO,WE,FR;COUNT=10", new String[]{"Wed Jun 26 19:54:40 GMT 2002",
        "Fri Jun 28 19:54:40 GMT 2002", "Mon Jul 08 19:54:40 GMT 2002", "Wed Jul 10 19:54:40 GMT 2002",
        "Fri Jul 12 19:54:40 GMT 2002", "Mon Jul 22 19:54:40 GMT 2002", "Wed Jul 24 19:54:40 GMT 2002",
        "Fri Jul 26 19:54:40 GMT 2002", "Mon Aug 05 19:54:40 GMT 2002", "Wed Aug 07 19:54:40 GMT 2002",});

    testRule("FREQ=WEEKLY;COUNT=20;INTERVAL=2;", new String[]{"Wed Jun 26 19:54:40 GMT 2002",
        "Wed Jul 10 19:54:40 GMT 2002", "Wed Jul 24 19:54:40 GMT 2002", "Wed Aug 07 19:54:40 GMT 2002",
        "Wed Aug 21 19:54:40 GMT 2002", "Wed Sep 04 19:54:40 GMT 2002", "Wed Sep 18 19:54:40 GMT 2002",
        "Wed Oct 02 19:54:40 GMT 2002", "Wed Oct 16 19:54:40 GMT 2002", "Wed Oct 30 19:54:40 GMT 2002",
        "Wed Nov 13 19:54:40 GMT 2002", "Wed Nov 27 19:54:40 GMT 2002", "Wed Dec 11 19:54:40 GMT 2002",
        "Wed Dec 25 19:54:40 GMT 2002", "Wed Jan 08 19:54:40 GMT 2003", "Wed Jan 22 19:54:40 GMT 2003",
        "Wed Feb 05 19:54:40 GMT 2003", "Wed Feb 19 19:54:40 GMT 2003", "Wed Mar 05 19:54:40 GMT 2003",
        "Wed Mar 19 19:54:40 GMT 2003",});

  }
}
