package ch.ess.cal.web.test;

import org.apache.cactus.WebRequest;

import ch.ess.cal.db.User;
import ch.ess.cal.web.user.UserForm;
 
/**
 * @author sr
 */
public class UserEditTest extends BaseStrutsTestCase {

  public UserEditTest(String name) {
    super(name);
  }
  
  public void beginCancel(WebRequest theRequest) {
    authenticate(theRequest);
  }  
  public void testCancel() throws Exception {
          
    setRequestPathInfo("/storeUser");
    
    addRequestParameter("org.apache.struts.taglib.html.CANCEL", "Cancel");
    actionPerform();
    
    verifyForward("list");
    verifyNoActionErrors();
    verifyNoActionMessages();    
  }  


  public void beginNew(WebRequest theRequest) {
    authenticate(theRequest);
  }  
  public void testNew() throws Exception {
    
    setRequestPathInfo("/newUser");
        
    actionPerform();
    
    UserForm form = (UserForm)getActionForm();
    assertNotNull(form);
    
    verifyTilesForward("edit", ".user.edit");
    verifyNoActionErrors();
    verifyNoActionMessages();    
  }  
  
  public void beginEdit(WebRequest theRequest) {
    authenticate(theRequest);
  }  
  public void testEdit() throws Exception {
        
    setRequestPathInfo("/editUser");
    User user = User.find("admin");
    
    addRequestParameter("id", user.getId().toString());
    actionPerform();
    
    UserForm form = (UserForm)getActionForm();
    assertNotNull(form);
    
    
    assertEquals(form.getFirstName(), user.getFirstName());
    assertEquals(form.getName(), user.getName());
    assertEquals(form.getUserName(), user.getUserName());
    assertEquals(form.getId(), user.getId());    
    assertEquals(form.getPassword(), "hasPassword");
    assertEquals(form.getRetypePassword(), "hasPassword");
    assertEquals(form.getLocaleStr(), user.getLocale().toString());
    
    verifyTilesForward("edit", ".user.edit");
    verifyNoActionErrors();
    verifyNoActionMessages();    
  } 
  
}
