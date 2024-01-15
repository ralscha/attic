package ch.ess.base.web;

import ch.ess.base.annotation.spring.Autowire;
import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.annotation.struts.StrutsActions;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.ui.control.ControlActionContext;

@SpringAction(autowire = Autowire.BYNAME)
@StrutsActions(actions = {
    @StrutsAction(path = "/administration", forwards = @Forward(name = "success", path = "/menuinfo.jsp")),
    @StrutsAction(path = "/time", forwards = @Forward(name = "success", path = "/menuinfo.jsp")),
    @StrutsAction(path = "/calendar", forwards = @Forward(name = "success", path = "/menuinfo.jsp"))})
    
public class MenuInfoAction extends FWAction {

  public void doExecute(final ActionContext ctx) throws Exception {
    ctx.request().setAttribute("menuInfoDisplay", ctx.mapping().getPath());
    CrumbsUtil.updateCallStack(this, ctx);
    CrumbsUtil.updateCrumbs(ctx);
    ctx.forwardByName("success");
  }
  
  public void onCrumbClick(ControlActionContext ctx, String key) throws Exception {
    ctx.request().setAttribute("menuInfoDisplay", ctx.mapping().getPath());
    CrumbsUtil.updateCallStack(this, ctx);
    CrumbsUtil.updateCrumbs(ctx);
    ctx.forwardByName("success");
  }
}
