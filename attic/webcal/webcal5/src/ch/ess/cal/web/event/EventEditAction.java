package ch.ess.cal.web.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.PosEnum;
import ch.ess.cal.enums.RecurrenceTypeEnum;
import ch.ess.cal.enums.SensitivityEnum;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.service.EventUtil;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.LazyInit;
import ch.ess.spring.annotation.SpringBean;
import ch.ess.spring.annotation.SpringProperty;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;

@StrutsAction(path="/eventEdit",
              form=EventForm.class, 
              input="/eventedit.jsp",
              scope=ActionScope.SESSION,
              validate=false, 
              roles="$event", 
              forwards={@Forward(name = "back", path = "/eventList.do", redirect=true),
                        @Forward(name = "scheduler", path = "/scheduler.do?inputYear={0}&inputMonth={1}&inputDay={2}&startCrumb=1&startCrumbPath=/calendar", redirect=true),
                        @Forward(name = "group", path = "/groupMonth.do?inputYear={0}&inputMonth={1}&startCrumb=1&startCrumbPath=/calendar", redirect=true),
                        @Forward(name = "groupyear", path = "/groupYear.do?inputYear={0}&startCrumb=1&startCrumbPath=/calendar", redirect=true),
                        @Forward(name = "month", path = "/monthView.do?inputYear={0}&inputMonth={1}&startCrumb=1&startCrumbPath=/calendar", redirect=true),
                        @Forward(name = "year", path = "/yearView.do?inputYear={0}&startCrumb=1&startCrumbPath=/calendar", redirect=true)})
@SpringBean(name="/editEvent",lazyInit=LazyInit.TRUE, autowire=Autowire.BYTYPE)
@SpringProperty(name="dao", ref="eventDao")
public class EventEditAction extends AbstractEditAction<Event> {

  private CategoryDao categoryDao;
  private GroupDao groupDao;
  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;

  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  @Override
  public void back_onClick(FormActionContext ctx) {
    EventForm eventForm = (EventForm)ctx.form();
    if (StringUtils.isNotBlank(eventForm.getFrom())) {
      ctx.forwardByName(eventForm.getFrom(), eventForm.getInputYear(), eventForm.getInputMonth(), eventForm.getInputDay());
    } else {
      super.back_onClick(ctx);
    }
    ctx.session().removeAttribute("EventForm");
  }
  
  

  @Override
  public void delete_onClick(FormActionContext ctx) {
    String id = ctx.request().getParameter("modelId");
    if (StringUtils.isNotBlank(id)) {
      getDao().delete(id);
      
      EventForm eventForm = (EventForm)ctx.form();
      if (!"scheduler".equals(eventForm.getFrom())) {
        ctx.addGlobalMessage("common.deleteOK");
      }
      
      if (StringUtils.isNotBlank(eventForm.getFrom())) {
        ctx.forwardByName(eventForm.getFrom(), eventForm.getInputYear(), eventForm.getInputMonth());
      } else {
        ctx.forwardByName("back");
      }
      ctx.session().removeAttribute("EventForm");
      return;
    }
    ctx.forwardToInput();
  }

