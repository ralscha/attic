package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 12:24:24 $  
 * 
 * @struts.action path="/forward" name="forwardForm" scope="request" validate="false"
 */

public class ForwardAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    ForwardForm fForm = (ForwardForm) form;

    ActionForward forward = new ActionForward(fForm.getPath());
    forward.setRedirect(true);

    return forward;

  }
}