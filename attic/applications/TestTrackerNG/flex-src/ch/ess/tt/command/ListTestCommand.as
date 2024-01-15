package ch.ess.tt.command {
  import ch.ess.tt.business.TestDelegate;
  import ch.ess.tt.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import mx.collections.ArrayCollection;
  import mx.collections.Sort;
  import mx.collections.SortField;
    
  public class ListTestCommand extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);
      
      var handlers:Callbacks = new Callbacks(onResult, onFault); 
      var delegate:TestDelegate = new TestDelegate(handlers);
      delegate.list();
    }

    public function onResult(event:Object):void {  
      var model:ModelLocator = ModelLocator.getInstance();   
      var newTestCollection:ArrayCollection = ArrayCollection(event.result);
            
      var customerSort:Sort = (model.testsAC ? model.testsAC.sort : null);

      if (customerSort) {
        newTestCollection.sort = customerSort;               
      } else {
        var sort:Sort = new Sort();
        sort.fields = [new SortField("name", true)];          
        newTestCollection.sort = sort;
      }    
      newTestCollection.refresh(); 
     
      model.testsAC = newTestCollection;           
       
      notifyCaller(event);    
    }
    
    override public function onFault(event:Object):void {
      TestTracker.debug("ListTestCommand#fault: " + event);
      super.onFault(event);
    }
  
  }
}