package ch.ess.ex4u.shared;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// client data request interface

@RemoteServiceRelativePath("springGwtServices/zielgefaessServiceRequestImpl")
public interface ZielgefaessServiceRequest extends RemoteService {

  public ArrayList<ZielgefaessRPC> fetch(Long vertragId);

  public HashMap<String, String> fetchAsValueMap(Long vertragId);

  public Boolean update(ZielgefaessRPC zielgefaess);

  public Boolean add(ZielgefaessRPC zielgefaess);

  public Boolean remove(Long zielgefaessId);

}
