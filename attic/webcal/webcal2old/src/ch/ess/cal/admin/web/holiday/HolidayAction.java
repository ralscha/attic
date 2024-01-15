package ch.ess.cal.admin.web.holiday;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;

public class HolidayAction extends HibernateAction {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMessages messages,
    Session sess)
    throws Exception {

    MapForm searchForm = (MapForm)form;
    String name = (String)searchForm.getValue("name");

    List resultList;

    if (name != null) {
      name = "%" + name.trim() + "%";
      resultList =
        sess.find(
          "from Holiday as hol where hol.builtin = true and hol.name like ? order by hol.country, hol.name",
          name,
          Hibernate.STRING);
    } else {
      resultList = sess.find("from Holiday as hol where hol.builtin = true order by hol.country, hol.name");
    }
    
    
    request.getSession().setAttribute(Constants.RESULT_ID, resultList);

    if (resultList.isEmpty()) {
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

}
