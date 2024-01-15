package ch.ess.cal.admin.web.user;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;

public class UserListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassUser",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("userName", String.class),
          new DynaProperty("name", String.class),
          new DynaProperty("firstName", String.class),
          new DynaProperty("admin", String.class),
          });

  }

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    ActionMessages messages,
    Session sess)
    throws Exception {

    MapForm searchForm = (MapForm)form;
    String userName = (String)searchForm.getValue("userName");

    List resultList;

    if (userName != null) {
      userName = "%" + userName.trim() + "%";
      resultList =
        sess.find(
          "from User as u where u.userName like ? order by u.userName asc",
          userName,
          Hibernate.STRING);
    } else {
      resultList = sess.find("from User as u order by u.userName asc");
    }
    
    
    List resultDynaList = new ArrayList();
    
    for (Iterator it = resultList.iterator(); it.hasNext();) {
      User user = (User)it.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", user.getUserId());
      dynaBean.set("userName", user.getUserName());
      dynaBean.set("name", user.getName());
      dynaBean.set("firstName", user.getFirstName());
      if (user.getUserGroup() != null) {
        dynaBean.set("admin", "Y");
      } else {
        dynaBean.set("admin", "");
      }
      resultDynaList.add(dynaBean);  
    }
    
    
    request.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultList.isEmpty()) {
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

}
