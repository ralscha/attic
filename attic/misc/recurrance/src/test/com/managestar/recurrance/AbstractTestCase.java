package com.managestar.recurrance;

import java.io.*;
import java.text.*;
import java.util.*;

import junit.framework.*;

/**
 * Abstract base class for test cases.
 */
public abstract class AbstractTestCase extends TestCase {
  
  protected Calendar start;
  protected Calendar end;
  protected TimeZone tz;
  
  protected final static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

  /** 
   * Basedir for all file I/O. Important when running tests from
   * the reactor.
   */
  public String basedir = System.getProperty("basedir");

  /**
   * Constructor.
   */
  public AbstractTestCase(String testName) {
    super(testName);

    start = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    start.set(Calendar.YEAR, 2002);
    start.set(Calendar.MONTH, Calendar.JUNE);
    start.set(Calendar.DATE, 26);
    start.set(Calendar.HOUR_OF_DAY, 19);
    start.set(Calendar.MINUTE, 54);
    start.set(Calendar.SECOND, 40);
    start.set(Calendar.MILLISECOND, 0);
    

    end = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    end.set(Calendar.YEAR, 2007);
    end.set(Calendar.MONTH, Calendar.JUNE);
    end.set(Calendar.DATE, 25);
    end.set(Calendar.HOUR_OF_DAY, 19);
    end.set(Calendar.MINUTE, 54);
    end.set(Calendar.SECOND, 40);
    end.set(Calendar.MILLISECOND, 0);
    
    
    tz = TimeZone.getTimeZone("GMT");

        
    
    
  }

  /**
   * Get test input file.
   *
   * @param path Path to test input file.
   */
  public String getTestFile(String path) {
    return new File(basedir, path).getAbsolutePath();
  }
  
  
  
  /**
   * Tests a rule
   *
   * @param rule RFC 2445 rule string
   * @param expectedDates Expected dates as string array   
   */
  protected void testRule(String rule, String[] expectedDates) {
    testRule(rule, expectedDates, true);
  }
  
  /**
   * Tests a rule
   *
   * @param rule RFC 2445 rule string
   * @param expectedDates Expected dates as string array
   * @param xmlTest test xml ?
   */    
  protected void testRule(String rule, String[] expectedDates, boolean xmlTest) {
    try {
      Recurrance r = new Recurrance(rule, start, end);
      
      List expected = new ArrayList();
      for (int i = 0; i < expectedDates.length; i++) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeZone(tz);
        cal.setTime(dateFormat.parse(expectedDates[i]));
        cal.set(Calendar.MILLISECOND, 0);
        expected.add(cal);
      }    
      
      compareList(r.getAllMatchingDates(), expected);
      for (Iterator it = expected.iterator(); it.hasNext();) {
        Calendar expectedCal = (Calendar)it.next();
        assertTrue(r.matches(expectedCal));
      }
      
      if (xmlTest) {
        String xmlRule = RecurranceRuleXml.convertRRuleToXml(rule);           
        r = new Recurrance(xmlRule, start, end);
        compareList(expected, r.getAllMatchingDates());
        for (Iterator it = expected.iterator(); it.hasNext();) {
          Calendar expectedCal = (Calendar)it.next();
          assertTrue(r.matches(expectedCal));
        }        
      }
      
            
      
    } catch (ParseException e) {
      e.printStackTrace();
      fail(e.toString());      
    }
  }  
  
  /**
   *Compare two lists and print outors
   */  
  protected static void compareList(List a, List b) {
    
    assertEquals(a.size(), b.size());
    
    for (int ctr = 0; ctr < a.size(); ctr++) {
      boolean found = false;
      
      long va = ((Calendar)a.get(ctr)).getTime().getTime();
      for (int bctr = 0; bctr < b.size(); bctr++) {
        long vb = ((Calendar)b.get(bctr)).getTime().getTime();
        
        if (va == vb) {
          found = true;
          break;
        }
      }
      if (!found) {
        System.err.println((((Calendar)a.get(ctr)).getTime()));
      }
      assertTrue(found);
    }
  }  
}
