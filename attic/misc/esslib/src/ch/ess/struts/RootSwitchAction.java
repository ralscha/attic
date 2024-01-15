package ch.ess.struts;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.apache.struts.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;
import org.apache.struts.util.*;

public class RootSwitchAction extends Action {

  /**
   * Commons Logging instance.
   */
  private static Log log = LogFactory.getLog(SwitchAction.class);

  /**
   * The message resources for this package.
   */
  private static MessageResources messages = MessageResources.getMessageResources("org.apache.struts.actions.LocalStrings");

  // --------------------------------------------------------- Public Methods

  /**
   * Process the specified HTTP request, and create the corresponding HTTP
   * response (or forward to another web component that will create it),
   * with provision for handling exceptions thrown by the business logic.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The HTTP response we are creating
   *
   * @exception Exception if the application business logic throws
   *  an exception
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    // Identify the request parameters controlling our actions
    String page = mapping.getParameter();
    String prefix = "";
    
    if ((page == null) || (prefix == null)) {
      String message = messages.getMessage("switch.required");
      log.error(message);
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
      return (null);
    }

    // Switch to the requested application module
    RequestUtils.selectModule(prefix, request, getServlet().getServletContext());
    if (request.getAttribute(Globals.MODULE_KEY) == null) {
      String message = messages.getMessage("switch.prefix", prefix);
      log.error(message);
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
      return (null);
    }

    // Forward control to the specified module-relative URI
    return (new ActionForward(page));

  }

}


