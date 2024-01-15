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
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.Group;
import ch.ess.cal.service.EventDistribution;
import ch.ess.cal.service.EventUtil;
import ch.ess.cal.service.HolidayRegistry;
import ch.ess.cal.service.StringLength;
import ch.ess.cal.web.time.TimeListForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.control.ControlActionContext;

@SpringAction
@StrutsAction(path="/groupMonth", form=GroupMonthForm.class, roles="all", 
              input="/groupmonth.jsp", scope=ActionScope.SESSION, validate=false)
public class GroupMonthAction extends FWAction implements CrumbInfo {

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

  @Override
@SuppressWarnings("unchecked")
  public void doExecute(ActionContext ctx) throws Exception {
    GroupMonthForm monthForm = (GroupMonthForm)ctx.form();
    monthForm.setGraphicTyp(1);

    HttpSession session = ctx.session();
    Locale locale = getLocale(ctx.request());
    locale = translationService.checkLocale(locale);
    Calendar today = null;
    boolean highlightWeekends = true;
    String heightStr = "26";
    String widthStr = "26";
    int height = Integer.parseInt(heightStr);
    int width = Integer.parseInt(widthStr);

    User user = Util.getUser(session, userDao);
    if (user != null) {
      Config userConfig = userConfigurationDao.getUserConfig(user);
      highlightWeekends = userConfig.getBooleanProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);
      today = EventUtil.getTodayCalendar(user, userConfig);
      heightStr = userConfig.getStringProperty(UserConfig.OVERVIEW_PIC_HEIGHT, "26");
      widthStr = userConfig.getStringProperty(UserConfig.OVERVIEW_PIC_WIDTH, "26");
      
      if (StringUtils.isNotBlank(heightStr)) {
        height = Integer.parseInt(heightStr);
      }
      if (StringUtils.isNotBlank(widthStr)) {
        width = Integer.parseInt(widthStr);
      }
      
      monthForm.setShowQuickAdd(userConfig.getBooleanProperty(UserConfig.SHOW_QUICK_ADD, false));
      monthForm.setReadOnly(false);
    } else {
      today = new GregorianCalendar(TimeZone.getDefault());
      monthForm.setReadOnly(true);    
      monthForm.setShowQuickAdd(false);
    }
    
    Calendar selectedMonth = (Calendar)today.clone();
    
    if (monthForm.getInputYear() == 0) {
      monthForm.setInputYear(today.get(Calendar.YEAR));
      monthForm.setInputMonth(today.get(Calendar.MONTH));
    }
    
    selectedMonth.set(Calendar.DATE, 1);
    selectedMonth.set(Calendar.MONTH, monthForm.getInputMonth());
    selectedMonth.set(Calendar.YEAR, monthForm.getInputYear());
    
    if (monthForm.getInputMonth() == today.get(Calendar.MONTH) && (monthForm.getInputYear() == today.get(Calendar.YEAR))) {
      monthForm.setToday(today.get(Calendar.DATE));
    } else {
      monthForm.setToday(-1);
    }

    if ((monthForm.getGroupId() == null) || (monthForm.getGroupId() == 0)) {
            
      Config userConfig = userConfigurationDao.getUserConfig(user);      
      String defaultGroupId = userConfig.getStringProperty(UserConfig.DEFAULT_GROUP);
      if (StringUtils.isNotBlank(defaultGroupId)) {
        monthForm.setGroupId(new Integer(defaultGroupId));        
      } else {
        if (user != null && !user.getGroups().isEmpty()) {
          monthForm.setGroupId(user.getGroups().iterator().next().getId());
        } else if (user != null && !user.getAccessGroups().isEmpty()) {
          monthForm.setGroupId(user.getAccessGroups().iterator().next().getId());
        }
      }
    }
    
    
    //Length of Month
    int length = selectedMonth.getActualMaximum(Calendar.DATE);
    monthForm.setLength(length);

    //Daytitle
    String[] daytitles = new String[length + 1];

