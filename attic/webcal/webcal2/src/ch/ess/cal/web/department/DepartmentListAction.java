package ch.ess.cal.web.department;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.hibernate.Hibernate;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Department;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 29.09.2003 
  * 
  * @struts.action path="/listDepartment" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".department.list"
  */
public class DepartmentListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassDepartment",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("name", String.class),
          new DynaProperty("canDelete", Boolean.class),
          });

  }

  public ActionForward doAction()
    throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");


    Iterator resultIt = Department.find();
    List resultDynaList = new ArrayList();

    Locale locale = getLocale(ctx.getRequest());
 
    while (resultIt.hasNext()) {
      Department department = (Department)resultIt.next();
      if (Util.containsString(department.getTranslations(), searchText)) {
        DynaBean dynaBean = resultClass.newInstance();
        dynaBean.set("id", department.getId());
        dynaBean.set("name", department.getTranslations().get(locale));
        dynaBean.set("canDelete", new Boolean(department.canDelete()));
  
        resultDynaList.add(dynaBean);
      }
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);
    Hibernate.close(resultIt);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
