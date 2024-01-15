package common.ui.validate;

import java.awt.*;
import java.util.*;
import javax.swing.*;

// Set with JTextField.setInputVerifier

public class SwingFieldVerifier
  extends InputVerifier
{
  protected String name;
  protected Component parent;
  protected ArrayList validators = new ArrayList();
  
  public SwingFieldVerifier(Component parent, String name)
  {
    this.parent = parent;
    this.name = name;
  }
  
  public void addValidator(FieldValidator validator)
  {
    validators.add(validator);
  }
  
  public void removeValidator(FieldValidator validator)
  {
    validators.remove(validator);
  }
  
  public boolean verify(JComponent component)
  {
    JTextField field = (JTextField)component;
    return isValid(field.getText());
  }

  public boolean isValid(String value)
  {
    for (int i = 0; i < validators.size(); i++)
    {
      FieldValidator validator = (FieldValidator)validators.get(i);
      //System.out.println("Validating: " + validator.getClass().getName());
      if (!validator.isValid(value))
      {
        JOptionPane.showMessageDialog(parent,
          validator.getMessage(),
          "Input Error in '" + name + "' Field",
          JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }
    return true;
  }
}

