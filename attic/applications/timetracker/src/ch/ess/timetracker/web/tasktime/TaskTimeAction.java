package ch.ess.timetracker.web.tasktime;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.struts.action.ActionForward;

import ch.ess.common.web.BaseAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.db.User;
import ch.ess.timetracker.resource.UserConfig;
import ch.ess.timetracker.web.WebUtils;

/** 
 * @struts.action path="/tasktimes" name="mapForm" scope="session" validate="false"
 * @struts.action-forward name="success" path=".tasktime.list"
 * @struts.action-forward name="report" path=".tasktime.report"
 */
public class TaskTimeAction extends BaseAction {

  public ActionForward execute() throws Exception {

    WebContext ctx = WebContext.currentContext();
    MapForm mapForm = (MapForm)ctx.getForm();

    if ((mapForm.getValue("from") == null) && (mapForm.getValue("to") == null)) {

      User user = WebUtils.getUser(ctx.getRequest());
      UserConfig config = UserConfig.getUserConfig(user);
      int start = config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);
      
      Calendar cal = new GregorianCalendar();
      cal.setFirstDayOfWeek(start);
      
      mapForm.setValue("week", String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
      mapForm.setValue("year", String.valueOf(cal.get(Calendar.YEAR)));
      
      
      
    }
    
    if (ctx.getRequest().isUserInRole("customer")) {
      return findForward("report");
    } else {
      if (ctx.getRequest().getParameter("report") != null) {
        return findForward("report");
      } else {
        return findForward(ch.ess.common.Constants.FORWARD_SUCCESS);
      }
      
    }

  }

}
