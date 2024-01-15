package ch.ess.tt.command {
  import ch.ess.tt.business.PrincipalDelegate;
  import ch.ess.tt.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
    
  public class DeletePrincipalCommand extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
      var handlers:Callbacks = new Callbacks(onResult, onFault); 
      var delegate:PrincipalDelegate = new PrincipalDelegate(handlers);      
      delegate.deletePrincipal(event.data.id);      
    }
    
    public function onResult(event:Object):void {  
      var model:ModelLocator = ModelLocator.getInstance();  
       
      var id:int = event.result;
          
      for(var i:uint; i < model.principalsAC.length; i++) {

        if (id == model.principalsAC.getItemAt(i).id) {
          model.principalsAC.removeItemAt(i);
          break;
        }     
      }     
      
      notifyCaller(event); 
    }
    
    override public function onFault(event:Object):void {
      TestTracker.debug("DeletePrincipalCommand#fault: " + event);
      super.onFault(event);
    }
  
  }
}