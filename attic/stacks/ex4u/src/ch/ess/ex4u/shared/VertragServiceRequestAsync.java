package ch.ess.ex4u.shared;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface VertragServiceRequestAsync {

  void fetch(Long userId, AsyncCallback<ArrayList<VertragRPC>> callback);

  void fetchAsValueMap(Long userId, AsyncCallback<HashMap<String, String>> callback);

  void update(VertragRPC vertrag, AsyncCallback<Boolean> callback);

  void add(VertragRPC vertrag, AsyncCallback<Boolean> callback);

  void remove(Long vertragId, AsyncCallback<Boolean> callback);
}
