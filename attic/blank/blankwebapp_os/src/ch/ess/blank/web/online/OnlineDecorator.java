package ch.ess.blank.web.online;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.taglib.TagUtils;
import org.displaytag.decorator.TableDecorator;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 12:24:46 $
 */

public class OnlineDecorator extends TableDecorator {

  public String getSid() throws MalformedURLException {

    DynaBean obj = (DynaBean) this.getCurrentRowObject();
    String id = (String) obj.get("sid");

    Map params = new HashMap();
    params.put("sid", id);

    String showURL = TagUtils.getInstance().computeURL(getPageContext(), null, null, "/viewOnline.do", null, null,
        params, null, true);

    return "<a onclick=\"bClick=true\" href=\"" + showURL + "\">" + id + "</a>";
  }

}