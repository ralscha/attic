package ch.ess.cal.web.category;

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

import ch.ess.cal.db.Category;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.09.2003 
  * 
  * @struts.action path="/listCategory" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".category.list"
  */
public class CategoryListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassCategory",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("name", String.class),
          new DynaProperty("canDelete", Boolean.class),
          new DynaProperty("unknown", Boolean.class),
          new DynaProperty("colour", String.class),
          new DynaProperty("bwColour", String.class)
          });

  }

  public ActionForward doAction() throws Exception {
    WebContext ctx = WebContext.currentContext();
    
    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");

    Iterator resultIt = Category.find();

    List resultDynaList = new ArrayList();

    Locale locale = getLocale(ctx.getRequest());
 
    while (resultIt.hasNext()) {
      Category category = (Category)resultIt.next();
      if (Util.containsString(category.getTranslations(), searchText)) {
        DynaBean dynaBean = resultClass.newInstance();
        dynaBean.set("id", category.getId());
        dynaBean.set("name", category.getTranslations().get(locale));
        dynaBean.set("unknown", new Boolean(category.isUnknown()));
        dynaBean.set("canDelete", new Boolean(category.canDelete()));
        dynaBean.set("colour", category.getColour());
        dynaBean.set("bwColour", category.getBwColour());
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
