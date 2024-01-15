package ch.ess.cal.web.holiday;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Holiday;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.MapForm;
import ch.ess.common.web.WebContext;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 28.09.2003 
  * 
  * @struts.action path="/listHoliday" name="mapForm" scope="session" validate="false" roles="admin"
  * @struts.action-forward name="success" path=".holiday.list"
  */
public class HolidayListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassHoliday",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("active", Boolean.class),
          new DynaProperty("canDelete", Boolean.class),
          new DynaProperty("name", String.class),
          new DynaProperty("month", String.class),
          new DynaProperty("country", String.class),
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    MapForm searchForm = (MapForm)ctx.getForm();
    String searchText = (String)searchForm.getValue("searchText");

    String country = (String)searchForm.getValue("country");
    String show = (String)searchForm.getValue("show");

    Iterator resultIt = Holiday.find(country, show);

    Locale locale = getLocale(ctx.getRequest());
    
    DateFormatSymbols symbols = new DateFormatSymbols(locale);
    String[] months = symbols.getMonths();
    
    List resultDynaList = new ArrayList();

    while (resultIt.hasNext()) {
      Holiday holiday = (Holiday)resultIt.next();
      if (Util.containsString(holiday.getTranslations(), searchText)) {
        DynaBean dynaBean = resultClass.newInstance();
  
        dynaBean.set("id", holiday.getId());
        dynaBean.set("name",  holiday.getTranslations().get(locale));
        
        dynaBean.set("active", new Boolean(holiday.isActive()));
        dynaBean.set("canDelete", new Boolean(holiday.canDelete()));
        
        if (holiday.getMonth() != null) {
          dynaBean.set("month", months[holiday.getMonth().intValue()]);
        } else {
          dynaBean.set("month", null);
        }
        
        if (holiday.getCountry() != null) {
          dynaBean.set("country", holiday.getCountry());
        } else {
          dynaBean.set("country", null);
        }      
        
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