    //Holidays      
    String[] holidays = new String[length + 1];
    Map<Integer, String> result = holidayRegistry.getMonthHolidays(locale, selectedMonth);

    for (Map.Entry<Integer, String> entry : result.entrySet()) {

      Integer element = entry.getKey();
      String h = entry.getValue();

      h = StringEscapeUtils.escapeJavaScript(h);
      holidays[element] = h;
    }
    monthForm.setHolidays(holidays);

    //Weekends
    String[] weekends = new String[length + 1];

    //Year
    monthForm.setYear(selectedMonth.get(Calendar.YEAR));

    //Month
    monthForm.setMonth(selectedMonth.get(Calendar.MONTH));

    //Monthnames      
    DateFormatSymbols symbols = new DateFormatSymbols(locale);
    monthForm.setMonthName(symbols.getMonths()[selectedMonth.get(Calendar.MONTH)]);

    String[] shortWeekdays = symbols.getShortWeekdays();
    String[] monthNames = symbols.getMonths();
    monthForm.setMonthNames(monthNames);

    //Weekno, Colspan      
    selectedMonth.set(Calendar.DATE, 1);
    int mweekno = selectedMonth.get(Calendar.WEEK_OF_YEAR);
    int colspan = 0;

    String[] weekdayNames = new String[length + 1];
    for (int i = 1; i <= length; i++) {

      daytitles[i] = CalUtil.userCalendar2String(selectedMonth);

      int weekday = selectedMonth.get(Calendar.DAY_OF_WEEK);
      int weekno = selectedMonth.get(Calendar.WEEK_OF_YEAR);

      if (highlightWeekends) {
	      if ((weekday == Calendar.SUNDAY) || (weekday == Calendar.SATURDAY)) {
	        weekends[i] = "w";
	      }
      }

      if (weekno == mweekno) {
        colspan++;
      } else {
        monthForm.getWeekColspan().add(colspan);
        monthForm.getWeekNos().add(mweekno);
        mweekno = weekno;
        colspan = 1;
      }

      weekdayNames[i] = shortWeekdays[weekday];

      selectedMonth.add(Calendar.DATE, +1);

    }

    selectedMonth.add(Calendar.DATE, -1);

    monthForm.setWeekends(weekends);
    monthForm.setWeekdayNames(weekdayNames);
    monthForm.setDaytitle(daytitles);

    if (colspan > 0) {
      monthForm.getWeekColspan().add(colspan);
      monthForm.getWeekNos().add(mweekno);
    }

    //Users

    Integer groupId = monthForm.getGroupId();
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
    

    int year = selectedMonth.get(Calendar.YEAR);
    int month = selectedMonth.get(Calendar.MONTH);
    int lastday = selectedMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

    Calendar firstDay = CalUtil.newCalendar(Constants.UTC_TZ, year, month, 1);
    Calendar lastDay = CalUtil.newCalendar(Constants.UTC_TZ, year, month, lastday, 23, 59, 59, 999);

    Map<String, Map<String, EventDistribution>> userEvents = new HashMap<String, Map<String, EventDistribution>>();
    List<UserEvents> userList = new ArrayList<UserEvents>();

