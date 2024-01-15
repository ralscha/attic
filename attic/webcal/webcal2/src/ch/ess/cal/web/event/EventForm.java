package ch.ess.cal.web.event;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import ch.ess.cal.Constants;
import ch.ess.cal.db.Category;
import ch.ess.cal.db.Department;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.ImportanceEnum;
import ch.ess.cal.db.Recurrence;
import ch.ess.cal.db.RecurrenceTypeEnum;
import ch.ess.cal.db.SensitivityEnum;
import ch.ess.cal.db.User;
import ch.ess.cal.event.EventUtil;
import ch.ess.cal.resource.UserConfig;
import ch.ess.common.util.Util;
import ch.ess.common.web.PersistentForm;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 03.10.2003 
  * @struts.form name="eventForm"
  */
public class EventForm extends PersistentForm {

  private String subject;
  private String location;
  private boolean allday;
  private Long categoryId;
  private String start;
  private String end;
  private String startHour;
  private String startMinute;
  private String endHour;
  private String endMinute;
  private Long departmentId;
  private int sensitivity;
  private int importance;
  private String description;
  
  //reminders
  private List reminders;
  private String email;
  private String emailSelect;
  private String timeUnit;
  private Integer time;
  
  //recurrence
  private boolean recurrenceActive;
  private int recurrenceType;
  private Integer dailyEveryDay;
  
  private Integer weeklyEveryWeek;
  private int[] weeklyWeekday;
  
  private int monthlyType;
  private Integer monthlyDay;
  private Integer monthlyEveryMonth;
  private Integer monthlyPos;
  private Integer monthlyWeekdayPos;
  private Integer monthlyEveryMonthPos;
  
  private int yearlyType;
  private Integer yearlyEveryMonth;
  private Integer yearlyEveryDay;  
  private Integer yearlyPos;
  private Integer yearlyWeekdayPos;
  private Integer yearlyMonthPos;
  
  private int rangeType;
  private Integer rangeOccurrences;
  private String rangeEnd;
  
  

  public void reset(ActionMapping mapping, HttpServletRequest request) {
        
    if (page == 0) {
      allday = false;
    } else if (page == 2) {
      recurrenceActive = false;
      weeklyWeekday = new int[0];
    }
    
    super.reset(mapping, request);
  }

  public String getSubject() {
    return subject;
  }

  /**   
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="event.subject"
   */
  public void setSubject(String string) {
    subject = string;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String string) {
    location = string;
  }

  public boolean isAllday() {
    return allday;
  }

  public void setAllday(boolean b) {
    allday = b;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  /**   
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="category"
   */
  public void setCategoryId(Long long1) {
    categoryId = long1;
  }
  
  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long long1) {
    departmentId = long1;
  }
  
  public int getImportance() {
    return importance;
  }
  
  /**   
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="event.importance"
   */  
  public void setImportance(int importance) {
    this.importance = importance;
  }
  public int getSensitivity() {
    return sensitivity;
  }
  
  /**   
   * @struts.validator page="0" type="required"
   * @struts.validator-args arg0resource="event.sensitivity"
   */  
  public void setSensitivity(int sensitivity) {
    this.sensitivity = sensitivity;
  }  
  
  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List getReminders() {
    if (reminders == null) {
      reminders = new ArrayList();
    }
    return reminders;
  }

  public void setReminders(List list) {
    reminders = list;
  }
  
  /**   
   * @struts.validator page="1" type="email"
   * @struts.validator-args arg0resource="user.eMail"
   */
  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
    
  
  public String getEmailSelect() {
    return emailSelect;
  }

  public void setEmailSelect(String emailSelect) {
    this.emailSelect = emailSelect;
  }
  
 
  public Integer getTime() {
    return time;
  }

  /**   
   * @struts.validator page="1" type="integer"
   * @struts.validator-args arg0resource="event.reminder.time"
   */ 
  public void setTime(Integer time) {
    this.time = time;
  }

 
  public String getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(String timeUnit) {
    this.timeUnit = timeUnit;
  }

  public String getEnd() {
    return end;
  }

  public String getEndHour() {
    return endHour;
  }

  public String getEndMinute() {
    return endMinute;
  }

  public String getStart() {
    return start;
  }

  public String getStartHour() {
    return startHour;
  }

  public String getStartMinute() {
    return startMinute;
  }

