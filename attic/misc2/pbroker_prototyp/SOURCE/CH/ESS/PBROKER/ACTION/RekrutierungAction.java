package ch.ess.pbroker.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.MessageResources;
import ch.ess.pbroker.*;
import ch.ess.pbroker.db.*;
import ch.ess.pbroker.session.*;
import com.codestudio.util.*;

public final class RekrutierungAction extends ActionBase {

    public ActionForward perform(ActionServlet servlet,
				 ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
    	throws IOException, ServletException {

      HttpSession session = request.getSession();

      if (session.getAttribute(Constants.USER_KEY) == null)
        return (mapping.findForward("login"));

    
      KandidatenBasket basket = (KandidatenBasket)session.getAttribute(Constants.BASKET_KEY);

      if (basket == null) {
        basket = new KandidatenBasket();
        session.setAttribute(Constants.BASKET_KEY, basket);
      }
    
      Map lieferantenMap = new TreeMap();
      session.setAttribute(Constants.OFFERTANFRAGE_KEY, lieferantenMap);
    
      try {
        Iterator it = basket.getKandidatenIterator();
        while(it.hasNext()) {
          Kandidaten kandidat = (Kandidaten)it.next();
          Lieferanten lieferant = kandidat.getLieferanten();

          List klist = (List)lieferantenMap.get(lieferant.getLieferant());
          if (klist != null) {
            klist.add(kandidat);
          } else {
            klist = new ArrayList();
            klist.add(kandidat);
            lieferantenMap.put(lieferant.getLieferant(), klist);          
          }
        }
      } catch (Exception e) {
        throw new ServletException(e);
      }

      return (mapping.findForward("next"));   


    }


}
