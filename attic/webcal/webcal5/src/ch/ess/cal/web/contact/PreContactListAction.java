package ch.ess.cal.web.contact;

import ch.ess.base.annotation.spring.SpringAction;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.web.CrumbsUtil;
import ch.ess.cal.web.time.TimeListForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;

@SpringAction
@StrutsAction(path = "/preContactList", 
roles = "$contact", 
input = "/contactlist.jsp", 
scope = ActionScope.SESSION, 
forwards = @Forward(name = "success", path = "/contactList.do"))
public class PreContactListAction extends FWAction {


  @Override
public void doExecute(ActionContext ctx) throws Exception {

    CrumbsUtil.updateCallStack(this, ctx, "/contactList");
    CrumbsUtil.updateCrumbs(ctx);
    ctx.forwardByName("success");

  }

}
