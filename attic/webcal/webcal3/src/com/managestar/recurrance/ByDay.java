package com.managestar.recurrance;

/***

 By night one way, BYDAY another...

 Represents a day of the week and OPTIONALLY which count of that day of the week.

 For values of count:
 -N means the Nth last given week day in the unit of measure
 0 means all given week days in the unit of measure
 +N means the Nth first given week day in the unit of measure
 
 If you think this should be a bean, I encourage you to suck on one.

 ***/

public class ByDay {
  public ByDay(int count, String weekday) {
    this.count = count;
    this.weekday = weekday;
  }

  public ByDay(String descriptor) {
    //--- get the week
    weekday = descriptor.substring(descriptor.length() - 2);
    if (!Constants.weekDayValues.contains(weekday)) {
      throw new IllegalArgumentException("Error: " + weekday
          + " is not a valid weekday! Use capitolized two letter codes only");
    }

    //--- get the count
    if (descriptor.length() > 2) {
      count = Integer.parseInt(descriptor.substring(0, descriptor.length() - 2));
      if (count > 53 || count < -53) {
        throw new IllegalArgumentException("Error: You have asked for an illegal number of weekdays");
      }
    }
  }

  public int count = 0;
  public String weekday = null;

  @Override
  public String toString() {
    return "[" + count + ":" + weekday + "]";
  }
}
