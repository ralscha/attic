package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;

import ch.ess.base.Constants;
import ch.ess.base.model.BaseObject;
import ch.ess.base.persistence.Dao;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:14 $
 */
public abstract class DispatchAction<T extends BaseObject> extends AbstractAction {

  private static final String EDIT_ACTION = "edit";
  private static final String ADD_ACTION = "add";
  private static final String STORE_ACTION = "store";
  private static final String STOREADD_ACTION = "storeadd";
  private static final String DELETE_ACTION = "delete";

  private Dao<T> dao;

  protected Dao<T> getDao() {
    return dao;
  }
  
  @Override
  public ActionForward execute() throws Exception {

    WebContext ctx = WebContext.currentContext();
    
    ActionForward forward = null;


    String parameter = ctx.getMapping().getParameter();


      if (EDIT_ACTION.equals(parameter)) {
        forward = edit();
      } else if (ADD_ACTION.equals(parameter)) {
        forward = add();
      } else if (STORE_ACTION.equals(parameter)) {

        if (StringUtils.isNotBlank(ctx.getRequest().getParameter(DELETE_ACTION))) {
          return ctx.findForward("delete");
        }

        String storeAddAction = ctx.getRequest().getParameter(STOREADD_ACTION);
        if (StringUtils.isNotBlank(storeAddAction)) {
          forward = storeAndAdd();
        } else {
          forward = store();
        }


      } else if (DELETE_ACTION.equals(parameter)) {
        forward = delete();
      }


    return forward;
  }

  protected ActionForward storeAndAdd() throws Exception {

    WebContext ctx = WebContext.currentContext();
    ActionForward forward = store();
    
    //if there are no errors goto add
    ActionErrors errors = (ActionErrors) ctx.getRequest().getAttribute(Globals.ERROR_KEY);

    if ((errors == null) || (errors.isEmpty())) {
      clearForm(ctx.getForm());
      forward = add();
    }

    return forward;
  }

  protected abstract ActionForward store() throws Exception;

  protected abstract ActionForward delete() throws Exception;

  protected abstract ActionForward edit() throws Exception;

  protected abstract ActionForward add() throws Exception;

  protected Long getId(HttpServletRequest request) throws NumberFormatException {

    String idStr = request.getParameter(Constants.OBJECT_ID);

    Long userId = null;

    if (idStr != null) {
      userId = new Long(idStr);
    } else {
      //concurrent access
      HttpSession session = request.getSession();
      userId = (Long) session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
    }
    return userId;
  }

  protected void save(T baseObject) {
    dao.saveOrUpdate(baseObject);
    addOneMessageSession(Constants.ACTION_MESSAGE_UPDATE_OK);
  }
  
  protected void clearForm(final ActionForm form) {

    try {
      ActionForm emptyForm = form.getClass().newInstance();
      PropertyUtils.copyProperties(form, emptyForm);
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("clearForm", e);
    }
  }

}