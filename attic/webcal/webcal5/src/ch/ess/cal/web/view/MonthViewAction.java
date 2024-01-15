package ch.ess.cal.web.view;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Util;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.web.CrumbInfo;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.model.Event;
import ch.ess.cal.service.EventUtil;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;

@SpringAction
@StrutsAction(path = "/monthView", form = MonthViewForm.class, roles = "all", input = "/monthview.jsp", scope = ActionScope.REQUEST, validate = false)
public class MonthViewAction extends FWAction implements CrumbInfo {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private MonthBeanUtil monthBeanUtil;
  private EventDao eventDao;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  public void setMonthBeanUtil(MonthBeanUtil monthBeanUtil) {
    this.monthBeanUtil = monthBeanUtil;
  }

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }

  public void doExecute(ActionContext ctx) throws Exception {

    MonthViewForm monthForm = (MonthViewForm)ctx.form();

    HttpSession session = ctx.session();

    User user = Util.getUser(session, userDao);
    Config config = userConfigurationDao.getUserConfig(user);

    Locale locale = getLocale(ctx.request());

    Calendar today = EventUtil.getTodayCalendar(user, config);

    if (monthForm.getInputMonth() != null) {
      today.set(Calendar.MONTH, Integer.parseInt(monthForm.getInputMonth()));
    }

    if (monthForm.getInputYear() != null) {
      today.set(Calendar.YEAR, Integer.parseInt(monthForm.getInputYear()));
    }

    int month = today.get(Calendar.MONTH);
    int year = today.get(Calendar.YEAR);

    monthForm.setInputMonth(String.valueOf(month));
    monthForm.setInputYear(String.valueOf(year));

    MonthBean monthBean = monthBeanUtil.createMonthBean(locale, user, user, month, year, true, getResources(ctx.request()));

    DateFormatSymbols symbols = new DateFormatSymbols(locale);

    String[] monthNames = symbols.getMonths();
    monthForm.setMonthNames(monthNames);

    monthForm.setMonthBean(monthBean);

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
  
  public void increaseMonth_onClick(FormActionContext ctx) throws Exception {
	  MonthViewForm form = (MonthViewForm) ctx.form();
	  Calendar cal = Calendar.getInstance();
	  cal.set(Calendar.MONTH, Integer.valueOf(form.getInputMonth()) +1);
	  int old = Integer.valueOf(form.getInputMonth());
	  if(old > cal.get(Calendar.MONTH)){
		cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) +1);
		form.setInputYear(String.valueOf(cal.get(Calendar.YEAR)));	
	 }
	  form.setInputMonth(String.valueOf(cal.get(Calendar.MONTH)));
	  }
	  
	  public void decreaseMonth_onClick(FormActionContext ctx) throws Exception {
		  MonthViewForm form = (MonthViewForm) ctx.form();
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.MONTH, Integer.valueOf(form.getInputMonth()) -1);
			  int old = Integer.valueOf(form.getInputMonth());
			  if(old < cal.get(Calendar.MONTH)){
				cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) -1);
				form.setInputYear(String.valueOf(cal.get(Calendar.YEAR)));	
				 }
				  form.setInputMonth(String.valueOf(cal.get(Calendar.MONTH)));
			}
	
	  
	public void increaseYear_onClick(FormActionContext ctx) throws Exception {
		MonthViewForm form = (MonthViewForm) ctx.form();
			 
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) +1);
				form.setInputYear(String.valueOf(cal.get(Calendar.YEAR)));	
				
	  }
	  
	  public void decreaseYear_onClick(FormActionContext ctx) throws Exception {
		  MonthViewForm form = (MonthViewForm) ctx.form();

			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.YEAR, Integer.valueOf(form.getInputYear()) -1);
				form.setInputYear(String.valueOf(cal.get(Calendar.YEAR)));	
				
	  }

}
