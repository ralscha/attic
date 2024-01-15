package ch.ess.cal.web.view;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.cal.service.EventDistribution;

@StrutsAction(path = "/eventPic")
public class EventPictureAction extends Action {


  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    String userIdStr = request.getParameter("u");
    String dayStr = request.getParameter("d");

    GroupMonthForm gmf = (GroupMonthForm)request.getSession().getAttribute("GroupMonthForm");
    Map<String, Map<String, EventDistribution>> userEvents = gmf.getUserEvents();
    Map<String, EventDistribution> eventsMap = userEvents.get(userIdStr);
    if (eventsMap != null) {
      EventDistribution ed = eventsMap.get(dayStr);

      if (ed != null) {
        OutputStream out = response.getOutputStream();
        response.setContentType("image/gif");
        out.write(ed.getImage());
        out.flush();
        out.close();
      }
    }

    return null;
  }

}
