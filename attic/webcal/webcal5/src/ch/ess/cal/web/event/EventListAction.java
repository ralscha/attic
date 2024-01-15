package ch.ess.cal.web.event;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.MessageResources;

import ch.ess.base.Util;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.service.EventListDateObject;
import ch.ess.cal.service.EventUtil;
import ch.ess.cal.web.view.MonthViewForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;


public class EventListAction extends AbstractListAction {

  private EventDao eventDao;
  private UserDao userDao;
  private TranslationService translationService;

  public void setEventDao(final EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }


  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();

    MapForm searchForm = (MapForm)ctx.form();
    Locale locale = getLocale(ctx.request());
    MessageResources messages = getResources(ctx.request());

    String subject = null;
    String categoryId = null;
    String month = null;
    String year = null;
    String day = null;
    String eventId = null;
    if (searchForm != null) {
      subject = searchForm.getStringValue("subject");
      categoryId = searchForm.getStringValue("categoryId");
      day = searchForm.getStringValue("day");
      month = searchForm.getStringValue("month");
      year = searchForm.getStringValue("year");
      eventId = searchForm.getStringValue("eventId");
    
      if (StringUtils.isNotBlank(year)) {
        year = year.trim();
        if (!StringUtils.isNumeric(year)) {
          Calendar cal = new GregorianCalendar();
          year = String.valueOf(cal.get(Calendar.YEAR));
          searchForm.setValue("year", year);
        }
      }
    }

    List<Event> events = eventDao.findUserEvents(user, subject, categoryId, day, month, year, eventId);
    
    for (Event event : events) {

      DynaBean dynaBean = new LazyDynaBean("eventList");

      dynaBean.set("id", event.getId().toString());
      
      dynaBean.set("subject", event.getSubject());
      dynaBean.set("deletesubject", StringEscapeUtils.escapeJavaScript(event.getSubject()));

      EventCategory eventCategory = event.getEventCategories().iterator().next();
      dynaBean.set("category", translationService.getText(eventCategory.getCategory(), locale));

      Calendar startCal = CalUtil.utcLong2UserCalendar(event.getStartDate(), timeZone);
      Calendar endCal = CalUtil.utcLong2UserCalendar(event.getEndDate(), timeZone);
      dynaBean.set("start", new EventListDateObject(startCal, event.isAllDay()));
      dynaBean.set("end", new EventListDateObject(endCal, event.isAllDay()));

      if (event.getReminders().size() > 0) {
        dynaBean.set("reminder", Boolean.TRUE);

        dynaBean.set("reminderTooltip", EventUtil.getReminderTooltip(messages, locale, event.getReminders()));
      } else {
        dynaBean.set("reminder", Boolean.FALSE);
      }

      if (event.getRecurrences().size() > 0) {
        dynaBean.set("recurrence", Boolean.TRUE);
        String tooltip = "<span class=\\'smalltext\\'>";
        tooltip += EventUtil.getRecurrenceDescription(messages, locale, event.getRecurrences().iterator().next());
        tooltip += "</span>";
        dynaBean.set("recurrenceTooltip", tooltip);
      } else {
        dynaBean.set("recurrence", Boolean.FALSE);
      }

      dataModel.add(dynaBean);
    }
    
    

    //dataModel.addClassFormatter(EventListDateObject.class, new EventListDateObjectFormatter());
    dataModel.sort("subject", SortOrder.ASCENDING);
 
    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    eventDao.delete(key);
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Event event = eventDao.getById(id);
      if (event != null) {
        return event.getSubject();
      }
    }
    return null;
  } 
  public void increaseMonth_onClick(FormActionContext ctx) throws Exception {
	  MapForm form = (MapForm) ctx.form();
	  Calendar cal = Calendar.getInstance();
	  if(form.getValue("month") != null){
	  cal.set(Calendar.MONTH, Integer.valueOf((String) form.getValue("month")) +1);
	  int old = Integer.valueOf( Integer.valueOf((String) form.getValue("month")));
	  if(old > cal.get(Calendar.MONTH)){
		cal.set(Calendar.YEAR,  Integer.valueOf((String) form.getValue("year")) +1);
		form.setValue("year" , String.valueOf(cal.get(Calendar.YEAR)));	
	 }
	  form.setValue("month", String.valueOf(cal.get(Calendar.MONTH)));
	  }
  }
	  
	  public void decreaseMonth_onClick(FormActionContext ctx) throws Exception {
		  MapForm form = (MapForm) ctx.form();
		  Calendar cal = Calendar.getInstance();
		  if(form.getValue("month") != null){
			  cal.set(Calendar.MONTH, Integer.valueOf((String) form.getValue("month")) -1);
			  int old = Integer.valueOf( Integer.valueOf((String) form.getValue("month")));
			  if(old < cal.get(Calendar.MONTH)){
				cal.set(Calendar.YEAR,  Integer.valueOf((String) form.getValue("year")) -1);
				form.setValue("year" , String.valueOf(cal.get(Calendar.YEAR)));	
			}
			form.setValue("month", String.valueOf(cal.get(Calendar.MONTH)));
		}
	}
	
	  
	public void increaseYear_onClick(FormActionContext ctx) throws Exception {
		MapForm form = (MapForm) ctx.form();
		  if(form.getValue("year") != null){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf((String) form.getValue("year")) +1);
				form.setValue("year", String.valueOf(cal.get(Calendar.YEAR)));	
		  }	
	  }
	  
	  public void decreaseYear_onClick(FormActionContext ctx) throws Exception {
		  MapForm form = (MapForm) ctx.form();
		  if(form.getValue("year") != null){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf((String) form.getValue("year")) -1);
			  form.setValue("year", String.valueOf(cal.get(Calendar.YEAR)));	
		  }	
	  }
}
