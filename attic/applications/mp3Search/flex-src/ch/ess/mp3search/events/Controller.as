package ch.ess.mp3search.events {
  import ch.ess.mp3search.commands.DownloadCommand;
  import ch.ess.mp3search.commands.GetInfoCommand;
  import ch.ess.mp3search.commands.SearchCommand;
  
  import com.universalmind.cairngorm.control.FrontController;
  
  public class Controller extends FrontController {
    public function Controller() {
      registerAllCommands();
    }
        
    private function registerAllCommands():void {    
      addCommand(SearchEvent.EVENT_ID, SearchCommand);
      addCommand(DownloadEvent.EVENT_ID, DownloadCommand);
      addCommand(GetInfoEvent.EVENT_ID, GetInfoCommand);
    }
  }


}