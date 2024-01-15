package ch.ess.cal.admin.web.department;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;

public class DepartmentListAction extends HibernateAction {

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
          "from Department as department where department.name like ? order by department.name asc",
          name,
          Hibernate.STRING);
    } else {
      resultList = sess.find("from Department as department order by department.name asc");
    }
    
    request.getSession().setAttribute(Constants.RESULT_ID, resultList);

    if (resultList.isEmpty()) {
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

}
