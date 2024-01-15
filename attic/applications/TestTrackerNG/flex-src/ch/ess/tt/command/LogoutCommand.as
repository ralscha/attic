package ch.ess.tt.command {
  import ch.ess.tt.business.PrincipalDelegate;
  import ch.ess.tt.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import flexed.widgets.alerts.CAlert;
    
  public class LogoutCommand extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
       
      var handlers:Callbacks = new Callbacks(onResult, onFault); 
      var delegate:PrincipalDelegate = new PrincipalDelegate(handlers);
      delegate.logout();
    }

    public function onResult(event:Object):void {     
      var model:ModelLocator = ModelLocator.getInstance();
      model.principal = null;
      model.workflowState = ModelLocator.VIEWING_LOGIN_SCREEN;
      
      notifyCaller(event); 
    }
    
    override public function onFault(event:Object):void { 
      TestTracker.debug("LogoutCommand#onFault: " + event);
      CAlert.error("Logout Failed");
       
      super.onFault(event);  
    }
      
  }
}