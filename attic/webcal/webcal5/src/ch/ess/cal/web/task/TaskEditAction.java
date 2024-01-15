package ch.ess.cal.web.task;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.model.User;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.enums.StatusEnum;
import ch.ess.cal.enums.TaskReminderBeforeEnum;
import ch.ess.cal.model.Attachment;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.model.Task;
import ch.ess.cal.web.event.ReminderFormListControl;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

public class TaskEditAction extends AbstractEditAction<Task> {

  private CategoryDao categoryDao;
  private UserDao userDao;
  private GroupDao groupDao;

  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setGroupDao(GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    TaskForm taskForm = (TaskForm)form;

    taskForm.setImportance(ImportanceEnum.NORMAL.getValue());
    taskForm.setStatus(StatusEnum.NOTSTARTED.getValue());
    taskForm.setComplete("0");

    ReminderFormListControl reminderListControl = new ReminderFormListControl();
    reminderListControl.populateEmptyList();
    taskForm.setReminderList(reminderListControl);

    AttachmentFormListControl attachmentListControl = new AttachmentFormListControl();
    attachmentListControl.populateEmptyList();
    taskForm.setAttachmentList(attachmentListControl);
    
    User user = Util.getUser(ctx.session(), userDao);
    String[] userIds = new String[1];
    userIds[0] = user.getId().toString();
    taskForm.setUserIds(userIds);
  }

