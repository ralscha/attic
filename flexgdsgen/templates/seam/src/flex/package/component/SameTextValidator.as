package @packageProject@.component {
  import mx.validators.ValidationResult;
  import mx.validators.Validator;

  public class SameTextValidator extends Validator {
    
    public var otherSource:Object;
    public var otherProperty:String;
    public var sameFieldError:String;
    
    public function SameTextValidator() {
      super();
    }
    
    
    override protected function doValidation(value:Object):Array {
     
      var results:Array = value is String ? super.doValidation(value) : [];
      if (results.length > 0) {
        return results;
      }
      
      if (getValueFromSource() != otherSource[otherProperty]) {
        var vr:ValidationResult = new ValidationResult(true, "", "invalidValue", sameFieldError);
        results.push(vr);
      }
      
      return results;
      
    }
    

  }
}