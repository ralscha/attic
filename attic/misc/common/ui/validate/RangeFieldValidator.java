package common.ui.validate;

import java.awt.*;
import javax.swing.*;

public class RangeFieldValidator
  extends AbstractFieldValidator
{
  protected int min = Integer.MIN_VALUE;
  protected int max = Integer.MAX_VALUE;

  public RangeFieldValidator(int min, int max)
  {
    this(min, max, "You must provide a value between " + min + " and " + max);
  }
  
  public RangeFieldValidator(int min, int max, String message)
  {
    super(message);
    this.min = min;
    this.max = max;
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false; 
    try
    {
      int val = Integer.parseInt(value);
      return val >= min && val <= max;
    }
    catch (NumberFormatException e)
    {
      return false;
    }
  }
}

