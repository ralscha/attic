package ch.ess.cal.web.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Category;
import ch.ess.cal.db.Event;
import ch.ess.cal.db.User;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 03.10.2003 
  * 
  * @struts.action path="/listEvent" name="eventListForm" scope="session" validate="false"
  * @struts.action-forward name="success" path=".event.list"
  */
public class EventListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassEvent",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("subject", String.class),
          new DynaProperty("category", String.class),
          new DynaProperty("start", String.class),
          new DynaProperty("end", String.class),
          new DynaProperty("reminder", Boolean.class),
          new DynaProperty("recurrence", Boolean.class),
          new DynaProperty("canDelete", Boolean.class)
          });

  }

  public ActionForward doAction()
    throws Exception {
    WebContext ctx = WebContext.currentContext();

    User user = User.find(ctx.getRequest().getUserPrincipal().getName());    
    TimeZone tz = user.getTimeZoneObj();

    EventListForm listForm = (EventListForm)ctx.getForm();

    Iterator resultIt = Event.findUserEvents(user, listForm.getSubject(), listForm.getCategoryId(), listForm.getMonth(), listForm.getYear());

    List resultDynaList = new ArrayList();
    Locale loc = getLocale(ctx.getRequest());

    while (resultIt.hasNext()) {
      Event event = (Event)resultIt.next();
      String pattern;
      if (event.isAllDay()) {
        pattern = "dd.MM.yyyy";
      } else {
        pattern = "dd.MM.yyyy HH:mm";
      }
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", event.getId());
      dynaBean.set("subject", event.getSubject());
      
      Calendar cal = Util.utcLong2UserCalendar(event.getStartDate(), tz);
      dynaBean.set("start", Util.userCalendar2String(cal, pattern));
      
      cal = Util.utcLong2UserCalendar(event.getEndDate(), tz);
      dynaBean.set("end", Util.userCalendar2String(cal, pattern));
            
      
      dynaBean.set("category", ((Category)(event.getCategories().iterator().next())).getTranslations().get(loc));
      dynaBean.set("canDelete", new Boolean(event.canDelete()));
      
      
      if (HibernateSession.collectionSize(event.getReminders()) > 0) {
        dynaBean.set("reminder", Boolean.TRUE);
      } else {
        dynaBean.set("reminder", Boolean.FALSE);
      }

      if (HibernateSession.collectionSize(event.getRecurrences()) > 0) {
        dynaBean.set("recurrence", Boolean.TRUE);
      } else {
        dynaBean.set("recurrence", Boolean.FALSE);
      }
      
      
      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
