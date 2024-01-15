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
    
  public class UploadTestInfoAttachment1Command extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
      
      //nextEvent = new CairngormEvent(EventNames.UPLOAD_TEST_INFORMATION_ATTACHMENT_2);

      var model:ModelLocator = ModelLocator.getInstance();
      try {
        if (model.testFile1Ref && model.testFile1Ref.name) {
          var request:URLRequest = new URLRequest(model.baseURL + "/uploadTestInfo.action");       
          var variables:URLVariables = new URLVariables();
          variables.testId = model.selectedTest.id;  
          variables.no = 1;      
          request.data = variables;        
  
          CursorManager.setBusyCursor();
          model.testFile1Ref.addEventListener(Event.COMPLETE, completeHandler);  
          model.testFile1Ref.addEventListener(HTTPStatusEvent.HTTP_STATUS, fault);
          model.testFile1Ref.addEventListener(IOErrorEvent.IO_ERROR, fault);
          model.testFile1Ref.upload(request);
  
  
        } else {
          deleteAttachment();
        }
      } catch (error:IllegalOperationError) {
        deleteAttachment();
      }

    }
    
    private function deleteAttachment():void {
      var model:ModelLocator = ModelLocator.getInstance();
      if (model.delete1Attachment) {        
        var handlers:Callbacks = new Callbacks(onResult, onFault); 
        var delegate:TestDelegate = new TestDelegate(handlers);
        delegate.deleteAttachment(1, model.selectedTest.id);
      } else {
        model.testFile1Ref = null;        
      }      
       
    }

    public function onResult(event:Object):void {
      var model:ModelLocator = ModelLocator.getInstance();
      model.testFile1Ref = null;
      model.selectedTest.dateiFileName1 = null;
      model.selectedTest.dateiContentSize1 = 0;
      notifyCaller(event); 
    } 

    public function completeHandler(event:Object):void {
      CursorManager.removeBusyCursor();
      var model:ModelLocator = ModelLocator.getInstance();
      model.selectedTest.dateiFileName1 = model.testFile1Ref.name;
      model.selectedTest.dateiContentSize1 = model.testFile1Ref.size;
      model.testFile1Ref = null;           
      notifyCaller(event);      
    }
    
    override public function onFault(event:Object):void {
      CursorManager.removeBusyCursor();
      TestTracker.debug("UploadTestInfoAttachment1Command#fault: " + event);
      super.onFault(event);
    }
  
  }
}