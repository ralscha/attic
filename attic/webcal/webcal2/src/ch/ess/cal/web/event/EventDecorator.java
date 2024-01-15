package ch.ess.cal.web.event;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.taglib.TagUtils;

import ch.ess.common.web.ListDecorator;

public class EventDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("Event");
  }

  public String getDelete() throws MalformedURLException {
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("Event", "subject");
    } else {
      return getEmptyString();
    }
  }

  public String getReminder() throws MalformedURLException {

    Boolean reminder = getBooleanProperty("reminder");

    if (reminder.booleanValue()) {
      return getString("1", "reminder.gif");
    } else {
      return getEmptyString();
    }

  }

  public String getRecurrence() throws MalformedURLException {

    Boolean recurrence = getBooleanProperty("recurrence");

    if (recurrence.booleanValue()) {
      return getString("2", "recurrence.gif");
    } else {
      return getEmptyString();
    }

  }

  private String getString(String page, String image) throws MalformedURLException {
    DynaBean obj = (DynaBean)this.getCurrentRowObject();
    Long id = (Long)obj.get("id");

    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    params.put("page", page);
    String editURL = TagUtils.getInstance()
        .computeURL(getPageContext(), null, null, "/editEvent.do", null, params, null, true);

    String editImageURL = TagUtils.getInstance()
        .computeURL(getPageContext(), null, null, "/images/" + image, null, null, null, true);

    return "<a onclick=\"bClick=true\" href=\"" + editURL + "\"><img src=\"" + editImageURL
        + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\" /></a>";
  }

}