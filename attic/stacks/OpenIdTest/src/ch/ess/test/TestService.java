package ch.ess.test;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.granite.context.GraniteContext;
import org.granite.messaging.webapp.HttpGraniteContext;


public class TestService {
  public void addSessionVariables(Map<String,Object> variables) {
    for (Map.Entry<String,Object> entry : variables.entrySet()) {
      getSession().setAttribute(entry.getKey(), entry.getValue());
    }
  }
  
  @SuppressWarnings("unchecked")
  public Map<String,Object> getIdentifier() {    
    Map<String,Object> result = (Map<String,Object>)getSession().getAttribute("openid.result");    
    getSession().removeAttribute("openid.result");
    return result;
  }
  
  private HttpSession getSession() {
    return ((HttpGraniteContext)GraniteContext.getCurrentInstance()).getSession();
  }
}
