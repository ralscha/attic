package ch.ess.mp3search.events {
  import com.universalmind.cairngorm.events.Callbacks;
  import com.universalmind.cairngorm.events.UMEvent;
  
  import flash.events.Event;
  
  import mx.collections.ArrayCollection;
  import mx.controls.ProgressBar;
  
	public class DownloadEvent extends UMEvent {
		public static var EVENT_ID:String = "downloadEvent";
		private var _selectedSongs:ArrayCollection;
		private var _progressBar:ProgressBar;
		
		public function DownloadEvent(selectedSongs:ArrayCollection, progressBar:ProgressBar, handlers:Callbacks = null) {		  
			super(EVENT_ID, handlers);
			this._selectedSongs = selectedSongs;
			this._progressBar = progressBar;
		}
		
		public function get selectedSongs():ArrayCollection {
		  return _selectedSongs;
		}
		
		public function get progressBar():ProgressBar {
		  return _progressBar;
		}
		
		public override function copyFrom(src:Event):Event {
		  super.copyFrom(src);
		  this._selectedSongs = (src as DownloadEvent).selectedSongs;
		  
		  return this;
		} 
     	
	}
	
}