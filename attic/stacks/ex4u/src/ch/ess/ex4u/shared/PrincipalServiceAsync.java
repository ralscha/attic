package ch.ess.ex4u.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface PrincipalServiceAsync {

  void getPrincipal(AsyncCallback<PrincipalDetail> callback);

}
