package com.managestar.recurrance;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;
import com.ibm.icu.util.TimeZone;

/****

Describes a single recurrance rule.  This object is actually a facade for a number
of smaller objects that a user should never need to worry about.

****/

public class RecurranceRuleRfc implements RecurranceRule {

  private static final Log LOGGER = LogFactory.getLog(RecurranceRuleRfc.class);

  private final int SET_SIZE_LIMIT = 50000;

  protected String freq = null;
  protected int count = 0;
  protected int interval = 1;
  protected int weekStart = Calendar.MONDAY;
  protected String rule = null;

  protected Set secSet = new HashSet();
  protected Set minSet = new HashSet();
  protected Set hourSet = new HashSet();
  protected Set weekDaySet = new HashSet();
  protected Set monthDaySet = new HashSet();
  protected Set yearDaySet = new HashSet();
  protected Set yearSet = new HashSet();
  protected Set weekNumberSet = new HashSet();
  protected Set monthSet = new HashSet();
  protected Set bspSet = null;

  protected List resultList = null;

  protected List protoDateList = new ArrayList();

  protected Calendar startDate;
  protected Calendar endDate;
  protected Calendar untilDate;

  protected boolean foundFreq = false;
  protected boolean foundUntil = false;
  protected boolean foundCount = false;
  protected boolean foundInterval = false;
  protected boolean foundBySecond = false;
  protected boolean foundByMinute = false;
  protected boolean foundByHour = false;
  protected boolean foundByDay = false;
  protected boolean foundByMonthDay = false;
  protected boolean foundByYearDay = false;
  protected boolean foundByWeekNo = false;
  protected boolean foundByMonth = false;

  protected int offsetUnit = 0;
  protected int offsetAmount = 0;

