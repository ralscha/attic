package ch.ess.cal.web.view;

import java.awt.Font;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.TranslationService;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.CrumbInfo;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.Group;
import ch.ess.cal.service.EventDistribution;
import ch.ess.cal.service.EventUtil;
import ch.ess.cal.service.HolidayRegistry;
import ch.ess.cal.service.StringLength;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;

@SpringAction
@StrutsAction(path="/groupYear", form=GroupYearForm.class, roles="all", 
              input="/groupyear.jsp", scope=ActionScope.SESSION, validate=false)
public class GroupYearAction extends FWAction implements CrumbInfo {

  private UserDao userDao;
  private GroupDao groupDao;
  private EventDao eventDao;
  private TranslationService translationService;
  private UserConfigurationDao userConfigurationDao;
  private HolidayRegistry holidayRegistry;

  public void setTranslationService(final TranslationService translationService) {
    this.translationService = translationService;
  }

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  @SuppressWarnings("unchecked")
  public void doExecute(ActionContext ctx) throws Exception {
    GroupYearForm yearForm = (GroupYearForm)ctx.form();
    yearForm.setGraphicTyp(1);

    HttpSession session = ctx.session();
    Locale locale = getLocale(ctx.request());
    locale = translationService.checkLocale(locale);
    
    Calendar today = null;
    boolean highlightWeekends = true;
    String heightStr = "14";
    String widthStr = "13";
    int height = Integer.parseInt(heightStr);
    int width = Integer.parseInt(widthStr);

    User user = Util.getUser(session, userDao);
    if (user != null) {
      Config userConfig = userConfigurationDao.getUserConfig(user);
      highlightWeekends = userConfig.getBooleanProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);
      today = EventUtil.getTodayCalendar(user, userConfig);
//      heightStr = userConfig.getStringProperty(UserConfig.OVERVIEW_PIC_HEIGHT, "26");
//      widthStr = userConfig.getStringProperty(UserConfig.OVERVIEW_PIC_WIDTH, "26");
//      
//      if (StringUtils.isNotBlank(heightStr)) {
//        height = Integer.parseInt(heightStr);
//      }
//      if (StringUtils.isNotBlank(widthStr)) {
//        width = Integer.parseInt(widthStr);
//      }      
      
      yearForm.setShowQuickAdd(userConfig.getBooleanProperty(UserConfig.SHOW_QUICK_ADD, false));
      yearForm.setReadOnly(false);
    } else {
      today = new GregorianCalendar(TimeZone.getDefault());
      yearForm.setReadOnly(true);    
      yearForm.setShowQuickAdd(false);
    }
    
    Calendar selectedYear = (Calendar)today.clone();
    
    if (yearForm.getInputYear() == 0) {
      yearForm.setInputYear(today.get(Calendar.YEAR));
    }
    
    selectedYear.set(Calendar.DATE, 1);
    selectedYear.set(Calendar.MONTH, Calendar.JANUARY);
    selectedYear.set(Calendar.YEAR, yearForm.getInputYear());
    
    if (yearForm.getInputYear() == today.get(Calendar.YEAR)) {
      yearForm.setToday(today.get(Calendar.DAY_OF_YEAR));
    } else {
      yearForm.setToday(-1);
    }

    if ((yearForm.getGroupId() == null) || (yearForm.getGroupId() == 0)) {
            
      Config userConfig = userConfigurationDao.getUserConfig(user);      
      String defaultGroupId = userConfig.getStringProperty(UserConfig.DEFAULT_GROUP);
      if (StringUtils.isNotBlank(defaultGroupId)) {
        yearForm.setGroupId(new Integer(defaultGroupId));        
      } else {
        if (user != null && !user.getGroups().isEmpty()) {
          yearForm.setGroupId(user.getGroups().iterator().next().getId());
        } else if (user != null && !user.getAccessGroups().isEmpty()) {
          yearForm.setGroupId(user.getAccessGroups().iterator().next().getId());
        }
      }
    }
    
    //Length of Month
    int length = selectedYear.getActualMaximum(Calendar.DAY_OF_YEAR);
    yearForm.setLength(length);

    //Daytitle
    String[] daytitles = new String[length + 1];



    //Weekends
    String[] weekends = new String[length + 1];

