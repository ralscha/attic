package common.ui.validate;

import java.awt.*;
import javax.swing.*;

public class AlphaNumFieldValidator
  extends AbstractFieldValidator
{
  public AlphaNumFieldValidator()
  {
    this("This field must be an alphanumeric value");
  }
  
  public AlphaNumFieldValidator(String message)
  {
    super(message);
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false;
    for (int i = 0; i < value.length(); i++)
    {
      if (!Character.isLetterOrDigit(value.charAt(i)))
        return false;
    }
    return true;
  }
}

