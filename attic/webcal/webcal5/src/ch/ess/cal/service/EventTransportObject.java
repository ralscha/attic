package ch.ess.cal.service;

import java.io.Serializable;

public class EventTransportObject implements Serializable {

  private String start;
  private String end;
  private boolean allDay;
  private Integer startHour;
  private Integer startMinute;
  private Integer endHour;
  private Integer endMinute;

  private String subject;
  private String location;
  private String categories;
  private String entryId;
  private String user;
  private String sensivity;
  private String importance;
  private String body;

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCategories() {
    return categories;
  }

  public void setCategories(String categories) {
    this.categories = categories;
  }

  public String getEntryId() {
    return entryId;
  }

  public void setEntryId(String entryId) {
    this.entryId = entryId;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getSensivity() {
    return sensivity;
  }

  public void setSensivity(String sensivity) {
    this.sensivity = sensivity;
  }

  public String getImportance() {
    return importance;
  }

  public void setImportance(String importance) {
    this.importance = importance;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public boolean isAllDay() {
    return allDay;
  }

  public void setAllDay(boolean allDay) {
    this.allDay = allDay;
  }

  public Integer getStartHour() {
    return startHour;
  }

  public void setStartHour(Integer startHour) {
    this.startHour = startHour;
  }

  public Integer getStartMinute() {
    return startMinute;
  }

  public void setStartMinute(Integer startMinute) {
    this.startMinute = startMinute;
  }

  public Integer getEndHour() {
    return endHour;
  }

  public void setEndHour(Integer endHour) {
    this.endHour = endHour;
  }

  public Integer getEndMinute() {
    return endMinute;
  }

  public void setEndMinute(Integer endMinute) {
    this.endMinute = endMinute;
  }

}
