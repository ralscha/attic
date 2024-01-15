package ch.ess.cal.web.event;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.beanutils.LazyDynaClass;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.Constants;
import ch.ess.cal.Util;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.PosEnum;
import ch.ess.cal.enums.RecurrenceTypeEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.enums.StringValuedEnumReflect;
import ch.ess.cal.enums.TimeEnum;
import ch.ess.cal.model.Category;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.model.Reminder;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.CategoryDao;
import ch.ess.cal.persistence.EventDao;
import ch.ess.cal.persistence.GroupDao;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.event.impl.EventUtil;
import ch.ess.cal.service.impl.UserConfig;
import ch.ess.cal.web.AbstractEditAction;
import ch.ess.cal.web.DynaListDataModel;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;

/** 
 * @author sr
 * @version $Revision: 1.20 $ $Date: 2005/05/04 09:15:42 $ 
 * 
 * @struts.action path="/editEvent" name="eventForm" input="/eventedit.jsp" scope="session" validate="false" roles="$event" 
 * @struts.action-forward name="back"  path="/listEvent.do" redirect="true"
 * @struts.action-forward name="group" path="/groupMonth.do?inputYear={0}&amp;inputMonth={1}" redirect="true"
 * @struts.action-forward name="month" path="/monthView.do?inputYear={0}&amp;inputMonth={1}" redirect="true"
 * @struts.action-forward name="year"  path="/yearView.do?inputYear={0}" redirect="true"
 * 
 * @spring.bean name="/editEvent" lazy-init="true"
 * @spring.property name="clazz" value="ch.ess.cal.model.Event" 
 * @spring.property name="dao" reflocal="eventDao"
 **/
public class EventEditAction extends AbstractEditAction {

  private static LazyDynaClass lazyDynaClass = new LazyDynaClass();
  static {
    lazyDynaClass.add("dbId", Integer.class);
  }

  private CategoryDao categoryDao;
  private GroupDao groupDao;
  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;

