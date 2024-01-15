package ch.ess.issue.events
{
  import ch.ess.issue.entity.User;
  
  import flash.events.Event;

  public class LoginEvent extends Event {
    
    public static const LOGGED_ON:String = "loggedOn";
    
    private var _user:User;
    
    public function LoginEvent(user:User, type:String) {
      super(type);
      this._user = user;
    }
        
    public function get user():User {
      return _user;
    }
    
  }
}