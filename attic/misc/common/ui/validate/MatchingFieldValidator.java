package common.ui.validate;

import java.awt.*;
import javax.swing.*;

public class MatchingFieldValidator
  extends AbstractFieldValidator
{
  protected ValidatingField field;
 
  public MatchingFieldValidator(ValidatingField field)
  {
    this(field, "This field must match the field named " + field.getName());
  }
  
  public MatchingFieldValidator(ValidatingField field, String message)
  {
    super(message);
    this.field = field;
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false; 
    return value.equals(field.getValue());
  }
}

