package ch.ess.cal.web.task;

import ch.ess.cal.service.EventListDateObject;

import com.cc.framework.ui.model.Checkable;

public class TaskRow implements Checkable {
  private String id;
  private String subject;
  private String deletesubject;
  private String category;
  private EventListDateObject start;
  private EventListDateObject due;
  private int complete;
  private int checkState;
  private String reminderHtml;
  private String attachmentHtml;
  private String status;
  private String priority;
  private String tooltip;
  private String tooltipHeader;
  private int tooltipWidth;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDeletesubject() {
    return deletesubject;
  }

  public void setDeletesubject(String deletesubject) {
    this.deletesubject = deletesubject;
  }

  public String getReminderHtml() {
    return reminderHtml;
  }

  public void setReminderHtml(String reminderHtml) {
    this.reminderHtml = reminderHtml;
  }

  public EventListDateObject getStart() {
    return start;
  }

  public void setStart(EventListDateObject start) {
    this.start = start;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getCheckState() {
    return checkState;
  }

  public void setCheckState(final int state) {
    checkState = state;
  }

  public EventListDateObject getDue() {
    return due;
  }

  public void setDue(EventListDateObject due) {
    this.due = due;
  }

  public int getComplete() {
    return complete;
  }

  public void setComplete(int complete) {
    this.complete = complete;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getTooltip() {
    return tooltip;
  }

  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
  }

  public String getTooltipHeader() {
    return tooltipHeader;
  }

  public void setTooltipHeader(String tooltipHeader) {
    this.tooltipHeader = tooltipHeader;
  }

  public int getTooltipWidth() {
    return tooltipWidth;
  }

  public void setTooltipWidth(int tooltipWidth) {
    this.tooltipWidth = tooltipWidth;
  }

  public String getAttachmentHtml() {
    return attachmentHtml;
  }

  public void setAttachmentHtml(String attachmentHtml) {
    this.attachmentHtml = attachmentHtml;
  }

}
