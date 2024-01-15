package ch.ess.tt.util {
  import com.universalmind.cairngorm.events.Callbacks;
  import com.universalmind.cairngorm.events.UMEvent;

  public class CairngormUtils {
    
    public static function dispatchEvent(eventName:String, data:Object = null, handlers:Callbacks = null):void {
      var event:UMEvent = new UMEvent(eventName, handlers);
      event.data = data;
      event.dispatch();
    }           
       
  } 
}
