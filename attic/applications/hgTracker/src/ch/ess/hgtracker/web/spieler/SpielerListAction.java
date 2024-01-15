package ch.ess.hgtracker.web.spieler;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Spieler;

public class SpielerListAction extends Action {

  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    Session dbSession = (Session)request.getAttribute("dbSession");
    Transaction tx = dbSession.beginTransaction();
    try {
      String webLogin = request.getParameter("webLogin");
      Club club;
      boolean web = false;
      if (StringUtils.isNotBlank(webLogin)) {
        Criteria criteria = dbSession.createCriteria(Club.class);
        criteria.add(Restrictions.eq("webLogin", webLogin));
        club = (Club)criteria.uniqueResult();
        web = true;
      } else {
        club = (Club)request.getSession().getAttribute("club");
      }
      Criteria criteria = dbSession.createCriteria(Spieler.class);
      criteria.add(Restrictions.eq("club", club));
      criteria.addOrder(Order.asc("nachname"));
      criteria.addOrder(Order.asc("vorname"));
      List spielerList = criteria.list();
      if (spielerList.isEmpty()) {
        request.getSession().removeAttribute("spielerList");
      } else {
        request.getSession().setAttribute("spielerList", spielerList);
      }
      if (web) {
        if (request.getParameter("mobile") != null) {
          return mapping.findForward("mobile");
        }
        return mapping.findForward("web");
      } else {
        return mapping.getInputForward();
      }
    } finally {
      tx.commit();
    }
  }
}
