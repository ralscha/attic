package ch.ess.mp3search.events {
  import com.universalmind.cairngorm.events.Callbacks;
  import com.universalmind.cairngorm.events.UMEvent;
  
  import flash.events.Event;
  
	public class GetInfoEvent extends UMEvent {
		public static var EVENT_ID:String = "getInfoEvent";
		
		public function GetInfoEvent(handlers:Callbacks = null) {		  
			super(EVENT_ID, handlers);
		}
		
	}
	
}