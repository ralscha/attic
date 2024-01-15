package ch.ess.ex4u.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/principalServiceImpl")
public interface PrincipalService extends RemoteService {

  PrincipalDetail getPrincipal() ;
  
}
