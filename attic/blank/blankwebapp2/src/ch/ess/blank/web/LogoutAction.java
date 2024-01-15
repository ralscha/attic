package ch.ess.blank.web;

import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.web.BaseAction;
import ch.ess.common.web.WebContext;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:13 $ 
 * 
 * @struts.action path="/logout"
 * @struts.action-forward name="success" path=".logout"
 */
public class LogoutAction extends BaseAction {

  public ActionForward execute() throws Exception {

    WebContext.currentContext().getSession().invalidate();

    return findForward(Constants.FORWARD_SUCCESS);
  }

}