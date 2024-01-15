package ch.ess.cal.web.event;

import java.net.MalformedURLException;

import org.apache.struts.taglib.TagUtils;

import ch.ess.common.web.ListDecorator;

public class ReminderDecorator extends ListDecorator {

  public String getDelete() throws MalformedURLException {

    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {

      Integer id = getIntegerProperty("id");
      String deleteImageURL =
        TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/del.gif", null, null, null, true);

      return "<input type=\"image\" name=\"change.del." + id + "\" src=\"" + deleteImageURL + "\" />";

    } else {
      return getEmptyString();
    }

  }



}
