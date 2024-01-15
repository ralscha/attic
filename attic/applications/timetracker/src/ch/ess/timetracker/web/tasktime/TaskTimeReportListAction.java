package ch.ess.timetracker.web.tasktime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.type.Type;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;

/** 
  * @struts.action path="/reportTaskTime" name="mapForm" scope="session" validate="false"
  * @struts.action-forward name="success" path=".tasktime.report"
  */
public class TaskTimeReportListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassTaskTimeReport",
        null,
        new DynaProperty[] {
          new DynaProperty("totalHours", BigDecimal.class),
          new DynaProperty("totalCost", BigDecimal.class),
          new DynaProperty("project", String.class),
          new DynaProperty("customer", String.class),
          new DynaProperty("task", String.class)          
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();    
    MapForm searchForm = (MapForm)ctx.getForm();
    
    
    String reset = ctx.getRequest().getParameter("reset");
    if (!GenericValidator.isBlankOrNull(reset)) {
      searchForm.getValueMap().clear();
      return findForward(Constants.FORWARD_SUCCESS);
    }
    
    
    TaskTimeFilter ttf = TaskTimeFilter.createFilter();
        
    
    List valueList = new ArrayList();

    String where = "WHERE c.id = ?";
    valueList.add(ttf.getCustomerId());
    
    if (ttf.getFromDate() != null) {
      where += " AND tt.taskTimeDate >= ?";      
      valueList.add(ttf.getFromDate());
    }
    
    if (ttf.getToDate() != null) {
      where += " AND tt.taskTimeDate <= ?";
      valueList.add(ttf.getToDate());
    }
    
    Object[] values = valueList.toArray(new Object[valueList.size()]);
    Type[] types = new Type[valueList.size()];
    
    values[0] = new Long(ttf.getCustomerId()); 
    types[0] = Hibernate.LONG;
    
    for (int i = 1; i < types.length; i++) {
      types[i] = Hibernate.DATE;
    }      
  
    String sqlPart1 = "select c.name, p.name, t.name, sum(tt.workInHour), sum(tt.cost) from Customer c join c.projects p join p.tasks t join t.taskTimes tt ";
    String sqlPart2 = " group by t.name, p.name, c.name";
    
    
    List l = HibernateSession.currentSession().find(sqlPart1+where+sqlPart2, values, types);

    Iterator resultIt = l.iterator();
    
    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      Object[] result = (Object[])resultIt.next();
      
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("customer", result[0]);
      dynaBean.set("project", result[1]);
      dynaBean.set("task", result[2]);
      dynaBean.set("totalHours", result[3]);
      dynaBean.set("totalCost", result[4]);
      
      
      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);
  }

}
