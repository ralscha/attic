package ch.ess.cal.web.event;

import java.util.Map;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.Constants;
import ch.ess.cal.db.Event;
import ch.ess.cal.web.WebUtils;
import ch.ess.common.db.Persistent;
import ch.ess.common.util.Util;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.WebContext;

import com.ibm.icu.util.Calendar;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 03.10.2003 
  * 
  * @struts.action path="/newEvent" name="eventForm" input=".event.list" scope="session" validate="false" parameter="add"
  * @struts.action path="/editEvent" name="eventForm" input=".event.list" scope="session" validate="false" parameter="edit"
  * @struts.action path="/storeEvent" name="eventForm" input=".event.edit" scope="session" validate="true" parameter="store" 
  * @struts.action path="/deleteEvent" parameter="delete" validate="false"
  *
  * @struts.action-forward name="edit" path=".event.edit"
  * @struts.action-forward name="list" path="/listEvent.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteEvent.do" 
  * @struts.action-forward name="reload" path="/editEvent.do" 
  */
public class EventEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Event.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Event.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Event();
  }

  protected ActionForward store() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    EventForm ef = (EventForm)ctx.getForm();
    Map params = WebUtils.getChangeRequestParameterMap(ctx.getRequest());

    if (params.keySet().contains("page")) {
      int page = Integer.parseInt((String)params.get("page"));  
      ef.setPage(page);  
      
      if (page == 2) {
        Calendar cal = Util.string2Calendar(ef.getStart(), Constants.UTC_TZ);
        ef.setRecurrence(cal);
      }
          
      return ctx.getMapping().getInputForward();    
    } else if (ReminderUtil.handleReminderRequest(params, ef)) {
      return ctx.getMapping().getInputForward();  
    } else {
      return super.store();
    }
  }

}

