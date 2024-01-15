package {
  public class WordValue {
    private static const _enumMap:Object = {
      I         :"I",
      YOU       :"you",
      AM        :"am",
      ARE       :"are",
      HAPPY     :"happy",
      SAD       :"sad",
      GOOD      :"good",
      BAD       :"bad",
      JUMP      :"jump",
      OVER      :"over",
      LEFT      :"left",
      RIGHT     :"right",
      UNDER     :"under",
      HE        :"he",
      SHE       :"she",
      HIS       :"his",
      HER       :"her",
      CAT       :"cat",
      DOG       :"dog",
      CAR       :"car",
      APOS_S    :"'s",
      LY        :"ly",
      PHONE     :"phone",
      QUESTION  :"?",
      RUN       :"run",
      S         :"s",
      ING       :"ing",
      THE       :"the",
      IS        :"is",
      IN        :"in",
      WITH      :"with",
      A         :"a",
      IT        :"it",
      AND       :"and",
      HOUSE     :"house",
      TABLE     :"table",
      PUT       :"put",
      DID       :"did"
    };

    public var name:String;
    public var hessianTypeName:String = 
      "com.caucho.ria.examples.words.WordValue";

    public function WordValue(value:String)
    {
      switch (value) {
        case "'s":
          name = "APOS_S";
          break;

        case "?":
          name = "QUESTION";
          break;

        default:
          name = value.toUpperCase();
          break;
      }
    }

    public function toString():String
    {
      return enumValueToString(name);
    }

    public static function enumValueToString(enumValue:String):String
    {
      return _enumMap[enumValue];
    }
  }
}
