package @packageProject@.control.exception {
  import flexed.widgets.alerts.CAlert;
  import flash.utils.flash_proxy;
  import mx.messaging.messages.ErrorMessage;
  import mx.utils.object_proxy;
  import org.granite.tide.BaseContext;
  import org.granite.tide.IExceptionHandler;


  use namespace flash_proxy;
  use namespace object_proxy;


  public class DefaultExceptionHandler implements IExceptionHandler {

    public function accepts(emsg:ErrorMessage):Boolean {
      return true;
    }

    public function handle(context:BaseContext, emsg:ErrorMessage):void {
      CAlert.error("Unexpected error: " + emsg.faultCode + "\n" + emsg.faultString + "\n" + emsg.faultDetail);
    }
  }
}
