package ch.ess.tt.model {
  import com.adobe.cairngorm.model.IModelLocator;
  
  import flash.net.FileReference;
  
  import mx.collections.ArrayCollection;
  import mx.controls.DataGrid;
  

  [Bindable]
  public class ModelLocator implements IModelLocator {
    
    //main workflow states
    public static const VIEWING_LOGIN_SCREEN:int = 0;
    public static const VIEWING_MAIN_APP:int = 1;

    public var workflowState:int = VIEWING_LOGIN_SCREEN;


    //app workflow statates
    public static const VIEWING_APP_PRINCIPALS:int = 0;
    public static const VIEWING_APP_TEST:int = 1;

    public var appWorkflowState:int = VIEWING_APP_TEST;


    //crud states
    public static const VIEWING_LIST_SCREEN:int = 0;
    public static const VIEWING_EDIT_SCREEN:int = 1;
    public static const VIEWING_TREE_SCREEN:int = 2;
    

    //logged in Principal
    public var principal:Principal;  


    //Principal Management
    public var principalsAC:ArrayCollection;    
    public var selectedPrincipal:Principal;
    public var principalDataGrid:DataGrid;
    public var principalWorkflowState:int = VIEWING_LIST_SCREEN;  
 
    //Test Management
    public var testsAC:ArrayCollection;
    public var selectedTest:Test;
    public var testDataGrid:DataGrid;
    public var testWorkflowState:int = VIEWING_LIST_SCREEN;
    
    public var testFile1Ref:FileReference;
    public var testFile2Ref:FileReference;
    public var testFile3Ref:FileReference;
    public var delete1Attachment:Boolean;
    public var delete2Attachment:Boolean;
    public var delete3Attachment:Boolean;


    //Base URL
    public var baseURL:String = "http://localhost:8080/tt";


    //
    //Singleton stuff
    //
    private static var modelLocator:ModelLocator;

    public static function getInstance():ModelLocator{
      if (modelLocator == null) {
        modelLocator = new ModelLocator();
      }
      return modelLocator;
    }
        
    //The constructor should be private, but this is not
    //possible in ActionScript 3.0. So, throwing an Error if
    //a second PomodoModelLocator is created is the best we
    //can do to implement the Singleton pattern.
    public function ModelLocator() {
      if (modelLocator != null) {
        throw new Error("Only one ModelLocator instance may be instantiated.");
      }
    }
  }
}