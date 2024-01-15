package ch.ralscha.mycustomers.client;

import ch.ralscha.mycustomers.data.Principal;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface LoginServiceAsync {

  void getPrincipal(AsyncCallback<Principal> callback);

}
