package ch.ess.mp3search.business {
  import com.universalmind.cairngorm.business.Delegate;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import mx.rpc.AsyncToken;
  import mx.rpc.IResponder;
  
  
  public class SearchDelegate extends Delegate {
    
    public function SearchDelegate(handlers:IResponder, serviceID:String = "searchService") {
      super(handlers, serviceID);
    }

    public function search(query:String):void {
      var token:AsyncToken = service.search(query);
      prepareHandlers(token, Callbacks(responder));
    }

    public function getInfo():void {
      var token:AsyncToken = service.getInfo();
      prepareHandlers(token, Callbacks(responder));
    }
  }
}