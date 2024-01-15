package ch.ess.cal.web.view;

import java.util.ArrayList;
import java.util.List;

public class MonthBean {

  private int today;
  private int year;
  private int month;
  private String monthName;
  private String[] weekdayNames;

  private List<int[]> weekList = new ArrayList<int[]>();
  private List<Integer> weekNoList = new ArrayList<Integer>();

  private String[] holidays;
  private String[] daytitle;
  private List<EventDescription>[] events;
  private boolean[] weekends;
  private int weekLength;

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public String getMonthName() {
    return monthName;
  }

  public void setMonthName(String monthName) {
    this.monthName = monthName;
  }

  public String[] getWeekdayNames() {
    return weekdayNames;
  }

  public void setWeekdayNames(String[] weekdayNames) {
    this.weekdayNames = weekdayNames;
  }

  public List<int[]> getWeekList() {
    return weekList;
  }

  public void setWeekList(List<int[]> weekList) {
    this.weekList = weekList;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public List<Integer> getWeekNoList() {
    return weekNoList;
  }

  public void setWeekNoList(List<Integer> weekNoList) {
    this.weekNoList = weekNoList;
  }

  public String[] getDaytitle() {
    return daytitle;
  }

  public void setDaytitle(String[] daytitle) {
    this.daytitle = daytitle;
  }

  public List<EventDescription>[] getEvents() {
    return events;
  }

  public void setEvents(List<EventDescription>[] events) {
    this.events = events;
  }

  public String[] getHolidays() {
    return holidays;
  }

  public void setHolidays(String[] holidays) {
    this.holidays = holidays;
  }

  public boolean[] getWeekends() {
    return weekends;
  }

  public void setWeekends(boolean[] weekends) {
    this.weekends = weekends;
  }

  public int getToday() {
    return today;
  }

  public void setToday(int today) {
    this.today = today;
  }

  public int getWeekLength() {
    return weekLength;
  }

  public void setWeekLength(int weekLength) {
    this.weekLength = weekLength;
  }
}
