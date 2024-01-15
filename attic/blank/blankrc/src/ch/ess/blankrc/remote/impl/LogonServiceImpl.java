package ch.ess.blankrc.remote.impl;

import org.apache.commons.codec.digest.DigestUtils;

import ch.ess.blankrc.model.User;
import ch.ess.blankrc.remote.LogonService;
import ch.ess.blankrc.remote.support.Principal;
import ch.ess.blankrc.remote.support.PrincipalRegistry;
import ch.ess.blankrc.service.UserService;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2004/06/05 06:22:02 $
 * 
 * @spring.bean id="logonService"
 * @spring.property name="principalRegistry" reflocal="principalRegistry"
 * @spring.property name="userService" reflocal="userService"
 */

public class LogonServiceImpl implements LogonService {

  private PrincipalRegistry principalRegistry;
  private UserService userService;
  
  public void logoff(String userName, String ticket) {
    
    if ((ticket == null) || (userName == null))  {
      return;
    }
    
    Principal principal = principalRegistry.getPrincipal(ticket);
    if (principal != null) {
      if (userName.equals(principal.getUserName())) {
        principalRegistry.removeTicket(ticket);
      }
    }
    
  }
  
  public String logon(String userName, String token) {
    User user = userService.find(userName, token);
    
    if (user != null) {
      String ticket = createTicket(user);
      
      Principal principal = new Principal();
      principal.setUserName(user.getUserName());
      principal.setPermissions(userService.getPermission(user.getId()));
      
      principalRegistry.addPrincipal(ticket, principal);
      return ticket;
    }
    
    return null;
  }
  
  private String createTicket(User user) {

    StringBuffer sb = new StringBuffer(20);
    sb.append(user.getUserName());
    sb.append(user.getPasswordHash());
    sb.append(System.currentTimeMillis());

    return DigestUtils.md5Hex(sb.toString());    
  }
  
  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  public void setPrincipalRegistry(PrincipalRegistry principalRegistry) {
    this.principalRegistry = principalRegistry;
  }

}