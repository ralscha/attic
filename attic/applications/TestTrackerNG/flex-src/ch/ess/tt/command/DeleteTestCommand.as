package ch.ess.tt.command {
  import ch.ess.tt.business.TestDelegate;
  import ch.ess.tt.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
    
  public class DeleteTestCommand extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
      
      var handlers:Callbacks = new Callbacks(onResult, onFault); 
      var delegate:TestDelegate = new TestDelegate(handlers);
      delegate.deleteTest(event.data.id);      
    }
    
    public function onResult(event:Object):void {  
      var model:ModelLocator = ModelLocator.getInstance();  
       
      var id:int = event.result;
          
      for(var i:uint; i < model.testsAC.length; i++) {
        if (id == model.testsAC.getItemAt(i).id) {
          model.testsAC.removeItemAt(i);
          break;
        }     
      }     
      
      notifyCaller(event); 
    }
    
    override public function onFault(event:Object):void {
      TestTracker.debug("DeleteTestCommand#fault: " + event);
      super.onFault(event);
    }
  
  }
}