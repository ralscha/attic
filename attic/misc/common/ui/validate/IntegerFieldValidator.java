package common.ui.validate;

public class IntegerFieldValidator
  extends AbstractFieldValidator
{
  public IntegerFieldValidator()
  {
    this("You must enter an Integer value");
  }
  
  public IntegerFieldValidator(String message)
  {
    super(message);
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false;
    try
    {
      Integer.parseInt(value);
      return true;
    }
    catch (NumberFormatException e)
    {
      return false;
    }
  }
}

