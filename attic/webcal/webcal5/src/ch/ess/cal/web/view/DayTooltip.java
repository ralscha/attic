package ch.ess.cal.web.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;



@RemoteProxy(name = "dayTooltip")
public class DayTooltip {
  
  @RemoteMethod
  public TooltipResult getTooltip(int day, String id, HttpSession session) {
    
    GroupMonthForm form = (GroupMonthForm)session.getAttribute("GroupMonthForm");
    List<UserEvents> userEvents = form.getUsers();
    UserEvents userEvent = null;
    for (UserEvents event : userEvents) {
      if (event.getId().toString().equals(id)) {
        userEvent = event;
        break;
      }
    }
    
    
    TooltipResult result = new TooltipResult();
    if (userEvent != null) {
    
      result.setDay(day);
      result.setTooltip(userEvent.getTooltip()[day]);
      result.setWidth(String.valueOf(userEvent.getTooltipWidth()[day]));
      result.setTitle(form.getDaytitle()[day]);
            
    }
    
    return result;
  }
}
