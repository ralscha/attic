package ch.ess.mp3search.commands {
  import ch.ess.mp3search.business.SearchDelegate;
  import ch.ess.mp3search.events.SearchEvent;
  import ch.ess.mp3search.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import flash.net.FileReference;
  
  import mx.collections.ArrayCollection;
  import mx.rpc.events.ResultEvent;
  
  
  public class SearchCommand extends Command {
    
    private var model:ModelLocator = ModelLocator.getInstance();
    
    override public function execute(event:CairngormEvent):void {
      // always call the super.execute() method which allows the 
      // callBack information to be cached.    
      super.execute(event);
    
      var handlers:Callbacks = new Callbacks(onResult_doSearch, onFault);
      var delegate:SearchDelegate = new SearchDelegate(handlers);    
      delegate.search((event as SearchEvent).query);      
    }
    
    private function onResult_doSearch(event:ResultEvent):void {
      model.songsAC = ArrayCollection(event.result);
      notifyCaller(event);
    }
    
  }
}