  /** 
   * @spring.property reflocal="categoryDao"
   */
  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  /** 
   * @spring.property reflocal="groupDao"
   */
  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  /** 
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * @spring.property reflocal="userConfigurationDao"
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  @Override
  public void back_onClick(FormActionContext ctx) {
    EventForm eventForm = (EventForm)ctx.form();
    if (StringUtils.isNotBlank(eventForm.getFrom())) {
      ctx.forwardByName(eventForm.getFrom(), eventForm.getInputYear(), eventForm.getInputMonth());
    } else {
      super.back_onClick(ctx);
    }
  }

  @Override
  public void doPostExecute(ActionContext ctx) throws Exception {

    EventForm eventForm = (EventForm)ctx.form();

    if (eventForm.getTabset().equals("recurrenceTab")) {

      EventDao eventDao = (EventDao)getDao();

      if (StringUtils.isNotBlank(eventForm.getModelId())) {
        Event event = (Event)eventDao.get(eventForm.getModelId());
        if (event.getRecurrences().size() == 0) {
          eventForm.setNewRecurrence();
        }
      } else {
        eventForm.setNewRecurrence();
      }
    }

    super.doPostExecute(ctx);
  }

  @Override
  protected void newItem(final ActionContext ctx) {
    EventForm eventForm = (EventForm)ctx.form();

    String from = eventForm.getFrom();
    String inputDay = eventForm.getInputDay();
    String inputMonth = eventForm.getInputMonth();
    String inputYear = eventForm.getInputYear();

    clearForm(eventForm);

    eventForm.setFrom(from);
    eventForm.setInputDay(inputDay);
    eventForm.setInputMonth(inputMonth);
    eventForm.setInputYear(inputYear);

    if (StringUtils.isNotBlank(inputDay) && StringUtils.isNotBlank(inputMonth) && StringUtils.isNotBlank(inputYear)) {
      int day = Integer.parseInt(inputDay);
      int month = Integer.parseInt(inputMonth);
      int year = Integer.parseInt(inputYear);
      Calendar startCal = new GregorianCalendar(year, month, day);
      eventForm.setStart(Util.userCalendar2String(startCal));
    }

    eventForm.setImportance(String.valueOf(ImportanceEnum.NORMAL.getValue()));
    eventForm.setSensitivity(String.valueOf(SensitivityEnum.PUBLIC.getValue()));

    SimpleListControl emailListControl = new SimpleListControl();
    DynaListDataModel dataModel = new DynaListDataModel();
    emailListControl.setDataModel(dataModel);
    eventForm.setReminderList(emailListControl);

    User user = Util.getUser(ctx.session(), userDao);
    String[] userIds = new String[1];
    userIds[0] = user.getId().toString();
    eventForm.setUserIds(userIds);

  }

  @Override
  public void formToModel(final ActionContext ctx, final Object model) {
    Event event = (Event)model;
    EventForm eventForm = (EventForm)ctx.form();

    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();

    event.setSubject(eventForm.getSubject());
    event.setLocation(eventForm.getLocation());

    if (event.getId() == null) {
      event.setCreateDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());
    } else {
      event.setModificationDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());
    }

    event.getUsers().clear();

    if (StringUtils.isNotBlank(eventForm.getGroupId())) {
      event.setGroup((Group)groupDao.get(eventForm.getGroupId()));
    } else {
      event.setGroup(null);

      String[] userIds = eventForm.getUserIds();
      if (userIds != null) {

        for (String id : userIds) {
          if (StringUtils.isNotBlank(id)) {
            User auser = (User)userDao.get(id);
            event.getUsers().add(auser);
          }
        }
      }

    }

    event.getEventCategories().clear();
    EventCategory eventCategory = new EventCategory();
    eventCategory.setCategory((Category)categoryDao.get(eventForm.getCategoryId()));
    eventCategory.setEvent(event);
    event.getEventCategories().add(eventCategory);

    ImportanceEnum importance = StringValuedEnumReflect.getEnum(ImportanceEnum.class, eventForm.getImportance());
    SensitivityEnum sensitivity = StringValuedEnumReflect.getEnum(SensitivityEnum.class, eventForm.getSensitivity());

    event.setImportance(importance);
    event.setSensitivity(sensitivity);

    event.setDescription(eventForm.getDescription());

    boolean allDay = eventForm.isAllday();

    String startHour = eventForm.getStartHour();
    String startMinute = eventForm.getStartMinute();
    String endHour = eventForm.getEndHour();
    String endMinute = eventForm.getEndMinute();
    String start = eventForm.getStart();
    String end = eventForm.getEnd();

    //Dates
    if (StringUtils.isBlank(startHour) && StringUtils.isBlank(startMinute) && StringUtils.isBlank(endHour)
        && StringUtils.isBlank(endMinute)) {
      allDay = true;
    }

    event.setAllDay(allDay);

    Calendar startCal;
    Calendar endCal;

    if (allDay) {
      startCal = Util.string2Calendar(start, timeZone);
      event.setStartDate(startCal.getTimeInMillis());
      if (StringUtils.isNotBlank(end)) {
        endCal = Util.string2Calendar(end, timeZone);
        event.setEndDate(endCal.getTimeInMillis());
      } else {
        endCal = (Calendar)startCal.clone();
        event.setEndDate(event.getStartDate());
      }
    } else {
      startCal = Util.string2Calendar(start, startHour, startMinute, timeZone);
      event.setStartDate(startCal.getTimeInMillis());
      if (StringUtils.isNotBlank(end)) {
        endCal = Util.string2Calendar(end, endHour, endMinute, timeZone);
        event.setEndDate(endCal.getTimeInMillis());
      } else {

        String h = startHour;
        String m = startMinute;

        if (StringUtils.isNotBlank(endHour) || StringUtils.isNotBlank(endMinute)) {
          h = endHour;
          m = endMinute;
        }

        endCal = Util.string2Calendar(start, h, m, timeZone);
        event.setEndDate(endCal.getTimeInMillis());
      }

    }

    if (event.getEndDate() < event.getStartDate()) {
      long temp = event.getStartDate();
      Calendar tempCal = startCal;

      event.setStartDate(event.getEndDate());
      startCal = endCal;

      event.setEndDate(temp);
      endCal = tempCal;
    }

    //not supported in gui
    event.setSequence(0);
    event.setPriority(0);

    //Reminders
    DynaListDataModel dataModel = (DynaListDataModel)eventForm.getReminderList().getDataModel();
    addReminders(event, user.getLocale(), dataModel);

    //Recurrence

    if (eventForm.isRecurrenceActive()) {
      if (StringUtils.isNotBlank(eventForm.getRecurrenceType())) {
        Recurrence recurrence = new Recurrence();
        recurrence.setEvent(event);
        recurrence.setExclude(false);

        recurrence.setActive(eventForm.isRecurrenceActive());

        RecurrenceTypeEnum recurrenceType = StringValuedEnumReflect.getEnum(RecurrenceTypeEnum.class, eventForm.getRecurrenceType());
        recurrence.setType(recurrenceType);

        switch (recurrenceType) {
          case DAILY :
            recurrence.setInterval(new Integer(eventForm.getDailyEveryDay()));
            break;
          case WEEKLY :
            recurrence.setInterval(new Integer(eventForm.getWeeklyEveryWeek()));

            String[] weeklyWeekday = eventForm.getWeeklyWeekday();
            int[] weeklyWeekdayInt = new int[weeklyWeekday.length];
            for (int i = 0; i < weeklyWeekday.length; i++) {
              weeklyWeekdayInt[i] = Integer.parseInt(weeklyWeekday[i]);
            }
            recurrence.setDayOfWeekMask(EventUtil.getDayOfWeekMask(weeklyWeekdayInt));

            break;
          case MONTHLY :
            recurrence.setInterval(new Integer(eventForm.getMonthlyEveryMonth()));
            recurrence.setDayOfMonth(new Integer(eventForm.getMonthlyDay()));
            break;
          case MONTHLY_NTH :
            recurrence.setInstance(StringValuedEnumReflect.getEnum(PosEnum.class, eventForm.getMonthlyPos()));
            recurrence.setInterval(new Integer(eventForm.getMonthlyEveryMonthPos()));

            recurrence.setDayOfWeekMask(EventUtil.getDayOfWeekMask(Integer.parseInt(eventForm.getMonthlyWeekdayPos())));
            break;
          case YEARLY :
            recurrence.setDayOfMonth(new Integer(eventForm.getYearlyEveryDay()));
            recurrence.setMonthOfYear(new Integer(eventForm.getYearlyEveryMonth()));
            break;
          case YEARLY_NTH :
            recurrence.setMonthOfYear(new Integer(eventForm.getYearlyMonthPos()));
            recurrence.setInstance(StringValuedEnumReflect.getEnum(PosEnum.class, eventForm.getYearlyPos()));
            recurrence.setDayOfWeekMask(EventUtil.getDayOfWeekMask(Integer.parseInt(eventForm.getYearlyWeekdayPos())));
            break;

          case DATES :
            //not implemented yet
            break;
        }

        int rangeType = Integer.parseInt(eventForm.getRangeType());
        if (rangeType == 0) {
          recurrence.setAlways(true);
        } else if (rangeType == 1) {
          recurrence.setAlways(false);
          recurrence.setOccurrences(new Integer(eventForm.getRangeOccurrences()));
        } else if (rangeType == 2) {
          recurrence.setAlways(false);
          recurrence.setUntil(new Long(Util.string2Calendar(eventForm.getRangeEnd(), Constants.UTC_TZ)
              .getTimeInMillis()));
        }

        Config config = userConfigurationDao.getUserConfig(user);
        String rule = EventUtil.getRfcRule(recurrence, config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK,
            Calendar.MONDAY));
        recurrence.setRfcRule(rule);

        long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
        diff = diff / 1000 / 60;

        recurrence.setDuration(new Long(diff));

        if (eventForm.isAllday()) {
          recurrence.setStartTime(null);
          recurrence.setEndTime(null);
          startCal.set(Calendar.HOUR_OF_DAY, 0);
          startCal.set(Calendar.MINUTE, 0);
          startCal.set(Calendar.SECOND, 0);
          startCal.set(Calendar.MILLISECOND, 0);
        } else {
          recurrence.setStartTime(startCal.getTimeInMillis());
          recurrence.setEndTime(endCal.getTimeInMillis());
        }

        List<Calendar> days = EventUtil.getDays(rule, recurrence, startCal);
        if (!days.isEmpty()) {
          Calendar s = days.get(0);
          recurrence.setPatternStartDate(new Long(s.getTimeInMillis()));

          if (recurrence.isAlways()) {
            recurrence.setPatternEndDate(null);
          } else {
            Calendar e = days.get(days.size() - 1);
            recurrence.setPatternEndDate(new Long(e.getTimeInMillis()));
          }
        }

        event.getRecurrences().clear();
        event.getRecurrences().add(recurrence);
      }
    } else {
      event.getRecurrences().clear();
    }
  }

  private void addReminders(Event event, Locale locale, DynaListDataModel dataModel) {
    Map<Integer, Reminder> idMap = new HashMap<Integer, Reminder>();

    Set<Reminder> dbReminders = event.getReminders();
    for (Reminder reminder : dbReminders) {
      idMap.put(reminder.getId(), reminder);
    }

    for (int i = 0; i < dataModel.size(); i++) {
      DynaBean dynaBean = (DynaBean)dataModel.getElementAt(i);

      Integer id = (Integer)dynaBean.get("dbId");

      if (id != null) {
        idMap.remove(id);
      } else {
        Reminder newReminder = new Reminder();
        newReminder.setEmail((String)dynaBean.get("email"));
        newReminder.setEvent(event);
        newReminder.setTime(((Integer)dynaBean.get("time")).intValue());
        newReminder.setTimeUnit((TimeEnum)dynaBean.get("timeUnit"));
        newReminder.setMinutesBefore(((Integer)dynaBean.get("minutes")));
        newReminder.setLocale(locale);
        dbReminders.add(newReminder);
      }
    }

    for (Iterator<Reminder> it = idMap.values().iterator(); it.hasNext();) {
      dbReminders.remove(it.next());
    }
  }

  @Override
  public void modelToForm(final ActionContext ctx, final Object model) {
    Event event = (Event)model;
    EventForm eventForm = (EventForm)ctx.form();

    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();

    eventForm.setTabset("generalTab");

    eventForm.setSubject(event.getSubject());

    EventCategory eventCategory = event.getEventCategories().iterator().next();
    eventForm.setCategoryId(eventCategory.getCategory().getId().toString());

    if (event.getGroup() != null) {
      eventForm.setGroupId(event.getGroup().getId().toString());
    } else {
      eventForm.setGroupId(null);
    }

    Set<User> users = event.getUsers();
    String[] userIds = null;

    if (!users.isEmpty()) {
      userIds = new String[users.size()];

      int ix = 0;
      for (User auser : users) {
        userIds[ix++] = auser.getId().toString();
      }
    } else {
      userIds = null;
    }
    eventForm.setUserIds(userIds);

    eventForm.setDescription(event.getDescription());
    eventForm.setLocation(event.getLocation());

    eventForm.setAllday(event.isAllDay());

    eventForm.setImportance(String.valueOf(event.getImportance().getValue()));
    eventForm.setSensitivity(String.valueOf(event.getSensitivity().getValue()));

    eventForm.setStartHour(null);
    eventForm.setStartMinute(null);
    eventForm.setEndHour(null);
    eventForm.setEndMinute(null);

    Calendar startCal = null;

    startCal = Util.utcLong2UserCalendar(event.getStartDate(), timeZone);
    eventForm.setStart(Util.userCalendar2String(startCal));
    if (!event.isAllDay()) {
      eventForm.setStartHour(Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.HOUR_OF_DAY)));
      eventForm.setStartMinute(Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.MINUTE)));
    }

    if (event.getEndDate() != null) {
      Calendar endCal = Util.utcLong2UserCalendar(event.getEndDate(), timeZone);
      eventForm.setEnd(Util.userCalendar2String(endCal));
      if (!event.isAllDay()) {
        eventForm.setEndHour(Constants.TWO_DIGIT_FORMAT.format(endCal.get(Calendar.HOUR_OF_DAY)));
        eventForm.setEndMinute(Constants.TWO_DIGIT_FORMAT.format(endCal.get(Calendar.MINUTE)));
      }
    } else {
      eventForm.setEnd(null);
    }

    //Reminder
    DynaListDataModel dataModel = new DynaListDataModel();

    Set<Reminder> reminders = event.getReminders();
    int ix = 0;
    for (Reminder reminder : reminders) {

      DynaBean dynaBean = new LazyDynaBean(lazyDynaClass);
      dynaBean.set("id", String.valueOf(ix));

      dynaBean.set("dbId", reminder.getId());
      dynaBean.set("email", reminder.getEmail());

      dynaBean.set("minutes", new Integer(reminder.getMinutesBefore()));
      dynaBean.set("time", new Integer(reminder.getTime()));

      TimeEnum timeEnum = reminder.getTimeUnit();
      dynaBean.set("timeUnit", timeEnum);

      Locale locale = getLocale(ctx.request());
      MessageResources messages = getResources(ctx.request());

      dynaBean.set("timeStr", reminder.getTime() + " " + messages.getMessage(locale, timeEnum.getKey()));

      dataModel.add(dynaBean);

    }

    SimpleListControl emailListControl = new SimpleListControl();
    emailListControl.setDataModel(dataModel);

    eventForm.setReminderList(emailListControl);

    eventForm.setReminderEmail(null);
    eventForm.setReminderEmailSelect(null);
    eventForm.setReminderTime(null);
    eventForm.setReminderTimeUnit(null);

    //Recurrence
    //recurrence    
    if (!event.getRecurrences().isEmpty()) {

      Recurrence recur = event.getRecurrences().iterator().next();

      eventForm.setRecurrenceActive(recur.isActive());
      eventForm.setRecurrenceType(String.valueOf(recur.getType().getValue()));

      eventForm.setDailyEveryDay(null);
      eventForm.setWeeklyEveryWeek(null);
      eventForm.setWeeklyWeekday(null);
      eventForm.setMonthlyEveryMonth(null);
      eventForm.setMonthlyDay(null);
      eventForm.setMonthlyEveryMonthPos(null);
      eventForm.setYearlyMonthPos(null);
      eventForm.setMonthlyPos(null);
      eventForm.setYearlyPos(null);
      eventForm.setMonthlyWeekdayPos(null);
      eventForm.setYearlyWeekdayPos(null);
      eventForm.setYearlyEveryDay(null);
      eventForm.setYearlyEveryMonth(null);

      switch (recur.getType()) {

        case DAILY :
          eventForm.setDailyEveryDay(String.valueOf(recur.getInterval()));
          break;

        case WEEKLY :
          eventForm.setWeeklyEveryWeek(String.valueOf(recur.getInterval()));

          Integer[] weekdayArray = EventUtil.getWeekdayArray(recur.getDayOfWeekMask());
          String[] weekdayStrArray = new String[weekdayArray.length];
          for (int i = 0; i < weekdayArray.length; i++) {
            weekdayStrArray[i] = weekdayArray[i].toString();
          }
          eventForm.setWeeklyWeekday(weekdayStrArray);
          break;
        case MONTHLY :
          eventForm.setMonthlyEveryMonth(String.valueOf(recur.getInterval()));
          eventForm.setMonthlyDay(String.valueOf(recur.getDayOfMonth()));
          break;
        case MONTHLY_NTH :
          eventForm.setMonthlyPos(String.valueOf(recur.getInstance().getValue()));
          eventForm.setMonthlyEveryMonthPos(String.valueOf(recur.getInterval()));
          eventForm.setMonthlyWeekdayPos(String.valueOf(EventUtil.getWeekday(recur.getDayOfWeekMask())));
          break;
        case YEARLY :
          eventForm.setYearlyEveryDay(String.valueOf(recur.getDayOfMonth()));
          eventForm.setYearlyEveryMonth(String.valueOf(recur.getMonthOfYear()));
          break;
        case YEARLY_NTH :
          eventForm.setYearlyMonthPos(String.valueOf(recur.getMonthOfYear()));
          eventForm.setYearlyPos(String.valueOf(recur.getInstance().getValue()));
          eventForm.setYearlyWeekdayPos(String.valueOf(EventUtil.getWeekday(recur.getDayOfWeekMask())));
          break;
        case DATES :
          //no action
          break;
      }

      //Range start
      eventForm.setRangeOccurrences(null);
      eventForm.setRangeEnd(null);

      if (recur.isAlways()) {
        eventForm.setRangeType("0");
      } else if (recur.getOccurrences() != null) {
        eventForm.setRangeType("1");
        eventForm.setRangeOccurrences(String.valueOf(recur.getOccurrences()));
      } else if (recur.getUntil() != null) {
        eventForm.setRangeType("2");
        Calendar tmp = Util.utcLong2UserCalendar(recur.getUntil().longValue(), Constants.UTC_TZ);
        eventForm.setRangeEnd(Util.userCalendar2String(tmp));
      }
      //Range end

    }

  }

  public void reminderList_onSort(final ControlActionContext ctx, final String column, final SortOrder direction) {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();
    dataModel.sort(column, direction);
    ctx.control().execute(ctx, column, direction);
  }

  public void reminderList_onDelete(final ControlActionContext ctx, final String key) throws Exception {
    DynaListDataModel dataModel = (DynaListDataModel)ctx.control().getDataModel();

    for (int i = 0; i < dataModel.size(); i++) {
      if (key.equals(dataModel.getUniqueKey(i))) {

        dataModel.remove(i);
        return;
      }
    }

  }

  public void add_onClick(final FormActionContext ctx) {

    EventForm eventForm = (EventForm)ctx.form();
    if ((StringUtils.isNotBlank(eventForm.getReminderEmail()) || StringUtils.isNotBlank(eventForm
        .getReminderEmailSelect()))
        && (StringUtils.isNotBlank(eventForm.getReminderTime()) && StringUtils.isNotBlank(eventForm
            .getReminderTimeUnit()))) {

      Locale locale = getLocale(ctx.request());
      MessageResources messages = getResources(ctx.request());

      ActionErrors errors = new ActionErrors();

      if (StringUtils.isNotBlank(eventForm.getReminderEmail())
          && !GenericValidator.isEmail(eventForm.getReminderEmail())) {
        errors.add("reminderEmail", new ActionMessage("errors.email", messages.getMessage(locale, "user.eMail")));
      }

      if (!GenericValidator.isInt(eventForm.getReminderTime())) {
        errors.add("reminderTime", new ActionMessage("errors.integer", messages.getMessage(locale,
            "event.reminder.time")));
      } else {
        int reminderTime = Integer.parseInt(eventForm.getReminderTime());
        if (reminderTime <= 0) {
          errors.add("reminderTime", new ActionMessage("errors.positive", messages.getMessage(locale,
              "event.reminder.time")));
        }
      }

      ctx.addErrors(errors);

      // If there are any Errors return and display an Errormessage
      if (ctx.hasErrors()) {
        ctx.forwardToInput();
        return;
      }

      DynaListDataModel dataModel = (DynaListDataModel)eventForm.getReminderList().getDataModel();

      int max = 0;
      for (int i = 0; i < dataModel.size(); i++) {
        String unique = dataModel.getUniqueKey(i);
        max = Math.max(max, Integer.parseInt(unique));
      }

      String email;
      if (StringUtils.isNotBlank(eventForm.getReminderEmail())) {
        email = eventForm.getReminderEmail();
      } else {
        email = eventForm.getReminderEmailSelect();
      }

      DynaBean dynaBean = new LazyDynaBean(lazyDynaClass);

      dynaBean.set("id", String.valueOf(max + 1));
      dynaBean.set("email", email);
      dynaBean.set("dbId", null);

      TimeEnum timeEnum = StringValuedEnumReflect.getEnum(TimeEnum.class, eventForm.getReminderTimeUnit());
      int minutes = 0;
      int time = Integer.parseInt(eventForm.getReminderTime());

      switch (timeEnum) {
        case MINUTE :
          minutes = time;
          break;
        case HOUR :
          minutes = time * 60;
          break;
        case DAY :
          minutes = time * 60 * 24;
          break;
        case WEEK :
          minutes = time * 60 * 24 * 7;
          break;
        case MONTH :
          minutes = time * 60 * 24 * 31;
          break;
        case YEAR :
          minutes = time * 60 * 24 * 365;
          break;
      }

      dynaBean.set("minutes", new Integer(minutes));
      dynaBean.set("time", new Integer(eventForm.getReminderTime()));
      dynaBean.set("timeUnit", timeEnum);

      dynaBean.set("timeStr", eventForm.getReminderTime() + " " + messages.getMessage(locale, timeEnum.getKey()));

      dataModel.add(dynaBean);

      eventForm.setReminderEmail(null);
      eventForm.setReminderEmailSelect(null);
      eventForm.setReminderTime(null);
      eventForm.setReminderTimeUnit(null);
    }
    ctx.forwardToInput();
  }

}
