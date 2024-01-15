package common.ui.validate;

public class BlankFieldValidator
  extends AbstractFieldValidator
{
  public BlankFieldValidator()
  {
    super("You must provide a value for this field");
  }
  
  public BlankFieldValidator(String message)
  {
    super(message);
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false; 
    return value.length() > 0;
  }
}

