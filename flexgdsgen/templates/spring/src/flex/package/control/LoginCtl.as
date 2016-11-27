package @packageProject@.control {
  import com.hurlant.crypto.Crypto;
  import com.hurlant.crypto.hash.IHash;
  import com.hurlant.util.Hex;
  
  import flash.utils.ByteArray;
  
  import flexed.widgets.alerts.CAlert;
  
  import org.granite.tide.events.TideFaultEvent;
  import org.granite.tide.events.TideResultEvent;
  import org.granite.tide.spring.Context;
  import org.granite.tide.spring.Identity;


  [Bindable]
  [Name("loginCtl")]
  public class LoginCtl {

    [In]
    public var context:Context;

    [In]
    public var identity:Identity;
    
    [In]
    public var userService:Object;    

    [Observer("authenticate")]
    public function authenticate(username:String, password:String):void {
      var hash:IHash = Crypto.getHash("sha1");
      var result:ByteArray = hash.hash(Hex.toArray(Hex.fromString(password)));
      var passwordHash:String = Hex.fromArray(result);

      identity.login(username, passwordHash);
    }

    
    [Observer("requestNewPassword")]
    public function requestNewPassword(userName:String):void {
      userService.requestNewPassword(userName, onRequestNewPassword);      
    }
    
    private function onRequestNewPassword(event:TideResultEvent):void {
      context.raiseEvent("newPasswortSent");
    }
    

    [Observer("getLocale")]
    public function getLocale():void {
      userService.getLocale(onGetLocale);
    }

    private function onGetLocale(e:TideResultEvent):void {
      context.raiseEvent("gotLocale", e.result);
    }

  }
}