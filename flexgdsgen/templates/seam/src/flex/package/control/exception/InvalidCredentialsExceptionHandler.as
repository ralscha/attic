package @packageProject@.control.exception {

  import flash.utils.flash_proxy;
  
  import flexed.widgets.alerts.CAlert;
  
  import mx.messaging.messages.ErrorMessage;
  import mx.resources.ResourceManager;
  import mx.utils.object_proxy;
  
  import org.granite.tide.BaseContext;
  import org.granite.tide.IExceptionHandler;

  use namespace flash_proxy;
  use namespace object_proxy;


  public class InvalidCredentialsExceptionHandler implements IExceptionHandler {

    public static const INVALID_CREDENTIALS:String = "Server.Security.InvalidCredentials";


    public function accepts(emsg:ErrorMessage):Boolean {
      return emsg.faultCode == INVALID_CREDENTIALS;
    }

    public function handle(context:BaseContext, emsg:ErrorMessage):void {
      CAlert.error(ResourceManager.getInstance().getString('login','Login_failed'));
    }
  }
}
