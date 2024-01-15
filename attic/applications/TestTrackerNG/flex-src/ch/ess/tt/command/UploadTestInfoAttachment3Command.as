package ch.ess.tt.command {
  import ch.ess.tt.business.TestDelegate;
  import ch.ess.tt.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import flash.errors.IllegalOperationError;
  import flash.events.Event;
  import flash.events.HTTPStatusEvent;
  import flash.events.IOErrorEvent;
  import flash.net.URLRequest;
  import flash.net.URLVariables;
  
  import mx.managers.CursorManager;
    
  public class UploadTestInfoAttachment3Command extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);

      var model:ModelLocator = ModelLocator.getInstance();
      try {
        if (model.testFile3Ref && model.testFile3Ref.name) {
          var request:URLRequest = new URLRequest(model.baseURL + "/uploadTestInfo.action");        
          var variables:URLVariables = new URLVariables();
          variables.testId = model.selectedTest.id;  
          variables.no = 3;      
          request.data = variables;        
          
          CursorManager.setBusyCursor();   
          model.testFile3Ref.addEventListener(Event.COMPLETE, completeHandler);  
          model.testFile3Ref.addEventListener(HTTPStatusEvent.HTTP_STATUS, fault);
          model.testFile3Ref.addEventListener(IOErrorEvent.IO_ERROR, fault);
          model.testFile3Ref.upload(request);
          
        } else {
          deleteAttachment();
          
        }
      } catch (error:IllegalOperationError) {
        deleteAttachment();
      }           

    }

    private function deleteAttachment():void {
      var model:ModelLocator = ModelLocator.getInstance();
      if (model.delete3Attachment) {
        var handlers:Callbacks = new Callbacks(onResult, onFault); 
        var delegate:TestDelegate = new TestDelegate(handlers);
        
        delegate.deleteAttachment(3, model.selectedTest.id);
      } else {
        model.testFile3Ref = null;
        model.testWorkflowState = ModelLocator.VIEWING_LIST_SCREEN;
      }      
    }

    public function onResult(event:Object):void {
      var model:ModelLocator = ModelLocator.getInstance();
      model.testFile3Ref = null;
      model.selectedTest.dateiFileName3 = null;
      model.selectedTest.dateiContentSize3 = 0;      
      model.testWorkflowState = ModelLocator.VIEWING_LIST_SCREEN;
      notifyCaller(event); 
    } 

    public function completeHandler(event:Object):void {
      CursorManager.removeBusyCursor();
      var model:ModelLocator = ModelLocator.getInstance();    
      model.selectedTest.dateiFileName3 = model.testFile3Ref.name;
      model.selectedTest.dateiContentSize3 = model.testFile3Ref.size;
      
      model.testFile3Ref = null;
      model.testWorkflowState = ModelLocator.VIEWING_LIST_SCREEN;    
      notifyCaller(event);            
    }
    
    override public function onFault(event:Object):void {
      CursorManager.removeBusyCursor();
      TestTracker.debug("UploadTestInfoAttachment3Command#fault: " + event);
      super.onFault(event);
    }
  
  }
}