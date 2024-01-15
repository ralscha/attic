package ch.ess.cal.web.event;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.data.CalendarOutputter;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.cal.service.ExportIcalImpl;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;

@StrutsAction(path = "/exportIcal", roles = "$event")
public class ExportAction extends FWAction {

  private UserDao userDao;
  private ExportIcalImpl exportIcal;
  

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }
  
  public void setExportIcal(ExportIcalImpl exportIcal) {
    this.exportIcal = exportIcal;
  }

  public void doExecute(ActionContext ctx) throws Exception {
    User user = Util.getUser(ctx.session(), userDao);
    
    
    ctx.response().setContentType("text/calendar");
    ctx.response().setHeader("Cache-control", "must-revalidate");
    ctx.response().setHeader("Content-disposition", "attachment;filename=webcal.ics");
    OutputStream out = ctx.response().getOutputStream();
  
    Calendar firstDay = new GregorianCalendar();
    firstDay.add(Calendar.YEAR, -1);
    
    Calendar lastDay = new GregorianCalendar();
    lastDay.add(Calendar.YEAR, 5);
 
    net.fortuna.ical4j.model.Calendar ical = exportIcal.exportIcal(user, firstDay.getTimeInMillis(), lastDay.getTimeInMillis());
    
    CalendarOutputter outputter = new CalendarOutputter();
    outputter.output(ical, out);
    
    
    out.close();
    


    ctx.forwardToResponse();

  }

 
}