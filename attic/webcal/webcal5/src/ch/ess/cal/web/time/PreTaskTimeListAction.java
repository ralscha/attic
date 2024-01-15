package ch.ess.cal.web.time;

import java.util.Calendar;
import java.util.Date;

import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.annotation.struts.StrutsActions;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.enums.TimeRangeEnum;
import ch.ess.cal.web.TimeMonthViewForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.security.SecurityUtil;

/** 
 * @author sr
 * @version $Revision: 632 $ $Date: 2004-12-04 18:37:45 +0100 (Sat, 04 Dec 2004) $ 
 * 
 * @struts.action path="/preListTaskTime" roles="$time;$time_admin" name="taskTimeFilterForm" 
 * input="/tasktimelist.jsp" scope="session" validate="false" parameter="task"
 * @struts.action path="/preReportTaskTime" roles="$time;$time_admin" name="taskTimeFilterForm" input="/tasktimelist.jsp" scope="session" validate="false" parameter="report" 
 * @struts.action path="/preUserReportTaskTime" roles="$time;$time_admin" name="taskTimeFilterForm" input="/tasktimelist.jsp" scope="session" validate="false" parameter="userreport" 
 * @struts.action-forward name="task" path="/listTaskTime.do" 
 * @struts.action-forward name="report" path="/tasktimereport.jsp"
 * @struts.action-forward name="userreport" path="/tasktimeuserreport.jsp" 
 */

@StrutsActions(actions = {
        @StrutsAction(path = "/preTimeList", 
                      roles = "$time", 
                      form = TimeListForm.class, 
                      input = "/timelist.jsp", 
                      scope = ActionScope.SESSION, 
                      forwards = @Forward(name = "success", path = "/timeList.do")),
        @StrutsAction(path = "/preTimeReportList", 
                      roles = "$timeadmin;$time", 
                      form = TimeListForm.class, 
                      input = "/timereportlist.jsp", 
                      parameter = "report",
                      scope = ActionScope.SESSION, 
                      forwards = @Forward(name = "success", path = "/timeReportList.do")),                      
        @StrutsAction(path = "/preDayReportList", 
                      roles = "$timeadmin;$time", 
                      form = TimeListForm.class, 
                      input = "/dayreportlist.jsp", 
                      parameter = "report",
                      scope = ActionScope.SESSION, 
                      forwards = @Forward(name = "success", path = "/dayReportList.do")),                      
        @StrutsAction(path = "/preTimeMatrixReportList", 
                      roles = "$timeadmin;$time", 
                      form = TimeListForm.class, 
                      input = "/timematrixreportlist.jsp", 
                      parameter = "report",
                      scope = ActionScope.SESSION, 
                      forwards = @Forward(name = "success", path = "/timeMatrixReportList.do")),
        @StrutsAction(path = "/preTimeMonthView", 
                      roles = "$timeadmin;$time", 
                      form = TimeMonthViewForm.class, 
                      input = "/timemonthview.jsp", 
                      parameter = "report",
                      scope = ActionScope.SESSION, 
                      forwards = @Forward(name = "success", path = "/timeMonthView.do"))     
        })
public class PreTaskTimeListAction extends FWAction {

  @Override
public void doExecute(final ActionContext ctx) throws Exception {
    try {
    	TimeMonthViewForm searchForm = (TimeMonthViewForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		searchForm.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
		searchForm.setYear(String.valueOf(cal.get(Calendar.YEAR)));
		searchForm.setUserId(userPrincipal.getUserId());
		searchForm.setScale("D");
		
	} catch (Exception e) {
		TimeListForm form = (TimeListForm)ctx.form();
	    Calendar today = Calendar.getInstance();
	    today.setMinimalDaysInFirstWeek(4);
	    form.setYear(String.valueOf(today.get(Calendar.YEAR)));
	    form.setWeek(String.valueOf(today.get(Calendar.WEEK_OF_YEAR)));
	    form.setMonth(null);
	    form.setScale(TimeRangeEnum.DAY.getValue());

	    if (!"report".equals(ctx.mapping().getParameter())) {
	      UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
	      form.setUserId(userPrincipal.getUserId());
	    }
	    
	    ctx.session().removeAttribute("sumresult");
	    ctx.session().removeAttribute("filter");
	    ctx.session().removeAttribute("usersumresult");
	    ctx.session().removeAttribute("usersumresult_cols");
	    ctx.session().removeAttribute("userday");
	}
    ctx.forwardByName("success");

  }

}