  /**   
   * @struts.validator page="0" type="date"
   * @struts.validator-args arg0resource="event.end"
   * @struts.validator-var name="datePatternStrict" value="dd.MM.yyyy" 
   */
  public void setEnd(String string) {
    end = string;
  }


  public void setEndHour(String str) {
    endHour = str;
  }


  public void setEndMinute(String str) {
    endMinute = str;
  }

  /**   
   * @struts.validator page="0" type="required,date"
   * @struts.validator-args arg0resource="event.start"
   * @struts.validator-var name="datePatternStrict" value="dd.MM.yyyy"  
   */
  public void setStart(String string) {
    start = string;
  }


  public void setStartHour(String str) {
    startHour = str;
  }


  public void setStartMinute(String str) {
    startMinute = str;
  }


  public Integer getDailyEveryDay() {
    return dailyEveryDay;
  }

  public Integer getMonthlyDay() {
    return monthlyDay;
  }

  public Integer getMonthlyEveryMonth() {
    return monthlyEveryMonth;
  }

  public Integer getMonthlyEveryMonthPos() {
    return monthlyEveryMonthPos;
  }

  public Integer getMonthlyPos() {
    return monthlyPos;
  }

  public int getMonthlyType() {
    return monthlyType;
  }

  public Integer getMonthlyWeekdayPos() {
    return monthlyWeekdayPos;
  }

  public String getRangeEnd() {
    return rangeEnd;
  }

  public Integer getRangeOccurrences() {
    return rangeOccurrences;
  }

  public int getRangeType() {
    return rangeType;
  }

  public boolean isRecurrenceActive() {
    return recurrenceActive;
  }

  public int getRecurrenceType() {
    return recurrenceType;
  }

  public Integer getWeeklyEveryWeek() {
    return weeklyEveryWeek;
  }

  public int[] getWeeklyWeekday() {
    return weeklyWeekday;
  }

  public Integer getYearlyEveryDay() {
    return yearlyEveryDay;
  }

  public Integer getYearlyEveryMonth() {
    return yearlyEveryMonth;
  }

  public Integer getYearlyMonthPos() {
    return yearlyMonthPos;
  }

  public Integer getYearlyPos() {
    return yearlyPos;
  }

  public int getYearlyType() {
    return yearlyType;
  }

  public Integer getYearlyWeekdayPos() {
    return yearlyWeekdayPos;
  }


  /**   
   * @struts.validator page="2" type="integer"
   * @struts.validator-args arg0resource="event.recurrence.everyday"
   */
  public void setDailyEveryDay(Integer i) {
    dailyEveryDay = i;
  }

  /**   
   * @struts.validator page="2" type="integer"
   * @struts.validator-args arg0resource="event.recurrence.monthlyday"
   */
  public void setMonthlyDay(Integer i) {
    monthlyDay = i;
  }

  /**   
   * @struts.validator page="2" type="integer"
   * @struts.validator-args arg0resource="event.recurrence.everymonth"
   */
  public void setMonthlyEveryMonth(Integer i) {
    monthlyEveryMonth = i;
  }

  /**   
   * @struts.validator page="2" type="integer"
   * @struts.validator-args arg0resource="event.recurrence.everymonth"
   */
  public void setMonthlyEveryMonthPos(Integer i) {
    monthlyEveryMonthPos = i;
  }

  public void setMonthlyPos(Integer i) {
    monthlyPos = i;
  }

  public void setMonthlyType(int i) {
    monthlyType = i;
  }

  public void setMonthlyWeekdayPos(Integer i) {
    monthlyWeekdayPos = i;
  }

  public void setRangeEnd(String string) {
    rangeEnd = string;
  }

  public void setRangeOccurrences(Integer i) {
    rangeOccurrences = i;
  }

  public void setRangeType(int i) {
    rangeType = i;
  }

  public void setRecurrenceActive(boolean b) {
    recurrenceActive = b;
  }

  public void setRecurrenceType(int i) {
    recurrenceType = i;
  }

  /**   
   * @struts.validator page="2" type="integer"
   * @struts.validator-args arg0resource="event.recurrence.everyweek"
   */
  public void setWeeklyEveryWeek(Integer i) {
    weeklyEveryWeek = i;
  }

  public void setWeeklyWeekday(int[] i) {
    weeklyWeekday = i;
  }

