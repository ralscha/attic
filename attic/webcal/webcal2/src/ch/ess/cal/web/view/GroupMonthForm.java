package ch.ess.cal.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 27.09.2003 
  * @struts.form name="groupMonthForm"
  */  
public class GroupMonthForm extends ActionForm {
  
  private String monthName;
  private int year;
  private int month;
  
  private List weekColspan;
  private List weekNos;
  private String[] weekdayNames;
  private String[] monthNames;
  private String[] holidays;
  private int length;
  private int today;
        
  private List users;
  private Map userEvents;
  private Long departmentId;
  private String department;
  
  //Inputforms
  private int inputMonth;
  private int inputYear;
  
  public GroupMonthForm() {
    weekColspan = new ArrayList();
    weekNos = new ArrayList();
    weekdayNames = null;
    monthNames = null;
    holidays = null;
  }


  public void reset(ActionMapping mapping, HttpServletRequest request) {
    inputMonth = -1;
    inputYear = -1;
    
    weekColspan.clear();
    weekNos.clear();
    weekdayNames = null;
    monthNames = null;
    holidays = null;    
  }

  public String getMonthName() {
    return monthName;
  }

  public List getWeekColspan() {
    return weekColspan;
  }

  public String[] getWeekdayNames() {
    return weekdayNames;
  }

  public List getWeekNos() {
    return weekNos;
  }

  public void setMonthName(String string) {
    monthName = string;
  }

  public void setWeekColspan(List list) {
    weekColspan = list;
  }

  public void setWeekdayNames(String[] wn) {
    weekdayNames = wn;
  }

  public void setWeekNos(List list) {
    weekNos = list;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int string) {
    year = string;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int i) {
    length = i;
  }

  public String[] getMonthNames() {
    return monthNames;
  }

  public void setMonthNames(String[] mn) {
    monthNames = mn;
  }


  public String[] getHolidays() {
    return holidays;
  }

  public void setHolidays(String[] h) {
    holidays = h;
  }

  public int getToday() {
    return today;
  }

  public void setToday(int i) {
    today = i;
  }


  public int getInputMonth() {
    return inputMonth;
  }

  public int getInputYear() {
    return inputYear;
  }

  public void setInputMonth(int i) {
    inputMonth = i;
  }

  public void setInputYear(int i) {
    inputYear = i;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int i) {
    month = i;
  }

  public List getUsers() {
    return users;
  }

  public void setUsers(List list) {
    users = list;
  }

  public Map getUserEvents() {
    return userEvents;
  }

  public void setUserEvents(Map map) {
    userEvents = map;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long long1) {
    departmentId = long1;
  }

  public String getDepartment() {
    return department;
  }

}
