package com.managestar.recurrance;

import java.util.List;

import com.ibm.icu.util.Calendar;

/****

Describes a single recurrance rule.  This object is actually a facade for a number
of smaller objects that a user should never need to worry about.

****/

public interface RecurranceRule {

  public static final int MINIMAL_DAYS_IN_FIRST_WEEK = 4;
  /***
  Returns a List of Calendar objects which match the getn rule.  You can then create 
  a whatever for each of those dates if you want to.  Note that the start and end
  may be a subset of the rules start and end range.
  @param start the inclusive start of the range over which to return dates
  @param end the inclusive end of the range over which to dates
  @return a list of dates
  ***/
  List getAllMatchingDatesOverRange(Calendar start, Calendar end);

  List getAllMatchingDates();

  /*** 
  @return true iff the getn date appears as a recurrance in the rule set.
  ***/
  boolean matches(Calendar value);

  /***
  @return the next date in the recurrance after the getn one, or null if there is no
  such date.  Note that even if value is a match, this will return the one after it.
  ***/
  Calendar next(Calendar value);

  /***
  @return the previous date in the recurrance before the getn one, or null if there 
  is no such date.  Note that even if value is a match, this will return the one 
  before it.
  ***/
  Calendar prev(Calendar value);

  /***
  @return the rule used to describe this recurrance
  ***/
  String getRule();

  RuleDescription getRuleDescription();
}
