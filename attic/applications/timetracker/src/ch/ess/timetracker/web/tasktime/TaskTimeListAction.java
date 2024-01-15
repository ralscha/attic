package ch.ess.timetracker.web.tasktime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import ch.ess.timetracker.db.TaskTime;

/** 
  * @struts.action path="/listTaskTime" name="mapForm" scope="session" validate="false"
  * @struts.action-forward name="success" path=".tasktime.list"
  */
public class TaskTimeListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassTaskTime",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("date", Date.class),
          new DynaProperty("hours", BigDecimal.class),
          new DynaProperty("hourRate", BigDecimal.class),
          new DynaProperty("total", BigDecimal.class),
          new DynaProperty("shortName", String.class),
          new DynaProperty("description", String.class),
          new DynaProperty("project", String.class),
          new DynaProperty("customer", String.class),
          new DynaProperty("task", String.class),          
          new DynaProperty("canDelete", Boolean.class)
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();    
    MapForm searchForm = (MapForm)ctx.getForm();
    
    String reset = ctx.getRequest().getParameter("reset");
    if (!GenericValidator.isBlankOrNull(reset)) {
      searchForm.getValueMap().clear();
      ctx.getSession().removeAttribute("taskOption");
      ctx.getSession().removeAttribute("projectOption");
      ctx.getSession().removeAttribute("customerOption");
      ctx.getSession().removeAttribute("customerId");
      ctx.getSession().removeAttribute("projectId");
      return findForward(Constants.FORWARD_SUCCESS);
    }
    
    
    String changeProject = ctx.getRequest().getParameter("changeProject");
    String changeCustomer = ctx.getRequest().getParameter("changeCustomer");
    
    if (!GenericValidator.isBlankOrNull(changeProject)) {      
      
      String projectId = (String)searchForm.getValue("projectId");
      
      if (!GenericValidator.isBlankOrNull(projectId)) {
        ctx.getSession().removeAttribute("taskOption");        
        ctx.getSession().setAttribute("projectId", new Long(projectId));
      } else {
        ctx.getSession().removeAttribute("taskOption");
        ctx.getSession().removeAttribute("projectId");
        searchForm.getValueMap().remove("taskId"); 
      }
      
      return findForward(Constants.FORWARD_SUCCESS);
    } else if (!GenericValidator.isBlankOrNull(changeCustomer)) {      
      String customerId = (String)searchForm.getValue("customerId");
      
      if (!GenericValidator.isBlankOrNull(customerId)) {
        
        ctx.getSession().removeAttribute("projectOption");
        ctx.getSession().removeAttribute("taskOption");        
        ctx.getSession().setAttribute("customerId", new Long(customerId));
        ctx.getSession().removeAttribute("projectId");
      } else {
        ctx.getSession().removeAttribute("projectOption");
        ctx.getSession().removeAttribute("taskOption");
        ctx.getSession().removeAttribute("customerId");
        ctx.getSession().removeAttribute("projectId");
        searchForm.getValueMap().remove("projectId");
        searchForm.getValueMap().remove("customerId");
        searchForm.getValueMap().remove("taskId");        
      }
      
      return findForward(Constants.FORWARD_SUCCESS);
    } 
    
        
    TaskTimeFilter ttf = TaskTimeFilter.createFilter();
    
    Iterator resultIt = TaskTime.find(ttf.getFromDate(), ttf.getToDate(),                                      
                                      ttf.getCustomerId(), ttf.getProjectId(),
                                      ttf.getTaskId(), ttf.getCustomers(),
                                      ttf.getUserId());
        

    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      TaskTime taskTime = (TaskTime)resultIt.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", taskTime.getId());
      dynaBean.set("date", taskTime.getTaskTimeDate());
            
      dynaBean.set("hours", taskTime.getWorkInHour());
      dynaBean.set("hourRate", taskTime.getHourRate());
      
      dynaBean.set("total", taskTime.getCost());
      
      dynaBean.set("task", taskTime.getTask().getName());
      dynaBean.set("shortName", taskTime.getUser().getShortName());
      dynaBean.set("description", taskTime.getDescription());
      dynaBean.set("project", taskTime.getTask().getProject().getName());
      dynaBean.set("customer", taskTime.getTask().getProject().getCustomer().getName());
      dynaBean.set("canDelete", new Boolean(taskTime.canDelete()));

      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
