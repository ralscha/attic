package common.ui.calculator;

import java.util.*;

public class CalculatorStack extends Stack
{
  public boolean isNumber()
  {
    return (peek() instanceof Double);
  }
  
  public double peekNumber()
  {
    return ((Double)peek()).doubleValue();
  }
  
  public double popNumber()
  {
    return ((Double)pop()).doubleValue();
  }
  
  public void pushNumber(double number)
  {
    push(new Double(number));
  }

  public boolean isFunction()
  {
    return (peek() instanceof CalculatorCommands.Function);
  }
  
  public CalculatorCommands.Function popFunction()
  {
    return (CalculatorCommands.Function)pop();
  }
  
  public void pushFunction(CalculatorCommands.Function function)
  {
    push(function);
  }
}

