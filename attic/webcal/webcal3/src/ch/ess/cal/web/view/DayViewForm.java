package ch.ess.cal.web.view;

import java.util.List;

import org.apache.struts.action.ActionForm;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:11 $
 * 
 * @struts.form name="dayViewForm"
 */
public class DayViewForm extends ActionForm {

  private MonthBean[] monthBean;

  private String[] hours;
  private String dayString;
  private List allDayEvents;

  //  private String[][] distribution;
  //  private int[][] colspan;
  //  private int[][] rowspan;  
  //  private int cols;  

  //Inputforms
  private String inputDate;
  private String inputMonth;
  private String inputYear;

  public String getInputMonth() {
    return inputMonth;
  }

  public void setInputMonth(String inputMonth) {
    this.inputMonth = inputMonth;
  }

  public String getInputYear() {
    return inputYear;
  }

  public void setInputYear(String inputYear) {
    this.inputYear = inputYear;
  }

  public String getInputDate() {
    return inputDate;
  }

  public void setInputDate(String inputDate) {
    this.inputDate = inputDate;
  }

  public MonthBean[] getMonthBean() {
    return monthBean;
  }

  public void setMonthBean(MonthBean[] monthBean) {
    this.monthBean = monthBean;
  }

  public String[] getHours() {
    return hours;
  }

  public void setHours(String[] hours) {
    this.hours = hours;
  }

  public List getAllDayEvents() {
    return allDayEvents;
  }

  public void setAllDayEvents(List allDayEvents) {
    this.allDayEvents = allDayEvents;
  }

  public String getDayString() {
    return dayString;
  }

  public void setDayString(String dayString) {
    this.dayString = dayString;
  }

}
