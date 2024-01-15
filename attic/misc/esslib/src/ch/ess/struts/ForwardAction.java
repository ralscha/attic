package ch.ess.struts;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.util.*;

public class ForwardAction extends Action {

  // ----------------------------------------------------- Instance Variables

  /**
   * The message resources for this package.
   */
  private static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.actions.LocalStrings");

  // --------------------------------------------------------- Public Methods

  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it).
   * Return an <code>ActionForward</code> instance describing where and how
   * control should be forwarded, or <code>null</code> if the response has
   * already been completed.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @exception Exception if an error occurs
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    // Create a RequestDispatcher the corresponding resource
    String path = mapping.getParameter();

    if (path == null) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, messages.getMessage("forward.path"));
      return (null);
    }

    // Let the controller handle the request
    ActionForward retVal = new ActionForward(path);

    return retVal;
  }

}
