package ch.ess.testtracker.util {
  import flash.events.Event;
  
  import org.granite.tide.events.TideEvent;
  import org.granite.tide.seam.Context;

  public class ContextTideEvent extends TideEvent {
    
    public var localContext:Context;
    
    public function ContextTideEvent(localContext:Context, type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
      super(type, bubbles, cancelable);
      this.localContext = localContext;
    }
        
    override public function clone():Event {
      return new ContextTideEvent(localContext, type);
    }
    
  }
}
