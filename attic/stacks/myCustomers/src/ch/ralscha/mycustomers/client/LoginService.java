package ch.ralscha.mycustomers.client;

import ch.ralscha.mycustomers.data.Principal;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/loginService")
public interface LoginService extends RemoteService {
  Principal getPrincipal();
}
