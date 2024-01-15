package ch.ess.base.web.tag;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;

import ch.ess.base.Constants;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;

public class PopupCalendarTag extends AbstractTemplateTag {

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


  public void setTrigger(String trigger) {
    this.trigger = trigger;
  }

  public String getElement() {
    return element;
  }


  public void setElement(final String string) {
    element = string;
  }

  public String getShowOthers() {
    return showOthers;
  }


  public void setShowOthers(String showOthers) {
    this.showOthers = showOthers;
  }

  public String getShowsTime() {
    return showsTime;
  }


  public void setShowsTime(String showsTime) {
    this.showsTime = showsTime;
  }

  public String getWeekNumbers() {
    return weekNumbers;
  }


  public void setWeekNumbers(String weekNumbers) {
    this.weekNumbers = weekNumbers;
  }

  @Override
  public int doStartTag() throws JspException {

    User user = getUser();

    UserConfigurationDao userConfigurationDao = (UserConfigurationDao)getBean("userConfigurationDao");
    Config userConfig = userConfigurationDao.getUserConfig(user);
    int start = userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);

    Map<String,Object>  context = new HashMap<String,Object> ();

    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("inputField", "\"" + element + "\"");

    //paramMap.put("ifFormat", "\"%d.%m.%Y\"");
    if ("true".equals(getShowsTime())) {
      paramMap.put("ifFormat", "\"" + Constants.getJavascriptDateTimeFormatPattern() + "\"");
    } else {
      paramMap.put("ifFormat", "\"" + Constants.getJavascriptDateFormatPattern() + "\"");
    }

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

    merge("popupcalendarlink.ftl", context);

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
