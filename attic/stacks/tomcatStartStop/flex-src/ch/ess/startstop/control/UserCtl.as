
package ch.ess.startstop.control {

  import ch.ess.startstop.entity.Info;
  
  import com.hurlant.crypto.Crypto;
  import com.hurlant.crypto.hash.IHash;
  import com.hurlant.util.Hex;
  
  import flash.events.Event;
  import flash.utils.ByteArray;
  
  import flexed.widgets.alerts.CAlert;
  
  import mx.collections.ArrayCollection;
  
  import org.granite.tide.events.TideContextEvent;
  import org.granite.tide.events.TideFaultEvent;
  import org.granite.tide.events.TideResultEvent;
  import org.granite.tide.seam.Context;
  import org.granite.tide.seam.security.Identity;

  [Bindable]
  public class UserCtl {

    [In]
    public var identity:Identity = null; 
    
    [In]
    public var pollerStarter:Object;
    
    [In]
    public var onoff:Object;
    
    [In]
    public var buildLogger:Object;
    
    [In]
    public var buildWar:Object;
    
    [In]
    public var cleanup:Object;
    
    [In]
    public var showLog:Object;
    
    [In]
    public var memInfo:Object;
    
        
    public var status:String;
    public var showStartButton:Boolean;
    public var showStopButton:Boolean;
    public var buildString:String;
    public var cleanupStatusString:String;
    public var showLogString:String;    

    public var memData:ArrayCollection;

    public function UserCtl(name:String, context:Context):void {
      // Flex 2
      context.bindIn(this, "identity");
      context.bindIn(this, "pollerStarter");
      context.bindIn(this, "onoff");
      context.bindIn(this, "buildLogger");
      context.bindIn(this, "buildWar");
      context.bindIn(this, "cleanup");
      context.bindIn(this, "showLog");
      context.bindIn(this, "memInfo");
    }

    public function startService():void {
      onoff.on();
    }
    
    public function stopService():void {
      onoff.off();
    }
    
    public function startBuild():void {
      buildString = "";
      buildWar.build();
    }
    
    public function cleanupWorkdirs():void {
      cleanupStatusString = "Working...";
      cleanup.cleanup(onCleanup);
    }
    
    private function onCleanup(event:TideResultEvent):void {
      if (event.result != null) {
        cleanupStatusString = String(event.result);
      } else {
        cleanupStatusString = "Cleanup successful";
      }
    }
    
    public function showLogContents():void {
      showLogString = "";
      showLog.showLogFiles(onShowLogFiles);
    }
    
    private function onShowLogFiles(event:TideResultEvent):void {
      if (event.result != null) {        
        showLogString = String(event.result);
      } else {
        showLogString = "";
      }
    }
    
    [Observer("notification")]
    public function notificationHandler(event:TideContextEvent):void {
      status = event.context.messages[0].summary;    
      
      showStopButton = (status.indexOf("running") > 0);
      showStartButton = (status.indexOf("stopped") > 0);                  
    }
    
    [Observer("notification")]
    public function getLogLines(event:TideContextEvent):void {
      buildLogger.getLogs(onGetLogs);
      memInfo.getInfo(onGetInfo);
    }
    
    public function onGetInfo(event:TideResultEvent):void {
      var i:Info = Info(event.result);
      
      memData = new ArrayCollection([{label: "Memory", free: i.free, used: i.used}]);
    }
    
    public function onGetLogs(event:TideResultEvent):void {
      var logs:ArrayCollection = event.result as ArrayCollection;
      for each (var line:String in logs) {
        if (line != null) {
          buildString += "\n";
          buildString += line;
        }
      }
    }

    public function authenticate(username:String, password:String):void {
      var hash:IHash = Crypto.getHash("sha1");
      var result:ByteArray = hash.hash(Hex.toArray(Hex.fromString(password)));
      var passwordHash:String = Hex.fromArray(result);            
                  
      identity.username = username;
      identity.password = passwordHash;
      identity.login(authenticateResult, authenticateFault);
    }

    private function authenticateResult(event:TideResultEvent):void {
      event.context.addContextEventListener("notification", notificationHandler, true);
      event.context.addContextEventListener("notification", getLogLines, true);
      
      pollerStarter.start(); 
      dispatchEvent(new Event("loggedIn"));
    }

    private function authenticateFault(event:TideFaultEvent):void {
      if (event.context.messages && event.context.messages.length > 0) {
        CAlert.error("Authentication error : " + event.context.messages.getItemAt(0).summary);
      }
    }

    public function logout():void	{
      pollerStarter.stop();
      identity.logout();
    }

  }
}