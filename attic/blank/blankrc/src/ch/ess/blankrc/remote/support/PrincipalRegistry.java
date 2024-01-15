
package ch.ess.blankrc.remote.support;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2004/06/05 06:22:02 $
 * 
 * @spring.bean id="principalRegistry"
 */
public class PrincipalRegistry {
  private Map ticketMap;
  
  public PrincipalRegistry() {
    ticketMap = new HashMap();
  }

  public Map getTicketMap() {
    return ticketMap;
  }

  public void setTicketMap(Map ticketMap) {
    this.ticketMap = ticketMap;
  }

  public Principal getPrincipal(String ticket) {
    return (Principal)ticketMap.get(ticket);
  }

  public void addPrincipal(String ticket, Principal principal) {
    ticketMap.put(ticket, principal);
  }
  
  public void removeTicket(String ticket) {
    ticketMap.remove(ticket);
  }
}