  @Override
  public void formToModel(ActionContext ctx, Task task) {

    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();
    TaskForm taskForm = (TaskForm)ctx.form();

    if (task.getId() == null) {
      task.setSequence(0);
      task.setPriority(0);
      task.setSensitivity(SensitivityEnum.PRIVATE);

      task.setCreateDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());
    } else {
      task.setModificationDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());
    }

    task.setSubject(taskForm.getSubject());
    task.setLocation(taskForm.getLocation());


    task.getEventCategories().removeAll(task.getEventCategories());
    if (StringUtils.isNotBlank(taskForm.getCategoryId())) {
      EventCategory eventCategory = new EventCategory();
      eventCategory.setCategory(categoryDao.findById(taskForm.getCategoryId()));
      eventCategory.setEvent(task);
      task.getEventCategories().add(eventCategory);
    }

    ImportanceEnum importance = StringValuedEnumReflect.getEnum(ImportanceEnum.class, taskForm.getImportance());
    task.setImportance(importance);

    StatusEnum status = StringValuedEnumReflect.getEnum(StatusEnum.class, taskForm.getStatus());
    task.setStatus(status);
    task.setComplete(Integer.parseInt(taskForm.getComplete()));

    task.setDescription(taskForm.getDescription());

    boolean allDay = false;
    boolean allDayDue = false;

    String startHour = taskForm.getStartHour();
    String startMinute = taskForm.getStartMinute();
    String start = taskForm.getStart();

    String dueHour = taskForm.getDueHour();
    String dueMinute = taskForm.getDueMinute();
    String due = taskForm.getDue();

    String completeDate = taskForm.getCompleteDate();

    //Dates
    if (StringUtils.isBlank(startHour) && StringUtils.isBlank(startMinute)) {
      allDay = true;
    }

    if (StringUtils.isBlank(dueHour) && StringUtils.isBlank(dueMinute)) {
      allDayDue = true;
    }

    task.setAllDay(allDay);
    task.setAllDayDue(allDayDue);

    if (StringUtils.isNotBlank(start)) {
      Calendar startCal;

      if (allDay) {
        startCal = CalUtil.string2Calendar(start, Constants.UTC_TZ);
        task.setStartDate(startCal.getTimeInMillis());
      } else {
        startCal = CalUtil.string2Calendar(start, startHour, startMinute, timeZone);
        task.setStartDate(startCal.getTimeInMillis());
      }
    } else {
      task.setStartDate(null);
    }

    if (StringUtils.isNotBlank(due)) {
      Calendar dueCal;

      if (allDayDue) {
        dueCal = CalUtil.string2Calendar(due, Constants.UTC_TZ);
        task.setDueDate(dueCal.getTimeInMillis());
      } else {
        dueCal = CalUtil.string2Calendar(due, dueHour, dueMinute, timeZone);
        task.setDueDate(dueCal.getTimeInMillis());
      }
    } else {
      task.setDueDate(null);
    }

    if (StringUtils.isNotBlank(completeDate)) {
      Calendar completeCal = CalUtil.string2Calendar(completeDate, Constants.UTC_TZ);
      task.setCompleteDate(completeCal.getTimeInMillis());
    } else {
      task.setCompleteDate(null);
    }

    //Users
    task.getUsers().removeAll(task.getUsers());
    
    if (StringUtils.isNotBlank(taskForm.getGroupId())) {
      task.setGroup(groupDao.findById(taskForm.getGroupId()));
    } else {
      task.setGroup(null);

      String[] userIds = taskForm.getUserIds();
      if (userIds != null) {

        for (String id : userIds) {
          if (StringUtils.isNotBlank(id)) {
            User auser = userDao.findById(id);
            task.getUsers().add(auser);
          }
        }
      }
      if (task.getUsers().size() == 0) {
        //keiner ausgew�hlt, also mich selber reintun
        task.getUsers().add(user);
      }

    }

    //Reminders
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("locale", user.getLocale());
    taskForm.getReminderList().formToModel(task, task.getReminders(), parameters);

    //Attachments
    taskForm.getAttachmentList().formToModel(task, task.getAttachments());

    if (task.getUid() == null) {
      task.setUid(CalUtil.createUid(task));
    }

  }

  @Override
  public void modelToForm(ActionContext ctx, Task task) {
    TaskForm taskForm = (TaskForm)ctx.form();

    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();

    taskForm.setTabset("generalTab");

    taskForm.setSubject(task.getSubject());

    if (!task.getEventCategories().isEmpty()) {
      EventCategory eventCategory = task.getEventCategories().iterator().next();
      taskForm.setCategoryId(eventCategory.getCategory().getId().toString());
    } else {
      taskForm.setCategoryId(null);
    }
    
    if (task.getGroup() != null) {
      taskForm.setGroupId(task.getGroup().getId().toString());
    } else {
      taskForm.setGroupId(null);
    }

    Set<User> users = task.getUsers();
    String[] userIds = null;

    if (!users.isEmpty()) {
      userIds = new String[users.size()];

      int ix = 0;
      for (User auser : users) {
        userIds[ix++] = auser.getId().toString();
      }
    } 
    taskForm.setUserIds(userIds);

    taskForm.setDescription(task.getDescription());
    taskForm.setLocation(task.getLocation());

    taskForm.setImportance(task.getImportance().getValue());
    taskForm.setStatus(task.getStatus().getValue());
    taskForm.setComplete(task.getComplete().toString());

    taskForm.setStartHour(null);
    taskForm.setStartMinute(null);
    taskForm.setDueHour(null);
    taskForm.setDueMinute(null);

    if (task.getStartDate() != null) {
      Calendar startCal = CalUtil.utcLong2UserCalendar(task.getStartDate(), timeZone);
      taskForm.setStart(CalUtil.userCalendar2String(startCal));
      if (!task.isAllDay()) {
        taskForm.setStartHour(Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.HOUR_OF_DAY)));
        taskForm.setStartMinute(Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.MINUTE)));
      }
    } else {
      taskForm.setStart(null);
    }

    if (task.getDueDate() != null) {
      Calendar dueCal = CalUtil.utcLong2UserCalendar(task.getDueDate(), timeZone);
      taskForm.setDue(CalUtil.userCalendar2String(dueCal));
      if (!task.getAllDayDue()) {
        taskForm.setDueHour(Constants.TWO_DIGIT_FORMAT.format(dueCal.get(Calendar.HOUR_OF_DAY)));
        taskForm.setDueMinute(Constants.TWO_DIGIT_FORMAT.format(dueCal.get(Calendar.MINUTE)));
      }
    } else {
      taskForm.setDue(null);
    }

    if (task.getCompleteDate() != null) {
      Calendar completeCal = CalUtil.utcLong2UserCalendar(task.getCompleteDate(), timeZone);
      taskForm.setCompleteDate(CalUtil.userCalendar2String(completeCal));
    } else {
      taskForm.setCompleteDate(null);
    }

    //Reminder
    ReminderFormListControl reminderListControl = new ReminderFormListControl();
    reminderListControl.populateList(ctx, task.getReminders());
    taskForm.setReminderList(reminderListControl);

    taskForm.setReminderEmail(null);
    taskForm.setReminderEmailSelect(null);
    taskForm.setReminderTime(null);
    taskForm.setReminderTimeUnit(null);

    AttachmentFormListControl attachmentListControl = new AttachmentFormListControl();
    Set<Attachment> attachments = task.getAttachments();
    attachmentListControl.populateList(ctx, attachments);
    taskForm.setAttachmentList(attachmentListControl);

  }

  @Override
  protected void afterSave(FormActionContext ctx, Task task) {
    Set<Reminder> reminders = task.getReminders();
    if (!reminders.isEmpty()) {
      Calendar startCal = null;
      if (task.getStartDate() != null) {
        startCal = CalUtil.utcLong2UtcCalendar(task.getStartDate());
      }
      Calendar dueCal = null;
      if (task.getDueDate() != null) {
        dueCal = CalUtil.utcLong2UtcCalendar(task.getDueDate());
      }
      if ((startCal != null) || (dueCal != null)) {
        for (Reminder reminder : reminders) {
          TaskReminderBeforeEnum before = reminder.getTaskBefore();
          if (before == TaskReminderBeforeEnum.BEFORE_DUE && dueCal == null) {
            before = TaskReminderBeforeEnum.BEFORE_START;
            reminder.setTaskBefore(before);
          } else if (before == TaskReminderBeforeEnum.BEFORE_START && startCal == null) {
            before = TaskReminderBeforeEnum.BEFORE_DUE;
            reminder.setTaskBefore(before);
          }

          Calendar reminderStart;
          if (before == TaskReminderBeforeEnum.BEFORE_START) {
            reminderStart = (Calendar)startCal.clone();
          } else {
            reminderStart = (Calendar)dueCal.clone();
          }

          reminderStart.add(Calendar.MINUTE, -reminder.getMinutesBefore());
          reminder.setReminderDate(reminderStart.getTimeInMillis());
        }
      } else {
        reminders.clear();
      }
    }

    AttachmentFormListControl attachmentListControl = new AttachmentFormListControl();
    Set<Attachment> attachments = task.getAttachments();
    attachmentListControl.populateList(ctx, attachments);
    TaskForm taskForm = (TaskForm)ctx.form();
    taskForm.setAttachmentList(attachmentListControl);

    getDao().save(task);

    ReminderFormListControl reminderListControl = new ReminderFormListControl();
    reminderListControl.populateList(ctx, task.getReminders());
    taskForm.setReminderList(reminderListControl);
  }

  public void add_onClick(final FormActionContext ctx) {
    TaskForm taskForm = (TaskForm)ctx.form();
    taskForm.getReminderList().add(ctx);
  }

  public void addAttachment_onClick(final FormActionContext ctx) throws IOException {
    TaskForm taskForm = (TaskForm)ctx.form();
    taskForm.getAttachmentList().add(ctx);
  }

}
