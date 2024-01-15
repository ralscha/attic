package ch.ess.mp3search.events {
  import com.universalmind.cairngorm.events.Callbacks;
  import com.universalmind.cairngorm.events.UMEvent;
  
  import flash.events.Event;
  
	public class SearchEvent extends UMEvent {
		public static var EVENT_ID:String = "searchEvent";
		private var _query:String;
		
		public function SearchEvent(q:String, handlers:Callbacks = null) {		  
			super(EVENT_ID, handlers);
			this._query = q;
		}
		
		public function get query():String {
		  return _query;
		}
		
		public override function copyFrom(src:Event):Event {
		  super.copyFrom(src);
		  this._query = (src as SearchEvent).query;
		  
		  return this;
		} 
     	
	}
	
}