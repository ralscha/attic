package ch.ess.task.web.online;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.taglib.TagUtils;
import org.displaytag.decorator.TableDecorator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:28 $
  */

public class OnlineDecorator extends TableDecorator {

  public String getSid() throws MalformedURLException {

    DynaBean obj = (DynaBean)this.getCurrentRowObject();
    String id = (String)obj.get("sid");

    Map params = new HashMap();
    params.put("sid", id);

    String showURL =
      TagUtils.getInstance().computeURL(getPageContext(), null, null, "/viewOnline.do", null, params, null, true);


    return "<a onclick=\"bClick=true\" href=\"" + showURL + "\">"+id+"</a>";
  }


}
