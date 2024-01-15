package ch.ess.cal.web.event;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.Util;
import ch.ess.cal.model.Event;
import ch.ess.cal.model.EventCategory;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.EventDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.event.impl.EventUtil;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.MapForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @struts.action path="/listEvent" roles="$event" name="mapForm" input="/eventlist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/eventlist.jsp" 
 * @struts.action-forward name="edit" path="/editEvent.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editEvent.do" redirect="true"
 * 
 * @spring.bean name="/listEvent" lazy-init="true"
 * @spring.property name="attribute" value="events"
 */
public class EventListAction extends AbstractListAction {

  private EventDao eventDao;
  private UserDao userDao;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="eventDao"
   */
  public void setEventDao(final EventDao eventDao) {
    this.eventDao = eventDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  /** 
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    DynaListDataModel dataModel = new DynaListDataModel();

    User user = Util.getUser(ctx.session(), userDao);
    TimeZone timeZone = user.getTimeZone();

    MapForm searchForm = (MapForm)ctx.form();
    Locale locale = getLocale(ctx.request());
    MessageResources messages = getResources(ctx.request());

    String subject = null;
    String categoryId = null;
    String month = null;
    String year = null;
    if (searchForm != null) {
      subject = searchForm.getStringValue("subject");
      categoryId = searchForm.getStringValue("categoryId");
      month = searchForm.getStringValue("month");
      year = searchForm.getStringValue("year");
    }

    List<Event> events = eventDao.findUserEvents(user, subject, categoryId, month, year);

    for (Event event : events) {

      DynaBean dynaBean = new LazyDynaBean("eventList");

      dynaBean.set("id", event.getId().toString());
      dynaBean.set("subject", event.getSubject());

      EventCategory eventCategory = event.getEventCategories().iterator().next();
      dynaBean.set("category", translationService.getText(eventCategory.getCategory(), locale));

      Calendar startCal = Util.utcLong2UserCalendar(event.getStartDate(), timeZone);
      Calendar endCal = Util.utcLong2UserCalendar(event.getEndDate(), timeZone);
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

    dataModel.addClassFormatter(EventListDateObject.class, new EventListDateObjectFormatter());
    dataModel.sort("subject", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return eventDao.deleteCond(key);
  }

}
