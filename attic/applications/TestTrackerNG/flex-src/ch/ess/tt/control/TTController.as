package ch.ess.tt.control {
  import ch.ess.tt.command.*;
  
  import com.universalmind.cairngorm.control.FrontController;

  public class TTController extends FrontController {
    public function TTController() {
      registerAllCommands();
    }
        
    private function registerAllCommands():void {    
      addCommand(EventNames.LOGIN, LoginCommand);
      addCommand(EventNames.LOGOUT, LogoutCommand);    
      
      addCommand(EventNames.LIST_PRINCIPAL, ListPrincipalCommand);
      addCommand(EventNames.DELETE_PRINCIPAL, DeletePrincipalCommand);   
      addCommand(EventNames.SAVE_PRINCIPAL, SavePrincipalCommand);
      
      addCommand(EventNames.LIST_TEST, ListTestCommand);
      addCommand(EventNames.SAVE_TEST, SaveTestCommand);
      addCommand(EventNames.DELETE_TEST, DeleteTestCommand);
      addCommand(EventNames.UPLOAD_TEST_INFORMATION_ATTACHMENT_1, UploadTestInfoAttachment1Command);
      addCommand(EventNames.UPLOAD_TEST_INFORMATION_ATTACHMENT_2, UploadTestInfoAttachment2Command);
      addCommand(EventNames.UPLOAD_TEST_INFORMATION_ATTACHMENT_3, UploadTestInfoAttachment3Command);      
      addCommand(EventNames.DOWNLOAD_TEST_INFORMATION_ATTACHMENT, DownloadTestInfoAttachmentCommand);
    }
  }
}