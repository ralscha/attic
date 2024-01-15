package ch.ess.tt.business {
  import ch.ess.tt.model.Principal;
  
  import com.universalmind.cairngorm.business.Delegate;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import mx.rpc.AsyncToken;
  import mx.rpc.IResponder;
  
  public class PrincipalDelegate extends Delegate {
    
    public function PrincipalDelegate(handlers:IResponder, serviceID:String = "principalService") {
      super(handlers, serviceID);
    }
        
        
    public function login(login:String, password:String):void {      
      var token:AsyncToken = service.loginPrincipal(login, password);
      prepareHandlers(token, Callbacks(responder));                      
    }
    
    public function logout():void {
      var token:AsyncToken = service.logoutPrincipal();
      prepareHandlers(token, Callbacks(responder));       
    }
    
    public function list():void {
      var token:AsyncToken = service.list();
      prepareHandlers(token, Callbacks(responder));                
    }
    
    public function deletePrincipal(id:int):void {
      var token:AsyncToken = service.deletePrincipal(id);
      prepareHandlers(token, Callbacks(responder));           
    }
    
    public function save(principal:Principal):void {
      var token:AsyncToken = service.save(principal);
      prepareHandlers(token, Callbacks(responder));        
    }    
  }
}