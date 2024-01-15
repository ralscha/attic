package ch.ess.hgtracker.web.login;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import ch.ess.hgtracker.db.Club;

public class LoginAction extends Action {

  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    LoginForm loginform = (LoginForm)form;
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      Criteria criteria = dbSession.createCriteria(Club.class);
      criteria.add(Restrictions.eq("benutzername", loginform.getBenutzername()));
      criteria.add(Restrictions.eq("passwort", loginform.getPasswort()));
      List<Club> clubList = criteria.list();
      if (clubList.size() == 0) {
        // club nicht gefunden, fehlermeldung herausschreien
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("login.failed"));
        saveErrors(request, messages);
        return mapping.getInputForward();
      } else {
        // club gefunden
        Club club = clubList.get(0);
        request.getSession().setAttribute("club", club);
        return mapping.findForward("weiter");
      }
    } finally {
      tx.commit();
    }
  }
}
