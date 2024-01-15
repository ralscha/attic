package com.managestar.recurrance;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;



/**
 * misc unit tests
 */
public class MiscTest extends AbstractTestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public MiscTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(MiscTest.class);
  }


  /**
   * misc tests
   */
  public void testMisc() {
 
    
    start = new GregorianCalendar(TimeZone.getTimeZone("PST"));
    start.set(Calendar.YEAR, 2003);
    start.set(Calendar.MONTH, Calendar.JANUARY);
    start.set(Calendar.DATE, 26);
    start.set(Calendar.HOUR_OF_DAY, 19);
    start.set(Calendar.MINUTE, 54);
    start.set(Calendar.SECOND, 40);
    start.set(Calendar.MILLISECOND, 0);
    

    end = new GregorianCalendar(TimeZone.getTimeZone("PST"));
    end.set(Calendar.YEAR, 2004);
    end.set(Calendar.MONTH, Calendar.JANUARY);
    end.set(Calendar.DATE, 31);
    end.set(Calendar.HOUR_OF_DAY, 19);
    end.set(Calendar.MINUTE, 55);
    end.set(Calendar.SECOND, 40);
    end.set(Calendar.MILLISECOND, 0);
    
    
    Recurrance r = new Recurrance("FREQ=YEARLY", start, end);
    assertEquals(2, r.getAllMatchingDates().size());    
    
    r.addRule("FREQ=MONTHLY", start, end);
    assertEquals(13, r.getAllMatchingDates().size());
    
    
    Calendar tmp = new GregorianCalendar(TimeZone.getTimeZone("CET"));
    tmp.set(2004, Calendar.JANUARY, 27, 4, 54, 40);
    tmp.set(Calendar.MILLISECOND, 0);
    assertTrue(r.matches(tmp));
    
       
    start.set(Calendar.YEAR, 2005);
    end.set(Calendar.YEAR, 2006);

    r.addRule("FREQ=MONTHLY;INTERVAL=2", start, end);
    assertEquals(20, r.getAllMatchingDates().size());
    
    tmp.set(2005, Calendar.JANUARY, 27, 4, 54, 40);    
    assertTrue(r.matches(tmp));
    tmp.set(2006, Calendar.JANUARY, 27, 4, 54, 40);
    assertTrue(r.matches(tmp));
    
    

    Calendar from = new GregorianCalendar(TimeZone.getTimeZone("CET"));
    from.set(2003, Calendar.DECEMBER, 27, 4, 54, 40);
    from.set(Calendar.MILLISECOND, 0);
    assertTrue(r.matches(from));
    
    
    Calendar to = new GregorianCalendar(TimeZone.getTimeZone("CET"));
    to.set(2005, Calendar.MARCH, 27, 5, 54, 40); //daylight saving time
    to.set(Calendar.MILLISECOND, 0);
    assertTrue(r.matches(to));
    
    List tl = r.getAllMatchingDatesOverRange(from, to);
    assertEquals(4, tl.size());
    
 /*
  *      for (Iterator it = re.getAllMatchingDates().iterator(); it.hasNext();) {
       Calendar element = (Calendar)it.next();
       System.out.println(element.getTime());
     }

  */   
    
    
    
  }
}