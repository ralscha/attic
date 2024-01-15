package ch.ess.ex4u.shared;

import java.util.ArrayList;
import java.util.Map;
import com.google.gwt.user.client.rpc.AsyncCallback;

// client data response callback interface 

public interface ServiceRequestAsync {
  
  // ---------- user/roles ----------

  void getAssignedRollen(Long userId, AsyncCallback<ArrayList<Map<String, String>>> callback);

  void getUnassignedRollen(Long userId, AsyncCallback<ArrayList<Map<String, String>>> callback);

  void assignRolle(Long userId, Long roleId, AsyncCallback<Boolean> callback);

  void deassignRolle(Long userId, Long roleId, AsyncCallback<Boolean> callback);

  void setAssignedRollen(Long userId, ArrayList<Map<String, String>> roles, AsyncCallback<Boolean> callback);

  // ---------- contract/users ----------

  void getAssignedVertragEmas(Long vId, AsyncCallback<ArrayList<Map<String, String>>> callback);

  void getUnassignedVertragEmas(Long vId, AsyncCallback<ArrayList<Map<String, String>>> callback);

  void assignVertragEma(Long vId, Long uId, AsyncCallback<Boolean> callback);

  void deassignVertragEma(Long vId, Long uId, AsyncCallback<Boolean> callback);


  // ---------- contract/bins ----------

  void getAssignedVertragGefaesse(Long vId, AsyncCallback<ArrayList<Map<String, String>>> callback);

  void getUnassignedVertragGefaesse(Long vId, AsyncCallback<ArrayList<Map<String, String>>> callback);

  void assignVertragGefaess(Long vId, Long gId, AsyncCallback<Boolean> callback);
  
  void deassignVertragGefaess(Long vId, Long gId, AsyncCallback<Boolean> callback);
}
