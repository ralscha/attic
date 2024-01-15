package ch.ess.cal.web.event;

import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.web.CrumbsUtil;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;

@StrutsAction(path = "/preImport", roles = "$event", 
              forwards = @Forward(name = "success", path = "/importical.jsp"))
public class PreImportAction extends FWAction {

  public void doExecute(ActionContext ctx) throws Exception {

    CrumbsUtil.updateCallStack(this, ctx, "/importIcal");
    CrumbsUtil.updateCrumbs(ctx);
    ctx.forwardByName("success");

  }

}
