package ch.ess.addressbook.web.online;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.web.BaseAction;
import ch.ess.common.web.WebContext;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamRequest;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.4 $ $Date: 2003/11/11 19:03:32 $ 
  * 
  * @struts.action path="/viewOnline" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".online.view"
  */
public class OnlineViewAction extends BaseAction {

  private static DynaClass resultClass;
  
  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassRequests",
        null,
        new DynaProperty[] { new DynaProperty("count", Integer.class), new DynaProperty("request", String.class)});
  }

  public ActionForward execute() throws Exception {

    WebContext ctx = WebContext.currentContext();
    List resultList = new ArrayList();
    
    HttpServletRequest request = ctx.getRequest();

    List list = (List)request.getSession().getAttribute(Constants.RESULT_ID);    
    String sid = request.getParameter("sid");
    for (Iterator it = list.iterator(); it.hasNext();) {
      DynaBean obj = (DynaBean)it.next();
      
      if (obj.get("sid").equals(sid)) {
        request.getSession().setAttribute("online", obj);
        break;
      }      
    }
    
    Map clickstreams = (Map)getServlet().getServletContext().getAttribute("clickstreams");
    Clickstream stream = (Clickstream)clickstreams.get(sid);
    
    Iterator clickstreamIt = stream.getStream().iterator();
    
    int i = 1;
    while (clickstreamIt.hasNext()) {
      String click = ((ClickstreamRequest)clickstreamIt.next()).toString();
      
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("count", new Integer(i));
      dynaBean.set("request", click);
      
      resultList.add(dynaBean);
      i++;
    }

    ctx.getSession().setAttribute("onlineshow", resultList);

    return findForward(Constants.FORWARD_SUCCESS);
  }

}
