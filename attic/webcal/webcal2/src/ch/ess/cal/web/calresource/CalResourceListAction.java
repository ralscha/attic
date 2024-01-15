package ch.ess.cal.web.calresource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Resource;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 30.09.2003 
  * 
  * @struts.action path="/listCalResource" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".calresource.list"
  */
public class CalResourceListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassResource",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("name", String.class),
          new DynaProperty("resourceGroup", String.class),
          new DynaProperty("canDelete", Boolean.class)
          });

  }

  public ActionForward doAction() throws Exception {
    WebContext ctx = WebContext.currentContext();
    
    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");
    String resourceGroupId = (String)searchForm.getValue("resourceGroupId");
    Locale locale = getLocale(ctx.getRequest());
    
    Iterator resultIt = Resource.find(resourceGroupId);
    List resultDynaList = new ArrayList();
 
    while (resultIt.hasNext()) {
      Resource res = (Resource)resultIt.next();
      if (Util.containsString(res.getTranslations(), searchText)) {
        DynaBean dynaBean = resultClass.newInstance();
        dynaBean.set("id", res.getId());
        
        dynaBean.set("name", res.getTranslations().get(locale));
        dynaBean.set("resourceGroup", res.getResourceGroup().getTranslations().get(locale));
        
        dynaBean.set("canDelete", new Boolean(res.canDelete()));
  
        resultDynaList.add(dynaBean);
      }
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
