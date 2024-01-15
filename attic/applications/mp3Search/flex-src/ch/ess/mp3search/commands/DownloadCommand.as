package ch.ess.mp3search.commands {
  import ch.ess.mp3search.events.DownloadEvent;
  import ch.ess.mp3search.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  
  import flash.events.Event;
  import flash.events.HTTPStatusEvent;
  import flash.events.IOErrorEvent;
  import flash.events.SecurityErrorEvent;
  import flash.net.FileReference;
  import flash.net.URLRequest;
  import flash.net.URLVariables;
  
  import mx.controls.ProgressBar;
  import mx.managers.CursorManager;
  
  
  public class DownloadCommand extends Command {
    
    private var model:ModelLocator = ModelLocator.getInstance();
    private var fileRef:FileReference;
    private var progressBar:ProgressBar;
    
    override public function execute(event:CairngormEvent):void {
      // always call the super.execute() method which allows the 
      // callBack information to be cached.    
      super.execute(event);
    
      var downloadEvent:DownloadEvent = event as DownloadEvent;
      this.progressBar = downloadEvent.progressBar;
          
      CursorManager.setBusyCursor();
      
      fileRef = new FileReference();     
      
      var urlRequest:URLRequest = new URLRequest(model.baseURL + "downloadMp3Zip.action");
      
      var variables:URLVariables = new URLVariables();
      variables.selectedFiles = downloadEvent.selectedSongs;  
      urlRequest.data = variables;   
          
          
      //fileRef.addEventListener(Event.COMPLETE, completeHandler);  
      fileRef.addEventListener(HTTPStatusEvent.HTTP_STATUS, fault);
      fileRef.addEventListener(IOErrorEvent.IO_ERROR, fault);
      fileRef.addEventListener(SecurityErrorEvent.SECURITY_ERROR, fault);
  
      fileRef.addEventListener(Event.OPEN, openHandler);
      //fileRef.addEventListener(ProgressEvent.PROGRESS, progressHandler);
      fileRef.addEventListener(Event.COMPLETE, completeHandler);
                  
      fileRef.addEventListener(Event.CANCEL, cancelHandler);
                        
      fileRef.download(urlRequest, "playlist.zip");
      

    }
    
    
    public override function onFault(info:Object):void {
      CursorManager.removeBusyCursor();
      progressBar.visible = false;
      super.onFault(info);
    }

    private function cancelHandler(event:Event):void {
      CursorManager.removeBusyCursor();
      progressBar.visible = false;
      notifyCaller(event);
    }
    
    private function completeHandler(event:Event):void {
      CursorManager.removeBusyCursor();
      progressBar.visible = false;
      notifyCaller(event);
    }
    
    private function openHandler(event:Event):void {
      progressBar.visible = true;
      progressBar.source = fileRef;
    }




  }
}