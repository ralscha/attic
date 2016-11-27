package @packageProject@.control {
  import com.hurlant.crypto.Crypto;
  import com.hurlant.crypto.hash.IHash;
  import com.hurlant.util.Hex;
  
  import flash.utils.ByteArray;
  
  import org.granite.tide.events.TideResultEvent;
  import org.granite.tide.seam.Context;
  import org.granite.tide.seam.security.Identity;
  
  [Bindable]
  [Name("loginCtl")]
  public class LoginCtl {

    [In]
    public var context:Context;

    [In]
    public var identity:Identity; 

    [In]
    public var userHome:Object;    
    
    [In]
    public var newPasswordCreator:Object;

    [Observer("authenticate")]
    public function authenticate(username:String, password:String):void {
      var hash:IHash = Crypto.getHash("sha1");
      var result:ByteArray = hash.hash(Hex.toArray(Hex.fromString(password)));
      var passwordHash:String = Hex.fromArray(result);            
      identity.username = username;
      identity.password = passwordHash;                  
      identity.login();
    }

    
    [Observer("requestNewPassword")]
    public function requestNewPassword(userName:String):void {
      newPasswordCreator.requestNewPassword(userName, onRequestNewPassword);      
    }

    private function onRequestNewPassword(event:TideResultEvent):void {
      context.raiseEvent("newPasswortSent");
      }
    

    [Observer("getLocale")]
    public function getLocale():void {
      userHome.getLocale(onGetLocale);
    }

    private function onGetLocale(e:TideResultEvent):void {
      context.raiseEvent("gotLocale", e.result);
    }

  }
}