package common.ui.validate;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class JValidatingField
  extends JTextField
  implements ValidatingField
{
  protected String name;
  protected SwingFieldVerifier verifier;
  
  public JValidatingField(String name)
  {
    this(name, new PlainDocument(), "", 0);
  }
  
  public JValidatingField(String name, int col)
  {
    this(name, new PlainDocument(), "", col);
  }
  
  public JValidatingField(String name, String text)
  {
    this(name, new PlainDocument(), text, 0);
  }
  
  public JValidatingField(String name, String text, int col)
  {
    this(name, new PlainDocument(), text, col);
  }
  
  public JValidatingField(String name, Document doc, String text, int col)
  {
    super(doc, text, col);
    this.name = name;
    setInputVerifier(verifier = new SwingFieldVerifier(this, name));
  }
  
  public String getName()
  {
    return name;
  }
  
  public String getValue()
  {
    return getText();
  }

  public void addValidator(FieldValidator validator)
  {
    verifier.addValidator(validator);
  }
  
  public void removeValidator(FieldValidator validator)
  {
    verifier.removeValidator(validator);
  }
}

