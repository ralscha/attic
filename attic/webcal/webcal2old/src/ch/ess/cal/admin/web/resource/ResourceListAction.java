package ch.ess.cal.admin.web.resource;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;
import net.sf.hibernate.expression.*;

import org.apache.commons.beanutils.*;
import org.apache.commons.validator.*;
import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;

public class ResourceListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClass",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("name", String.class),
          new DynaProperty("group", String.class),
          new DynaProperty("description", String.class),
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

    ResourceListForm searchForm = (ResourceListForm)form;
    String name = (String)searchForm.getValue("name");
    String groupIdStr = (String)searchForm.getValue("resourceGroupId");
    
    
    List resultList;
    Criteria criteria = sess.createCriteria(Resource.class);    
    criteria.addOrder(Order.asc("name"));
        
    if (name != null) {
      name = "%" + name.trim() + "%";
      criteria.add(Expression.like("name", name));      
    }
    
    if (!GenericValidator.isBlankOrNull(groupIdStr)) {
      Long groupId = new Long(groupIdStr);
      criteria.add(Expression.eq("resourceGroup.id", groupId));
    } 

    resultList = criteria.list();

    List resultDynaList = new ArrayList();
    
    for (Iterator it = resultList.iterator(); it.hasNext();) {
      Resource res = (Resource)it.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", res.getResourceId());
      dynaBean.set("name", res.getName());
      dynaBean.set("group", res.getResourceGroup().getName());
      dynaBean.set("description", res.getDescription());
      resultDynaList.add(dynaBean);  
    }

    request.getSession().setAttribute(Constants.RESULT_ID, resultDynaList);

    if (resultList.isEmpty()) {
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

}
