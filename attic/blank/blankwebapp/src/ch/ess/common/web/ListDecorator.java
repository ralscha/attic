package ch.ess.common.web;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.taglib.TagUtils;
import org.displaytag.decorator.TableDecorator;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.10 $ $Date: 2004/05/22 12:24:25 $
 */
public class ListDecorator extends TableDecorator {

  public String getEmptyString() throws MalformedURLException {
    String blankImageURL = TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/x.gif", null, null,
        null, null, true);

    return "<img src=\"" + blankImageURL + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\" />";
  }

  public String getEditString(String object) throws MalformedURLException {

    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    Long id = (Long) obj.get("id");

    Map params = new HashMap();
    params.put("id", id);
    params.put("action", "edit");
    String editURL = TagUtils.getInstance().computeURL(getPageContext(), null, null, "/edit" + object + ".do", null,
        null, params, null, true);

    String editImageURL = TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/edit.gif", null,
        null, null, null, true);

    return "<a onclick=\"bClick=true\" href=\"" + editURL + "\"><img src=\"" + editImageURL
        + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\" /></a>";
  }

  public String getDeleteString(String object, String nameCol) throws MalformedURLException {

    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    Long id = (Long) obj.get("id");
    String name = (String) obj.get(nameCol);

    Map params = new HashMap();
    params.put("id", id);
    String editURL = TagUtils.getInstance().computeURL(getPageContext(), null, null, "/delete" + object + ".do", null,
        null, params, null, true);

    String deleteImageURL = TagUtils.getInstance().computeURL(getPageContext(), null, null, "/images/del.gif", null,
        null, null, null, true);

    return "<a onclick=\"bClick=true; return confirmRequest('" + name + "')\" href=\"" + editURL + "\"><img src=\""
        + deleteImageURL + "\" alt=\"\" width=\"16\" height=\"16\" border=\"0\" /></a>";
  }

  public Boolean getBooleanProperty(String property) {
    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    return (Boolean) obj.get(property);
  }

  public String getStringProperty(String property) {
    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    return (String) obj.get(property);
  }

  public Long getLongProperty(String property) {
    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    return (Long) obj.get(property);
  }

  public Integer getIntegerProperty(String property) {
    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    return (Integer) obj.get(property);
  }

  public BigDecimal getBigDecimalProperty(String property) {
    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    return (BigDecimal) obj.get(property);
  }

}