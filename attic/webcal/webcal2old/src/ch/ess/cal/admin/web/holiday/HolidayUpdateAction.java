package ch.ess.cal.admin.web.holiday;


import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Session;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.Constants;
import ch.ess.cal.common.HibernateAction;
import ch.ess.cal.db.Holiday;
import ch.ess.cal.resource.HolidayRegistry;

public class HolidayUpdateAction extends HibernateAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMessages messages,
    Session sess)
    throws Exception {


    
    HolidayUpdateForm huf = (HolidayUpdateForm)form;
    
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
            Holiday hol = (Holiday)sess.load(Holiday.class, ids[i]);
            hol.setActive(false);
            HolidayRegistry.removeHoliday(hol.getName());
          }
        }
      }
      
      ids = huf.getNotactiveId();
      if (ids != null) {    
        for (int i = 0; i < ids.length; i++) {          
          if (idList.contains(ids[i])) {
            Holiday hol = (Holiday)sess.load(Holiday.class, ids[i]);
            hol.setActive(true);
            HolidayRegistry.addHoliday(hol);
          }
        }
      }
    } else if (huf.getShow() != null) {
      List resultList = sess.find("from Holiday as hol where hol.builtin = true and hol.active = false");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Holiday hol = (Holiday)it.next();
        hol.setActive(true);
        HolidayRegistry.addHoliday(hol);
      }
    } else if (huf.getHide() != null) {
      List resultList = sess.find("from Holiday as hol where hol.builtin = true and hol.active = true");
      for (Iterator it = resultList.iterator(); it.hasNext();) {
        Holiday hol = (Holiday)it.next();
        hol.setActive(false);
        HolidayRegistry.removeHoliday(hol.getName());
      }    
    }     
    

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

}
