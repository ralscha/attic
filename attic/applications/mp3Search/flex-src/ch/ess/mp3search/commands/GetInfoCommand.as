package ch.ess.mp3search.commands {
  import ch.ess.mp3search.business.SearchDelegate;
  import ch.ess.mp3search.model.Info;
  import ch.ess.mp3search.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import mx.collections.ArrayCollection;
  import mx.rpc.events.ResultEvent;
  
  
  public class GetInfoCommand extends Command {
    
    private var model:ModelLocator = ModelLocator.getInstance();
    
    override public function execute(event:CairngormEvent):void {
      // always call the super.execute() method which allows the 
      // callBack information to be cached.    
      super.execute(event);
    
      var handlers:Callbacks = new Callbacks(onResult_getInfo, onFault);
      var delegate:SearchDelegate = new SearchDelegate(handlers);    
      delegate.getInfo();
      
    }
    
    private function onResult_getInfo(event:ResultEvent):void {
      model.info = Info(event.result);
    }
    
  }
}