package common.ui.validate;

public class DecimalFieldValidator
  extends AbstractFieldValidator
{
  public DecimalFieldValidator()
  {
    this("This field must be a Decimal value");
  }
  
  public DecimalFieldValidator(String message)
  {
    super(message);
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false;
    try
    {
      Double.parseDouble(value);
      return true;
    }
    catch (NumberFormatException e)
    {
      return false;
    }
  }
}