  /**   
   * @struts.validator page="2" type="integer"
   * @struts.validator-args arg0resource="event.recurrence.yearlyday"
   */
  public void setYearlyEveryDay(Integer i) {
    yearlyEveryDay = i;
  }

  public void setYearlyEveryMonth(Integer i) {
    yearlyEveryMonth = i;
  }

  public void setYearlyMonthPos(Integer i) {
    yearlyMonthPos = i;
  }

  public void setYearlyPos(Integer i) {
    yearlyPos = i;
  }

  public void setYearlyType(int i) {
    yearlyType = i;
  }

  public void setYearlyWeekdayPos(Integer i) {
    yearlyWeekdayPos = i;
  }


  protected void fromForm() throws HibernateException {

    User user = User.find(getRequestUserName());
    UserConfig config = UserConfig.getUserConfig(user);
    TimeZone tz = user.getTimeZoneObj();

    Event event = (Event)getPersistent();
    event.setSubject(subject);
    event.setLocation(location);
    
    if (GenericValidator.isBlankOrNull(startHour)
      && GenericValidator.isBlankOrNull(startMinute)
      && GenericValidator.isBlankOrNull(endHour)
      && GenericValidator.isBlankOrNull(endMinute)) {
      allday = true;
    }

    event.setAllDay(allday);

    Calendar startCal;
    Calendar endCal;
    
    if (allday) {
      startCal = Util.string2Calendar(start, tz);
      event.setStartDate(startCal.getTimeInMillis());
      if (!GenericValidator.isBlankOrNull(end)) {
        endCal = Util.string2Calendar(end, tz);
        event.setEndDate(endCal.getTimeInMillis());
      } else {
        endCal = (Calendar)startCal.clone();
        event.setEndDate(event.getStartDate());
      }
    } else {
      startCal = Util.string2Calendar(start, startHour, startMinute, tz);
      event.setStartDate(startCal.getTimeInMillis());
      if (!GenericValidator.isBlankOrNull(end)) {
        endCal = Util.string2Calendar(end, endHour, endMinute, tz);
        event.setEndDate(endCal.getTimeInMillis());
      } else {
        
        String h = startHour;
        String m = startMinute;
        
        if (!GenericValidator.isBlankOrNull(endHour) ||
            !GenericValidator.isBlankOrNull(endMinute)) {
          h = endHour;
          m = endMinute;
        }
        
        endCal = Util.string2Calendar(start, h, m, tz);
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

    event.getCategories().clear();
    event.getCategories().add(Category.load(categoryId));

    event.getUsers().clear();
    
    if ((departmentId != null) && (departmentId.longValue() != 0)) {
      event.setDepartment(Department.load(departmentId));
    } else {
      event.getUsers().add(user);
      event.setDepartment(null);
    } 

    
    event.setSensitivity(SensitivityEnum.fromInt(sensitivity));
    event.setImportance(ImportanceEnum.fromInt(importance));

    event.setDescription(description);
    event.setTimeZone(user.getTimeZone());
    
    //reminders
    ReminderUtil.addReminder(event, getReminders(), user.getLocale());
    
    event.getRecurrences().clear();
    if (recurrenceActive) {
      //Recurrence
      Recurrence newRecur = new Recurrence();
      newRecur.setEvent(event);
      
      newRecur.setActive(recurrenceActive);
      
      if (recurrenceType == RecurrenceTypeEnum.DAILY.toInt()) {
        newRecur.setType(RecurrenceTypeEnum.DAILY);
        newRecur.setInterval(dailyEveryDay);
      } else if (recurrenceType == RecurrenceTypeEnum.WEEKLY.toInt()) { 
        newRecur.setType(RecurrenceTypeEnum.WEEKLY);
        newRecur.setInterval(weeklyEveryWeek);
        newRecur.setDayOfWeekMask(EventUtil.getDayOfWeekMask(weeklyWeekday));      
      } else if (recurrenceType == RecurrenceTypeEnum.MONTHLY.toInt()) { 
          
        if (monthlyType == RecurrenceTypeEnum.MONTHLY.toInt()) {   
          newRecur.setType(RecurrenceTypeEnum.MONTHLY);     
          newRecur.setInterval(monthlyEveryMonth);
          newRecur.setDayOfMonth(monthlyDay);
        } else {
          newRecur.setType(RecurrenceTypeEnum.MONTHLY_NTH);
          newRecur.setInstance(monthlyPos);
          newRecur.setInterval(monthlyEveryMonthPos);
          newRecur.setDayOfWeekMask(EventUtil.getDayOfWeekMask(monthlyWeekdayPos.intValue()));          
        }         
              
      } else if (recurrenceType == RecurrenceTypeEnum.YEARLY.toInt()) {
          
        if (yearlyType == RecurrenceTypeEnum.YEARLY.toInt()) {
          newRecur.setType(RecurrenceTypeEnum.YEARLY);
          newRecur.setDayOfMonth(yearlyEveryDay);
          newRecur.setMonthOfYear(yearlyEveryMonth);
        } else {
          newRecur.setType(RecurrenceTypeEnum.YEARLY_NTH);
          newRecur.setMonthOfYear(yearlyMonthPos);
          newRecur.setInstance(yearlyPos);
          newRecur.setDayOfWeekMask(EventUtil.getDayOfWeekMask(yearlyWeekdayPos.intValue()));     
        }
          
  
      } 
          
      if (rangeType == 0) {
        newRecur.setAlways(true);    
      } else if (rangeType == 1) {
        newRecur.setAlways(false);
        newRecur.setOccurrences(rangeOccurrences);      
      } else if (rangeType == 2) {
        newRecur.setAlways(false);
        newRecur.setUntil(new Long(Util.string2Calendar(rangeEnd, Constants.UTC_TZ).getTimeInMillis()));            
      } 
      String rule = EventUtil.getRfcRule(newRecur, config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY));
      newRecur.setRfcRule(rule);
      
      long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
      diff = diff / 1000 / 60;
      
      newRecur.setDuration(new Long(diff));
      
      if (allday) {
        newRecur.setStartTime(null);
        newRecur.setEndTime(null);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
      } else {
        newRecur.setStartTime(new Long(startCal.getTimeInMillis()));
        newRecur.setEndTime(new Long(endCal.getTimeInMillis()));
      }
            
      List days = EventUtil.getDays(rule, newRecur, startCal);
      Calendar s = (Calendar)days.get(0);        
      newRecur.setPatternStartDate(new Long(s.getTimeInMillis()));
      
      if (newRecur.isAlways()) {
        newRecur.setPatternEndDate(null);
      } else {
        Calendar e = (Calendar)days.get(days.size() - 1);    
        newRecur.setPatternEndDate(new Long(e.getTimeInMillis()));    
      }
                      
      event.getRecurrences().add(newRecur);
    }    
    

    event.setSequence(0);
    event.setPriority(0);
  }

  protected void toForm() throws HibernateException {

    User user = User.find(getRequestUserName());    
    TimeZone tz = user.getTimeZoneObj();

    Event event = (Event)getPersistent();
    Hibernate.initialize(event.getUsers());
    
    subject = event.getSubject();
    location = event.getLocation();
    allday = event.isAllDay();

    startHour = null;
    startMinute = null;
    endHour = null;
    endMinute = null;

    Calendar startCal = null;   

    if (event.getStartDate() > 0) {
      startCal = Util.utcLong2UserCalendar(event.getStartDate(), tz);
      start = Util.userCalendar2String(startCal, "dd.MM.yyyy");
      if (!allday) {
        startHour = Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.HOUR_OF_DAY));
        startMinute = Constants.TWO_DIGIT_FORMAT.format(startCal.get(Calendar.MINUTE));
      }
    } else {
      start = null;
    }

