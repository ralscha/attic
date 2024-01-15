package ch.ess.base.web.tag;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

public class PopupCalendarJsTag extends AbstractTemplateTag {

  @Override
  public int doStartTag() throws JspException {

    Map<String,String>  context = new HashMap<String,String> ();

    Locale locale = TagUtils.getInstance().getUserLocale(pageContext, null);

    String langJs;
    if ("de".equalsIgnoreCase(locale.getLanguage())) {
      langJs = "calendar-de.js";
    } else {
      langJs = "calendar-en.js";
    }

    try {
      String jspath = TagUtils.getInstance().computeURL(pageContext, null, null, "/scripts/calendar.js", null, null,
          null, null, true);
      String jslangpath = TagUtils.getInstance().computeURL(pageContext, null, null, "/scripts/lang/" + langJs, null,
          null, null, null, true);
      String jssetuppath = TagUtils.getInstance().computeURL(pageContext, null, null, "/scripts/calendar-setup.js",
          null, null, null, null, true);

      context.put("caljs", jspath);
      context.put("callangjs", jslangpath);
      context.put("calsetupjs", jssetuppath);

      String stylepath = TagUtils.getInstance().computeURL(pageContext, null, null, "/styles/calendar-blue.css", null,
          null, null, null, true);

      context.put("calstyle", stylepath);

    } catch (MalformedURLException e) {
      TagUtils.getInstance().saveException(pageContext, e);
      throw new JspException(e);
    }

    merge("popupcalendar.ftl", context);

    return SKIP_BODY;
  }
}
