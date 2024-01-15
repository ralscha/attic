package ch.ess.cal.web.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.Validator;
import ch.ess.base.annotation.struts.ValidatorField;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.cal.CalUtil;
import ch.ess.cal.enums.RecurrenceTypeEnum;


public class EventForm extends ReminderForm {

  private String inputMonth;
  private String inputDay;
  private String inputYear;
  private String from;

  private String subject;
  private String categoryId;
  private String groupId;
  private String description;
  private String location;

  private boolean allday;
  private String start;
  private String end;
  private String startHour;
  private String startMinute;
  private String endHour;
  private String endMinute;
  private String sensitivity;
  private String importance;

  private String tabset;
  private boolean quick;

  private String[] userIds;

  //Recurrence
  private boolean recurrenceActive;
  private String recurrenceType;
  private String dailyEveryDay;

  private String weeklyEveryWeek;
  private String[] weeklyWeekday;

  private String monthlyDay;
  private String monthlyEveryMonth;
  private String monthlyPos;
  private String monthlyWeekdayPos;
  private String monthlyEveryMonthPos;

  private String yearlyEveryMonth;
  private String yearlyEveryDay;
  private String yearlyPos;
  private String yearlyWeekdayPos;
  private String yearlyMonthPos;

  private String rangeType;
  private String rangeOccurrences;
  private String rangeEnd;

  public EventForm() {
    tabset = "generalTab";
  }

  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    allday = false;
    quick = false;
    if (request.getParameter("recurrenceType") != null) {
      weeklyWeekday = null;
      recurrenceActive = false;
    }
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

    if (!CalUtil.isValidTime(endHour, endMinute)) {
      errors.add("endHour", new ActionMessage("event.error.notvalidtime", translate(request, "event.end")));
    }

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

    if (StringUtils.isNotBlank(end)) {
      try {
        format.parse(end);
      } catch (ParseException pe) {
        errors.add("end", new ActionMessage("errors.date", translate(request, "event.end")));
      }
    }