    if (event.getEndDate() > 0) {
      Calendar endCal = Util.utcLong2UserCalendar(event.getEndDate(), tz);
      end = Util.userCalendar2String(endCal, "dd.MM.yyyy");
      if (!allday) {
        endHour = Constants.TWO_DIGIT_FORMAT.format(endCal.get(Calendar.HOUR_OF_DAY));
        endMinute = Constants.TWO_DIGIT_FORMAT.format(endCal.get(Calendar.MINUTE));
      }
    } else {
      end = null;
    }

    if (!event.getCategories().isEmpty()) {
      categoryId = ((Category) (event.getCategories().iterator().next())).getId();
    } else {
      categoryId = null;
    }

    if (event.getDepartment() != null) {
      departmentId = event.getDepartment().getId();
    } else {
      departmentId = null;
    }
    
    if (event.getSensitivity() != null) {
      sensitivity = event.getSensitivity().toInt();
    } else {
      sensitivity = SensitivityEnum.NO_SENSITIVITY.toInt();
    }
    
    if (event.getImportance() != null) {
      importance = event.getImportance().toInt();
    } else {
      importance = ImportanceEnum.NORMAL.toInt();
    }
        
    description = event.getDescription();
    
    //reminders
    reminders = ReminderUtil.getReminders(event, getMessages(), getLocale());
    
