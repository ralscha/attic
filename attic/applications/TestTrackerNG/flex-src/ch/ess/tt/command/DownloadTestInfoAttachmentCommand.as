package ch.ess.tt.command {
  import ch.ess.tt.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  
  import flash.events.HTTPStatusEvent;
  import flash.events.IOErrorEvent;
  import flash.net.FileReference;
  import flash.net.URLRequest;
  import flash.net.URLVariables;
    
  public class DownloadTestInfoAttachmentCommand extends Command {
    private var fileRef:FileReference;
    

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
      
      fileRef = new FileReference();     
      var no:int = int(event.data);     
      var model:ModelLocator = ModelLocator.getInstance();
      
      var urlRequest:URLRequest = new URLRequest(model.baseURL + "/downloadTestInfo.action");
      var variables:URLVariables = new URLVariables();
      variables.testId = model.selectedTest.id;  
      variables.no = no;      
      urlRequest.data = variables;   
          
          
      //fileRef.addEventListener(Event.COMPLETE, completeHandler);  
      fileRef.addEventListener(HTTPStatusEvent.HTTP_STATUS, onFault);
      fileRef.addEventListener(IOErrorEvent.IO_ERROR, onFault);
                              
      fileRef.download(urlRequest, model.selectedTest['dateiFileName'+no]);
        
      notifyCaller(event);   
    }
    
    override public function onFault(event:Object):void {      
      TestTracker.debug("DownloadTestInfoAttachmentCommand#fault: " + event);
      super.onFault(event);
    }
      
  }
}