package ch.ess.cal.web.holiday;


import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Holiday;
import ch.ess.cal.resource.HolidayRegistry;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;


    
/** 
  * @author  Ralph Schaer
  * @version 1.0, 28.09.2003 
  * 
  * @struts.action path="/updateHoliday" name="holidayUpdateForm" scope="request" validate="false" roles="admin"
  * @struts.action-forward name="success" path="/listHoliday.do"
  */
public class HolidayUpdateAction extends HibernateAction {

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    HolidayUpdateForm huf = (HolidayUpdateForm)ctx.getForm();
    
    if (huf.getSave() != null) {
    
      List idList;
      if (huf.getCheck() != null) {    
        idList = Arrays.asList(huf.getCheck());
      } else {
        idList = Collections.EMPTY_LIST;
      }
      
      
      Long[] ids = huf.getActiveId();
      if (ids != null) {    
        for (int i = 0; i < ids.length; i++) {          
          if (!idList.contains(ids[i])) {
            Holiday hol = Holiday.load(ids[i]);
            hol.setActive(false);
            HolidayRegistry.removeHoliday(hol);
          }
        }
      }
      
      ids = huf.getNotactiveId();
      if (ids != null) {    
        for (int i = 0; i < ids.length; i++) {          
          if (idList.contains(ids[i])) {
            Holiday hol = Holiday.load(ids[i]);
            hol.setActive(true);
            HolidayRegistry.addHoliday(hol);
          }
        }
      }
    } else if (huf.getShow() != null) {
      
      List dynas = (List)ctx.getSession().getAttribute(Constants.RESULT_ID);
      
      for (Iterator it = dynas.iterator(); it.hasNext();) {
        DynaBean obj = (DynaBean)it.next();
        Holiday hol = Holiday.load((Long)obj.get("id"));
        hol.setActive(true);
        HolidayRegistry.addHoliday(hol);         
      }
    
      
    } else if (huf.getHide() != null) {
      List dynas = (List)ctx.getSession().getAttribute(Constants.RESULT_ID);
      
      for (Iterator it = dynas.iterator(); it.hasNext();) {
        DynaBean obj = (DynaBean)it.next();
        Holiday hol = Holiday.load((Long)obj.get("id"));
        hol.setActive(false);
        HolidayRegistry.removeHoliday(hol);       
      }
        
    }     
    
    addOneMessageRequest(Constants.ACTION_MESSAGE_UPDATE_OK);

    return findForward(Constants.FORWARD_SUCCESS);

  }



}
