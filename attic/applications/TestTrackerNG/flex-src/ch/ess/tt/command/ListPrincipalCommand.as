package ch.ess.tt.command {
  import ch.ess.tt.business.PrincipalDelegate;
  import ch.ess.tt.model.ModelLocator;
  
  import com.adobe.cairngorm.control.CairngormEvent;
  import com.universalmind.cairngorm.commands.Command;
  import com.universalmind.cairngorm.events.Callbacks;
  
  import mx.collections.ArrayCollection;
  import mx.collections.Sort;
  import mx.collections.SortField;
    
  public class ListPrincipalCommand extends Command {

    override public function execute(event:CairngormEvent):void {
      super.execute(event);       
      var handlers:Callbacks = new Callbacks(onResult, onFault);       
      
      var delegate:PrincipalDelegate = new PrincipalDelegate(handlers);
      delegate.list();
    }

    public function onResult(event:Object):void {  
      var model:ModelLocator = ModelLocator.getInstance();   
      var newPrincipalCollection:ArrayCollection = ArrayCollection(event.result);
            
      var customerSort:Sort = (model.principalsAC ? model.principalsAC.sort : null);

      if (customerSort) {
        newPrincipalCollection.sort = customerSort;               
      } else {
        var sort:Sort = new Sort();
        sort.fields = [new SortField("name", true)];          
        newPrincipalCollection.sort = sort;
      }    
      newPrincipalCollection.refresh(); 
     
      model.principalsAC = newPrincipalCollection;           
        
      notifyCaller(event);    
    }
    
    override public function onFault(event:Object):void {
      TestTracker.debug("ListPrincipalCommand#fault: " + event);
      super.onFault(event);
    }
  
  }
}