package ch.ess.tt.command {
  import ch.ess.tt.business.PrincipalDelegate;
  import ch.ess.tt.model.ModelLocator;
  import ch.ess.tt.model.Principal;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
    
  public class SavePrincipalCommand extends Command {

   override public function execute(event:CairngormEvent):void {
     super.execute(event);
     
     var handlers:Callbacks = new Callbacks(onResult, onFault); 
     var delegate:PrincipalDelegate = new PrincipalDelegate(handlers);
     delegate.save(event.data);      
   }
    
    public function onResult(event:Object):void {  
      var model:ModelLocator = ModelLocator.getInstance();  
       
      var principal:Principal = Principal(event.result);
             
      model.selectedPrincipal = principal;
      
      for(var i:uint; i < model.principalsAC.length; i++) {        
        if (principal.id == model.principalsAC.getItemAt(i).id) {
          model.principalsAC.removeItemAt(i);
          break;
        }
      }
        
      model.principalsAC.addItem(principal);            
      model.principalsAC.refresh();         
      model.principalDataGrid.selectedItem = principal;    
      
      notifyCaller(event);   
    }
    
    override public function onFault(event:Object):void {
      TestTracker.debug("SavePrincipalCommand#fault: " + event);
      super.onFault(event);
    }
  
  }
}