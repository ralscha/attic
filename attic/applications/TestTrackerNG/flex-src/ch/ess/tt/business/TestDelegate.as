package ch.ess.tt.business {
  import ch.ess.tt.model.Test;
  
  import com.universalmind.cairngorm.business.Delegate;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import mx.rpc.AsyncToken;
  import mx.rpc.IResponder;
  
  public class TestDelegate extends Delegate {
        
    public function TestDelegate(handlers:IResponder, serviceID:String = "testService") {
      super(handlers, serviceID);
    }
    
    public function list():void {
      var token:AsyncToken = service.list();
      prepareHandlers(token, Callbacks(responder));        
    }
    
    public function deleteTest(id:int):void {
      var token:AsyncToken = service.deleteTest(id);
      prepareHandlers(token, Callbacks(responder));          
    }
    
    public function save(test:Test):void {
      var token:AsyncToken = service.save(test);
      prepareHandlers(token, Callbacks(responder));   
    }  
    
    public function deleteAttachment(no:int, id:int):void {      
      var token:AsyncToken = service.deleteAttachment(no, id);
      prepareHandlers(token, Callbacks(responder));               
    }  
  }
}