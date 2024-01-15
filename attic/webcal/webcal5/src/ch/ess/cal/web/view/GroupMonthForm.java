package ch.ess.cal.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import ch.ess.cal.service.EventDistribution;


public class GroupMonthForm extends ActionForm {

  private String monthName;
  private int year;
  private int month;

  private List<Integer> weekColspan;
  private List<Integer> weekNos;
  private String[] weekdayNames;
  private String[] monthNames;
  private String[] holidays;
  private String[] weekends;
  private String[] daytitle;
  private String collision;
  private int length;
  private int today;

  private List<UserEvents> users;
  private Map<String, Map<String, EventDistribution>> userEvents;
  private Integer groupId;
  private String group;
  private boolean readOnly;
  private boolean showQuickAdd;

  private int graphicTyp;

  //Inputforms
  private int inputMonth;
  private int inputYear;

  public GroupMonthForm() {
    weekColspan = new ArrayList<Integer>();
    weekNos = new ArrayList<Integer>();
    weekdayNames = null;
    monthNames = null;
    holidays = null;
  }

  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    weekColspan.clear();
    weekNos.clear();
    weekdayNames = null;
    monthNames = null;
    holidays = null;
    daytitle = null;
  }

  public String getMonthName() {
    return monthName;
  }

  public List<Integer> getWeekColspan() {
    return weekColspan;
  }

  public String[] getWeekdayNames() {
    return weekdayNames;
  }

  public List<Integer> getWeekNos() {
    return weekNos;
  }

  public void setMonthName(String string) {
    monthName = string;
  }

  public void setWeekColspan(List<Integer> list) {
    weekColspan = list;
  }

  public void setWeekdayNames(String[] wn) {
    weekdayNames = wn;
  }

  public void setWeekNos(List<Integer> list) {
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

  public String[] getWeekends() {
    return weekends;
  }

  public void setWeekends(String[] weekends) {
    this.weekends = weekends;
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

  public List<UserEvents> getUsers() {
    return users;
  }

  public void setUsers(List<UserEvents> list) {
    users = list;
  }

  public Map<String, Map<String, EventDistribution>> getUserEvents() {
    return userEvents;
  }

  public void setUserEvents(Map<String, Map<String, EventDistribution>> map) {
    userEvents = map;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer long1) {
    groupId = long1;
  }

  public String getGroup() {
    return group;
  }

  public String[] getDaytitle() {
    return daytitle;
  }

  public void setDaytitle(String[] daytitle) {
    this.daytitle = daytitle;
  }

  public int getGraphicTyp() {
    return graphicTyp;
  }

  public void setGraphicTyp(int graphicTyp) {
    this.graphicTyp = graphicTyp;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public boolean isShowQuickAdd() {
    return showQuickAdd;
  }

  public void setShowQuickAdd(boolean showQuickAdd) {
    this.showQuickAdd = showQuickAdd;
  }


public String getCollision() {
	return collision;
}

public void setCollision(String collision) {
	this.collision = collision;
}
  
  
}
