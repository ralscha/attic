package common.ui.validate;

public interface FieldValidator
{
  public boolean isValid(String value);
  public String getMessage();
}

