package ch.ess.task.web.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Hibernate;

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
import ch.ess.task.db.Task;
import ch.ess.task.db.User;
import ch.ess.task.web.task.search.SearchTask;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 14.09.2003 
  * 
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
          new DynaProperty("priority", String.class),
          new DynaProperty("assignedTo", String.class),
          new DynaProperty("status", String.class),
          new DynaProperty("complete", Integer.class),
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");
    String projectId = (String)searchForm.getValue("projectId");
    String priorityId = (String)searchForm.getValue("priorityId");
    String userId = (String)searchForm.getValue("userId");
    String statusId = (String)searchForm.getValue("statusId");
    

    Iterator resultIt;

    if (!GenericValidator.isBlankOrNull(searchText)) {     
            
      List ids = SearchTask.search(searchText);

      if (ids != null) {
        resultIt = Task.findWithIds(ids, projectId, priorityId, userId, statusId);        
      } else {
        resultIt = Collections.EMPTY_LIST.iterator();
      }
   
    } else {
      resultIt = Task.find(projectId, priorityId, userId, statusId);
    }

    List resultDynaList = new ArrayList();
    Locale locale = getLocale(ctx.getRequest());

    while (resultIt.hasNext()) {
      Task task = (Task)resultIt.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", task.getId());
      dynaBean.set("name", task.getName());
      if (task.getProject() != null) {
        dynaBean.set("project", task.getProject().getTranslations().get(locale));
      }
      if (task.getPriority() != null) {
        dynaBean.set("priority", task.getPriority().getTranslations().get(locale));
      }
      if (task.getStatus() != null) {
        dynaBean.set("status", task.getStatus().getTranslations().get(locale));
      }
      
      User u = task.getAssignedTo();
      if (task.getAssignedTo() != null) {
        dynaBean.set("assignedTo", u.getName() + " " + u.getFirstName());
      }      
      
      dynaBean.set("complete", task.getComplete());      

      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);
    
    try {
      Hibernate.close(resultIt);
    } catch (IllegalArgumentException iae) { 
      //no action
    }

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
