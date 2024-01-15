package ch.ralscha.mycustomers.server;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ch.ralscha.mycustomers.client.LoginService;
import ch.ralscha.mycustomers.data.Principal;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

  @Override
  public Principal getPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Principal principal = new Principal();
    principal.setName(authentication.getName());
    
    return principal;
  }

}
