package ch.ess.cal.web.app;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.security.SecurityUtil;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $ 
 * 
 * @struts.action path="/logout"
 * @struts.action-forward name="success" path="/logout.jsp"
 */
public class LogoutAction extends FWAction {

  public void doExecute(final ActionContext ctx) throws Exception {
    SecurityUtil.unregisterPrincipal(ctx.session());

    ctx.session().invalidate();

    ctx.forwardByName("success");
  }

}
