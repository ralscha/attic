package ch.ess.cal.web.view;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.cal.Util;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.event.impl.EventUtil;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $ 
 * 
 * @struts.action path="/monthView" name="monthViewForm" roles="all" input="/monthview.jsp" scope="request" validate="false"  
 * 
 * @spring.bean name="/monthView" lazy-init="true" 
 */
public class MonthViewAction extends Action {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private MonthBeanUtil monthBeanUtil;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * @spring.property reflocal="userConfigurationDao" 
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  /**
   * @spring.property reflocal="monthBeanUtil" 
   */
  public void setMonthBeanUtil(MonthBeanUtil monthBeanUtil) {
    this.monthBeanUtil = monthBeanUtil;
  }

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    MonthViewForm monthForm = (MonthViewForm)form;

    HttpSession session = request.getSession();

    User user = Util.getUser(session, userDao);
    Config config = userConfigurationDao.getUserConfig(user);

    Locale locale = getLocale(request);

    Calendar today = EventUtil.getTodayCalendar(user, config);

    if (monthForm.getInputMonth() != null) {
      today.set(Calendar.MONTH, Integer.parseInt(monthForm.getInputMonth()));
    }

    if (monthForm.getInputYear() != null) {
      today.set(Calendar.YEAR, Integer.parseInt(monthForm.getInputYear()));
    }

    int month = today.get(Calendar.MONTH);
    int year = today.get(Calendar.YEAR);

    MonthBean monthBean = monthBeanUtil.createMonthBean(locale, user, user, month, year, true);

    DateFormatSymbols symbols = new DateFormatSymbols(locale);

    String[] monthNames = symbols.getMonths();
    monthForm.setMonthNames(monthNames);

    monthForm.setMonthBean(monthBean);

    return mapping.getInputForward();
  }

}
