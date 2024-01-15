package ch.ess.cal.web.view;

public class DayAllDayDescription {
  private String time;
  private String subject;
  private String location;
  private String category;
  private String colour;
  private String reminder;
  private String recurrence;

  public String getRecurrence() {
    return recurrence;
  }

  public void setRecurrence(String recurrence) {
    this.recurrence = recurrence;
  }

  public String getReminder() {
    return reminder;
  }

  public void setReminder(String reminder) {
    this.reminder = reminder;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getColour() {
    return colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

}
