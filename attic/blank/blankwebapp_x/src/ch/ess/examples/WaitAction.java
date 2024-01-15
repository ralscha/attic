package ch.ess.examples;

import org.apache.struts.action.ActionForward;

import ch.ess.common.Constants;
import ch.ess.common.web.BaseAction;
import ch.ess.common.web.WebContext;

public class WaitAction extends BaseAction {

  public ActionForward execute() throws Exception {

    WaitActionForm waitForm = (WaitActionForm) WebContext.currentContext().getForm();

    Thread.sleep(Math.abs(waitForm.getSeconds()) * 1000);

    return findForward(Constants.FORWARD_SUCCESS);

  }

}