  /***
  Creates a recurrance object based on the rule.  The parses will figure out
  whether the rule is xml or RRULE formated and behave appropriately
  @param rule the rule description
  @param start the inclusive start of the range over which to recurr
  @param end the inclusive end of the range over which to recurr
  ***/
  RecurranceRuleRfc(String ruleI, Calendar startI, Calendar endI) {
    try {

      if (startI != null) {
        this.startDate = (Calendar)startI.clone();
      }

      if (endI != null) {
        this.endDate = (Calendar)endI.clone();
      }

      this.rule = ruleI;

      List ruleList = new ArrayList();
      StringTokenizer stk = new StringTokenizer(rule, ";");

      //===== READ the rules that have been provided and make the meta-sets and instructions 
      //===== that will be needed later.
      while (stk.hasMoreTokens()) {
        String curRule = stk.nextToken();
        ruleList.add(curRule);
        String key = curRule.substring(0, curRule.indexOf("="));
        String value = curRule.substring(curRule.indexOf("=") + 1);
        //println("  "+key+"  =  "+value);

        if ("FREQ".equals(key)) {
          //--- no dups
          if (foundFreq) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundFreq = true;
          }
          //--- only valid options
          if (!Constants.freqValues.contains(value)) {
            LOGGER.debug(">>" + Constants.freqValues);
            throw new IllegalArgumentException("Error: invalid FREQ value");
          }
          //--- accept the rule
          freq = value;
        } else if ("COUNT".equals(key)) {
          //--- no dups
          if (foundCount) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundCount = true;
          }
          if (foundUntil) {
            throw new IllegalArgumentException("Error: UNTIL and COUNT are mutually exclusive");
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          count = Integer.parseInt(value);
          /*
          if (count < 1) {
            throw new IllegalArgumentException("Error: COUNT < 1");
          }
          */
        } else if ("UNTIL".equals(key)) {
          //--- no dups
          if (foundUntil) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundUntil = true;
          }
          if (foundCount) {
            throw new IllegalArgumentException("Error: UNTIL and COUNT are mutually exclusive");
          }

          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }

          if (value.length() == 8) {
            try {

              TimeZone tz;
              if (endDate != null) {
                tz = endDate.getTimeZone();
              } else {
                tz = startDate.getTimeZone();
              }
              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
              dateFormat.setTimeZone(tz);
              Date tmp = dateFormat.parse(value);

              untilDate = new GregorianCalendar(tz);
              untilDate.setTime(tmp);
              untilDate.set(Calendar.HOUR_OF_DAY, 23);
              untilDate.set(Calendar.MINUTE, 59);
              untilDate.set(Calendar.SECOND, 59);
              untilDate.set(Calendar.MILLISECOND, 999);
            } catch (ParseException e1) {
              throw new IllegalArgumentException("Error: until not a valid date value " + value);
            }
          } else if (value.length() == 16) {
            try {
              Date tmp = ch.ess.cal.Constants.DATE_UTC_FORMAT.parse(value);
              untilDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
              untilDate.setTime(tmp);
            } catch (ParseException e1) {
              throw new IllegalArgumentException("Error: until not a valid utc date-time value " + value);
            }
          } else if (value.length() == 15) {
            try {
              Date tmp = ch.ess.cal.Constants.DATE_LOCAL_FORMAT.parse(value);
              untilDate = new GregorianCalendar();
              untilDate.setTime(tmp);      
            } catch (ParseException e1) {
              throw new IllegalArgumentException("Error: until not a valid utc date-time value " + value);
            }                  
          } else {
            throw new IllegalArgumentException("Error: until date must by a date or utc date-time " + value);
          }

        } else if ("INTERVAL".equals(key)) {
          //--- no dups
          if (foundInterval) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundInterval = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          interval = Integer.parseInt(value);
          if (interval < 1) {
            throw new IllegalArgumentException("Error: INTERVAL < 1");
          }
        } else if ("BYSECOND".equals(key)) {
          //--- no dups
          if (foundBySecond) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundBySecond = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          if ("SECONDLY".equals(freq)) {
            throw new IllegalArgumentException("Error: freq=SECONDLY and BYSECOND are mutually exclusive");
          }
          secSet = getNumberSet(value, 0, 59, key);
        } else if ("BYMINUTE".equals(key)) {
          //--- no dups
          if (foundByMinute) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundByMinute = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          if ("MINUTELY".equals(freq)) {
            throw new IllegalArgumentException("Error: freq=MINUTELY and BYMINUTE are mutually exclusive");
          }
          minSet = getNumberSet(value, 0, 59, key);
        } else if ("BYHOUR".equals(key)) {
          //--- no dups
          if (foundByHour) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundByHour = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          if ("HOURLY".equals(freq)) {
            throw new IllegalArgumentException("Error: freq=HOURLY and BYHOUR are mutually exclusive");
          }
          hourSet = getNumberSet(value, 0, 23, key);
        } else if ("BYDAY".equals(key)) {
          //--- no dups
          if (foundByDay) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundByDay = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          //if ( "DAILY".equals(freq) ) throw new IllegalArgumentException("Error: freq=DAILY and BYDAY are mutually exclusive (try freq=WEEKLY)");
          weekDaySet = getWeekDaySet(value);
        } else if ("BYMONTHDAY".equals(key)) {
          //--- no dups
          if (foundByMonthDay) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundByMonthDay = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          //if ( "DAILY".equals(freq) ) throw new IllegalArgumentException("Error: freq=DAILY and BYMONTHDAY are mutually exclusive");
          monthDaySet = getNumberSet(value, -31, 31, key);
        } else if ("BYYEARDAY".equals(key)) {
          //--- no dups
          if (foundByYearDay) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundByYearDay = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          //if ( "DAILY".equals(freq) ) throw new IllegalArgumentException("Error: freq=DAILY and BYYEARDAY are mutually exclusive");
          yearDaySet = getNumberSet(value, -366, 366, key);
        } else if ("BYWEEKNO".equals(key)) {
          //--- no dups
          if (foundByWeekNo) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundByWeekNo = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          if (!"YEARLY".equals(freq)) {
            throw new IllegalArgumentException("Error: BYWEEKNO is only valid in a FREQ=YEARLY command");
          }
          weekNumberSet = getNumberSet(value, -53, 53, key);
        } else if ("BYMONTH".equals(key)) {
          //--- no dups
          if (foundByMonth) {
            throw new IllegalArgumentException("Error: duplicate key");
          } else {
            foundByMonth = true;
          }
          if (!foundFreq) {
            throw new IllegalArgumentException("Error: freq must be specified before " + key);
          }
          if ("MONTHLY".equals(freq)) {
            throw new IllegalArgumentException("Error: freq=MONTHLY and BYMONTH are mutually exclusive");
          }
          monthSet = getNumberSetHoldTheEvil(value, 1, 12, key);
        } else if ("WKST".equals(key)) {
          if (!Constants.weekDayValues.contains(value)) {
            throw new IllegalArgumentException(
              "Error: " + value + " is not a valid weekday! Use capitolized two letter codes only");
          }

          weekStart = Constants.convertDayNameToDayNumber(value);

        } else if ("BYSETPOS".equals(key)) {
          //--- remember which items out of the individual frequency sets we want to keep
          bspSet = getNumberSet(value, -366, 366, key);
        } else if ("OFFSET".equals(key)) {
          if (value.endsWith("DATE")) {
            offsetUnit = Calendar.DATE;
            offsetAmount = Integer.parseInt(value.substring(0, value.length() - 4));
          } else if (value.endsWith("HOUR")) {
            offsetUnit = Calendar.HOUR;
            offsetAmount = Integer.parseInt(value.substring(0, value.length() - 4));
          } else if (value.endsWith("MIN")) {
            offsetUnit = Calendar.MINUTE;
            offsetAmount = Integer.parseInt(value.substring(0, value.length() - 3));
          } else {
            throw new IllegalArgumentException("Error: unable to parse OFFSET");
          }
        } else {
          throw new IllegalArgumentException("Error: illegal key");
        }

      }

      if (!foundFreq) {
        throw new IllegalArgumentException("Error: FREQ is required");
      }

      if (startDate == null) {
        startDate = Calendar.getInstance();
      }
      startDate.setMinimalDaysInFirstWeek(MINIMAL_DAYS_IN_FIRST_WEEK);
      startDate.setFirstDayOfWeek(weekStart);

      if (untilDate != null) {
        endDate = untilDate;
      }

      if (endDate == null) {
        endDate = (Calendar)startDate.clone();
        endDate.add(Constants.convertFreqNameToCalendarField(freq), interval * 2);
      }

      endDate.setMinimalDaysInFirstWeek(MINIMAL_DAYS_IN_FIRST_WEEK);
      endDate.setFirstDayOfWeek(weekStart);

      //===== WALK the dates that are described in the freq/interval.  Also fill in any
      //===== sets that are required at each level
      Calendar cur = (Calendar)startDate.clone();
      //cur.setLenient(false);            

      Calendar limit = (Calendar)endDate.clone();
      //limit.setLenient(false);

      //----------------- Build the year list 

      if ("YEARLY".equals(freq)) {
        //--- build by freq
        protoDateList.addAll(getFullDateValueList(Calendar.YEAR, interval, cur, limit));
      }

      //----------------- Build the month list 
      if ("MONTHLY".equals(freq)) {
        //--- build by freq
        protoDateList.addAll(getFullDateValueList(Calendar.MONTH, interval, cur, limit));
      } else if (!foundByMonth) {
        monthSet.add(new Integer(cur.get(Calendar.MONTH))); // adding user fudge
      }

      //----------------- Build the day list  STILL VERY EVIL
      if ("DAILY".equals(freq)) {
        //--- build by freq
        protoDateList.addAll(getFullDateValueList(Calendar.DAY_OF_MONTH, interval, cur, limit));
      } else if (!foundByDay && !foundByWeekNo && !foundByMonthDay && !foundByYearDay) {
        monthDaySet.add(new Integer(cur.get(Calendar.DAY_OF_MONTH)));
      }

      //----------------- Build the week list     
      if ("WEEKLY".equals(freq)) {
        //--- build by freq
        protoDateList.addAll(getFullDateValueList(Calendar.WEEK_OF_YEAR, interval, cur, limit));
        if (!foundByDay) {
          weekDaySet.add(new ByDay(0, Constants.convertDayNumberToDayName(cur.get(Calendar.DAY_OF_WEEK))));
        }
      }
      //--- the week from original date is not taken as a default

      //----------------- Build the hour list 
      if ("HOURLY".equals(freq)) {
        //--- build by freq
        protoDateList.addAll(getFullDateValueList(Calendar.HOUR_OF_DAY, interval, cur, limit));
      } else if (!foundByHour) {
        hourSet.add(new Integer(cur.get(Calendar.HOUR_OF_DAY)));
      }

      //----------------- Build the minute list 
      if ("MINUTELY".equals(freq)) {
        //--- build by freq
        protoDateList.addAll(getFullDateValueList(Calendar.MINUTE, interval, cur, limit));
      } else if (!foundByMinute) {
        minSet.add(new Integer(cur.get(Calendar.MINUTE)));
      }

      //----------------- Build the second list 
      if ("SECONDLY".equals(freq)) {
        //--- build by freq
        protoDateList.addAll(getFullDateValueList(Calendar.SECOND, interval, cur, limit));
      } else if (!foundBySecond) {
        secSet.add(new Integer(cur.get(Calendar.SECOND)));
      }

      resultList = new ArrayList();

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("pS   " + protoDateList.size());
        LOGGER.debug("yS   " + yearSet.size() + "  " + yearSet);
        LOGGER.debug("mS   " + monthSet.size() + "  " + monthSet);
        LOGGER.debug("mDS  " + monthDaySet.size() + "  " + monthDaySet);
        LOGGER.debug("wDS  " + weekDaySet.size() + "  " + weekDaySet);
        LOGGER.debug("hS   " + hourSet.size() + "  " + hourSet);
        LOGGER.debug("minS " + minSet.size() + "  " + minSet);
        LOGGER.debug("sS   " + secSet.size() + "  " + secSet);
      }

      //===== CULL the dates that are not acceptable to BYxyz commands which are of a higher
      //===== magnitude than the frequency
      int freqNumber = Constants.convertFreqNameToFreqNumber(freq);

      for (int pdCtr = protoDateList.size() - 1; pdCtr >= 0; pdCtr--) {
        Calendar curProtoDate = (Calendar)protoDateList.get(pdCtr);

        // BYMONTH
        if (foundByMonth && freqNumber <= Constants.FREQ_MONTHLY) {
          if (!isDateInMetaSet(curProtoDate, Calendar.MONTH, monthSet)) {
            LOGGER.debug("remove by month");
            protoDateList.remove(pdCtr);
            continue;
          }
        }
        // BYWEEKNO
        if (foundByWeekNo && freqNumber <= Constants.FREQ_WEEKLY) {
          if (!isDateInMetaSet(curProtoDate, Calendar.WEEK_OF_YEAR, weekNumberSet)) {
            LOGGER.debug("remove by week no");
            protoDateList.remove(pdCtr);
            continue;
          }
        }
        // BYYEARDAY
        if (foundByYearDay && freqNumber <= Constants.FREQ_DAILY) {
          if (!isDateInMetaSet(curProtoDate, Calendar.DAY_OF_YEAR, yearDaySet)) {
            LOGGER.debug("remove by year day");
            protoDateList.remove(pdCtr);
            continue;
          }
        }
        // BYMONTHDAY
        if (foundByMonthDay && freqNumber <= Constants.FREQ_DAILY) {
          if (!isDateInMetaSet(curProtoDate, Calendar.DAY_OF_MONTH, monthDaySet)) {
            LOGGER.debug("remove by month day");
            protoDateList.remove(pdCtr);
            continue;
          }
        }
        // BYDAY - Deeply Evil
        if (foundByDay && freqNumber <= Constants.FREQ_DAILY) {
          if (!isDateInWeekDayMetaSet(curProtoDate, weekDaySet)) {
            LOGGER.debug("remove by month day");
            protoDateList.remove(pdCtr);
            continue;
          }
        }
        // BYHOUR
        if (foundByHour && freqNumber <= Constants.FREQ_HOURLY) {
          if (!isDateInMetaSet(curProtoDate, Calendar.HOUR_OF_DAY, hourSet)) {
            LOGGER.debug("remove by hour");
            protoDateList.remove(pdCtr);
            continue;
          }
        }
        // BYMINUTE
        if (foundByMinute && freqNumber <= Constants.FREQ_MINUTELY) {
          if (!isDateInMetaSet(curProtoDate, Calendar.MINUTE, minSet)) {
            LOGGER.debug("remove by minute");
            protoDateList.remove(pdCtr);
            continue;
          }
        }
      }

      //===== SPEW in the dates that are added by using a BYxyz of lower 
      //===== magnitude than the frequency

      Set resultSet = new HashSet();

      for (int pdCtr = protoDateList.size() - 1; pdCtr >= 0; pdCtr--) {
        Calendar curProtoDate = (Calendar)protoDateList.get(pdCtr);

        Set resultPile = new HashSet();
        resultPile.add(curProtoDate);
        if (resultPile.size() > SET_SIZE_LIMIT) {
          throw new IllegalArgumentException("Recurrance too large.  Try moving your end date forward.  For example, if you are doing a MINUTELY recurrance over 5 years, you will get this error even in your COUNT is set to 2.  Working with a smaller set relieves this problem.");
        }

        // BYMONTH
        if (freqNumber > Constants.FREQ_MONTHLY) {
          resultPile = breedMetaSet(resultPile, Calendar.MONTH, monthSet);
        }

        // BYWEEKNO
        if (freqNumber > Constants.FREQ_WEEKLY) {
          resultPile = breedMetaSet(resultPile, Calendar.WEEK_OF_YEAR, weekNumberSet);
        }
        // BYYEARDAY
        if (freqNumber > Constants.FREQ_DAILY && freqNumber != Constants.FREQ_WEEKLY) {
          resultPile = breedMetaSet(resultPile, Calendar.DAY_OF_YEAR, yearDaySet);
        }
        // BYMONTHDAY
        if (freqNumber > Constants.FREQ_DAILY && freqNumber != Constants.FREQ_WEEKLY) {
          resultPile = breedMetaSet(resultPile, Calendar.DAY_OF_MONTH, monthDaySet);
        }
        // BYDAY - Deeply Evil
        if (freqNumber > Constants.FREQ_DAILY /*&& freqNumber != Constants.FREQ_WEEKLY*/
          ) {
          resultPile = breedWeekDayMetaSet(resultPile, weekDaySet, startDate, endDate);
        }
        // BYHOUR
        if (freqNumber > Constants.FREQ_HOURLY) {
          resultPile = breedMetaSet(resultPile, Calendar.HOUR_OF_DAY, hourSet);
        }
        // BYMINUTE
        if (freqNumber > Constants.FREQ_MINUTELY) {
          resultPile = breedMetaSet(resultPile, Calendar.MINUTE, minSet);
        }
        // BYSECOND
        if (freqNumber > Constants.FREQ_SECONDLY) {
          resultPile = breedMetaSet(resultPile, Calendar.SECOND, secSet);
        }

        resultSet.addAll(resultPile);
        if (resultSet.size() > SET_SIZE_LIMIT) {
          throw new IllegalArgumentException("Recurrance too large.  Try moving your end date forward.  For example, if you are doing a MINUTELY recurrance over 5 years, you will get this error even in your COUNT is set to 2.  Working with a smaller set relieves this problem.");
        }
      }

      LOGGER.debug("pS pb " + resultSet.size());

      //--- convert the calendar objects into date objects
      for (Iterator rsIt = resultSet.iterator(); rsIt.hasNext();) {
        resultList.add(rsIt.next());
      }

      Collections.sort(resultList, CalendarUtil.calComparator);

      //===== ENSURE the limitation on the primary dates
      //--- ensure the bounds

      for (int rctr = resultList.size() - 1; rctr >= 0; rctr--) {
        Calendar curDate = (Calendar)resultList.get(rctr);

        if (!(CalendarUtil.isBeforeOrEqual(curDate, endDate) && CalendarUtil.isAfterOrEqual(curDate, startDate))) {
          resultList.remove(rctr);
        }
      }

      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("rS " + resultList.size());
      }

      //===== FILTER the dates returned
      //--- apply the BYSETPOS filter by examining the dates for each recurrance and culling appropriately
      //--- note that finalList MUST be sorted for this to work.
      resultList = filterUsingBySetPos(resultList, bspSet, Constants.convertFreqNameToCalendarField(freq));

      //--- apply the OFFSET filter by altering each date as requested.  NOTE:  This may take dates outside
      //      the start and end range.  (I.E. you'll want a meeting reminder before the start of the first 
      //      meeting)

      //--- ensure the count
      List finalList;
      if (count != 0 && resultList.size() > count) {
        finalList = resultList.subList(0, count);
      } else {
        finalList = resultList;
      }

      //==== MODIFY the dates returned using OFFSET
      if (offsetAmount != 0) {
        for (int ctr = 0; ctr < finalList.size(); ctr++) {
          Calendar curMod = (Calendar)finalList.get(ctr);
          curMod.add(offsetUnit, offsetAmount);
          finalList.set(ctr, curMod);
        }

      }

      resultList = new ArrayList(finalList);

    } catch (RuntimeException e) {
      LOGGER.error(rule, e);
      throw e;
    }

  }

  /***
  Returns a List of Calendar objects which match the given rule.  You can then create 
  a whatever for each of those dates if you want to.  Note that the start and end
  may be a subset of the rules start and end range.
  @param start the inclusive start of the range over which to return dates
  @param end the inclusive end of the range over which to dates
  @return a list of dates
  ***/
  public List getAllMatchingDatesOverRange(Calendar start, Calendar end) {
    return null;
  }

  /***
  returns a list of all the dates that matched.
  ***/
  public List getAllMatchingDates() {
    return resultList;
  }

  /***
  @return true iff the given date appears as a recurrance in the rule set.
  ***/
  public boolean matches(Calendar value) {
    return false;
  }

  /***
  @return the next date in the recurrance after the getn one, or null if there is no
  such date.  Note that even if value is a match, this will return the one after it.
  ***/
  public Calendar next(Calendar value) {
    return null;
  }

  /***
  @return the previous date in the recurrance before the getn one, or null if there 
  is no such date.  Note that even if value is a match, this will return the one 
  before it.
  ***/
  public Calendar prev(Calendar value) {
    return null;
  }

  /***
  @return the rule used to create this object
  ***/
  public String getRuleAsRRule() {
    return rule;
  }

  //==================== Utilities
  /***
  split a list into validated numbers
  ***/
  private Set getNumberSet(String list, int low, int high, String key) {
    Set result = new HashSet();
    StringTokenizer stk = new StringTokenizer(list, ",");
    while (stk.hasMoreTokens()) {
      int cur = Integer.parseInt(stk.nextToken());
      //--- be in range
      if (cur < low || cur > high) {
        throw new IllegalArgumentException("ERROR: invalid integer list: " + key);
      }
      //--- if low != 0 then 0 is disallowed
      if (low != 0 && cur == 0) {
        throw new IllegalArgumentException("ERROR: zero is not allowed in " + key);
      }
      result.add(new Integer(cur));
    }
    return result;
  }

  /***
  Use this for getting months, when the user gives it to you 1 based, but java wants it 0 based.
  ***/
  private Set getNumberSetHoldTheEvil(String list, int low, int high, String key) {
    Set old = getNumberSet(list, low, high, key);
    Set result = new HashSet();

    for (Iterator it = old.iterator(); it.hasNext();) {
      result.add(new Integer(((Integer)it.next()).intValue() - 1));
    }

    return result;

  }

  /***
  split a list into validated ByDay objects
  ***/
  private Set getWeekDaySet(String list) {
    Set result = new HashSet();
    StringTokenizer stk = new StringTokenizer(list, ",");
    while (stk.hasMoreTokens()) {
      ByDay bd = new ByDay(stk.nextToken());
      if (bd.count != 0 && !("MONTHLY".equals(freq) || "YEARLY".equals(freq))) {
        throw new IllegalArgumentException("  ERROR: You can only specify a number with a week day in the YEARLY or MONTHLY frequencies.");
      }
      result.add(bd);
    }
    return result;
  }

  /***
  Gets all of the valid units over a span of time with respect to the interval.
  This is only used for finding frequencies.
  @param unit a Calendar constant such as YEAR, DAY_OF_MONTH, HOUR, etc
  @param interval the interval to jump across. 1 => every, 2 => every other, etc
  @param cur the start of the range
  @param end the end of the range.
  ***/
  /*
  private List getValueList(int unit, int interval, Calendar inputCur, Calendar inputEnd) {
    Calendar cur = roundDown(unit, (Calendar)inputCur.clone());
    Calendar end = roundDown(unit, (Calendar)inputEnd.clone());
  
    int val = cur.get(unit);
  
    List resultList = new ArrayList();
    //println("val "+val);
    resultList.add(new Integer(val));
  
    cur.add(unit, interval);
  
    while (cur.getTime().before(end.getTime()) || cur.getTime().equals(end.getTime())) { // while not past the end date
      val = cur.get(unit);
      //println("val "+val);
      resultList.add(new Integer(val));
      cur.add(unit, interval);
      if (resultList.size() > SET_SIZE_LIMIT) {
        throw new IllegalArgumentException("Recurrance too large.  Try moving your end date forward.  For example, if you are doing a MINUTELY recurrance over 5 years, you will get this error even in your COUNT is set to 2.  Working with a smaller set relieves this problem.");
      }
  
    }
  
    return resultList;
  }*/

  /***
  Gets all of the valid units over a span of time with respect to the interval.
  This is only used for finding frequencies.
  @param unit a Calendar constant such as YEAR, DAY_OF_MONTH, HOUR, etc
  @param interval the interval to jump across. 1 => every, 2 => every other, etc
  @param cur the start of the range
  @param end the end of the range.
  ***/
  private List getFullDateValueList(int unit, int interval, Calendar inputCur, Calendar inputEnd) {
    Calendar cur = roundDown(unit, (Calendar)inputCur.clone());
    Calendar end = roundDown(unit, (Calendar)inputEnd.clone());

    LOGGER.debug("Round Down  " + cur.getTime());
    LOGGER.debug("Round Up    " + end.getTime());
    //int val = cur.get(unit);

    List resultList = new ArrayList();
    resultList.add(cur.clone());
    LOGGER.debug("val " + cur.getTime());

    cur.add(unit, interval);

    while (CalendarUtil.isBeforeOrEqual(cur, end)) { // while not past the end date
      LOGGER.debug("val " + cur.getTime());
      resultList.add(cur.clone());
      cur.add(unit, interval);
      if (resultList.size() > SET_SIZE_LIMIT) {
        throw new IllegalArgumentException("Recurrance too large.  Try moving your end date forward.  For example, if you are doing a MINUTELY recurrance over 5 years, you will get this error even in your COUNT is set to 2.  Working with a smaller set relieves this problem.");
      }
    }

    return resultList;
  }

  /***
  Round down a date.  i.e. if I have 1998/03/29 at 13:30 and I round at the 
  MONTH level, I'll get 1998/03/01 at 00:00
  @param unRounded a calendar value
  @return a rounded calendar value
  ***/
  private Calendar roundDown(int unit, Calendar unRounded) {
    Calendar result = (Calendar)unRounded.clone();
    //println("pre "+result.getTime());

    if (unit < Calendar.YEAR) {
      result.set(Calendar.YEAR, 0);
    }
    if (unit < Calendar.MONTH) {
      result.set(Calendar.MONTH, 0);
    }
    if (unit < Calendar.DAY_OF_MONTH) {
      result.set(Calendar.DAY_OF_MONTH, 1);
    }
    if (unit < Calendar.HOUR_OF_DAY) {
      result.set(Calendar.HOUR_OF_DAY, 0);
    }
    if (unit < Calendar.MINUTE) {
      result.set(Calendar.MINUTE, 0);
    }
    if (unit < Calendar.SECOND) {
      result.set(Calendar.SECOND, 0);
    }

    if (unit == Calendar.WEEK_OF_YEAR) {
      result.set(Calendar.WEEK_OF_YEAR, unRounded.get(Calendar.WEEK_OF_YEAR));
    }

    //println("post "+result.getTime());
    return result;
  }

  /***
  This one is pretty specific.  Say you have a set of day_in_month values.  However some of the
  values are negative. (i.e. -3 meaning the third to the last day of the month)  This method makes
  a new set that replaces the negative values with the appropriate positive values, then checks that
  the date given is actually in the list of acceptable values.  If it is, it returns true.
  @param curProtoDate a date under consideration for inclusion
  @param unit the unit of interest DAY_OF_MONTH, WEEK_OF_YEAR, DAY_OF_WEEK, etc
  @param metaSet the set potentially containing negative values
  @return true iff the date is part of the meta set
  ***/
  private boolean isDateInMetaSet(Calendar curProtoDate, int unit, Set metaSet) {
    Integer cur = new Integer(curProtoDate.get(unit));
    Set locallyValidSet = new HashSet(metaSet);

    int localMax = curProtoDate.getActualMaximum(unit);
    int modVal;

    for (Iterator it = locallyValidSet.iterator(); it.hasNext();) {
      modVal = ((Integer)it.next()).intValue();
      if (modVal < 0) {
        //-1 implies the last item in the group, so the +1 makes it all work out.
        locallyValidSet.add(new Integer(modVal + localMax + 1));
      }
    }

    if (!locallyValidSet.contains(cur)) {
      return false;
    }

    return true;
  }

  /***
  Deep magic from before the beginning of time.  
  The values in the metaSet must be ByDay values.  Then depending on the presence or non presence of the
  count the field and also on the frequency value, this method will return false for dates that do not 
  match the expanded meta set.
  @param curProtoDate a date under consideration for inclusion
  @param unit the unit of interest DAY_OF_MONTH, WEEK_OF_YEAR, DAY_OF_WEEK, etc
  @param metaSet the set potentially containing negative values
  @return true iff the date is part of the meta set
  ***/
  private boolean isDateInWeekDayMetaSet(Calendar curProtoDate, Set metaSet) {
    for (Iterator it = metaSet.iterator(); it.hasNext();) {
      ByDay bd = (ByDay)it.next();

      if (bd.count == 0) {
        //--- any correct day of the week will do
        if (curProtoDate.get(Calendar.DAY_OF_WEEK) == Constants.convertDayNameToDayNumber(bd.weekday)) {
          return true;
        }
      } else {
        Calendar hunter = huntDayOfWeek(curProtoDate, bd);
        if (hunter.get(Calendar.DAY_OF_YEAR) == curProtoDate.get(Calendar.DAY_OF_YEAR)) {
          return true;
        }
      }
    }
    //--- we can only get here if none of the previous items matched and returned true.
    return false;

  }

  /***
  Hunts for the n first or last count of a weekday within a Calendar.MONTH or a Calendar.YEAR
  as described by the ByDay object.
  @param curProtoDate the date we are looking relative two.  (gives the month/year of interest)
  @param unit either Calendar.MONTH or Calendar.YEAR depending on the current
  ***/
  private Calendar huntDayOfWeek(Calendar curProtoDate, ByDay bd) {
    Calendar hunter = (Calendar)curProtoDate.clone();
    //println("hunting");

    // figure out if we're talking months or years.
    int unit = Calendar.DAY_OF_MONTH;
    if ("YEARLY".equals(freq) && !foundByMonth) {
      unit = Calendar.DAY_OF_YEAR;
    }
    //println("hunting: unit "+unit);

    // figure out what direction we're going
    int start = 1;
    int limit = curProtoDate.getActualMaximum(unit);
    int direction = 1;
    if (bd.count < 0) {
      start = limit;
      limit = 1;
      direction = -1;
    }
    //println("hunting: start "+start);
    //println("hunting: limit "+limit);

    // figure out day we want
    int goalDow = Constants.convertDayNameToDayNumber(bd.weekday);

    // spin through the days and hunt for the date of the requested week day
    hunter.set(unit, start);
    int foundCount = 0;
    int safety = 0;
    while (foundCount < Math.abs(bd.count)) {
      if (hunter.get(Calendar.DAY_OF_WEEK) == goalDow) {
        foundCount++;
      }
      hunter.add(unit, direction);
      safety++;
      if (safety > 366) {
        // this should never happen.  :]
        throw new IllegalArgumentException("  ERROR: This loop has spun out of control. Either the week code you entered is bad, or you have asked for too large of an ofset");
      }
    }
    // undo the for-loop fudging and return the value
    hunter.add(unit, -1 * direction);
    //println("hunting: found count "+foundCount);
    //println("hunting: returning "+hunter.getTime());

    return hunter;
  }

  /***
  This one is pretty specific.  Say you have a set of day_in_month values.  However some of the
  values are negative. (i.e. -3 meaning the third to the last day of the month)  This method makes
  a new set that replaces the negative values with the appropriate positive values, then multiplies all
  of the entries in the original set by the entries in the meta set.  The resulting hash of entries is 
  returned to the user
  @param origSet the set of original values
  @param unit the unit of interest DAY_OF_MONTH, WEEK_OF_YEAR, DAY_OF_WEEK, etc
  @param metaSet the set potentially containing negative values
  @return the now more populous set
  ***/
  private Set breedMetaSet(Set origSet, int unit, Set metaSet) {
    if (metaSet == null || metaSet.size() == 0) {
      return origSet;
    }

    Set result = new HashSet();

    for (Iterator oit = origSet.iterator(); oit.hasNext();) {
      Calendar curProtoDate = (Calendar)oit.next();

      int localMax = curProtoDate.getActualMaximum(unit);
      int modVal;

      for (Iterator it = metaSet.iterator(); it.hasNext();) {
        modVal = ((Integer)it.next()).intValue();
        if (modVal < 0) {
          //-1 implies the last item in the group, so the +1 makes it all work out.
          modVal = modVal + localMax + 1;
        }
        Calendar newDate = (Calendar)curProtoDate.clone();
        newDate.set(unit, modVal);
        result.add(newDate);
        if (result.size() > SET_SIZE_LIMIT) {
          throw new IllegalArgumentException("Recurrance too large.  Try moving your end date forward.  For example, if you are doing a MINUTELY recurrance over 5 years, you will get this error even in your COUNT is set to 2.  Working with a smaller set relieves this problem.");
        }
      }
    }

    return result;
  }

  /***
  Deep magic.  This method takes a given date and using the meta set of weekDays, it finds the proper
  week day or days that are being discussed and puts those days into the result set to be returned.
  @param origSet the set of original values
  @param metaSet the set ByDay values describing the week days of interest
  @return the now more populous set
  ***/
  private Set breedWeekDayMetaSet(Set origSet, Set metaSet, Calendar start, Calendar end) {
    LOGGER.debug("in bwdms");
    if (metaSet == null || metaSet.size() == 0) {
      return origSet;
    }

    int unit = Calendar.MONTH;
    if ("YEARLY".equals(freq)) {
      unit = Calendar.YEAR;
    }
    LOGGER.debug("in bwdms: unit = " + unit);

    Set result = new HashSet();

    for (Iterator oit = origSet.iterator(); oit.hasNext();) {
      Calendar curProtoDate = (Calendar)oit.next();

      for (Iterator it = metaSet.iterator(); it.hasNext();) {
        ByDay bd = (ByDay)it.next();

        if (bd.count == 0) {
          LOGGER.debug("in bwdms: finding all");
          // they want all occurrences
          ByDay localbd = new ByDay(1, bd.weekday);

          Calendar str = (Calendar)start.clone();
          Calendar hunter = huntDayOfWeek(str, localbd);

          // some of the other BYxyz commands may limit the scope of this breeding.  
          // figure that out here
          //int localUnit = unit;
          boolean needSameMonth = false;
          boolean needSameWeek = false;
          boolean needSameDay = false;
          if (foundByMonth || "MONTHLY".equals(freq)) {
            needSameMonth = true;
          }
          //if ( foundByMonthDay || foundByWeekNo || foundByYearDay || "WEEKLY".equals(freq) ) needSameWeek = true;
          if (foundByWeekNo || "WEEKLY".equals(freq)) {
            needSameWeek = true;
          }
          if (foundByMonthDay || foundByYearDay || "DAILY".equals(freq)) {
            needSameDay = true;
          }

          LOGGER.debug("nsm " + needSameMonth);
          LOGGER.debug("nsw " + needSameWeek);
          LOGGER.debug("nsd " + needSameDay);

          while (CalendarUtil.isBeforeOrEqual(hunter, end)) {
            //if ( hunter.get(Calendar.YEAR) == curProtoDate.get(Calendar.YEAR) ){
            if (!needSameMonth || (needSameMonth && isSameMonth(hunter, curProtoDate))) {
              if (!needSameWeek || (needSameWeek && isSameWeek(hunter, curProtoDate))) {
                if (!needSameDay || (needSameDay && isSameDay(hunter, curProtoDate))) {
                  result.add(hunter.clone());
                  if (result.size() > SET_SIZE_LIMIT) {
                    throw new IllegalArgumentException("Recurrance too large.  Try moving your end date forward.  For example, if you are doing a MINUTELY recurrance over 5 years, you will get this error even in your COUNT is set to 2.  Working with a smaller set relieves this problem.");
                  }
                }
              }
            }
            //}
            hunter.add(Calendar.WEEK_OF_YEAR, 1);
          }

        } else {
          LOGGER.debug("in bwdms: finding specific");
          // they want a specific count
          result.add(huntDayOfWeek(curProtoDate, bd));
        }
      }
    }

    return result;
  }

  private boolean isSameMonth(Calendar hunter, Calendar curProtoDate) {
    return (hunter.get(Calendar.MONTH) == curProtoDate.get(Calendar.MONTH))
      && (hunter.get(Calendar.YEAR) == curProtoDate.get(Calendar.YEAR));
  }

  private boolean isSameWeek(Calendar hunter, Calendar curProtoDate) {

    if (hunter.get(Calendar.WEEK_OF_YEAR) == curProtoDate.get(Calendar.WEEK_OF_YEAR)) {
      if ((curProtoDate.get(Calendar.MONTH) == Calendar.DECEMBER) && (curProtoDate.get(Calendar.WEEK_OF_YEAR) == 1)) {

        if ((hunter.get(Calendar.MONTH) == Calendar.DECEMBER)
          || ((hunter.get(Calendar.MONTH) == Calendar.JANUARY)
            && (hunter.get(Calendar.YEAR) == curProtoDate.get(Calendar.YEAR) + 1))) {
          return true;
        } else {
          return false;
        }

      } else {
        return (hunter.get(Calendar.YEAR) == curProtoDate.get(Calendar.YEAR));
      }
    }

    return false;

  }

  private boolean isSameDay(Calendar hunter, Calendar curProtoDate) {
    return (hunter.get(Calendar.DAY_OF_YEAR) == curProtoDate.get(Calendar.DAY_OF_YEAR))
      && (hunter.get(Calendar.YEAR) == curProtoDate.get(Calendar.YEAR));
  }

  /***
  @param finalList a SORTED ArrayList of Calendar objects
  ***/
  public List filterUsingBySetPos(List inputList, Set bspSet, int freqNumber) {
    LOGGER.debug("in bsp");
    //--- sanity checking
    if (inputList == null) {
      return null;
    }

    if (bspSet == null) {
      return inputList;
    }

    //--- holding areas
    List newResultList = new ArrayList();
    List curList = new ArrayList();

    //--- set up for the loop by holding the current unit value
    Calendar curCal = ((Calendar)inputList.get(0));
    int curVal = curCal.get(freqNumber);

    //--- for each date in the sorted list
    for (int ctr = 0; ctr < inputList.size(); ctr++) {
      curCal = ((Calendar)inputList.get(ctr));

      if (curCal.get(freqNumber) == curVal) {
        //--- if we're still in the same set, add it to the current set
        curList.add(inputList.get(ctr));
      } else {
        //--- if we've found a new set, process the old one  JJHNOTE
        newResultList.addAll(getSetPosItems(bspSet, curList));
        curList.clear();
        curList.add(inputList.get(ctr));
        curVal = curCal.get(freqNumber);
      }

    }
    //--- process the last set
    newResultList.addAll(getSetPosItems(bspSet, curList));

    return newResultList;
  }

  public List getSetPosItems(Set controlSet, List values) {
    LOGGER.debug("in getSetPosItems");
    List resultList = new ArrayList();
    Integer curInt = null;

    Iterator it = controlSet.iterator();
    while (it.hasNext()) {
      curInt = (Integer)it.next();
      int i = curInt.intValue();
      try {
        if (i >= 0) {
          //-- 1 is the first, not 0
          resultList.add(values.get(i - 1));
        } else {
          //-- take the nth to the last
          resultList.add(values.get(values.size() + i));
        }
      } catch (IndexOutOfBoundsException ioobe) {
        //--- this may happen as a matter of normal work
      }
    }

    return resultList;
  }

  public String getRule() {
    return rule;
  }


  public RuleDescription getRuleDescription() {
    RuleDescription descr = new RuleDescription();
    
    if (!bspSet.isEmpty()) {
      descr.setBspSet(bspSet);
    }

    if (!hourSet.isEmpty()) {
      descr.setHourSet(hourSet);
    }

    if (!minSet.isEmpty()) {
      descr.setMinSet(minSet);
    }
    
    if (!monthDaySet.isEmpty()) {
      descr.setMonthDaySet(monthDaySet);
    }
    
    if (!monthSet.isEmpty()) {
      descr.setMonthSet(monthSet);
    }
    
    if (!secSet.isEmpty()) {
      descr.setSecSet(secSet);
    }

    if (!weekDaySet.isEmpty()) {
      descr.setWeekDaySet(weekDaySet);
    }

    if (!weekNumberSet.isEmpty()) {
      descr.setWeekNumberSet(weekNumberSet);
    }
    
    if (count > 0) {
      descr.setCount(new Integer(count));
    }
        
    
    descr.setFreq(freq);
    
    descr.setInterval(new Integer(interval));
    descr.setRule(rule);
    
        
    descr.setEndDate(endDate);      
    
    descr.setOffsetAmount(new Integer(offsetAmount));
    descr.setOffsetUnit(new Integer(offsetUnit));
    
    
    descr.setStartDate(startDate);
    descr.setUntilDate(untilDate);
    
    descr.setWeekStart(new Integer(weekStart));
    
    return descr;
  }

}
