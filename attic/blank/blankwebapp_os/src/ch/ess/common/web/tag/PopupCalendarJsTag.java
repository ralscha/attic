package ch.ess.common.web.tag;

import java.net.MalformedURLException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.struts.taglib.TagUtils;
import org.apache.velocity.VelocityContext;

import ch.ess.blank.db.User;
import ch.ess.blank.resource.UserConfig;
import ch.ess.blank.web.WebUtils;
import ch.ess.common.db.HibernateSession;

/**
 * @author  Ralph Schaer
 * @version $Revision: 1.5 $ $Date: 2004/05/22 12:24:40 $ 
 * 
 * @jsp.tag name="popupCalendarJs" body-content="empty"
 */
public class PopupCalendarJsTag extends VelocityTag {

  private boolean showToday;
  private boolean showWeekNumber;

  public PopupCalendarJsTag() {
    init();
  }

  public boolean isShowToday() {
    return showToday;
  }

  public boolean isShowWeekNumber() {
    return showWeekNumber;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setShowToday(boolean b) {
    showToday = b;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setShowWeekNumber(boolean b) {
    showWeekNumber = b;
  }

  // --------------------------------------------------------- Public Methods

  /**
   * Process the popupCalendarLocale tag.
   * 
   * @exception JspException
   *              if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    VelocityContext context = new VelocityContext();

    context.put("gotocurrent", getMessage("popupcal.gotocurrent"));
    context.put("today", getMessage("popupcal.today"));
    context.put("week", getMessage("popupcal.week"));
    context.put("leftMessage", getMessage("popupcal.leftMessage"));
    context.put("rightMessage", getMessage("popupcal.rightMessage"));
    context.put("selectMonth", getMessage("popupcal.selectMonth"));
    context.put("selectYear", getMessage("popupcal.selectYear"));
    context.put("selectDate", getMessage("popupcal.selectDate"));

    Locale locale = TagUtils.getInstance().getUserLocale(pageContext, null);

    DateFormatSymbols symbols = new DateFormatSymbols(locale);

    String[] months = symbols.getMonths();
    String[] monthsshort = symbols.getShortMonths();

    for (int i = 0; i < months.length; i++) {
      context.put("month" + (i + 1), months[i]);
      context.put("months" + (i + 1), monthsshort[i]);
    }

    int start = Calendar.MONDAY;

    Transaction tx = null;
    try {
      Session sess = HibernateSession.currentSession();

      tx = sess.beginTransaction();
      User user = WebUtils.getUser((HttpServletRequest) pageContext.getRequest());
      UserConfig config = UserConfig.getUserConfig(user);
      start = config.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);

      tx.commit();

    } catch (HibernateException e) {
      HibernateSession.rollback(tx);
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    } finally {
      HibernateSession.closeSession();
    }

    String[] weekdays = symbols.getShortWeekdays();
    if (start == Calendar.MONDAY) {
      context.put("week1", weekdays[Calendar.MONDAY]);
      context.put("week2", weekdays[Calendar.TUESDAY]);
      context.put("week3", weekdays[Calendar.WEDNESDAY]);
      context.put("week4", weekdays[Calendar.THURSDAY]);
      context.put("week5", weekdays[Calendar.FRIDAY]);
      context.put("week6", weekdays[Calendar.SATURDAY]);
      context.put("week7", weekdays[Calendar.SUNDAY]);
    } else {
      context.put("week1", weekdays[Calendar.SUNDAY]);
      context.put("week2", weekdays[Calendar.MONDAY]);
      context.put("week3", weekdays[Calendar.TUESDAY]);
      context.put("week4", weekdays[Calendar.WEDNESDAY]);
      context.put("week5", weekdays[Calendar.THURSDAY]);
      context.put("week6", weekdays[Calendar.FRIDAY]);
      context.put("week7", weekdays[Calendar.SATURDAY]);
    }

    context.put("start", String.valueOf(start - 1));

    try {
      String imageDir = TagUtils.getInstance().computeURL(pageContext, null, null, "/images/", null, null, null, null,
          true);
      String jspath = TagUtils.getInstance().computeURL(pageContext, null, null, "/scripts/popcalendar.js", null, null,
          null, null, true);

      context.put("imageDir", imageDir);
      context.put("popupcaljs", jspath);

    } catch (MalformedURLException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    }

    if (showWeekNumber) {
      context.put("showWeekNumber", "1");
    } else {
      context.put("showWeekNumber", "0");
    }

    if (showToday) {
      context.put("showToday", "1");
    } else {
      context.put("showToday", "0");
    }

    merge("/ch/ess/common/web/tag/popupcalendar.vm", context);

    return SKIP_BODY;
  }

  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    init();
  }

  private void init() {
    showToday = true;
    showWeekNumber = true;
  }

}