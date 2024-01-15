package ch.ess.cal.web.tag;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.velocity.VelocityContext;

import ch.ess.cal.Constants;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.service.impl.UserConfig;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $ 
 * 
 * @jsp.tag name="popupCalendar" body-content="empty"
 */
public class PopupCalendarTag extends AbstractVelocityTag {

  private String element = null;
  private String trigger = null;
  private String weekNumbers = null;
  private String showsTime = null;
  private String showOthers = null;

  public PopupCalendarTag() {
    init();
  }

  public String getTrigger() {
    return trigger;
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true"   
   */
  public void setTrigger(String trigger) {
    this.trigger = trigger;
  }

  public String getElement() {
    return element;
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true"   
   */
  public void setElement(final String string) {
    element = string;
  }

  public String getShowOthers() {
    return showOthers;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setShowOthers(String showOthers) {
    this.showOthers = showOthers;
  }

  public String getShowsTime() {
    return showsTime;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setShowsTime(String showsTime) {
    this.showsTime = showsTime;
  }

  public String getWeekNumbers() {
    return weekNumbers;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setWeekNumbers(String weekNumbers) {
    this.weekNumbers = weekNumbers;
  }

  @Override
  public int doStartTag() throws JspException {

    User user = getUser();

    UserConfigurationDao userConfigurationDao = (UserConfigurationDao)getBean("userConfigurationDao");
    Config userConfig = userConfigurationDao.getUserConfig(user);
    int start = userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);

    VelocityContext context = new VelocityContext();

    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("inputField", "\"" + element + "\"");

    //paramMap.put("ifFormat", "\"%d.%m.%Y\"");
    paramMap.put("ifFormat", "\"" + Constants.getJavascriptDateFormatPattern() + "\"");

    paramMap.put("button", "\"" + trigger + "\"");
    paramMap.put("firstDay", "" + (start - 1) + "");

    if ("false".equals(getWeekNumbers())) {
      paramMap.put("weekNumbers", "false");
    }

    if ("true".equals(getShowOthers())) {
      paramMap.put("showOthers", "true");
    }

    if ("true".equals(getShowsTime())) {
      paramMap.put("showsTime", "true");
    }

    context.put("params", paramMap);

    merge("/ch/ess/cal/web/tag/popupcalendarlink.vm", context);

    return SKIP_BODY;
  }

  /**
   * Release all allocated resources.
   */
  @Override
  public void release() {
    super.release();
    init();
  }

  private void init() {
    element = null;
    trigger = null;
    weekNumbers = null;
    showsTime = null;
    showOthers = null;
  }

}
