package ch.ess.ex4u.shared;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.AsyncCallback;

// client data response callback interface 

public interface ZeitServiceRequestAsync {

  void fetch(Long userId, AsyncCallback<ArrayList<ZeitRPC>> callback);

  void add(ZeitRPC zeit, AsyncCallback<Boolean> callback);

  void remove(Long zeitId, AsyncCallback<Boolean> callback);

  void update(ZeitRPC zeit, AsyncCallback<Boolean> callback);

}
