package common.ui.calculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculatorButton extends JButton
  implements ActionListener
{
  protected CalculatorCommands.Command command;
  
  protected JCalculator calculator;
  
  public CalculatorButton(
    String text, JCalculator calculator,
    CalculatorCommands.Command command)
  {
    super(text);
    this.calculator = calculator;
    this.command = command;
    addActionListener(this);
    setMargin(new Insets(2, 2, 2, 2));
    setDefaultCapable(false);
  }
  
  public void paintComponent(Graphics g)
  {
    setForeground(getParent().getForeground());
    super.paintComponent(g);
  }

  public void actionPerformed(ActionEvent event)
  {
    if (command != null)
    {
      if (command instanceof CalculatorCommands.Unary)
      {
        evaluate();
        CalculatorStack stack = calculator.getStack();
        stack.pushFunction((CalculatorCommands.Function)command);
        evaluate();
      }
      if (command instanceof CalculatorCommands.Binary)
      {
        evaluate();
        CalculatorField field = calculator.getField();
        CalculatorStack stack = calculator.getStack();
        stack.pushNumber(field.getNumber());
        stack.pushFunction((CalculatorCommands.Function)command);
        field.clearField();
      }
      if (!(command instanceof CalculatorCommands.Function))
      {
        command.exec(calculator);
      }
    }
    // Handle '='
    else evaluate();
  }

  protected void evaluate()
  {
    CalculatorStack stack = calculator.getStack();
    if (!stack.isEmpty() && stack.isFunction())
    {
      CalculatorField field = calculator.getField();
      CalculatorCommands.Function function = stack.popFunction();
      stack.pushNumber(field.getNumber());
      function.exec(calculator);
      field.setNumber(stack.popNumber());
    }
  }
}

