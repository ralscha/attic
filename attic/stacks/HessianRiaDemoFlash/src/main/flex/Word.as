package {
  public class Word {
    public var _x:int;
    public var _y:int;
    public var _value:WordValue;
    public var hessianTypeName:String = "com.caucho.ria.examples.words.Word";

    public function Word(value:String)
    {
      _value = new WordValue(value);
    }
  }
}
