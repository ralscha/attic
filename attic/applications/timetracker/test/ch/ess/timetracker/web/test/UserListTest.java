package ch.ess.timetracker.web.test;

import java.util.Iterator;
import java.util.List;

import org.apache.cactus.WebRequest;
import org.apache.commons.beanutils.DynaBean;

import ch.ess.common.Constants;
import ch.ess.timetracker.db.User;
 
/**
 * @author sr
 */
public class UserListTest extends BaseStrutsTestCase {

  public UserListTest(String name) {
    super(name);
  }
    
  public void beginList(WebRequest theRequest) {
    authenticate(theRequest);
  }  
  public void testList() throws Exception {
    setRequestPathInfo("/listUser");
      
    actionPerform();
    
    List result = (List)session.getAttribute(Constants.RESULT_ID);
    assertEquals(result.size(), 2);
    
    verifyTilesForward("success", ".user.list");
    verifyNoActionErrors();
    verifyNoActionMessages();
    
    for (Iterator it = result.iterator(); it.hasNext(); ) {
      DynaBean dynaBean = (DynaBean) it.next();
      Long id = (Long)dynaBean.get("id");
      User user = User.load(id);
      
      String userName = (String)dynaBean.get("userName");
      String name = (String)dynaBean.get("name");
      String firstName = (String)dynaBean.get("firstName");
      Boolean canDelete = (Boolean)dynaBean.get("canDelete");
      
      assertEquals(userName, user.getUserName());
      assertEquals(name, user.getName());
      assertEquals(firstName, user.getFirstName());
      assertEquals(canDelete.booleanValue(), user.canDelete());
      
    }   
  }
  

  public void beginList2(WebRequest theRequest) {
    authenticate(theRequest);
  }  
  public void testList2() throws Exception {
    setRequestPathInfo("/listUser");
    addRequestParameter("value(searchText)", "admin");
    
    actionPerform();
    
    List result = (List)session.getAttribute(Constants.RESULT_ID);
    assertEquals(result.size(), 1);
    
    verifyTilesForward("success", ".user.list");
    verifyNoActionErrors();
    verifyNoActionMessages();
  }
  
  
  public void beginList3(WebRequest theRequest) {
    authenticate(theRequest);
  }  
  public void testList3() throws Exception {
    setRequestPathInfo("/listUser");
    addRequestParameter("value(searchText)", "xy");
    
    actionPerform();
    
    verifyActionMessages(new String[]{"common.noResultsfound"});
    
    List result = (List)session.getAttribute(Constants.RESULT_ID);
    assertEquals(result.size(), 0);
    
    verifyTilesForward("success", ".user.list");
  }
  

}
