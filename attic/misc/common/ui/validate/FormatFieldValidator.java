package common.ui.validate;

import java.text.*;

public class FormatFieldValidator
  extends AbstractFieldValidator
{
  protected Format format;

  public FormatFieldValidator(Format format)
  {
    this(format, "You must provide valid text format");
  }
  
  public FormatFieldValidator(Format format, String message)
  {
    super(message);
    this.format = format;
  }

  public boolean isValid(String value)
  {
    if (isNull(value)) return false; 
    try
    {
      format.parseObject(value);
      return true;
    }
    catch (ParseException e)
    {
      return false;
    }
  }
}

