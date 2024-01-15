
package ch.ess.testtracker.control {
  import com.hurlant.crypto.Crypto;
  import com.hurlant.crypto.hash.IHash;
  import com.hurlant.util.Hex;
  
  import flash.utils.ByteArray;
  
  import flexed.widgets.alerts.CAlert;
  
  import org.granite.tide.AbstractClientComponent;
  import org.granite.tide.events.TideEvent;
  import org.granite.tide.events.TideFaultEvent;
  import org.granite.tide.events.TideResultEvent;
  import org.granite.tide.seam.Context;
  import org.granite.tide.seam.Seam;


  public class PrincipalCtl extends AbstractClientComponent {

   public function PrincipalCtl(componentName:String, context:Context):void {
     super(componentName, context);
   }

    public function authenticate(username:String, password:String):void {
      var hash:IHash = Crypto.getHash("sha1");
      var result:ByteArray = hash.hash(Hex.toArray(Hex.fromString(password)));
      var passwordHash:String = Hex.fromArray(result);            
                  
      tideContext.identity.username = username;
      tideContext.identity.password = passwordHash;
      tideContext.identity.login(authenticateResult, authenticateFault);
    }

    private function authenticateResult(event:TideResultEvent):void {      
      tideContext.dispatchEvent(new TideEvent("loggedIn"));
    }

    private function authenticateFault(event:TideFaultEvent):void {
      if (tideContext.messages) {
        CAlert.error("Authentication error : " + tideContext.messages.getItemAt(0).summary);
      }
    }

    public function logout():void	{
      tideContext.identity.logout();
    }

  }
}