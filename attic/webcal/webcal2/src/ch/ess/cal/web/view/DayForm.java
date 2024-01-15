package ch.ess.cal.web.view;


import java.util.List;

import org.apache.struts.action.ActionForm;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @struts.form name="dayForm"
  */  
public class DayForm extends ActionForm {

  private String monthName;
  private int year;
  private int month;
  private int day;  
  private String[] hours;  
  private String dayString;
  private List allDayEvents;
  private String[][] distribution;
  private int[][] colspan;
  private int[][] rowspan;
  
  private int cols;

  public int[][] getColspan() {
    return colspan;
  }

  public void setColspan(int[][] colspan) {
    this.colspan = colspan;
  }

  public int[][] getRowspan() {
    return rowspan;
  }

  public void setRowspan(int[][] rowspan) {
    this.rowspan = rowspan;
  }

  public int getCols() {
    return cols;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }

  public String[][] getDistribution() {
    return distribution;
  }

  public void setDistribution(String[][] distribution) {
    this.distribution = distribution;
  }

  public List getAllDayEvents() {
    return allDayEvents;
  }

  public void setAllDayEvents(List allDayEvents) {
    this.allDayEvents = allDayEvents;
  }



  public String[] getHours() {
    return hours;
  }

  public void setHours(String[] hours) {
    this.hours = hours;
  }

  public String getDayString() {
    return dayString;
  }

  public void setDayString(String dayString) {
    this.dayString = dayString;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

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

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
