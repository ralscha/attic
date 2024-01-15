package ch.ess.tt.command {
  
  import ch.ess.tt.business.PrincipalDelegate;
  import ch.ess.tt.model.ModelLocator;
  import ch.ess.tt.model.Principal;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import flexed.widgets.alerts.CAlert;
    
  public class LoginCommand extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
      
      var handlers:Callbacks = new Callbacks(onResult, onFault); 
      var delegate:PrincipalDelegate = new PrincipalDelegate(handlers);
      
      delegate.login(event.data.login, event.data.password);
    }

    public function onResult(event:Object):void {
      var result:Object = event.result;
      if (event.result == null) {
        CAlert.error("Login failed.");
      } else {
        var model:ModelLocator = ModelLocator.getInstance();
        model.principal = Principal(event.result);
        model.workflowState = ModelLocator.VIEWING_MAIN_APP;
      }
      
      notifyCaller(event); 
    }
    
     override public function onFault(event:Object):void {
       TestTracker.debug("LoginCommand#fault: " + event);
       CAlert.error("Login Failed");
       super.onFault(event);
     }
  
  }
}