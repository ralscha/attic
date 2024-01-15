package ch.ess.timetracker.web.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;
import ch.ess.timetracker.db.Project;

/** 
  * @struts.action path="/listProject" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".project.list"
  */
public class ProjectListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassProject",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("name", String.class),
          new DynaProperty("customer", String.class),
          new DynaProperty("canDelete", Boolean.class)
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");
    String customerId = (String)searchForm.getValue("customerId");

    Iterator resultIt;

    
    resultIt = Project.findWithSearchtext(searchText, customerId);

    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      Project project = (Project)resultIt.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", project.getId());
      dynaBean.set("name", project.getName());
      dynaBean.set("customer", project.getCustomer().getName());
      dynaBean.set("canDelete", new Boolean(project.canDelete()));

      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
