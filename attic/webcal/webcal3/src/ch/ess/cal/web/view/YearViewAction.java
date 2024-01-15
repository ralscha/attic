package ch.ess.cal.web.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
 * @struts.action path="/yearView" name="yearViewForm" roles="all" input="/yearview.jsp" scope="request" validate="false"  
 * 
 * @spring.bean name="/yearView" lazy-init="true" 
 */
public class YearViewAction extends Action {

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

    YearViewForm yearForm = (YearViewForm)form;

    HttpSession session = request.getSession();

    User user = Util.getUser(session, userDao);
    Config config = userConfigurationDao.getUserConfig(user);

    Locale locale = getLocale(request);

    Calendar today = EventUtil.getTodayCalendar(user, config);

    if (yearForm.getInputYear() != null) {
      today.set(Calendar.YEAR, Integer.parseInt(yearForm.getInputYear()));
    }

    int year = today.get(Calendar.YEAR);
    List<MonthBean> monthList = new ArrayList<MonthBean>();
    for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
      MonthBean monthBean = monthBeanUtil.createMonthBean(locale, user, user, month, year, false);
      monthList.add(monthBean);
    }

    yearForm.setMonthBean(monthList.toArray(new MonthBean[monthList.size()]));

    return mapping.getInputForward();
  }

}
