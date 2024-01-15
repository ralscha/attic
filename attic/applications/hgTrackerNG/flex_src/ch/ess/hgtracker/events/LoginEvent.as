package ch.ess.hgtracker.events
{
  import ch.ess.hgtracker.db.Club;
  
  import flash.events.Event;

  public class LoginEvent extends Event {
    
    public static const LOGGED_ON:String = "loggedOn";
    
    private var _club:Club;
    
    public function LoginEvent(club:Club, type:String) {
      super(type);
      this._club = club;
    }
        
    public function get club():Club {
      return _club;
    }
    
  }
}