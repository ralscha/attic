package ch.ess.hgtracker.web.spieler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SpielerNewAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    SpielerForm spielerform = (SpielerForm)form;
    spielerform.setAktiv(Boolean.TRUE);

    return mapping.getInputForward();
  }
}
