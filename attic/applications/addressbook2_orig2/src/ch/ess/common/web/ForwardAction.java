package ch.ess.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2003/11/11 19:17:17 $  
  * 
  * @struts.action path="/forward" name="mapForm" scope="request" validate="false"
  */

public class ForwardAction extends Action {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    MapForm mapForm = (MapForm)form;
    String path = (String)mapForm.getValue("path");

    ActionForward forward = new ActionForward(path);
    forward.setRedirect(true);
    
    return forward;
    
  }
}