  @Override
  public void doPostExecute(ActionContext ctx) throws Exception {

    EventForm eventForm = (EventForm)ctx.form();

    if (eventForm.getTabset().equals("recurrenceTab")) {

      EventDao eventDao = (EventDao)getDao();

      if (StringUtils.isNotBlank(eventForm.getModelId())) {
        Event event = eventDao.findById(eventForm.getModelId());
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
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    EventForm eventForm = (EventForm)form;

    EventForm oldForm = (EventForm)ctx.form();
    String from = oldForm.getFrom();
    String inputDay = oldForm.getInputDay();
    String inputMonth = oldForm.getInputMonth();
    String inputYear = oldForm.getInputYear();

    eventForm.setFrom(from);
    eventForm.setInputDay(inputDay);
    eventForm.setInputMonth(inputMonth);
    eventForm.setInputYear(inputYear);

    if (StringUtils.isNotBlank(inputDay) && StringUtils.isNotBlank(inputMonth) && StringUtils.isNotBlank(inputYear)) {
      int day = Integer.parseInt(inputDay);
      int month = Integer.parseInt(inputMonth);
      int year = Integer.parseInt(inputYear);
      Calendar startCal = new GregorianCalendar(year, month, day);
      eventForm.setStart(CalUtil.userCalendar2String(startCal));
      eventForm.setEnd(eventForm.getStart());
    }

    eventForm.setImportance(String.valueOf(ImportanceEnum.NORMAL.getValue()));
    eventForm.setSensitivity(String.valueOf(SensitivityEnum.PUBLIC.getValue()));

    ReminderFormListControl reminderListControl = new ReminderFormListControl();
    reminderListControl.populateEmptyList();
    eventForm.setReminderList(reminderListControl);

    User user = Util.getUser(ctx.session(), userDao);
    String[] userIds = new String[1];
    userIds[0] = user.getId().toString();
    eventForm.setUserIds(userIds);

  }

  @Override
  public void save_onClick(final FormActionContext ctx) throws Exception {

    EventForm eventForm = (EventForm)ctx.form();
    if (eventForm.isQuick()) {
      ActionErrors errors = callValidate(ctx, eventForm);
      ctx.addErrors(errors);
      // If there are any Errors return and display an Errormessage
      if (ctx.hasErrors()) {
        ctx.forwardByName(eventForm.getFrom(), eventForm.getInputYear(), eventForm.getInputMonth());
        return;
      }
      
      Event event = new Event();
      formToModel(ctx, event);

      getDao().save(event);

      ctx.addGlobalMessage("common.updateOK");

      ctx.forwardByName(eventForm.getFrom(), eventForm.getInputYear(), eventForm.getInputMonth());
      ctx.session().removeAttribute("EventForm");            
      
    } else {
      super.save_onClick(ctx);
    }          

  }
  

  @Override
  public void formToModel(ActionContext ctx, Event event) {
        
    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();
    EventForm eventForm = (EventForm)ctx.form();
    
//////START QUICK
    if (eventForm.isQuick()) {
      

                        
        boolean hasStartDate = false;
        
        if (StringUtils.isNotBlank(eventForm.getStart())) {
          SimpleDateFormat parseFormat = new SimpleDateFormat(Constants.getDateTimeFormatPattern());
          parseFormat.setTimeZone(timeZone);
          
          try {
            Date date = parseFormat.parse(eventForm.getStart());
            Calendar cal = new GregorianCalendar(timeZone);
            cal.setTime(date);
            
            SimpleDateFormat format = new SimpleDateFormat(Constants.getDateFormatPattern());
            format.setTimeZone(timeZone);
  
            eventForm.setStart(format.format(cal.getTime()));
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            eventForm.setStartHour(String.valueOf(hour));
            eventForm.setStartMinute(String.valueOf(minute));
            
            hasStartDate = true;
          } catch (ParseException e) {
            
            parseFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
            parseFormat.setTimeZone(timeZone);
            try {
              Date date = parseFormat.parse(eventForm.getStart());
              Calendar cal = new GregorianCalendar(timeZone);
              cal.setTime(date);
              
              SimpleDateFormat format = new SimpleDateFormat(Constants.getDateFormatPattern());
              format.setTimeZone(timeZone);
    
              eventForm.setStart(format.format(cal.getTime()));
              
              eventForm.setStartHour(null);
              eventForm.setStartMinute(null);              
              
              hasStartDate = true;            
            } catch (ParseException e1) {
              //no action
            }
            
          }
        }
        
        if (StringUtils.isNotBlank(eventForm.getEnd())) {
          SimpleDateFormat parseFormat = new SimpleDateFormat(Constants.getDateTimeFormatPattern());
          parseFormat.setTimeZone(timeZone);
          
          try {
            Date date = parseFormat.parse(eventForm.getEnd());
            Calendar cal = new GregorianCalendar(timeZone);
            cal.setTime(date);
            
            SimpleDateFormat format = new SimpleDateFormat(Constants.getDateFormatPattern());
            format.setTimeZone(timeZone);
  
            eventForm.setEnd(format.format(cal.getTime()));
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if ((minute != 0) && (hour != 0)) {
              eventForm.setEndHour(String.valueOf(hour));
              eventForm.setEndMinute(String.valueOf(minute));
            }
          } catch (ParseException e) {
            parseFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
            parseFormat.setTimeZone(timeZone);
            try {
              Date date = parseFormat.parse(eventForm.getEnd());
              Calendar cal = new GregorianCalendar(timeZone);
              cal.setTime(date);
              
              SimpleDateFormat format = new SimpleDateFormat(Constants.getDateFormatPattern());
              format.setTimeZone(timeZone);
    
              eventForm.setEnd(format.format(cal.getTime()));
              
              eventForm.setEndHour(null);
              eventForm.setEndMinute(null);              
                        
            } catch (ParseException e1) {
              //no action
            }         
          }      
        } else {
          if (hasStartDate) {
            eventForm.setStartHour(null);
            eventForm.setStartMinute(null);
            eventForm.setAllday(true);
          }
        }
      }
      

            

//////END QUICK      
        
    if (event.getId() == null) {
      event.setSequence(0);
      event.setPriority(0);
      
      event.setCreateDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());
    } else {
      event.setModificationDate(new GregorianCalendar(Constants.UTC_TZ).getTimeInMillis());   
    }

    event.setSubject(eventForm.getSubject());
    event.setLocation(eventForm.getLocation());

    event.getUsers().removeAll(event.getUsers());

    if (StringUtils.isNotBlank(eventForm.getGroupId())) {
      event.setGroup(groupDao.findById(eventForm.getGroupId()));
    } else {
      event.setGroup(null);

      String[] userIds = eventForm.getUserIds();
      if (userIds != null) {

        for (String id : userIds) {
          if (StringUtils.isNotBlank(id)) {
            User auser = userDao.findById(id);
            event.getUsers().add(auser);
          }
        }
      }
      if (event.getUsers().size() == 0) {
        //keiner ausgewählt, also mich selber reintun
        event.getUsers().add(user);
      }

    }

    event.getEventCategories().removeAll(event.getEventCategories());
    EventCategory eventCategory = new EventCategory();
    eventCategory.setCategory(categoryDao.findById(eventForm.getCategoryId()));
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
      startCal = CalUtil.string2Calendar(start, Constants.UTC_TZ);
      event.setStartDate(startCal.getTimeInMillis());
      if (StringUtils.isNotBlank(end)) {
        endCal = CalUtil.string2Calendar(end, Constants.UTC_TZ);
      } else {
        endCal = (Calendar)startCal.clone();
      }
      event.setEndDate(endCal.getTimeInMillis());
      
    } else {
      startCal = CalUtil.string2Calendar(start, startHour, startMinute, timeZone);
      event.setStartDate(startCal.getTimeInMillis());
      if (StringUtils.isNotBlank(end)) {
        endCal = CalUtil.string2Calendar(end, endHour, endMinute, timeZone);
        event.setEndDate(endCal.getTimeInMillis());
      } else {

        String h = startHour;
        String m = startMinute;

        if (StringUtils.isNotBlank(endHour) || StringUtils.isNotBlank(endMinute)) {
          h = endHour;
          m = endMinute;
        }

        endCal = CalUtil.string2Calendar(start, h, m, timeZone);
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


    //Reminders
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("locale", user.getLocale());
    if (eventForm.getReminderList() != null) {
      eventForm.getReminderList().formToModel(event, event.getReminders(), parameters);
    }
    
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
          recurrence.setUntil(CalUtil.string2Calendar(eventForm.getRangeEnd(), Constants.UTC_TZ)
              .getTimeInMillis());
        }

        Config config = userConfigurationDao.getUserConfig(user);
        String rule = EventUtil.getRfcRule(recurrence, config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK,
            Calendar.MONDAY));
        recurrence.setRfcRule(rule);

        long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
        diff = diff / 1000 / 60;

        recurrence.setDuration(diff);

        if (eventForm.isAllday()) {
          startCal.set(Calendar.HOUR_OF_DAY, 0);
          startCal.set(Calendar.MINUTE, 0);
          startCal.set(Calendar.SECOND, 0);
          startCal.set(Calendar.MILLISECOND, 0);
        }

        List<Calendar> days = EventUtil.getDays(rule, startCal);
        if (!days.isEmpty()) {
          Calendar s = days.get(0);
          recurrence.setPatternStartDate(s.getTimeInMillis());

          if (recurrence.isAlways()) {
            recurrence.setPatternEndDate(null);
          } else {
            Calendar e = days.get(days.size() - 1);
            recurrence.setPatternEndDate(e.getTimeInMillis());
          }
        } else {
          recurrence.setPatternStartDate(event.getStartDate());
        }

        event.getRecurrences().removeAll(event.getRecurrences());
        event.getRecurrences().add(recurrence);
      }
    } else {
      event.getRecurrences().removeAll(event.getRecurrences());
    }
    
    if (event.getUid() == null) {
      event.setUid(CalUtil.createUid(event));
    }

  }

  
  @Override
  public void modelToForm(ActionContext ctx, Event event) {
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

    startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), timeZone);
    eventForm.setStart(CalUtil.userCalendar2String(startCal));
    if (!event.isAllDay()) {
      eventForm.setStartHour(Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.HOUR_OF_DAY)));
      eventForm.setStartMinute(Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.MINUTE)));
    }

    if (event.getEndDate() != null) {
      Calendar endCal = CalUtil.utcLong2UserCalendar(event.getEndDate(), timeZone);
      eventForm.setEnd(CalUtil.userCalendar2String(endCal));
      if (!event.isAllDay()) {
        eventForm.setEndHour(Constants.TWO_DIGIT_FORMAT.format(endCal.get(Calendar.HOUR_OF_DAY)));
        eventForm.setEndMinute(Constants.TWO_DIGIT_FORMAT.format(endCal.get(Calendar.MINUTE)));
      }
    } else {
      eventForm.setEnd(null);
    }

    //Reminder
    ReminderFormListControl reminderControl = new ReminderFormListControl();
    reminderControl.populateList(ctx, event.getReminders());
    eventForm.setReminderList(reminderControl);

    eventForm.setReminderEmail(null);
    eventForm.setReminderEmailSelect(null);
    eventForm.setReminderTime(null);
    eventForm.setReminderTimeUnit(null);

    //Recurrence   
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
        Calendar tmp = CalUtil.utcLong2UserCalendar(recur.getUntil().longValue(), Constants.UTC_TZ);
        eventForm.setRangeEnd(CalUtil.userCalendar2String(tmp));
      }
      //Range end

    }

  }


  public void add_onClick(final FormActionContext ctx) {
    EventForm eventForm = (EventForm)ctx.form();
    eventForm.getReminderList().add(ctx);
  }


  @Override
  protected void afterSave(FormActionContext ctx, Event event) {    
    ReminderFormListControl reminderControl = new ReminderFormListControl();
    reminderControl.populateList(ctx, event.getReminders());
    EventForm eventForm = (EventForm)ctx.form();
    eventForm.setReminderList(reminderControl);  
  }
}
