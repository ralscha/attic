package ch.ess.ex4u.shared;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ZielgefaessServiceRequestAsync {

  void fetch(Long vertragId, AsyncCallback<ArrayList<ZielgefaessRPC>> callback);

  void fetchAsValueMap(Long vertragId, AsyncCallback<HashMap<String, String>> callback);

  void remove(Long zielgefaessId, AsyncCallback<Boolean> callback);

  void update(ZielgefaessRPC zielgefaess, AsyncCallback<Boolean> callback);

  void add(ZielgefaessRPC zielgefaess, AsyncCallback<Boolean> callback);

}
