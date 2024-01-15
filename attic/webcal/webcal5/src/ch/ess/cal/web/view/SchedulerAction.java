package ch.ess.cal.web.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.CrumbInfo;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.enums.RecurrenceTypeEnum;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.Recurrence;
import ch.ess.cal.service.EventUtil;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.taglib.TagHelp;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ControlButton;
import com.cc.framework.ui.control.SchedulerControl;
import com.cc.framework.ui.model.AppointmentPriority;
import com.cc.framework.ui.model.RecurrenceType;
import com.cc.framework.ui.model.SchedulerDesignModel;
import com.cc.framework.ui.model.SchedulerScope;
import com.cc.framework.ui.model.SchedulerView;
import com.cc.framework.ui.model.imp.RecurrencePatternImp;
import com.cc.framework.ui.model.imp.SchedulerDataModelImp;
import com.cc.framework.ui.model.imp.SchedulerDesignModelImp;
import com.cc.framework.util.CalendarHelp;
import com.lowagie.text.DocumentException;

@SpringAction
@StrutsAction(path = "/scheduler", roles = "all", forwards = {
    @Forward(name = "success", path = "/scheduler.jsp"),
    @Forward(name = "editEvent", path = "/eventEdit.do?from=scheduler&inputYear={0}&inputMonth={1}&inputDay={2}&id={3}"),
    @Forward(name = "addEvent", path = "/eventEdit.do?from=scheduler&inputYear={0}&inputMonth={1}&inputDay={2}")})
public class SchedulerAction extends FWAction implements CrumbInfo {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private EventDao eventDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  @Override
public void doExecute(ActionContext ctx) throws Exception {
    String inputDay = ctx.request().getParameter("inputDay");
    String inputMonth = ctx.request().getParameter("inputMonth");
    String inputYear = ctx.request().getParameter("inputYear");

    HttpSession session = ctx.session();
	UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
	User user = userDao.findById(userPrincipal.getUserId());
    Config userConfig = userConfigurationDao.getUserConfig(user);
    System.out.println(user.getFirstName());

    SchedulerControl control = new SchedulerControl();

    SchedulerDesignModel designModel = new SchedulerDesignModelImp();
    designModel.setName("scheduler");
    designModel.setTitle("scheduler.title");
    designModel.setWidth("100%");
    designModel.setInterval(30);
    designModel.setDayStartHour(6);
    designModel.setDayEndHour(20);
    designModel.setMaxVisible(4);
    designModel.setRows(3);
    designModel.setColumns(3);
    designModel.setFilter(false);

    int start = userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);
    designModel.setFirstDayOfWeek(start);
    designModel.setCheckBoxMask(SchedulerScope.parseMask("false"));
    designModel.setShowPopups(true);
    designModel.setShowWeekEndDays(true);
    designModel.setCompressWeekEnds(true);
    designModel.setShowFrame(true);

    designModel.setButtonPermission(ControlButton.REFRESH, TagHelp.toPermission("true"));
    designModel.setButtonPermission(ControlButton.PRINTLIST, TagHelp.toPermission("false"));

    control.setDesignModel(designModel);
    Calendar today;

    if (StringUtils.isNotBlank(inputMonth) && StringUtils.isNotBlank(inputYear)) {
      today = new GregorianCalendar(user.getTimeZone());
      today.setMinimalDaysInFirstWeek(userConfig.getIntegerProperty(UserConfig.MIN_DAYS_IN_FIRST_WEEK, 4));
      today.setFirstDayOfWeek(userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY));

