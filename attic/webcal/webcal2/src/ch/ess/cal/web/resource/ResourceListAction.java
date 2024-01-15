package ch.ess.cal.web.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.TextResource;
import ch.ess.cal.resource.text.Resource;
import ch.ess.cal.resource.text.TextResources;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:53 $ 
  * 
  * @struts.action path="/listResource" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".resource.list"
  */
public class ResourceListAction extends HibernateAction {

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
    
    Iterator resultIt = TextResource.find();

    List resultDynaList = new ArrayList();
 
    while (resultIt.hasNext()) {
      TextResource tr = (TextResource)resultIt.next();
      if (Util.containsString(tr.getTranslations(), searchText)) {
        DynaBean dynaBean = resultClass.newInstance();
        dynaBean.set("id", tr.getId());
        
        String name = tr.getName();
        Resource resource = (Resource)TextResources.getResources().get(name);                
        dynaBean.set("name", resource.getDescription().get(locale));
        dynaBean.set("canDelete", new Boolean(tr.canDelete()));
  
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
