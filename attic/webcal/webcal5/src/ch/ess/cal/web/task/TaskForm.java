package ch.ess.cal.web.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.annotation.struts.Variable;
import ch.ess.cal.CalUtil;
import ch.ess.cal.web.event.ReminderForm;

public class TaskForm extends ReminderForm {

  private String subject;
  private String categoryId;
  private String description;
  private String location;

  private String start;
  private String startHour;
  private String startMinute;

  private String due;
  private String dueHour;
  private String dueMinute;

  private String completeDate;

  private String importance;
  private String status;
  private String complete;

  private String groupId;
  private String[] userIds;
  
  private String tabset;

  private AttachmentFormListControl attachmentList;
  private FormFile attachmentFormFile;

  public TaskForm() {
    tabset = "generalTab";
  }

  @Override
  public ActionErrors validate(final ActionMapping mapping, final HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }

    SimpleDateFormat format = new SimpleDateFormat(Constants.getParseDateFormatPattern());
    format.setLenient(false);
    format.setTimeZone(Constants.UTC_TZ);

    if (!CalUtil.isValidTime(startHour, startMinute)) {
      errors.add("startHour", new ActionMessage("event.error.notvalidtime", translate(request, "event.start")));
    }

    if (StringUtils.isNotBlank(start)) {
      try {
        Date d = format.parse(start);
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        if (cal.get(Calendar.YEAR) < 1900) {
          errors.add("start", new ActionMessage("errors.yeartosmall"));
        }

      } catch (ParseException pe) {
        errors.add("start", new ActionMessage("errors.date", translate(request, "event.start")));
      }
    }

    if (!CalUtil.isValidTime(dueHour, dueMinute)) {
      errors.add("dueHour", new ActionMessage("event.error.notvalidtime", translate(request, "task.due")));
    }

    if (StringUtils.isNotBlank(due)) {
      try {
        Date d = format.parse(due);
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        if (cal.get(Calendar.YEAR) < 1900) {
          errors.add("due", new ActionMessage("errors.yeartosmall"));
        }

      } catch (ParseException pe) {
        errors.add("due", new ActionMessage("errors.date", translate(request, "task.due")));
      }
    }

    if (StringUtils.isNotBlank(completeDate)) {
      try {
        Date d = format.parse(completeDate);
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        if (cal.get(Calendar.YEAR) < 1900) {
          errors.add("completeDate", new ActionMessage("errors.yeartosmall"));
        }

      } catch (ParseException pe) {
        errors.add("completeDate", new ActionMessage("errors.date", translate(request, "task.completeDate")));
      }
    }

    if (errors.isEmpty()) {
      return null;
    }
    return errors;

  }

  public String getTabset() {
    return tabset;
  }

  public void setTabset(String tabset) {
    this.tabset = tabset;
  }

  public String getSubject() {
    return subject;
  }

  @ValidatorField(key = "event.subject", required = true)
  public void setSubject(final String subject) {
    this.subject = subject;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(final String category) {
    this.categoryId = category;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(final String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getImportance() {
    return importance;
  }

  @ValidatorField(key = "event.importance", required = true)
  public void setImportance(String importance) {
    this.importance = importance;
  }

  public String getStatus() {
    return status;
  }

  @ValidatorField(key = "task.status", required = true)
  public void setStatus(String status) {
    this.status = status;
  }

  public String getComplete() {
    return complete;
  }

  /*
   *         <arg position="1" name="intRange" key="${var:min}" resource="false"/>
   <arg position="2" name="intRange" key="${var:max}" resource="false"/>     
   <var><var-name>min</var-name><var-value>0</var-value></var>
   <var><var-name>max</var-name><var-value>100</var-value></var>   
   */

  @ValidatorField(key = "task.complete", required = true, validators = {
      @Validator(name = "integer"),
      @Validator(name = "intRange", vars = {@Variable(name = "min", value = "0"),
          @Variable(name = "max", value = "100")})})
  public void setComplete(String complete) {
    this.complete = complete;
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getStartHour() {
    return startHour;
  }

  @ValidatorField(key = "event.start.hour", required = false, validators = @Validator(name = "integer"))
  public void setStartHour(String startHour) {
    this.startHour = startHour;
  }

  public String getStartMinute() {
    return startMinute;
  }

  @ValidatorField(key = "event.start.minute", required = false, validators = @Validator(name = "integer"))
  public void setStartMinute(String startMinute) {
    this.startMinute = startMinute;
  }

  public String getDue() {
    return due;
  }

  public void setDue(String due) {
    this.due = due;
  }

  public String getDueHour() {
    return dueHour;
  }

  @ValidatorField(key = "task.due.hour", required = false, validators = @Validator(name = "integer"))
  public void setDueHour(String dueHour) {
    this.dueHour = dueHour;
  }

  public String getDueMinute() {
    return dueMinute;
  }

  @ValidatorField(key = "task.due.minute", required = false, validators = @Validator(name = "integer"))
  public void setDueMinute(String dueMinute) {
    this.dueMinute = dueMinute;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(final String group) {
    this.groupId = group;
  }
  
  public String[] getUserIds() {
    return (String[])ArrayUtils.clone(userIds);
  }

  public void setUserIds(final String[] userIds) {
    this.userIds = (String[])ArrayUtils.clone(userIds);
  }
  
  public String getCompleteDate() {
    return completeDate;
  }

  public void setCompleteDate(String completeDate) {
    this.completeDate = completeDate;
  }

  public FormFile getAttachmentFormFile() {
    return attachmentFormFile;
  }

  public void setAttachmentFormFile(FormFile documentFormFile) {
    this.attachmentFormFile = documentFormFile;
  }

  public AttachmentFormListControl getAttachmentList() {
    return attachmentList;
  }

  public void setAttachmentList(AttachmentFormListControl documentList) {
    this.attachmentList = documentList;
  }

}