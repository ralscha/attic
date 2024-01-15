package ch.ess.timetracker.web.customer;

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
import ch.ess.timetracker.db.Customer;

/** 
  * @struts.action path="/listCustomer" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".customer.list"
  */
public class CustomerListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassCustomer",
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

    Iterator resultIt;

    resultIt = Customer.findWithSearchtext(searchText);

    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      Customer customer = (Customer)resultIt.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", customer.getId());
      dynaBean.set("name", customer.getName());
      dynaBean.set("canDelete", new Boolean(customer.canDelete()));

      resultDynaList.add(dynaBean);
    }

    ctx.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }

}
