package ch.ess.ex4u.shared;

import java.util.ArrayList;
import java.util.Map;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// client data request interface

@RemoteServiceRelativePath("springGwtServices/serviceRequestImpl")

public interface ServiceRequest extends RemoteService {
  
  // ---------- user/roles ----------

  public ArrayList<Map<String, String>> getAssignedRollen(Long userId);

  public ArrayList<Map<String, String>> getUnassignedRollen(Long userId);

  public Boolean assignRolle(Long userId, Long roleId);
  
  public Boolean deassignRolle(Long userId, Long roleId);
  
  public Boolean setAssignedRollen(Long userId, ArrayList<Map<String, String>> roles);

  // ---------- contract/users ----------

  public ArrayList<Map<String, String>> getAssignedVertragEmas(Long vId);

  public ArrayList<Map<String, String>> getUnassignedVertragEmas(Long vId);

  public Boolean assignVertragEma(Long vId, Long uId);
  
  public Boolean deassignVertragEma(Long vId, Long uId);

  // ---------- contract/bins ----------

  public ArrayList<Map<String, String>> getAssignedVertragGefaesse(Long vId);

  public ArrayList<Map<String, String>> getUnassignedVertragGefaesse(Long vId);

  public Boolean assignVertragGefaess(Long vId, Long gId);
  
  public Boolean deassignVertragGefaess(Long vId, Long gId);
}
