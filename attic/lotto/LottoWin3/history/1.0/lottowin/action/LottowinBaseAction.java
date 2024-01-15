

package lottowin.action;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import com.objectmatter.bsf.*;
import org.log4j.*;
import lottowin.db.*;

public class LottowinBaseAction extends Action {

  protected HttpSession session;
  protected Database db;
  protected User user;
  protected static Category CAT = null;
  protected boolean shouldHaveUser = true;

  public LottowinBaseAction() {
    CAT = Category.getInstance(getClass().getName());
  }

  public ActionForward preProcessing(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {

    session = request.getSession();
    NDC.push(session.getId());

    db = (Database)session.getAttribute(lottowin.Constants.DB_KEY);

    user = (User)session.getAttribute(lottowin.Constants.USER_KEY);
    if ((user == null) && (shouldHaveUser)) {
      CAT.warn("user object not in session");
      return (mapping.findForward("logon"));
    }

    return null; // execute perform method

  }

  
  public void postProcessing(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) 
                               throws IOException, ServletException {

    NDC.pop();
  }




}
