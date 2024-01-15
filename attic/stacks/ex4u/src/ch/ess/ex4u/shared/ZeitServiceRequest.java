package ch.ess.ex4u.shared;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// client data request interface

@RemoteServiceRelativePath("springGwtServices/zeitServiceRequestImpl")
public interface ZeitServiceRequest extends RemoteService {

  public ArrayList<ZeitRPC> fetch(Long userId);

  public Boolean update(ZeitRPC zeit);

  public Boolean add(ZeitRPC zeit);

  public Boolean remove(Long zeitId);

}
