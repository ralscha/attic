package ch.ess.cal.web.event;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.web.MapForm;

@StrutsAction(path = "/preEventList", roles="$event", form=MapForm.class, input="/eventList.do", 
              scope=ActionScope.SESSION, validate=false)
public class PreEventListAction extends Action {

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    MapForm mapForm = (MapForm)form;

    Calendar cal = new GregorianCalendar();
    mapForm.setValue("month", String.valueOf(cal.get(Calendar.MONTH)));
    mapForm.setValue("year", String.valueOf(cal.get(Calendar.YEAR)));
    return mapping.getInputForward();
  }
}
