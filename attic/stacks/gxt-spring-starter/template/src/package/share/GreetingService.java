package @packageProject@.share;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("springGwtServices/greet")
public interface GreetingService extends RemoteService {
  String greetServer(String name);
}
