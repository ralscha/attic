package ch.ess.cal.web;

import java.net.MalformedURLException;

import org.apache.struts.taglib.TagUtils;

import ch.ess.common.web.ListDecorator;

public class EmailDecorator extends ListDecorator {

  public String getDelete() throws MalformedURLException {

    Boolean canDelete = getBooleanProperty("canDelete");
    Boolean def = getBooleanProperty("default");

    if (canDelete.booleanValue() && !def.booleanValue()) {

      Integer id = getIntegerProperty("id");
      String deleteImageURL =
        TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/del.gif", null, null, null, true);

      return "<input type=\"image\" name=\"change.del." + id + "\" src=\"" + deleteImageURL + "\" />";

    } else {
      return getEmptyString();
    }

  }

  public String getDefault() throws MalformedURLException {

    Boolean def = getBooleanProperty("default");

    if (def.booleanValue()) {

      String checkedImageURL =
        TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/checked.gif", null, null, null, true);

      return "<img src=\"" + checkedImageURL + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\">";
    } else {
      Integer id = getIntegerProperty("id");

      String checkedImageURL =
        TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/checkedl.gif", null, null, null, true);

      return "<input type=\"image\" name=\"change.def." + id + "\" src=\"" + checkedImageURL + "\" />";

    }
  }

}
