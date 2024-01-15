package com.managestar.recurrance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/***
This class is the main developer interface to Recurrance.  It actually contains a 
list of RecurranceRule objects and uses them to fill the same set of methods as
RecurranceRule.  The difference is that you may union together more that one rule
using this object.

This class will eventually be able to handle the union and intersection of rules.
For now, it just holds the one rule.

***/

public class Recurrance implements RecurranceRule {
  private List ruleList = null;
  private List resultList = null;

  /***
  Creates a recurrance object based on the rule.  The parsers will figure out
  whether the rule is xml or RRULE formated and behave appropriately.  Uses the given
  time for the start and the start time plus FREQ*2*INTERVAL for the end.
  For example:
    FREQ=YEARLY;INTERVAL=2 will end four years from the start date.
    FREQ=MINUTELY;INTERVAL=5 will end ten minutes from the start date.
  Use this for finding the next when you don't have a set end date, and you don't want to
  get an occurrance for every minute between now and 2045.  (Which would take a long time
  to build up)
  @param rule the rule description
  @param start the inclusive start of the range over which to recurr
  @param end the inclusive end of the range over which to recurr
  ***/
  public Recurrance(String rule, Calendar start, Calendar end) {
    ruleList = new ArrayList();
    addRule(rule, start, end);
  }

  public void addRule(String rule, Calendar start, Calendar end) {
    if (rule == null) {
      return;
    }

    resultList = null;
    if (-1 != rule.indexOf("<")) {
      // make a xml rule
      ruleList.add(new RecurranceRuleXml(rule.trim(), start, end));
    } else {
      // make an rfc rule
      ruleList.add(new RecurranceRuleRfc(rule.trim(), start, end));
    }
  }

  /***
  @param rule the rule description
  @param start the inclusive start of the range over which to recurr
  ***/
  public Recurrance(String rule, Calendar start) {
    this(rule, start, null);
  }

  /***
  @param rule the rule description
  ***/
  public Recurrance(String rule) {
    this(rule, null, null);
  }

  /***
  Returns a List of Calendar objects which match the getn rule.  You can then create 
  a whatever for each of those dates if you want to.  Note that the start and end
  may be a subset of the rules start and end range.
  @param start the inclusive start of the range over which to return dates
  @param end the inclusive end of the range over which to dates
  @return a list of dates
  ***/
  public List getAllMatchingDatesOverRange(Calendar start, Calendar end) {
    List results = new ArrayList();
    List all = getAllMatchingDates();

    for (int ctr = 0; ctr < all.size(); ctr++) {
      Calendar cur = (Calendar)all.get(ctr);
      if (CalendarUtil.isBeforeOrEqual(cur, end) && CalendarUtil.isAfterOrEqual(cur, start)) {
        results.add(cur);
      }
    }

    return results;
  }

  /***
  @return a list of the dates that match the recurrance rule
  ***/
  public List getAllMatchingDates() {
    if (resultList == null) {
      resultList = new ArrayList();

      for (Iterator it = ruleList.iterator(); it.hasNext();) {
        RecurranceRule tmpRule = (RecurranceRule)it.next();
        List tmpList = tmpRule.getAllMatchingDates();
        for (Iterator it2 = tmpList.iterator(); it2.hasNext();) {
          Calendar c = (Calendar)it2.next();
          if (!contains(resultList, c)) {
            resultList.add(c);
          }
        }
      }

      //sort only when more than one rule
      if (ruleList.size() > 1) {
        Collections.sort(resultList, CalendarUtil.calComparator);
      }
    }
    return resultList;

  }

  /***
  @return the rule used to define this recurrance.
  ***/
  public String getRule() {
    String str = "";
    for (Iterator it = ruleList.iterator(); it.hasNext();) {
      if (str.length() > 0) {
        str += "/";
      }
      RecurranceRule rule = (RecurranceRule)it.next();
      str += rule.getRule();
    }
    return str;
  }

  /***
  @return true if the date appears as a recurrance in the rule set.
  ***/
  public boolean matches(Calendar value) {
    List all = getAllMatchingDates();
    return contains(all, value);
  }

  private boolean contains(List list, Calendar value) {
    for (Iterator it = list.iterator(); it.hasNext();) {
      Calendar cal = (Calendar)it.next();
      if (CalendarUtil.isEqual(value, cal)) {
        return true;
      }
    }
    return false;
  }

  /***
  @return the next date in the recurrance after the getn one, or null if there is no
  such date.  Note that even if value is a match, this will return the one after it.
  ***/
  public Calendar next(Calendar value) {
    List all = getAllMatchingDates();

    for (int ctr = 0; ctr < all.size(); ctr++) {
      Calendar cur = (Calendar)all.get(ctr);
      if (cur.after(value)) {
        return cur;
      }
    }
    return null;
  }

  /***
  @return the previous date in the recurrance before the getn one, or null if there 
  is no such date.  Note that even if value is a match, this will return the one 
  before it.
  ***/
  public Calendar prev(Calendar value) {
    List all = getAllMatchingDates();

    for (int ctr = all.size() - 1; ctr > -1; ctr--) {
      Calendar cur = (Calendar)all.get(ctr);
      if (cur.before(value)) {
        return cur;
      }
    }
    return null;
  }

  /***
  Unit testing method
  ***/
  public static void main(String[] args) {

    /*
    System.err.println("\n\nRecurrance:\n ");
    System.err.println("Using input: " + args[0]);
    
    long t = System.currentTimeMillis();
    
    // now until about three years from now
    
    long end = 31536000000l;
    end = end * 5;
    Date e = new Date(t + end);
    
    System.err.println("start " + new Date(t));
    System.err.println("end   " + e);
    
    Recurrance r = new Recurrance(args[0], new Date(t), e);
    */

    Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("PST"));
    Recurrance r = new Recurrance("FREQ=MONTHLY", cal);
    List l = r.getAllMatchingDates();
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss Z z");

    for (Iterator it = l.iterator(); it.hasNext();) {
      Calendar element = (Calendar)it.next();
      System.out.println(element.getTimeZone().getDisplayName());
      format.setTimeZone(element.getTimeZone());
      System.out.println(format.format(element.getTime()));
    }
    System.out.println("finish");

  }

}
