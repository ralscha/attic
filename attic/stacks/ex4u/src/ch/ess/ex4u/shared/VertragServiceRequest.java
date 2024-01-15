package ch.ess.ex4u.shared;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// client data request interface

@RemoteServiceRelativePath("springGwtServices/vertragServiceRequestImpl")
public interface VertragServiceRequest extends RemoteService {

  public ArrayList<VertragRPC> fetch(Long userId);

  public HashMap<String, String> fetchAsValueMap(Long userId);

  public Boolean update(VertragRPC vertrag);

  public Boolean add(VertragRPC vertrag);

  public Boolean remove(Long vertragId);

}
