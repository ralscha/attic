package @packageProject@.component {
  import mx.validators.StringValidator;
  import mx.validators.ValidationResult;
  
  import org.granite.tide.events.TideResultEvent;
  import org.granite.tide.spring.Spring;

  public class UniquePropertyValidator extends StringValidator {
    private var _entityName:String;
    private var _entityProperty:String;
    private var _uniqueFieldError:String;
    private var _excludeMyselfId:Number;

    private var _valueChecking:Object = null;
    private var _lastCheckedValue:Object = null;
    private var _lastCheckedResults:Array = [];

    public function UniquePropertyValidator() {
      super();
    }

    private function reset():void {
      _lastCheckedValue = null;
      _lastCheckedResults = [];
      _valueChecking = null;     
    }

    public function set entityName(entityName:String):void {
      _entityName = entityName;
      reset();
    }

    public function set entityProperty(entityProperty:String):void {
      _entityProperty = entityProperty;
      reset();
    }
    
    public function set uniqueFieldError(uniqueFieldError:String):void {
      _uniqueFieldError = uniqueFieldError;
      reset();
    }

    public function set excludeMyselfId(excludeMyselfId:Number):void {
      _excludeMyselfId = excludeMyselfId;
      reset();
    }

    private function onIsUnique(e:TideResultEvent):void {
      validate(e);
    }

    override protected function doValidation(value:Object):Array {

      var results:Array = value is String ? super.doValidation(value) : [];
      if (results.length > 0) {
        return results;
      }

      if (value is TideResultEvent) {
        if (!TideResultEvent(value).result) {
          var vr:ValidationResult = new ValidationResult(true, "", "invalidValue", _uniqueFieldError);
          results.push(vr);
        }
        
        _lastCheckedValue = _valueChecking;
        _lastCheckedResults = results;
        _valueChecking = null;
        
        return results;

      } else {
        var value:Object = getValueFromSource();
        if (value != _lastCheckedValue) {
          if (_valueChecking == null) {
            _valueChecking = value;
            Spring.getInstance().getContext().validators.isPropertyUnique(_entityName, _entityProperty, value, _excludeMyselfId, onIsUnique);
          }
          return results;
        }        
      }
      
      
      return _lastCheckedResults;

    }

  }
}