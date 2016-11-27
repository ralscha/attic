package @packageProject@.control {
  import @packageProject@.component.StatusEnum;
  import @packageProject@.entity.User;
  
  import com.hurlant.crypto.Crypto;
  import com.hurlant.crypto.hash.IHash;
  import com.hurlant.util.Hex;
  
  import flash.utils.ByteArray;
  
  import flexed.widgets.alerts.CAlert;
  
  import mx.collections.ArrayCollection;
  import mx.controls.Alert;
  import mx.events.CloseEvent;
  import mx.events.CollectionEvent;
  import mx.events.CollectionEventKind;
  import mx.logging.ILogger;
  import mx.logging.Log;
  import mx.resources.ResourceManager;
  
  import org.granite.tide.collections.PersistentCollection;
  import org.granite.tide.events.TideResultEvent;
  import org.granite.tide.spring.Context;

  [Bindable]
  [Name("userCtl")]
  public class UserCtl {

    private static var log:ILogger = Log.getLogger("@packageProject@.control.UserCtl");

    [In]
    public var context:Context;
    
    [In]
    public var userService:Object;
    
    /*
    [In]
    public var users:Object;
    */

    [In]
    [Out]
    public var user:User;

    [Out]
    public var usersAC:ArrayCollection;
    
    [Out]
    public var selectedRolesAC:ArrayCollection;

    [Out]
    public var rolesAC:ArrayCollection;


    [Observer("searchUser")]
    public function search(searchText:String = null):void {
      
      userService.search(searchText, onFind);
      
      /* example  db query 
      var filter:Object = new Object();

      if (searchText != null && StringUtil.trim(searchText).length > 0) {
        filter.name = "%" + searchText + "%";
      }
      users.find(filter, onFind);
      */
    }

    private function onFind(e:TideResultEvent):void {
      usersAC = ArrayCollection(e.result);
    }

    [Observer("newUser")]
    public function newUser():void {
      user = new User();
      user.roles = new ArrayCollection();
      selectedRolesAC = new ArrayCollection();
      switchToEdit();
    }

    [Observer("editUser")]
    public function editUser(selectedUser:User):void {
      if (selectedUser) {
        user = selectedUser;        
        copySelectedRoles();
      }
    }

   private function copySelectedRoles():void {
     if (user.roles is PersistentCollection  && PersistentCollection(user.roles).isInitialized()) {
        selectedRolesAC = new ArrayCollection();
        for(var i:uint = 0; i <  user.roles.length; i++) {
          selectedRolesAC.addItem(user.roles.getItemAt(i));        
        }
        switchToEdit();
      } else {
        user.roles.addEventListener(CollectionEvent.COLLECTION_CHANGE, onCollectionRefresh);
        user.roles.length; 
      }
    }
   
   private function onCollectionRefresh(event:CollectionEvent = null):void {
     if (!event.kind == CollectionEventKind.REFRESH)
       return;
     
     user.roles.removeEventListener(CollectionEvent.COLLECTION_CHANGE, onCollectionRefresh);
          copySelectedRoles();
    }

    [Observer("mergeUser")]
    public function merge():void {
      if (user.id) {
        userService.merge(user, onUpdate);
      } else {
        userService.merge(user, onInsert);
      }
    }

    private function onUpdate(event:TideResultEvent):void {
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Save_successful"), StatusEnum.SUCCESSFUL);
      switchToList();
    }

    private function onInsert(event:TideResultEvent):void {
      search();
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Save_successful"), StatusEnum.SUCCESSFUL);
      switchToList();
    }

    [Observer("cancelUser")]
    public function cancel():void {      
      user = null;
      switchToList();
    }

    [Observer("deleteUser")]
    public function askDeleteUser(selectedUser:User):void {
      if (selectedUser) {
        user = selectedUser;
        switchToList();

        CAlert.confirm(ResourceManager.getInstance().getString("user", "DeleteQuestion"), deleteUser);
      }
    }

    private function deleteUser(event:CloseEvent):void {
      if (event.detail == Alert.YES) {
        userService.remove(user, onRemove);
      }
    }

    private function onRemove(event:TideResultEvent):void {
      user = null;
      search();
      context.raiseEvent("removedUser");
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Delete_successful"), StatusEnum.SUCCESSFUL);
      switchToList();
    }


    [Observer("initRoles")]
    public function initRoles():void {
      userService.findAllRoles(onFindAllRoles);
    }
    
    [Observer("newLocale")]
    public function newLocale(locale:String):void {
      userService.updateLocale(locale, onUpdateLocale);
    }
    
    private function onUpdateLocale(e:TideResultEvent):void {
      context.raiseEvent("statusChange", ResourceManager.getInstance().getString("common", "Save_successful"), StatusEnum.SUCCESSFUL);
    }


    [Observer("newPassword")]
    public function newPassword(newPassword:String):void {
      var hash:IHash = Crypto.getHash("sha1");
      var result:ByteArray = hash.hash(Hex.toArray(Hex.fromString(newPassword)));
      var passwordHash:String = Hex.fromArray(result);      
      
      userService.updatePassword(passwordHash, onUpdatePassword);
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