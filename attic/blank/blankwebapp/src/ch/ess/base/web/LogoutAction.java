package ch.ess.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.annotation.Forward;
import org.apache.struts.annotation.StrutsAction;

import ch.ess.base.Constants;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/07/03 10:25:05 $ 
 */
@StrutsAction(path = "/logout", forwards = @Forward(name = "success", path = "/logout.jsp"))
public class LogoutAction extends Action {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    request.getSession().removeAttribute(Constants.USER_SESSION);
    request.getSession().invalidate();
    return mapping.findForward("success");
  }

}