    //Recurrence
    if (getRangeType() != null) {
      RecurrenceTypeEnum recTypeEnum = StringValuedEnumReflect.getEnum(RecurrenceTypeEnum.class, getRecurrenceType());

      switch (recTypeEnum) {
        case DAILY :
          if (StringUtils.isBlank(dailyEveryDay)) {
            errors.add("dailyEveryDay", new ActionMessage("errors.required",
                translate(request, "event.recurrence.days")));
          }
          break;
        case WEEKLY :
          if (StringUtils.isBlank(weeklyEveryWeek)) {
            errors.add("weeklyEveryWeek", new ActionMessage("errors.required", translate(request,
                "event.recurrence.weeks")));
          }
          if ((weeklyWeekday == null) || (weeklyWeekday.length == 0)) {
            errors.add("weeklyWeekday", new ActionMessage("errors.required", translate(request,
                "event.recurrence.weeklyweekdays")));
          }

          break;
        case MONTHLY :
          if (StringUtils.isBlank(monthlyEveryMonth)) {
            errors.add("monthlyEveryMonth", new ActionMessage("errors.required", translate(request,
                "event.recurrence.months")));
          }

          if (StringUtils.isBlank(monthlyDay)) {
            errors.add("monthlyDay", new ActionMessage("errors.required", translate(request, "event.recurrence.day")));
          }

          break;
        case MONTHLY_NTH :

          if (StringUtils.isBlank(monthlyPos) || StringUtils.isBlank(monthlyWeekdayPos)) {
            errors.add("monthlyEveryMonthPos", new ActionMessage("errors.required", translate(request,
                "event.recurrence.weekday")));
          }

          if (StringUtils.isBlank(monthlyEveryMonthPos)) {
            errors.add("monthlyEveryMonthPos", new ActionMessage("errors.required", translate(request,
                "event.recurrence.months")));
          }

          break;
        case YEARLY :

          if (StringUtils.isBlank(yearlyEveryMonth)) {
            errors.add("yearlyEveryDay", new ActionMessage("errors.required", translate(request,
                "event.recurrence.month")));
          }

          if (StringUtils.isBlank(yearlyEveryDay)) {
            errors.add("yearlyEveryDay", new ActionMessage("errors.required", translate(request,
                "event.recurrence.yearly.every")));
          }

          break;
        case YEARLY_NTH :

          if (StringUtils.isBlank(yearlyPos) || StringUtils.isBlank(yearlyWeekdayPos)) {
            errors.add("yearlyEveryDay", new ActionMessage("errors.required", translate(request,
                "event.recurrence.weekday")));
          }
          if (StringUtils.isBlank(yearlyMonthPos)) {
            errors.add("yearlyEveryDay", new ActionMessage("errors.required", translate(request,
                "event.recurrence.month")));
          }

          break;
          
        case DATES : 
          //no action
          break;
      }

      if (rangeType.equals("1")) {
        if (StringUtils.isBlank(rangeOccurrences)) {
          errors.add("rangeOccurrences", new ActionMessage("errors.required", translate(request,
              "event.recurrence.range.endafter")));
        }
      } else if (rangeType.equals("2")) {
        if (GenericValidator.isBlankOrNull(rangeEnd)) {
          errors.add("rangeEnd", new ActionMessage("errors.required",
              translate(request, "event.recurrence.range.endby")));
        } else {
          try {
            format.parse(rangeEnd);
          } catch (ParseException pe) {
            errors
                .add("rangeEnd", new ActionMessage("errors.date", translate(request, "event.recurrence.range.endby")));
          }
        }
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

  @ValidatorField(key = "category", required = true)
  public void setCategoryId(final String category) {
    this.categoryId = category;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(final String group) {
    this.groupId = group;
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

  public boolean isAllday() {
    return allday;
  }

  public void setAllday(boolean allday) {
    this.allday = allday;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public String getEndHour() {
    return endHour;
  }

  @ValidatorField(key = "event.end.hour", required = false, validators = @Validator(name = "integer"))
  public void setEndHour(String endHour) {
    this.endHour = endHour;
  }

  public String getEndMinute() {
    return endMinute;
  }


  @ValidatorField(key = "event.end.minute", required = false, validators = @Validator(name = "integer"))
  public void setEndMinute(String endMinute) {
    this.endMinute = endMinute;
  }

  public String getImportance() {
    return importance;
  }


  @ValidatorField(key = "event.importance", required = true)
  public void setImportance(String importance) {
    this.importance = importance;
  }

  public String getSensitivity() {
    return sensitivity;
  }


  @ValidatorField(key = "event.sensitivity", required = true)
  public void setSensitivity(String sensitivity) {
    this.sensitivity = sensitivity;
  }

  public String getStart() {
    return start;
  }

  @ValidatorField(key = "event.start", required = true)
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

  public String getDailyEveryDay() {
    return dailyEveryDay;
  }

  @ValidatorField(key="event.recurrence.days", required=false, 
      validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setDailyEveryDay(String dailyEveryDay) {
    this.dailyEveryDay = dailyEveryDay;
  }

  public String getMonthlyDay() {
    return monthlyDay;
  }


  @ValidatorField(key="event.recurrence.day", required=false, 
      validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setMonthlyDay(String monthlyDay) {
    this.monthlyDay = monthlyDay;
  }

  public String getMonthlyEveryMonth() {
    return monthlyEveryMonth;
  }

  @ValidatorField(key="event.recurrence.months", required=false, 
      validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setMonthlyEveryMonth(String monthlyEveryMonth) {
    this.monthlyEveryMonth = monthlyEveryMonth;
  }

  public String getMonthlyEveryMonthPos() {
    return monthlyEveryMonthPos;
  }

  @ValidatorField(key="event.recurrence.months", required=false, 
      validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setMonthlyEveryMonthPos(String monthlyEveryMonthPos) {
    this.monthlyEveryMonthPos = monthlyEveryMonthPos;
  }

  public String getMonthlyPos() {
    return monthlyPos;
  }

  public void setMonthlyPos(String monthlyPos) {
    this.monthlyPos = monthlyPos;
  }

  public String getMonthlyWeekdayPos() {
    return monthlyWeekdayPos;
  }

  public void setMonthlyWeekdayPos(String monthlyWeekdayPos) {
    this.monthlyWeekdayPos = monthlyWeekdayPos;
  }

  public String getRangeEnd() {
    return rangeEnd;
  }

  public void setRangeEnd(String rangeEnd) {
    this.rangeEnd = rangeEnd;
  }

  public String getRangeOccurrences() {
    return rangeOccurrences;
  }

  @ValidatorField(key="event.recurrence.range.endafter", required=false, 
      validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setRangeOccurrences(String rangeOccurrences) {
    this.rangeOccurrences = rangeOccurrences;
  }

  public String getRangeType() {
    return rangeType;
  }

  public void setRangeType(String rangeType) {
    this.rangeType = rangeType;
  }

  public boolean isRecurrenceActive() {
    return recurrenceActive;
  }

  public void setRecurrenceActive(boolean recurrenceActive) {
    this.recurrenceActive = recurrenceActive;
  }

  public String getRecurrenceType() {
    return recurrenceType;
  }

  public void setRecurrenceType(String recurrenceType) {
    this.recurrenceType = recurrenceType;
  }

  public String getWeeklyEveryWeek() {
    return weeklyEveryWeek;
  }

  @ValidatorField(key="event.recurrence.weeks", required=false, 
      validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setWeeklyEveryWeek(String weeklyEveryWeek) {
    this.weeklyEveryWeek = weeklyEveryWeek;
  }

  public String[] getWeeklyWeekday() {
    return weeklyWeekday;
  }

  public void setWeeklyWeekday(String[] weeklyWeekday) {
    this.weeklyWeekday = weeklyWeekday;
  }

  public String getYearlyEveryDay() {
    return yearlyEveryDay;
  }

  @ValidatorField(key="event.recurrence.yearly.every", required=false, 
      validators= {@Validator(name="integer"),@Validator(name="positive")})  
  public void setYearlyEveryDay(String yearlyEveryDay) {
    this.yearlyEveryDay = yearlyEveryDay;
  }

  public String getYearlyEveryMonth() {
    return yearlyEveryMonth;
  }

  public void setYearlyEveryMonth(String yearlyEveryMonth) {
    this.yearlyEveryMonth = yearlyEveryMonth;
  }

  public String getYearlyMonthPos() {
    return yearlyMonthPos;
  }

  public void setYearlyMonthPos(String yearlyMonthPos) {
    this.yearlyMonthPos = yearlyMonthPos;
  }

  public String getYearlyPos() {
    return yearlyPos;
  }

  public void setYearlyPos(String yearlyPos) {
    this.yearlyPos = yearlyPos;
  }

  public String getYearlyWeekdayPos() {
    return yearlyWeekdayPos;
  }

  public void setYearlyWeekdayPos(String yearlyWeekdayPos) {
    this.yearlyWeekdayPos = yearlyWeekdayPos;
  }

  public String[] getUserIds() {
    return (String[])ArrayUtils.clone(userIds);
  }

  public void setUserIds(final String[] userIds) {
    this.userIds = (String[])ArrayUtils.clone(userIds);
  }

  public boolean isQuick() {
    return quick;
  }

  public void setQuick(boolean quick) {
    this.quick = quick;
  }

  void setNewRecurrence() {

    if (StringUtils.isNotBlank(getStart()) && !isRecurrenceActive()) {
      recurrenceType = "0";

      dailyEveryDay = "1";

      weeklyEveryWeek = "1";

      monthlyEveryMonth = "1";

      monthlyPos = "1";
      monthlyEveryMonthPos = "1";

      yearlyPos = "1";

      rangeType = "0";
      rangeOccurrences = "1";

      Calendar cal = CalUtil.string2Calendar(getStart(), Constants.UTC_TZ);

      if (cal != null) {
        String wd = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
        String m = String.valueOf(cal.get(Calendar.MONTH));
        String d = String.valueOf(cal.get(Calendar.DATE));

        weeklyWeekday = new String[]{wd};
        monthlyDay = d;
        monthlyWeekdayPos = wd;
        yearlyEveryMonth = m;
        yearlyEveryDay = d;
        yearlyWeekdayPos = wd;
        yearlyMonthPos = m;

        cal.add(Calendar.YEAR, 1);
        rangeEnd = CalUtil.userCalendar2String(cal, "dd.MM.yyyy");
      }
    }
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getInputDay() {
    return inputDay;
  }

  public void setInputDay(String inputDay) {
    this.inputDay = inputDay;
  }

  public String getInputMonth() {
    return inputMonth;
  }

  public void setInputMonth(String inputMonth) {
    this.inputMonth = inputMonth;
  }

  public String getInputYear() {
    return inputYear;
  }

  public void setInputYear(String inputYear) {
    this.inputYear = inputYear;
  }
}