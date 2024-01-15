package common.ui.validate;

public abstract class AbstractFieldValidator
  implements FieldValidator
{
  protected String message;

  public AbstractFieldValidator()
  {
    this("Field value is not valid");
  }
  
  public AbstractFieldValidator(String message)
  {
    this.message = message;
  }

  public abstract boolean isValid(String field);
  
  public String getMessage()
  {
    return message;
  }
  
  // Utility method, used by most subclasses
  protected boolean isNull(String value)
  {
    return value == null;
  }
}

