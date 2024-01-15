package ch.ess.mp3search.model {
  import com.adobe.cairngorm.model.IModelLocator;
  
  import mx.collections.ArrayCollection;
  

  [Bindable]
  public class ModelLocator implements IModelLocator {
    
    public var songsAC:ArrayCollection;
    public var playlistAC:ArrayCollection = new ArrayCollection();
       
       
    public var baseURL:String = "";  
    
    public var info:Info;
    
    //TEST 
    //public var baseURL:String = "../";
    
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