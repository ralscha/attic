package ch.ess.tt.command {
  import ch.ess.tt.business.TestDelegate;
  import ch.ess.tt.model.ModelLocator;
  import ch.ess.tt.model.Test;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
    
  public class SaveTestCommand extends Command {
//    public function SaveTestCommand() {
//      super(new CairngormEvent(EventNames.UPLOAD_TEST_INFORMATION_ATTACHMENT_1));
//    }

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
      
      var handlers:Callbacks = new Callbacks(onResult, onFault); 
      var delegate:TestDelegate = new TestDelegate(handlers);
      delegate.save(event.data);      
    }
    
    public function onResult(event:Object):void {  
      var model:ModelLocator = ModelLocator.getInstance();  
       
      var test:Test = Test(event.result);       
      
      model.selectedTest = test;
      
      for(var i:uint; i < model.testsAC.length; i++) {        
        if (test.id == model.testsAC.getItemAt(i).id) {
          model.testsAC.removeItemAt(i);
          break;
        }
      }
        
      model.testsAC.addItem(test);            
      model.testsAC.refresh();         
      model.testDataGrid.selectedItem = test;   
       
      notifyCaller(event); 
    }
    
    override public function onFault(event:Object):void {
      TestTracker.debug("SaveTestCommand#fault: " + event);
      super.onFault(event);
    }
  
  }
}