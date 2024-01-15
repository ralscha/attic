package common.ui.validate;

import java.awt.*;
import javax.swing.*;

public class AlphaFieldValidator
  extends AbstractFieldValidator
{
  public AlphaFieldValidator()
  {
    this("This field must be an alphabetical value");
  }
  
  public AlphaFieldValidator(String message)
  {
    super(message);
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false;
    for (int i = 0; i < value.length(); i++)
    {
      if (!Character.isLetter(value.charAt(i)))
        return false;
    }
    return true;
  }
}

