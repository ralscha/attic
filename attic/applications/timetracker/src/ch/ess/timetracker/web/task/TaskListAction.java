package ch.ess.timetracker.web.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.db.Task;

/** 
  * @struts.action path="/listTask" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".task.list"
  */
public class TaskListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassTask",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("name", String.class),
          new DynaProperty("project", String.class),
          new DynaProperty("customer", String.class),
          new DynaProperty("canDelete", Boolean.class)
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    MapForm searchForm = (MapForm)ctx.getForm();
    
    //Option change request
    String change = ctx.getRequest().getParameter("change");
    if (!GenericValidator.isBlankOrNull(change)) {      
      String customerId = (String)searchForm.getValue("customerId"); 
      if (!GenericValidator.isBlankOrNull(customerId)) {
        ctx.getSession().removeAttribute("projectOption");
        ctx.getSession().setAttribute("customerId", new Long(customerId));
      } else {
        ctx.getSession().removeAttribute("projectOption");
        ctx.getSession().removeAttribute("customerId");
      }
      return findForward(Constants.FORWARD_SUCCESS);
    }
        
    
    //Normal search request
    String searchText = (String)searchForm.getValue("searchText");
    String customerId = (String)searchForm.getValue("customerId");
    String projectId = (String)searchForm.getValue("projectId");

    
    Iterator resultIt = Task.findWithSearchtext(searchText, customerId, projectId);
 
    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      Task task = (Task)resultIt.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", task.getId());
      dynaBean.set("name", task.getName());
      dynaBean.set("project", task.getProject().getName());
      dynaBean.set("customer", task.getProject().getCustomer().getName());
      dynaBean.set("canDelete", new Boolean(task.canDelete()));

      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
