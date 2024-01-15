package ch.ess.addressbook.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.addressbook.db.User;
import ch.ess.common.Constants;
import ch.ess.common.web.DispatchAction;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  * 
  * @struts.action path="/editUser" name="userForm" input=".user.list" scope="session" validate="false" roles="admin"
  * @struts.action path="/storeUser" name="userForm" input=".user.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteUser" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".user.edit"
  * @struts.action-forward name="list" path="/listUser.do"
  * @struts.action-forward name="delete" path="/deleteUser.do" 
  * @struts.action-forward name="reload" path="/editUser.do?action=edit" 
  */
public class UserEditAction extends DispatchAction {

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    UserForm userForm = (UserForm)form;

    User newUser = new User();
    userForm.setUser(newUser, getLocale(request), getResources(request));

    return mapping.findForward(Constants.FORWARD_EDIT);
  }

  protected ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    Long userId = new Long(request.getParameter(Constants.OBJECT_ID));
    
    int deleted = User.delete(userId);

    if (deleted > 0) {
      addOneMessage(request, Constants.ACTION_MESSAGE_DELETE_OK);
    }

    return mapping.findForward(Constants.FORWARD_LIST);

  }

  protected ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    UserForm userForm = (UserForm)form;
    Long userId = getId(request);

    User user = User.load(userId);
    userForm.setUser(user, getLocale(request), getResources(request));

    return mapping.findForward(Constants.FORWARD_EDIT);
  }

  protected ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Transaction tx)
    throws Exception {

    UserForm userForm = (UserForm)form;

    if (isCancelled(request)) {
      return mapping.findForward(Constants.FORWARD_LIST);
    }

    ActionForward forward = save(mapping, request, tx, userForm.getUser());
    if (forward == null) {
      return mapping.findForward(Constants.FORWARD_LIST);
    } else {
      return forward;
    }

  }

}