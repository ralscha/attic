package com.managestar.recurrance;

import java.util.Calendar;
import java.util.Set;

public class RuleDescription {

  protected String freq;
  protected Integer count;
  protected Integer interval;
  protected Integer weekStart;
  protected String rule;

  protected Set<Integer> secSet;
  protected Set<Integer> minSet;
  protected Set<Integer> hourSet;
  protected Set<ByDay> weekDaySet;
  protected Set<Integer> monthDaySet;
  protected Set<Integer> yearDaySet;
  protected Set<Integer> yearSet;
  protected Set<Integer> weekNumberSet;
  protected Set<Integer> monthSet;
  protected Set<Integer> bspSet;

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

  public void setBspSet(Set<Integer> set) {
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

  public void setHourSet(Set<Integer> set) {
    hourSet = set;
  }

  public void setInterval(Integer integer) {
    interval = integer;
  }

  public void setMinSet(Set<Integer> set) {
    minSet = set;
  }

  public void setMonthDaySet(Set<Integer> set) {
    monthDaySet = set;
  }

  public void setMonthSet(Set<Integer> set) {
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

  public void setSecSet(Set<Integer> set) {
    secSet = set;
  }

  public void setStartDate(Calendar calendar) {
    startDate = calendar;
  }

  public void setUntilDate(Calendar calendar) {
    untilDate = calendar;
  }

  public void setWeekDaySet(Set<ByDay> set) {
    weekDaySet = set;
  }

  public void setWeekNumberSet(Set<Integer> set) {
    weekNumberSet = set;
  }

  public void setWeekStart(Integer integer) {
    weekStart = integer;
  }

  public void setYearDaySet(Set<Integer> set) {
    yearDaySet = set;
  }

  public void setYearSet(Set<Integer> set) {
    yearSet = set;
  }

}
