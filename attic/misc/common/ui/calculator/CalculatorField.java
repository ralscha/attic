package common.ui.calculator;

import javax.swing.*;

public class CalculatorField extends JTextField
{
  public CalculatorField()
  {
    this("");
  }

  public CalculatorField(String text)
  {
    super(text);
    setEditable(false);
    setHorizontalAlignment(RIGHT);
    setBorder(UIManager.getBorder("TextField.border"));
  }

  public void clearField()
  {
    setText("");
  }
  
  public boolean isClear()
  {
    return getText().trim().length() == 0;
  }
  
  public double getNumber()
  {
    double number = 0;
    try
    {
      number = Double.parseDouble(getText());
    }
    catch (NumberFormatException e) {}
    return number;
  }
  
  public void setNumber(double number)
  {
    setText("" + number);
  }

  public void addDigit(int digit)
  {
    setText(getText() + digit);
  }
  
  public void backspace()
  {
    if (isClear()) return;
    String text = getText();
    setText(text.substring(0, text.length() - 1));
  }
  
  public boolean hasDecimal()
  {
    return getText().indexOf('.') != -1;
  }

  public void addDecimal()
  {
    if (!hasDecimal())
      setText(getText() + '.');
  }
  
  public void invertSign()
  {
    String text = getText();
    if (text.startsWith("-"))
      setText(text.substring(1));
    else setText('-' + text);
  }
}

