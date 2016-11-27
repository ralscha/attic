
package @packageProject@.control {
  import @packageProject@.component.StatusEnum;
  import @packageProject@.entity.User;
  
  import com.hurlant.crypto.Crypto;
  import com.hurlant.crypto.hash.IHash;
  import com.hurlant.util.Hex;
  
  import flash.utils.ByteArray;
  
  import flexed.widgets.alerts.CAlert;
  
  import mx.collections.ArrayCollection;
  import mx.collections.ItemResponder;
  import mx.collections.ListCollectionView;
  import mx.collections.errors.ItemPendingError;
  import mx.controls.Alert;
  import mx.events.CloseEvent;
  import mx.logging.ILogger;
  import mx.logging.Log;
  import mx.resources.ResourceManager;
  
  import org.granite.tide.events.TideResultEvent;
  import org.granite.tide.seam.Context;

  [Bindable]
  [Name("userCtl")]
  public class UserCtl {

    private static var log:ILogger = Log.getLogger("@packageProject@.control.UserCtl");

    [In]
    public var context:Context;

    [In]
    public var userHome:Object;
    
    [In]
    public var exampleUser:User;        
    
    [In] 
    public var users:ListCollectionView;
        
    [Out]
    public var selectedRolesAC:ArrayCollection;

    [Out]
    public var rolesAC:ArrayCollection;
    

    [Observer("searchUser")]
    public function search(searchText:String):void {
      exampleUser.name = searchText;      
      users.refresh();
    }

    [Observer("newUser")]
    public function newUser():void {
      userHome.id = null; 
      userHome.instance = new User();
      userHome.instance.roles = new ArrayCollection(); 
      selectedRolesAC = new ArrayCollection();
      switchToEdit();
    }

    [Observer("editUser")]
    public function editUser(selectedUser:User):void {
      if (selectedUser) {
        userHome.id =  selectedUser.id;
        userHome.instance =  selectedUser;
        copySelectedRoles();   
        switchToEdit();                     
      }
    }
    
    private function copySelectedRoles():void {
      try {
        selectedRolesAC = new ArrayCollection();
        for(var i:uint = 0; i <  userHome.instance.roles.length; i++) {
          selectedRolesAC.addItem(userHome.instance.roles.getItemAt(i));        
        }
      } catch (e:ItemPendingError) {
        e.addResponder(new ItemResponder(
        function (result:Object, token:Object = null):void {
          copySelectedRoles();
        },
        function (fault:Object, token:Object = null):void {
          log.error("Error while loading collection: {0}", fault.toString());
        }));          
      }
    }

   [Observer("updateUser")]
    public function update():void {
      userHome.id = userHome.id;
      userHome.instance = userHome.instance;
      
      if (userHome.id) {
        userHome.update(onUpdate);
      } else {
        userHome.persist(onPersist);
      }
    }
    
    private function onUpdate(event:TideResultEvent):void {
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Save_successful"), StatusEnum.SUCCESSFUL);
      switchToList();
    }  
    
    private function onPersist(event:TideResultEvent):void {
      users.refresh(); 
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Save_successful"), StatusEnum.SUCCESSFUL);
      switchToList();
    }         
    
    [Observer("cancelUser")]
    public function cancel():void {            
      userHome.instance = null;
      userHome.id = null;
      
      switchToList();    
    }

    [Observer("deleteUser")]
    public function askDeleteUser(selectedUser:User):void {
      if (selectedUser) {
        userHome.id = selectedUser.id;
        userHome.instance = selectedUser; 

        switchToList();
              
        CAlert.confirm(ResourceManager.getInstance().getString("user", "DeleteQuestion"), deleteUser);
      }
    }
        
    private function deleteUser(event:CloseEvent):void {
      if (event.detail == Alert.YES) { 
        
        userHome.instance.roles = new ArrayCollection(); 
        
        userHome.remove(onRemove);
      }
    }

    private function onRemove(event:TideResultEvent):void {
      userHome.id = null;
      userHome.instance = null;                       
      users.refresh(); 

      context.raiseEvent("removedUser");
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Delete_successful"), StatusEnum.SUCCESSFUL);
      switchToList();
    }
    

    [Observer("initRoles")]
    public function initRoles():void {
      userHome.findAllRoles(onFindAllRoles);
    }
    
    [Observer("newLocale")]
    public function newLocale(locale:String):void {
      userHome.updateLocale(locale, onUpdateLocale);
    }
    
    private function onUpdateLocale(e:TideResultEvent):void {
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Save_successful"), StatusEnum.SUCCESSFUL);
    }


    [Observer("newPassword")]
    public function newPassword(newPassword:String):void {
      var hash:IHash = Crypto.getHash("sha1");
      var result:ByteArray = hash.hash(Hex.toArray(Hex.fromString(newPassword)));
      var passwordHash:String = Hex.fromArray(result);      
      
      userHome.updatePassword(passwordHash, onUpdatePassword);
    }

    private function onUpdatePassword(e:TideResultEvent):void {
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("configuration", "Password_changed"), StatusEnum.SUCCESSFUL);
      context.raiseEvent("passwordChanged");
    }
    
    private function onFindAllRoles(e:TideResultEvent):void {
      rolesAC = ArrayCollection(e.result);
    }

    private function switchToList():void {
      context.raiseEvent("switchToUserList");
    }

    private function switchToEdit():void {
      context.raiseEvent("switchToUserEdit");
    }
    
  }
}