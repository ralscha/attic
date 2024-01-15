package ch.ess.task.web.online;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.util.PublicMapDynaBean;
import ch.ess.common.web.BaseAction;
import ch.ess.common.web.WebContext;

import com.opensymphony.clickstream.Clickstream;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:28 $ 
  * 
  * @struts.action path="/listOnline" name="nullForm" input=".online.list" scope="request" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".online.list"
  */
public class OnlineListAction extends BaseAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassOnline",
        PublicMapDynaBean.class,
        new DynaProperty[] {
          new DynaProperty("count", Integer.class),
          new DynaProperty("sid", String.class),
          new DynaProperty("hostName", String.class),
          new DynaProperty("userName", String.class),
          new DynaProperty("start", String.class),
          new DynaProperty("last", String.class),
          new DynaProperty("bot", Boolean.class),
          new DynaProperty("referrer", String.class),
          new DynaProperty("length", Long.class),
          new DynaProperty("size", Integer.class)});
  }

  public ActionForward execute() throws Exception {

    List resultDynaList = new ArrayList();

    Map clickstreams = (Map)getServlet().getServletContext().getAttribute("clickstreams");

    Iterator it = clickstreams.keySet().iterator();
    int count = 1;

    while (it.hasNext()) {

      String key = (String)it.next();
      Clickstream stream = (Clickstream)clickstreams.get(key);

      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("count", new Integer(count));
      dynaBean.set("sid", key);
      dynaBean.set("hostName", stream.getHostname());

      if (stream.getSession() != null) {
        Map userName = (Map)stream.getSession().getAttribute("static_username");
        String userNameStr = userName.get("name") + " " + userName.get("firstName");
        dynaBean.set("userName", userNameStr);
      } else {
        dynaBean.set("userName", "");
      }

      dynaBean.set("size", new Integer(stream.getStream().size()));

      if (stream.isBot()) {
        dynaBean.set("bot", Boolean.TRUE);
      } else {
        dynaBean.set("bot", Boolean.FALSE);
      }

      dynaBean.set("referrer", stream.getInitialReferrer());

      dynaBean.set("start", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(stream.getStart()));
      dynaBean.set("last", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(stream.getLastRequest()));

      long streamLength = stream.getLastRequest().getTime() - stream.getStart().getTime();

      dynaBean.set("length", new Long(((streamLength / 60000) % 60)));

      resultDynaList.add(dynaBean);
      count++;
    }

    WebContext.currentContext().getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);
  }

}
