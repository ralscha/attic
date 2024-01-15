package ch.ess.base.web.login;

import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.security.SecurityUtil;

@StrutsAction(path = "/logout", forwards = @Forward(name = "success", path = "/logout.jsp"))
public class LogoutAction extends FWAction {

  public void doExecute(final ActionContext ctx) throws Exception {
    SecurityUtil.unregisterPrincipal(ctx.session());

    ctx.session().invalidate();

    ctx.forwardByName("success");
  }

}