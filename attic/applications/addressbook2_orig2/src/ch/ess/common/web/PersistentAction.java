package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.db.Persistent;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.9 $ $Date: 2003/11/11 19:17:17 $ 
  */
public abstract class PersistentAction extends DispatchAction {

  protected abstract Persistent loadPersistent(Long id) throws Exception ;
  protected abstract int deletePersistent(Long id) throws Exception ;
  protected abstract Persistent newPersistent() throws Exception ;
  
  
  protected ActionForward store() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    PersistentForm persistentForm = (PersistentForm)ctx.getForm();

    if (isCancelled(ctx.getRequest())) {
      return findForward(Constants.FORWARD_LIST);
    }

    Persistent persistent = persistentForm.getPersistentFromForm();
    
    ActionForward forward = save(persistent);
    if (forward == null) {
      return findForward(Constants.FORWARD_LIST);
    } else {
      return forward;
    }

  }

  protected ActionForward delete() throws Exception {

    HttpServletRequest request = WebContext.currentContext().getRequest();
    
    Long id = null;
    String idStr = request.getParameter(Constants.OBJECT_ID);
    
    if (!GenericValidator.isBlankOrNull(idStr)) {
      id = new Long(idStr);
    }

    int deleted = deletePersistent(id);

    if (deleted > 0) {
      addOneMessageSession(Constants.ACTION_MESSAGE_DELETE_OK);
    }

    return findForward(Constants.FORWARD_LIST);

  }

  protected ActionForward edit() throws Exception {

    PersistentForm persistentForm = (PersistentForm)WebContext.currentContext().getForm();
    Long id = getId();
    persistentForm.initForm();
    persistentForm.setPersistentToForm(loadPersistent(id));

    return findForward(Constants.FORWARD_EDIT);

  }

  protected ActionForward add() throws Exception {

    PersistentForm persistentForm = (PersistentForm)WebContext.currentContext().getForm();
    
    persistentForm.initForm();
    persistentForm.setPersistentToForm(newPersistent());

    return findForward(Constants.FORWARD_EDIT);

  }


}