      if (StringUtils.isNotBlank(inputDay) && StringUtils.isNumeric(inputDay)) {
        today.set(Calendar.DAY_OF_MONTH, Integer.parseInt(inputDay));
      } else {
        today.set(Calendar.DAY_OF_MONTH, 1);
      }
      today.set(Calendar.MONTH, Integer.parseInt(inputMonth));
      today.set(Calendar.YEAR, Integer.parseInt(inputYear));

    } else {
      today = EventUtil.getTodayCalendar(user, userConfig);
    }
    control.setDate(today);

    int month = today.get(Calendar.MONTH);
    int year = today.get(Calendar.YEAR);
    TimeZone timeZone = user.getTimeZone();
    int maxDays = today.getActualMaximum(Calendar.DATE);

    Calendar firstDay = CalUtil.newCalendar(timeZone, year, month, 1);
    Calendar lastDay = CalUtil.newCalendar(timeZone, year, month, maxDays, 23, 59, 59, 999);

    SchedulerDataModelImp dataModel = createDataModel(user, firstDay, lastDay);
    control.setDataModel(dataModel);

    CrumbsUtil.updateCallStack(this, ctx);
    CrumbsUtil.updateCrumbs(ctx);

    ctx.session().setAttribute("scheduler", control);
    ctx.forwardByName("success");
  }

  private SchedulerDataModelImp createDataModel(User user, Calendar firstDay, Calendar lastDay) {
    TimeZone timeZone = user.getTimeZone();
    SchedulerDataModelImp dataModel = new SchedulerDataModelImp(timeZone);

    List<Event> events = EventUtil.getEvents(eventDao, user, user, firstDay, lastDay);

    for (Event event : events) {
      FixedAppointmentImp appointment = new FixedAppointmentImp();
      appointment.setUniqueId(event.getId().toString());
      appointment.setAllDayEvent(event.isAllDay());

      if(event.isAllDay()){
    	  Calendar cal = CalUtil.utcLong2UserCalendar(event.getStartDate(), TimeZone.getTimeZone("UTC"));
    	  cal.set(Calendar.HOUR_OF_DAY, 0);
    	  appointment.setStartTime(cal);    	  
    	  cal = CalUtil.utcLong2UserCalendar(event.getEndDate(), TimeZone.getTimeZone("UTC"));
    	  cal.set(Calendar.HOUR_OF_DAY, 23);
    	  cal.set(Calendar.MINUTE, 59);
          appointment.setEndTime(cal);	  
      }else{
    	  appointment.setStartTime(CalUtil.utcLong2UserCalendar(event.getStartDate(), timeZone));
    	  appointment.setEndTime(CalUtil.utcLong2UserCalendar(event.getEndDate(), timeZone));
      }
      
      if (event.getImportance() == ImportanceEnum.HIGH) {
        appointment.setPriority(AppointmentPriority.HIGH);
      } else if (event.getImportance() == ImportanceEnum.LOW) {
        appointment.setPriority(AppointmentPriority.LOW);
      } else {
        appointment.setPriority(AppointmentPriority.NORMAL);
      }

      Set<Recurrence> recurrences = event.getRecurrences();
      if (!recurrences.isEmpty()) {
        Recurrence recur = recurrences.iterator().next();
        appointment.setRecurrence(recur);
        appointment.setTimezone(user.getTimeZone());

        RecurrencePatternImp pattern = new RecurrencePatternImp();

        RecurrenceType type = RecurrenceType.NONE;
        if (recur.getType() == RecurrenceTypeEnum.DAILY) {
          type = RecurrenceType.DAILY;
        } else if (recur.getType() == RecurrenceTypeEnum.MONTHLY) {
          type = RecurrenceType.MONTHLY;
        } else if (recur.getType() == RecurrenceTypeEnum.MONTHLY_NTH) {
          type = RecurrenceType.MONTHLY_NTH;
        } else if (recur.getType() == RecurrenceTypeEnum.WEEKLY) {
          type = RecurrenceType.WEEKLY;
        } else if (recur.getType() == RecurrenceTypeEnum.YEARLY) {
          type = RecurrenceType.YEARLY;
        } else if (recur.getType() == RecurrenceTypeEnum.YEARLY_NTH) {
          type = RecurrenceType.YEARLY_NTH;
        }

        int dayOfMonth = 0;
        int dayOfWeekMask = 0;
        int instance = 0;
        int interval = 0;
        int monthOfYear = 0;

        if (recur.getDayOfMonth() != null) {
          dayOfMonth = recur.getDayOfMonth();
        }

        if (recur.getDayOfWeekMask() != null) {
          dayOfWeekMask = recur.getDayOfWeekMask();
        }

        if (recur.getInstance() != null) {
          instance = Integer.parseInt(recur.getInstance().getValue());
        }

        if (recur.getInterval() != null) {
          interval = recur.getInterval();
        }

        if (recur.getMonthOfYear() != null) {
          monthOfYear = recur.getMonthOfYear();
        }

        pattern.setPattern(type, dayOfMonth, dayOfWeekMask, instance, interval, monthOfYear);

        Integer occurrences = recur.getOccurrences();
        if (occurrences != null) {
          Calendar patternStart = new GregorianCalendar(Constants.UTC_TZ);
          patternStart.setTimeInMillis(recur.getPatternStartDate());          
          pattern.setRange(patternStart, occurrences);
        } else if (recur.getPatternEndDate() != null) {
          Calendar patternStart = new GregorianCalendar(Constants.UTC_TZ);
          patternStart.setTimeInMillis(recur.getPatternStartDate()); 
          Calendar patternEnd = new GregorianCalendar(Constants.UTC_TZ);
          patternEnd.setTimeInMillis(recur.getPatternEndDate());
          pattern.setRange(patternStart, patternEnd);
        } else {
          Calendar patternStart = new GregorianCalendar(Constants.UTC_TZ);
          patternStart.setTimeInMillis(recur.getPatternStartDate()); 
          pattern.setRange(patternStart);
        }
        
        pattern.setAppointment(appointment);
        appointment.setRecurrencePattern(pattern);
      }

      appointment.setSubject(event.getSubject());
      appointment.setText(event.getDescription());
      dataModel.addAppointment(appointment);
    }
    return dataModel;
  }

  public void onAddAppointment(ControlActionContext ctx, long timeInMillis) {
    Calendar appointmentCal = new GregorianCalendar();
    appointmentCal.setTimeInMillis(timeInMillis);

    Object[] arguments = new Object[4];
    arguments[0] = appointmentCal.get(Calendar.YEAR);
    arguments[1] = appointmentCal.get(Calendar.MONTH);
    arguments[2] = appointmentCal.get(Calendar.DAY_OF_MONTH);

    ctx.forwardByName("addEvent", arguments);

  }

  public void onRefresh(ControlActionContext ctx) throws Exception {
    doExecute(ctx);
  }

  public void onAppointmentClick(ControlActionContext ctx, String key, long timeInMillis) {
    Calendar appointmentCal = new GregorianCalendar();
    appointmentCal.setTimeInMillis(timeInMillis);

    Object[] arguments = new Object[4];
    arguments[0] = appointmentCal.get(Calendar.YEAR);
    arguments[1] = appointmentCal.get(Calendar.MONTH);
    arguments[2] = appointmentCal.get(Calendar.DAY_OF_MONTH);
    arguments[3] = key;

    ctx.forwardByName("editEvent", arguments);
  }

  public void onChangeDate(ControlActionContext ctx, long timeInMillis, String view) {
    SchedulerControl control = (SchedulerControl)ctx.control();
    Calendar calendar = (Calendar)control.getDate().clone();
    CalendarHelp.setFromLong(calendar, timeInMillis);
    control.setDate(calendar);
    control.setView(SchedulerView.parse(view));
    updateDataModel(ctx, control);
    ctx.forwardByName("success");
  }

  public void onSelectDate(ControlActionContext ctx, long timeInMillis, String view) {
    SchedulerControl control = (SchedulerControl)ctx.control();
    Calendar calendar = (Calendar)control.getDate().clone();
    CalendarHelp.setFromLong(calendar, timeInMillis);
    control.setDate(calendar);
    control.setView(SchedulerView.parse(view));
    updateDataModel(ctx, control);
    ctx.forwardByName("success");
  }

  public void onView(ControlActionContext ctx, String view) throws JspException {

    SchedulerControl control = (SchedulerControl)ctx.control();
    control.setView(SchedulerView.parse(view));
    updateDataModel(ctx, control);
    
    SchedulerDesignModel designModel = (SchedulerDesignModel) control.getDesignModel();
    if (control.getView() == SchedulerView.WEEK) {
    	designModel.setButtonPermission(ControlButton.PRINTLIST, TagHelp.toPermission("true"));
    }else{

    	designModel.setButtonPermission(ControlButton.PRINTLIST, TagHelp.toPermission("false"));
    }
    control.setDesignModel(designModel);
    ctx.forwardByName("success");
  }

  @SuppressWarnings("unchecked")
  public void onPrintList(final ControlActionContext ctx) throws ServletException {

    try {
      SchedulerControl control = (SchedulerControl)ctx.control();
      if (control.getView() == SchedulerView.WEEK) {

        Calendar currentCalendar = control.getDate();

        User user = Util.getUser(ctx.session(), userDao);

        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        TimeZone timeZone = user.getTimeZone();

        Calendar firstDay = CalUtil.newCalendarStartWeek(timeZone, year, week);
        Calendar lastDay = CalUtil.newCalendarEndWeek(timeZone, year, week);

        List<Event> events = EventUtil.getEvents(eventDao, user, user, firstDay, lastDay);
        List<String>[] eventsArray = new ArrayList[7];
        
        Calendar currentDay = (Calendar)firstDay.clone();
        for (int i = 0; i < 7; i++) {
          List<String> eventsDay = new ArrayList<String>();

          for (Event event : events) {

            Calendar startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), timeZone);
            Calendar endCal = CalUtil.utcLong2UserCalendar(event.getEndDate(), timeZone);

            if (event.getRecurrences().isEmpty()) {

              if (CalUtil.isSameDay(currentDay, startCal)) {
                if (event.isAllDay()) {
                  eventsDay.add(event.getSubject());
                } else {
                  String subject = "";
                  subject = CalUtil.userCalendar2String(startCal, Constants.getTimeFormatPattern());
                  subject += " ";
                  subject += CalUtil.userCalendar2String(endCal, Constants.getTimeFormatPattern());
                  subject += " ";
                  subject += event.getSubject();
                  eventsDay.add(subject);
                }
              }

            } else {
              Calendar endOfDay = (Calendar)currentDay.clone();
              endOfDay.set(Calendar.HOUR_OF_DAY, 23);
              endOfDay.set(Calendar.MINUTE, 59);
              endOfDay.set(Calendar.SECOND, 59);
              endOfDay.set(Calendar.MILLISECOND, 999);
              List<Calendar> dates = EventUtil.getDaysBetween(event.getRecurrences().iterator().next(), currentDay,
                  endOfDay, user.getTimeZone());
              if (!dates.isEmpty()) {

                if (event.isAllDay()) {
                  eventsDay.add(event.getSubject());
                } else {
                  String subject = "";
                  subject = CalUtil.userCalendar2String(startCal, Constants.getTimeFormatPattern());
                  subject += " ";
                  subject += CalUtil.userCalendar2String(endCal, Constants.getTimeFormatPattern());
                  subject += " ";
                  eventsDay.add(event.getSubject());
                }

              }

            }
          }

          eventsArray[i] = eventsDay;

          currentDay.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        
        int last = firstDay.getActualMaximum(Calendar.DATE);
        List<Event>[] eventsArrayCurrentMonth = new ArrayList[last + 1];
        EventUtil.getEvents(eventDao, user, user, firstDay, lastDay, eventsArrayCurrentMonth, true);
        
        
        Calendar nextMonthCal = (Calendar)firstDay.clone();
        nextMonthCal.add(Calendar.MONTH, 1);
        last = nextMonthCal.getActualMaximum(Calendar.DATE);
        int nextYear = nextMonthCal.get(Calendar.YEAR);
        int nextMonth = nextMonthCal.get(Calendar.MONTH);
        Calendar nextFirstDay = CalUtil.newCalendar(Constants.UTC_TZ, nextYear, nextMonth, 1);
        Calendar nextLastDay = CalUtil.newCalendar(Constants.UTC_TZ, nextYear, nextMonth, last, 23, 59, 59, 999);
        

        List<Event>[] eventsArrayNextMonth = new ArrayList[last + 1];
        EventUtil.getEvents(eventDao, user, user, nextFirstDay, nextLastDay, eventsArrayNextMonth, true);
        
        

        String filename = "events.pdf";
        Util.setExportHeader(ctx.response(), "application/pdf", filename);
        ctx.response().setHeader("extension", "pdf");
        OutputStream out = ctx.response().getOutputStream();

        WeekPdfExport exporter = new WeekPdfExport(firstDay, eventsArray, user.getLocale(),
            eventsArrayCurrentMonth, eventsArrayNextMonth);
        byte[] pdfBytes = exporter.createPdf();

        out.write(pdfBytes);

        out.close();
        ctx.forwardToResponse();
        return;
      }

      ctx.forwardByName("success");
    } catch (IOException e) {
      throw new ServletException(e);
    } catch (DocumentException e) {
      throw new ServletException(e);
    }
  }

  private void updateDataModel(ControlActionContext ctx, SchedulerControl control) {
    Calendar start = (Calendar)control.getDate().clone();
    Calendar end;

    if (control.getView().equals(SchedulerView.DAY)) {
      end = (Calendar)start.clone();
    } else if (control.getView().equals(SchedulerView.WEEK) || control.getView().equals(SchedulerView.WORKWEEK)) {
      setFirstDayOfWeek(control, start);
      end = (Calendar)start.clone();
      end.add(Calendar.DAY_OF_MONTH, 6);

    } else if (control.getView().equals(SchedulerView.MONTH)) {
      start.set(Calendar.DAY_OF_MONTH, 1);
      end = (Calendar)start.clone();
      end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));

      setFirstDayOfWeek(control, start);
      setEndDayOfWeek(control, end);
    } else {
      start.set(Calendar.DAY_OF_MONTH, 1);
      end = (Calendar)start.clone();
      end.add(Calendar.MONTH, 12);
      end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));

      setFirstDayOfWeek(control, start);
      setEndDayOfWeek(control, end);

    }

    start.set(Calendar.HOUR_OF_DAY, 0);
    start.set(Calendar.MINUTE, 0);
    start.set(Calendar.SECOND, 0);
    start.set(Calendar.MILLISECOND, 0);

    end.set(Calendar.HOUR_OF_DAY, 23);
    end.set(Calendar.MINUTE, 59);
    end.set(Calendar.SECOND, 59);
    end.set(Calendar.MILLISECOND, 999);

    User user = Util.getUser(ctx.session(), userDao);
    SchedulerDataModelImp dataModel = createDataModel(user, start, end);
    control.setDataModel(dataModel);
  }

  private void setFirstDayOfWeek(SchedulerControl control, Calendar cal) {
    SchedulerDesignModel designModel = (SchedulerDesignModel)control.getDesignModel();
    int firstDayOfWeek = designModel.getFirstDayOfWeek();
    int currentWeekday = cal.get(Calendar.DAY_OF_WEEK);

    int diff = currentWeekday - firstDayOfWeek;
    if (diff < 0) {
      diff = 6;
    }

    cal.add(Calendar.DAY_OF_MONTH, -diff);
  }

  private void setEndDayOfWeek(SchedulerControl control, Calendar cal) {
    SchedulerDesignModel designModel = (SchedulerDesignModel)control.getDesignModel();
    int firstDayOfWeek = designModel.getFirstDayOfWeek();
    int currentWeekday = cal.get(Calendar.DAY_OF_WEEK);

    if (firstDayOfWeek == Calendar.SUNDAY) {
      cal.add(Calendar.DAY_OF_MONTH, Calendar.SATURDAY - currentWeekday);
    } else {
      if (currentWeekday != Calendar.SUNDAY) {
        cal.add(Calendar.DAY_OF_MONTH, 8 - currentWeekday);
      }
    }

  }

  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Event event = eventDao.getById(id);
      if (event != null) {
        return event.getSubject();
      }
    }
    return null;
  }

  public void onCrumbClick(final ControlActionContext ctx, final String key) throws Exception {
    doExecute(ctx);
  }


}