    //Year
    yearForm.setYear(selectedYear.get(Calendar.YEAR));

    //Monthnames      
    DateFormatSymbols symbols = new DateFormatSymbols(locale);
    String[] shortWeekdays = symbols.getShortWeekdays();
    String[] monthNames = symbols.getMonths();

    //Weekno, Colspan      
    selectedYear.set(Calendar.DATE, 1);
    int mweekno = selectedYear.get(Calendar.WEEK_OF_YEAR);
    int mmonthno = Calendar.JANUARY;
    int colspan = 0;
    int monthcolspan = 0;

    String[] weekdayNames = new String[length + 1];
    Integer[] monthDays = new Integer[length + 1];
    Integer[] months = new Integer[length + 1];
    
    Calendar loopCal = (Calendar)selectedYear.clone();
    
    for (int i = 1; i <= length; i++) {

      daytitles[i] = CalUtil.userCalendar2String(loopCal);

      int weekday = loopCal.get(Calendar.DAY_OF_WEEK);
      int weekno = loopCal.get(Calendar.WEEK_OF_YEAR);

      int monthno = loopCal.get(Calendar.MONTH);
      
      if (highlightWeekends) {
        if ((weekday == Calendar.SUNDAY) || (weekday == Calendar.SATURDAY)) {
          weekends[i] = "w";
        }
      }

      if (weekno == mweekno) {
        colspan++;
      } else {
        yearForm.getWeekColspan().add(colspan);
        yearForm.getWeekNos().add(mweekno);
        mweekno = weekno;
        colspan = 1;
      }

      weekdayNames[i] = shortWeekdays[weekday];
      
      monthDays[i] = loopCal.get(Calendar.DAY_OF_MONTH); 
      months[i] = loopCal.get(Calendar.MONTH);
      
      if (monthno == mmonthno) {
        monthcolspan++;
      } else {
        yearForm.getMonthColspan().add(monthcolspan);
        yearForm.getMonthNames().add(monthNames[mmonthno]);
        mmonthno = monthno;
        monthcolspan = 1;
      }
      
      loopCal.add(Calendar.DATE, +1);
    }


    yearForm.setWeekends(weekends);
    yearForm.setWeekdayNames(weekdayNames);
    yearForm.setMonthDays(monthDays);
    yearForm.setMonth(months);
    yearForm.setDaytitle(daytitles);

    if (colspan > 0) {
      yearForm.getWeekColspan().add(colspan);
      yearForm.getWeekNos().add(mweekno);            
    }
    
    if (monthcolspan > 0) {
      yearForm.getMonthColspan().add(monthcolspan);
      yearForm.getMonthNames().add(monthNames[mmonthno]);
    }

    //Users

    Integer groupId = yearForm.getGroupId();
    Group group = null;
    Set<User> usersSet = new TreeSet<User>(new UserComparator());

    if (groupId == null) {
      if (user != null && user.getGroups().size() > 0) {
        group = user.getGroups().iterator().next();
      } else {
        if (user != null && user.getAccessGroups().size() > 0) {
          group = user.getAccessGroups().iterator().next();
        }
      }
    } else {
      group = groupDao.findById(groupId.toString());
    }

    if (group != null) {
      usersSet.addAll(group.getUsers());
    } else {
      usersSet.add(user);
    }
    

    int year = selectedYear.get(Calendar.YEAR);

    Calendar firstDay = CalUtil.newCalendar(Constants.UTC_TZ, year, Calendar.JANUARY, 1);
    Calendar lastDay = CalUtil.newCalendar(Constants.UTC_TZ, year, Calendar.DECEMBER, 31, 23, 59, 59, 999);

    //Holidays      
    String[] holidays = new String[length + 1];
    MultiKeyMap holidayMap = holidayRegistry.getYearHolidays(getLocale(ctx.request()), year);
    
    Integer yearInteger = new Integer(selectedYear.get(Calendar.YEAR));
    for (int i = 1; i <= length; i++) {
      String holidayText = (String)holidayMap.get(yearInteger, new Integer(months[i]), 
          new Integer(monthDays[i]));
      if (holidayText != null) {
        holidayText = StringEscapeUtils.escapeJavaScript(holidayText);
        holidays[i] = holidayText;
      }
    }
    
