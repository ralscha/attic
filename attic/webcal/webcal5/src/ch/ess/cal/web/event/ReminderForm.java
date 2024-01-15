package ch.ess.cal.web.event;

import ch.ess.base.web.AbstractForm;

public class ReminderForm extends AbstractForm {

  private ReminderFormListControl reminderList;
  private String reminderEmail;
  private String reminderEmailSelect;
  private String reminderTimeUnit;
  private String reminderTime;
  private String reminderBefore;

  public String getReminderEmail() {
    return reminderEmail;
  }

  public void setReminderEmail(String reminderEmail) {
    this.reminderEmail = reminderEmail;
  }

  public String getReminderEmailSelect() {
    return reminderEmailSelect;
  }

  public void setReminderEmailSelect(String reminderEmailSelect) {
    this.reminderEmailSelect = reminderEmailSelect;
  }

  public ReminderFormListControl getReminderList() {
    return reminderList;
  }

  public void setReminderList(ReminderFormListControl reminderList) {
    this.reminderList = reminderList;
  }

  public String getReminderTime() {
    return reminderTime;
  }

  public void setReminderTime(String reminderTime) {
    this.reminderTime = reminderTime;
  }

  public String getReminderTimeUnit() {
    return reminderTimeUnit;
  }

  public void setReminderTimeUnit(String reminderTimeUnit) {
    this.reminderTimeUnit = reminderTimeUnit;
  }

  public String getReminderBefore() {
    return reminderBefore;
  }

  public void setReminderBefore(String reminderBefore) {
    this.reminderBefore = reminderBefore;
  }

}
