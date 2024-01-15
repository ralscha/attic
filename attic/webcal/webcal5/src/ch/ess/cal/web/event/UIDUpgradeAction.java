package ch.ess.cal.web.event;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.model.Event;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;

@StrutsAction(path = "/uidupdate", roles = "$event")
public class UIDUpgradeAction extends FWAction {

  private EventDao eventDao;
  
  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void doExecute(ActionContext ctx) throws Exception {
    
    List<Event> events = eventDao.findAllEventsWithNoUID();
    if (events != null) {
      for (Event event : events) {
        event.setUid(CalUtil.createUid(event));
        eventDao.save(event);
      }
    }
    
    events = eventDao.findAllEventsWithNoCreateDate();
    if (events != null) {
      for (Event event : events) {
        event.setCreateDate(new Date().getTime());
        eventDao.save(event);
      }
    }
    
  
    
        
    events = eventDao.findAll();
    for (Event event : events) {            
      if (event.isAllDay()) {
        Calendar start = new GregorianCalendar(Constants.UTC_TZ);
        start.setTimeInMillis(event.getStartDate() + (60 * 60 * 1000 * 6));
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        
        Calendar end = new GregorianCalendar(Constants.UTC_TZ);
        end.setTimeInMillis(event.getEndDate() + (60 * 60 * 1000 * 6));
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);        
        
        event.setStartDate(start.getTimeInMillis());
        event.setEndDate(end.getTimeInMillis());
        eventDao.save(event);
      }
    }
    

    ctx.forwardToResponse();

  }
}
