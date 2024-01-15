package ch.ess.tt.command {
  import ch.ess.tt.business.TestDelegate;
  import ch.ess.tt.control.EventNames;
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
    
  public class UploadTestInfoAttachment2Command extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);

      //nextEvent = new CairngormEvent(EventNames.UPLOAD_TEST_INFORMATION_ATTACHMENT_3);

      var model:ModelLocator = ModelLocator.getInstance();
      
      try {
        if (model.testFile2Ref && model.testFile2Ref.name) {
          var request:URLRequest = new URLRequest(model.baseURL + "/uploadTestInfo.action");
          var variables:URLVariables = new URLVariables();
          variables.testId = model.selectedTest.id;  
          variables.no = 2;      
          request.data = variables;   
          
          CursorManager.setBusyCursor();             
          model.testFile2Ref.addEventListener(Event.COMPLETE, completeHandler);  
          model.testFile2Ref.addEventListener(HTTPStatusEvent.HTTP_STATUS, fault);
          model.testFile2Ref.addEventListener(IOErrorEvent.IO_ERROR, fault);
          model.testFile2Ref.upload(request);
          
        } else {
          deleteAttachment();
        }
      } catch (error:IllegalOperationError) {
        deleteAttachment();
      }         
    }

    private function deleteAttachment():void {
      var model:ModelLocator = ModelLocator.getInstance();
      if (model.delete2Attachment) {
        
        var handlers:Callbacks = new Callbacks(onResult, onFault); 
        var delegate:TestDelegate = new TestDelegate(handlers);
        delegate.deleteAttachment(2, model.selectedTest.id);        
        
      } else {
        model.testFile2Ref = null;      
      }      
    }

    public function onResult(event:Object):void {
      var model:ModelLocator = ModelLocator.getInstance();
      model.testFile2Ref = null;
      model.selectedTest.dateiFileName2 = null;
      model.selectedTest.dateiContentSize2 = 0;
      notifyCaller(event); 
    } 

    public function completeHandler(event:Object):void {
      CursorManager.removeBusyCursor();
      var model:ModelLocator = ModelLocator.getInstance();
      model.selectedTest.dateiFileName2 = model.testFile2Ref.name;
      model.selectedTest.dateiContentSize2 = model.testFile2Ref.size;
      
      model.testFile2Ref = null;
      
      notifyCaller(event); 
             
    }
    
    override public function onFault(event:Object):void {
      CursorManager.removeBusyCursor();
      TestTracker.debug("UploadTestInfoAttachment2Command#fault: " + event);
      super.onFault(event);
    }
  
  }
}