    //recurrence    
    if (!event.getRecurrences().isEmpty()) {
      
      Recurrence recur = (Recurrence)event.getRecurrences().iterator().next();

      recurrenceActive = recur.isActive();     
      recurrenceType = recur.getType().toInt();


      dailyEveryDay = null;
      weeklyEveryWeek = null;
      monthlyEveryMonth = null;
      monthlyDay = null;
      monthlyEveryMonthPos = null;
      yearlyMonthPos = null;
      monthlyPos = null;
      yearlyPos = null;
      monthlyWeekdayPos = null;
      yearlyWeekdayPos = null;
      yearlyEveryDay = null;
      yearlyEveryMonth = null;
      
      if (recurrenceType == RecurrenceTypeEnum.DAILY.toInt()) {        
        dailyEveryDay = recur.getInterval();  

      } else if (recurrenceType == RecurrenceTypeEnum.WEEKLY.toInt()) {           
        weeklyEveryWeek = recur.getInterval();          
        weeklyWeekday = EventUtil.getWeekdayArray(recur.getDayOfWeekMask());
            
      } else if (recurrenceType == RecurrenceTypeEnum.MONTHLY.toInt()) {
        monthlyType = RecurrenceTypeEnum.MONTHLY.toInt();
        monthlyEveryMonth = recur.getInterval();
        monthlyDay = recur.getDayOfMonth();
        
      } else if (recurrenceType == RecurrenceTypeEnum.MONTHLY_NTH.toInt()) {
        recurrenceType = RecurrenceTypeEnum.MONTHLY.toInt();
        monthlyType = RecurrenceTypeEnum.MONTHLY_NTH.toInt();
        monthlyPos = recur.getInstance();
        monthlyEveryMonthPos = recur.getInterval();
        monthlyWeekdayPos = new Integer(EventUtil.getWeekday(recur.getDayOfWeekMask()));
                
      } else if (recurrenceType == RecurrenceTypeEnum.YEARLY.toInt()) {
        yearlyType = RecurrenceTypeEnum.YEARLY.toInt();
        yearlyEveryDay = recur.getDayOfMonth();
        yearlyEveryMonth = recur.getMonthOfYear();

      } else if (recurrenceType == RecurrenceTypeEnum.YEARLY_NTH.toInt()) {
        recurrenceType = RecurrenceTypeEnum.YEARLY.toInt();
        yearlyType = RecurrenceTypeEnum.YEARLY_NTH.toInt();     
        yearlyMonthPos = recur.getMonthOfYear(); 
        yearlyPos = recur.getInstance();
        yearlyWeekdayPos = new Integer(EventUtil.getWeekday(recur.getDayOfWeekMask()));   
      } 
      
      //Range start
      rangeOccurrences = null;
      rangeEnd = null;
      
      if (recur.isAlways()) {
        rangeType = 0;
      } else if (recur.getOccurrences() != null) {
        rangeType = 1;
        rangeOccurrences = recur.getOccurrences();
      } else if (recur.getUntil() != null) {
        rangeType = 2;
        Calendar tmp = Util.utcLong2UserCalendar(recur.getUntil().longValue(), tz);
        rangeEnd = Util.userCalendar2String(tmp, "dd.MM.yyyy");        
      }      
      //Range end
      
    
    } else {
      setRecurrence(startCal);    
    }
  }

  
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    
    if (page == 0) {     
      if (!isValidTime(startHour, startMinute)) {
        errors.add("start", new ActionMessage("event.error.notvalidtime", getMessages().getMessage(getLocale(), "event.start")));
      }
      
      if (!isValidTime(endHour, endMinute)) {
        errors.add("end", new ActionMessage("event.error.notvalidtime", getMessages().getMessage(getLocale(), "event.end")));
      }
    } else if (page == 2) {
      if (recurrenceType == RecurrenceTypeEnum.DAILY.toInt()) {        
        if (dailyEveryDay.intValue() == 0) {
          errors.add("dailyEveryDay", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.everyday")));
        }  

      } else if (recurrenceType == RecurrenceTypeEnum.WEEKLY.toInt()) { 
        if (weeklyEveryWeek.intValue() == 0) {
          errors.add("weeklyEveryWeek", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.everyweek")));
        }          
        if (weeklyWeekday.length == 0) {
          errors.add("weeklyWeekday", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.weeklyweekdays")));
        }
        
      } else if (recurrenceType == RecurrenceTypeEnum.MONTHLY.toInt()) { 
        
        if (monthlyType == RecurrenceTypeEnum.MONTHLY.toInt()) {
          if (monthlyEveryMonth.intValue() == 0) {
            errors.add("monthlyEveryMonth", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.everymonth")));
          }
          
          if (monthlyDay.intValue() == 0) {
            errors.add("monthlyDay", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.monthlyday")));
          }
        } else {
          if (monthlyEveryMonthPos.intValue() == 0) {
            errors.add("monthlyEveryMonthPos", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.everymonth")));          
          }
        }         
            
      } else if (recurrenceType == RecurrenceTypeEnum.YEARLY.toInt()) {
        
        if (yearlyType == RecurrenceTypeEnum.YEARLY.toInt()) {
          if (yearlyEveryDay.intValue() == 0) {
            errors.add("yearlyEveryDay", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.yearlyday")));          
          }
        } 
        

      } 
        
        
      if (rangeType == 1) {
        if (rangeOccurrences.intValue() == 0) {
          errors.add("rangeOccurrences", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.rangeoccurrences")));
        }
      } else if (rangeType == 2) {
        if (GenericValidator.isBlankOrNull(rangeEnd)) {
          errors.add("rangeEnd", new ActionMessage("event.recurrence.missing", getMessages().getMessage(getLocale(), "event.recurrence.rangeend")));
        }
      } 
    }    
    
    
    if (errors.isEmpty()) {
      return null;
    } else {
      return errors;
    }
  }

  private boolean isValidTime(String hstr, String mstr) {    
    try {    
      int h = 0;      
      if (!GenericValidator.isBlankOrNull(hstr)) {
        h = Integer.parseInt(hstr);
      }
      
      int m = 0;
      if (!GenericValidator.isBlankOrNull(mstr)) {
        m = Integer.parseInt(mstr);
      }
      
      if ((h < 0) || (h > 23)) {
        return false;
      }
      if ((m < 0) || (m > 59)) {
        return false;
      }
    } catch (NumberFormatException nfe) {
      return false;
    } 
    
    return true;
  }
  


  void setRecurrence(Calendar cal) {
    Event event = (Event)getPersistent();
    if (event.getRecurrences().isEmpty()) {
      
      recurrenceActive = false;
      recurrenceType = RecurrenceTypeEnum.DAILY.toInt();      

      dailyEveryDay = Constants.INTEGER_ONE;
        
      weeklyEveryWeek = Constants.INTEGER_ONE;      
      
      monthlyType = RecurrenceTypeEnum.MONTHLY.toInt();
      monthlyEveryMonth = Constants.INTEGER_ONE;
      
      monthlyPos = Constants.INTEGER_ONE;
      monthlyEveryMonthPos = Constants.INTEGER_ONE;
      
            
      yearlyType = RecurrenceTypeEnum.YEARLY.toInt();


      yearlyPos = Constants.INTEGER_ONE;      

      rangeType = 2;
      rangeOccurrences = Constants.INTEGER_ONE;

      if (cal != null) {
        Integer wd = new Integer(cal.get(Calendar.DAY_OF_WEEK));     
        Integer m = new Integer(cal.get(Calendar.MONTH));
        Integer d = new Integer(cal.get(Calendar.DATE));

        weeklyWeekday = new int[]{wd.intValue()};
        monthlyDay = d;        
        monthlyWeekdayPos = wd;        
        yearlyEveryMonth = m;
        yearlyEveryDay = d;
        yearlyWeekdayPos = wd;
        yearlyMonthPos = m;

        cal.add(Calendar.YEAR, 1);
        rangeEnd = Util.userCalendar2String(cal, "dd.MM.yyyy");
      } 
              
                  
    }  
  }

}