    EventComparator eventComparator = new EventComparator();
    List<Event> collisionList = new ArrayList<Event>();
    for (User us : usersSet) {
      if (us.isEnabled()) {

        List<Event>[] eventsArray = new ArrayList[length + 1];
  
        Map<String, EventDistribution> eventsMap = new HashMap<String, EventDistribution>();
  
        EventUtil.getEvents(eventDao, us, user, firstDay, lastDay, eventsArray, true);
  
        for (int i = 1; i <= length; i++) {
  
          List<Event> eventsList = eventsArray[i];
          if ((eventsList != null) && !eventsList.isEmpty()) {
            EventDistribution ed = eventsMap.get(String.valueOf(i));
            if (ed == null) {
              Calendar now = CalUtil.newCalendar(selectedMonth.getTimeZone(), year, month, i);
              ed = new EventDistribution(now);
              eventsMap.put(String.valueOf(i), ed);
            }
  
            for (Event ev : eventsList) {
              ed.addEvent(ev);
              collisionList.add(ev);
            }
          }
        }
  
        for (EventDistribution ed : eventsMap.values()) {
          ed.compact();
          ed.setImage(CalUtil.createImage(ed, monthForm.getGraphicTyp(), width, height));
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
          picFileName[i] = null;
          if (eventList != null) {
            Collections.sort(eventList, eventComparator);
            StringLength stringLength = EventUtil.getTooltip(user, eventList, selectedMonth.getTimeZone(), translationService, locale, tooltipFont, getResources(ctx.request())); 
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
    monthForm.setUsers(userList);
    monthForm.setUserEvents(userEvents);
    
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
  
  private List<Object[]> checkEventCollision(List<Event> events, User user){
	  List<Object[]> result = new ArrayList<Object[]>();
	  Object[] o = new Object[2];
	  Boolean collided;
	  Object[] dummy = null;
	  List<Event> eventsCopy = new ArrayList<Event>();
	  List<Object[]> toDelete = new ArrayList<Object[]>();
	  
	  for(Event e : events){
		  eventsCopy.add(e);
	  }
	  
	  for(Event e : eventsCopy){
		  if(dummy!=null){
			  toDelete.add(dummy);
		  }
		  for(Event e2 : events){			  
			  collided = false;
			  if(e.getId() != e2.getId()){
				  if( e.getStartDate() >= e2.getStartDate() && e.getStartDate() <= e2.getEndDate()){
					  collided = true;
				  }else if(  e.getStartDate() <= e2.getStartDate() && e.getEndDate() >= e2.getStartDate()){
					  collided = true;
				  }else if( e.getStartDate() == e2.getStartDate() && e.getEndDate() == e2.getEndDate()){
					  collided = true;
				  }
			  }
			  
			  if(collided){
				o[0] = e;
				o[1] = e2;
				result.add(o);
			  }
		  }
		 dummy = o;
	  }
	  
	  for(Object[] obj : toDelete){
		  result.remove(obj);
	  }
	  return result;
  }
  
  
  public void increaseMonth_onClick(FormActionContext ctx) throws Exception {
	  GroupMonthForm form = (GroupMonthForm) ctx.form();
	  Calendar cal = Calendar.getInstance();
	  cal.set(Calendar.MONTH, Integer.valueOf(form.getInputMonth()) +1);
	  int old = Integer.valueOf(form.getInputMonth());
	  if(old > cal.get(Calendar.MONTH)){
		cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) +1);
		form.setInputYear(cal.get(Calendar.YEAR));	
	 }
	  form.setInputMonth(cal.get(Calendar.MONTH));
	  }
	  
	  public void decreaseMonth_onClick(FormActionContext ctx) throws Exception {
		  GroupMonthForm form = (GroupMonthForm) ctx.form();
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.MONTH, Integer.valueOf(form.getInputMonth()) -1);
			  int old = Integer.valueOf(form.getInputMonth());
			  if(old < cal.get(Calendar.MONTH)){
				cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) -1);
				form.setInputYear(cal.get(Calendar.YEAR));	
			 }
			form.setInputMonth(cal.get(Calendar.MONTH));
	  }
	  
	public void increaseYear_onClick(FormActionContext ctx) throws Exception {
		GroupMonthForm form = (GroupMonthForm) ctx.form();
		  if(form.getInputYear() >0){
			 
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) +1);
			  form.setInputYear(cal.get(Calendar.YEAR));
		  }
	  }
	  
	  public void decreaseYear_onClick(FormActionContext ctx) throws Exception {
		  GroupMonthForm form = (GroupMonthForm) ctx.form();
		  if(form.getInputYear() > 0){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) -1);
			  form.setInputYear(cal.get(Calendar.YEAR));	
		  }
	  }
}
