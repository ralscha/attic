package ch.ess.task.web.priority;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;
import ch.ess.task.db.Priority;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.09.2003 
  * 
  * @struts.action path="/listPriority" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".priority.list"
  */
public class PriorityListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassPriority",
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

    Iterator resultIt = Priority.find();

    List resultDynaList = new ArrayList();

    Locale locale = getLocale(ctx.getRequest());
 
    while (resultIt.hasNext()) {
      Priority priority = (Priority)resultIt.next();
      if (Util.containsString(priority.getTranslations(), searchText)) {
        DynaBean dynaBean = resultClass.newInstance();
        dynaBean.set("id", priority.getId());
        dynaBean.set("name", priority.getTranslations().get(locale));
        dynaBean.set("canDelete", new Boolean(priority.canDelete()));
  
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