    yearForm.setHolidays(holidays);    
    
    
    Map<String, Map<String, EventDistribution>> userEvents = new HashMap<String, Map<String, EventDistribution>>();
    List<UserEvents> userList = new ArrayList<UserEvents>();

    EventComparator eventComparator = new EventComparator();

    for (User us : usersSet) {
      if (us.isEnabled()) {

        List<Event>[] eventsArray = new ArrayList[length + 1];
  
        Map<String, EventDistribution> eventsMap = new HashMap<String, EventDistribution>();
  
        EventUtil.getEvents(eventDao, us, user, firstDay, lastDay, eventsArray, false);
  
        for (int i = 1; i <= length; i++) {
  
          List<Event> eventsList = eventsArray[i];
          if ((eventsList != null) && !eventsList.isEmpty()) {
            EventDistribution ed = eventsMap.get(String.valueOf(i));
            if (ed == null) {
              Calendar now = CalUtil.newCalendar(selectedYear.getTimeZone(), year, months[i], monthDays[i]);
              ed = new EventDistribution(now);
              eventsMap.put(String.valueOf(i), ed);
            }
  
            for (Event ev : eventsList) {
              ed.addEvent(ev);
            }
          }
        }
  
        for (EventDistribution ed : eventsMap.values()) {
          ed.compact();
          ed.setImage(CalUtil.createImage(ed, yearForm.getGraphicTyp(), width, height));
        }
  
        userEvents.put(us.getId().toString(), eventsMap);
  
        UserEvents ue = new UserEvents();
        ue.setId(us.getId());
  
        String[] tooltips = new String[length + 1];
        int[] tooltipsWidth = new int[length + 1];
        String[] eventIds = new String[length + 1];
        String[] picFileName = new String[length + 1];
        boolean[] multipleEvents = new boolean[length + 1];
        
        Font tooltipFont = new Font("Arial", Font.PLAIN, 9);
  
        for (int i = 1; i <= length; i++) {
          List<Event> eventList = eventsArray[i];
  
          eventIds[i] = null;
          if (eventList != null) {
            Collections.sort(eventList, eventComparator);
            StringLength stringLength = EventUtil.getTooltip(user, eventList, selectedYear.getTimeZone(), translationService, locale, tooltipFont, getResources(ctx.request())); 
            tooltips[i] = stringLength.getString();
            tooltipsWidth[i] = stringLength.getLength();
            
            if (!eventList.isEmpty()) {
              if (eventList.size() == 1) {
                Event event = eventList.get(0);
                eventIds[i] = event.getId().toString();
              } else {
                multipleEvents[i] = true;
              }
              
              EventDistribution ed = eventsMap.get(String.valueOf(i));
              picFileName[i] = CalUtil.getPicFileName(ed);
            }
          }
        }
  
        ue.setTooltip(tooltips);
        ue.setTooltipWidth(tooltipsWidth);
        ue.setPicFileName(picFileName);
        
        if (user != null) {        
          if (user.equals(us)) {
            ue.setEventId(eventIds);
            ue.setMultipleEvents(multipleEvents);
          } else {
            ue.setEventId(null);
          }
        } else {
          ue.setEventId(null);
        }
  
  
        if (us.getShortName() != null) {
          ue.setName(us.getShortName());
        } else {
          ue.setName(us.getFirstName() + " " + us.getName());
        }
  
        userList.add(ue);
      }
    }
    yearForm.setUsers(userList);
    yearForm.setUserEvents(userEvents);


    session.setAttribute("height", heightStr);
    session.setAttribute("width", widthStr);

    CrumbsUtil.updateCallStack(this, ctx);
    CrumbsUtil.updateCrumbs(ctx);
    
    ctx.forwardToInput();
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
  
	public void increaseYear_onClick(FormActionContext ctx) throws Exception {
		GroupYearForm form = (GroupYearForm) ctx.form();
		  if(form.getInputYear() >0){
			 
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) +1);
			  form.setInputYear(cal.get(Calendar.YEAR));
		  }
	  }
	  
	  public void decreaseYear_onClick(FormActionContext ctx) throws Exception {
		  GroupYearForm form = (GroupYearForm) ctx.form();
		  if(form.getInputYear() > 0){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) -1);
			  form.setInputYear(cal.get(Calendar.YEAR));	
		  }
	  }
  
}
