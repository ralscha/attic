package flexed.widgets.alerts
{
	import mx.controls.Alert;
	
	public class CAlert extends Alert
	{
		[Embed(source="alert_error.gif")]
		private static var iconError:Class;
		
		[Embed(source="alert_info.gif")]
		private static var iconInfo:Class;
		
		[Embed(source="alert_confirm.gif")]
		private static var iconConfirm:Class;

		public static function info(message:String, closehandler:Function=null):void{
			var a:Alert = show(message, "Information", Alert.OK, null, closehandler, iconInfo);
			a.height = 130;
		}
		
		public static function error(message:String, closehandler:Function=null):void{
			var a:Alert = show(message, "Error", Alert.OK, null, closehandler, iconError);
			a.height = 130;
		}
		
		public static function confirm(message:String, closehandler:Function=null):void{
			var a:Alert = show(message, "Confirmation", Alert.YES | Alert.NO, null, closehandler, iconConfirm);
			a.height = 130;
		}

	}
}