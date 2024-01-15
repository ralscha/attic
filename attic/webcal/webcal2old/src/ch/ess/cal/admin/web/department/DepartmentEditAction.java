package ch.ess.cal.admin.web.department;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.cal.*;
import ch.ess.cal.common.*;
import ch.ess.cal.db.*;
import ch.ess.cal.resource.*;

public class DepartmentEditAction extends DispatchAction {

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages)
    throws Exception {

    DepartmentForm departmentForm = (DepartmentForm)form;

    Department newDepartment = new Department();
    
    Session sess = null;    
    try {
      sess = HibernateManager.open(request);      
      departmentForm.setDepartment(newDepartment, sess, getAdminUser(request), getResources(request));
    } finally {
      HibernateManager.finallyHandling(sess);
    }    
    
    

    return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

  protected ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess)
    throws Exception {

    Long departmentId = new Long(request.getParameter(Constants.OBJECT_ID));
    Department department = null;

    try {
      department = (Department)sess.load(Department.class, departmentId);
    } catch (ObjectNotFoundException onfe) {
      //do nothing
    }

    if (department != null) {
      sess.delete(department);
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_DELETE_OK);
    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess)
    throws Exception {

    DepartmentForm departmentForm = (DepartmentForm)form;
    String idStr = request.getParameter(Constants.OBJECT_ID);

    Long departmentId = null;

    if (idStr != null) {
      departmentId = new Long(idStr);
    } else {
      //concurrent access
      departmentId = (Long)session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_CONCURRENT);
    }

    Department department = (Department)sess.load(Department.class, departmentId);
    departmentForm.setDepartment(department, sess, getAdminUser(request), getResources(request));

    return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

  protected ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess,
    Transaction tx)
    throws Exception {

    DepartmentForm departmentForm = (DepartmentForm)form;

    if (isCancelled(request)) {
      departmentForm.setDepartment(null, getAdminUser(request));
      tx.commit();
      return mapping.findForward(Constants.FORWARD_CANCEL);
    }
    
    Department department = departmentForm.getDepartment(sess);    
    sess.saveOrUpdate(department);

    try {
      tx.commit();
    } catch (StaleObjectStateException sose) {
      // concurrent access
      tx.rollback();
      departmentForm.setDepartment(null, getAdminUser(request));
      session.setAttribute(Constants.CONCURRENT_ID, department.getDepartmentId());
      return mapping.findForward(Constants.FORWARD_RELOAD);
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_UPDATE_OK);
    return mapping.findForward(Constants.FORWARD_SAVEANDBACK);

  }

}