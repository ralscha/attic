package com.managestar.recurrance;

import java.util.Set;

import com.ibm.icu.util.Calendar;

public class RuleDescription {

  protected String freq;
  protected Integer count;
  protected Integer interval;
  protected Integer weekStart;
  protected String rule;

  protected Set secSet;
  protected Set minSet;
  protected Set hourSet;
  protected Set weekDaySet;
  protected Set monthDaySet;
  protected Set yearDaySet;
  protected Set yearSet;
  protected Set weekNumberSet;
  protected Set monthSet;
  protected Set bspSet;

  protected Calendar startDate;
  protected Calendar endDate;
  protected Calendar untilDate;

  protected Integer offsetUnit;
  protected Integer offsetAmount;


  public Set getBspSet() {
    return bspSet;
  }

  public Integer getCount() {
    return count;
  }

  public Calendar getEndDate() {
    return endDate;
  }

  public String getFreq() {
    return freq;
  }

  public Set getHourSet() {
    return hourSet;
  }

  public Integer getInterval() {
    return interval;
  }

  public Set getMinSet() {
    return minSet;
  }

  public Set getMonthDaySet() {
    return monthDaySet;
  }

  public Set getMonthSet() {
    return monthSet;
  }

  public Integer getOffsetAmount() {
    return offsetAmount;
  }

  public Integer getOffsetUnit() {
    return offsetUnit;
  }

  public String getRule() {
    return rule;
  }

  public Set getSecSet() {
    return secSet;
  }

  public Calendar getStartDate() {
    return startDate;
  }

  public Calendar getUntilDate() {
    return untilDate;
  }

  public Set getWeekDaySet() {
    return weekDaySet;
  }

  public Set getWeekNumberSet() {
    return weekNumberSet;
  }

  public Integer getWeekStart() {
    return weekStart;
  }

  public Set getYearDaySet() {
    return yearDaySet;
  }

  public Set getYearSet() {
    return yearSet;
  }

  public void setBspSet(Set set) {
    bspSet = set;
  }

  public void setCount(Integer integer) {
    count = integer;
  }

  public void setEndDate(Calendar calendar) {
    endDate = calendar;
  }

  public void setFreq(String string) {
    freq = string;
  }

  public void setHourSet(Set set) {
    hourSet = set;
  }

  public void setInterval(Integer integer) {
    interval = integer;
  }

  public void setMinSet(Set set) {
    minSet = set;
  }

  public void setMonthDaySet(Set set) {
    monthDaySet = set;
  }

  public void setMonthSet(Set set) {
    monthSet = set;
  }

  public void setOffsetAmount(Integer integer) {
    offsetAmount = integer;
  }

  public void setOffsetUnit(Integer integer) {
    offsetUnit = integer;
  }

  public void setRule(String string) {
    rule = string;
  }

  public void setSecSet(Set set) {
    secSet = set;
  }

  public void setStartDate(Calendar calendar) {
    startDate = calendar;
  }

  public void setUntilDate(Calendar calendar) {
    untilDate = calendar;
  }

  public void setWeekDaySet(Set set) {
    weekDaySet = set;
  }

  public void setWeekNumberSet(Set set) {
    weekNumberSet = set;
  }

  public void setWeekStart(Integer integer) {
    weekStart = integer;
  }

  public void setYearDaySet(Set set) {
    yearDaySet = set;
  }

  public void setYearSet(Set set) {
    yearSet = set;
  }

}
