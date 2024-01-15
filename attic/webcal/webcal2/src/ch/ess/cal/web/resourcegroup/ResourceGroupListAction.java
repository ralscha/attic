package ch.ess.cal.web.resourcegroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.ResourceGroup;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 30.09.2003 
  * 
  * @struts.action path="/listResourceGroup" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".resourcegroup.list"
  */
public class ResourceGroupListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassUser",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("name", String.class),
          new DynaProperty("canDelete", Boolean.class)
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");
    Locale locale = getLocale(ctx.getRequest());
    
    Iterator resultIt = ResourceGroup.find();

    List resultDynaList = new ArrayList();
 
    while (resultIt.hasNext()) {
      ResourceGroup rg = (ResourceGroup)resultIt.next();
      if (Util.containsString(rg.getTranslations(), searchText)) {
        DynaBean dynaBean = resultClass.newInstance();
        dynaBean.set("id", rg.getId());
        
        dynaBean.set("name", rg.getTranslations().get(locale));
        dynaBean.set("canDelete", new Boolean(rg.canDelete()));
  
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
