package common.ui.calculator;

import java.awt.*;
import javax.swing.*;

public class CalculatorCommands
{
  public static interface Command
  {
    public void exec(JCalculator calculator);
  }
    
  public static interface Function extends Command {}
  
  public static interface Unary extends Function {}
  
  public static interface Binary extends Function {}

// ----------------------------------------------------------------
// BINARY FUNCTIONS
// ----------------------------------------------------------------
  
  public static class Plus implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      double field = stack.popNumber();
      stack.pushNumber(stack.popNumber() + field);
    }
  }

  public static class Minus implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      double field = stack.popNumber();
      stack.pushNumber(stack.popNumber() - field);
    }
  }

  public static class Mult implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      double field = stack.popNumber();
      stack.pushNumber(stack.popNumber() * field);
    }
  }

  public static class Div implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      double field = stack.popNumber();
      stack.pushNumber(stack.popNumber() / field);
    }
  }

  public static class And implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      int field = (int)Math.floor(stack.popNumber());
      int integer = (int)Math.floor(stack.popNumber());
      stack.pushNumber(integer & field);
    }
  }

  public static class Or implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      int field = (int)Math.floor(stack.popNumber());
      int integer = (int)Math.floor(stack.popNumber());
      stack.pushNumber(integer | field);
    }
  }

  public static class Xor implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      int field = (int)Math.floor(stack.popNumber());
      int integer = (int)Math.floor(stack.popNumber());
      stack.pushNumber(integer ^ field);
    }
  }

  public static class Left implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      int field = (int)Math.floor(stack.popNumber());
      int integer = (int)Math.floor(stack.popNumber());
      stack.pushNumber(integer << field);
    }
  }
    
  public static class Right implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      int field = (int)Math.floor(stack.popNumber());
      int integer = (int)Math.floor(stack.popNumber());
      stack.pushNumber(integer >> field);
    }
  }

  public static class Mod implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      double field = stack.popNumber();
      stack.pushNumber(stack.popNumber() % field);
    }
  }

  public static class Pow implements Binary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      double field = stack.popNumber();
      stack.pushNumber(Math.pow(stack.popNumber(), field));
    }
  }

// ----------------------------------------------------------------
// UNARY FUNCTIONS
// ----------------------------------------------------------------
  
  public static class Percent implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(stack.popNumber() / 100);
    }
  }

  public static class Reciprocal implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(1.0 / stack.popNumber());
    }
  }

  public static class Sqrt implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.sqrt(stack.popNumber()));
    }
  }

  public static class Abs implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.abs(stack.popNumber()));
    }
  }
    
  public static class Int implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.floor(stack.popNumber()));
    }
  }
    
  public static class Round implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.round(stack.popNumber()));
    }
  }
    
  public static class Factorial implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      int integer = (int)Math.floor(stack.popNumber());
      stack.pushNumber(factorial(integer));
    }
  }
  
  protected static double factorial(int number)
  {
    double result = 1;
    for (int i = number; i > 0; i--)
    {
      result *= i;
    }
    return result;
  }
    
  public static class PI implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.PI);
    }
  }

  public static class Not implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      int integer = (int)Math.floor(stack.popNumber());
      stack.pushNumber(~integer);
    }
  }
    
  public static class Exp implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.exp(stack.popNumber()));
    }
  }
  
  public static class Log implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      //stack.pushNumber(Math.log(stack.popNumber()));
    }
  }
  
  public static class Ln implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.log(stack.popNumber()));
    }
  }
  
  public static class Sin implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.sin(stack.popNumber()));
    }
  }
  
  public static class Cos implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.cos(stack.popNumber()));
    }
  }
  
  public static class Tan implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.tan(stack.popNumber()));
    }
  }
  
  public static class Asin implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.asin(stack.popNumber()));
    }
  }
  public static class Acos implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.acos(stack.popNumber()));
    }
  }
  public static class Atan implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.atan(stack.popNumber()));
    }
  }
  
  public static class Square implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.pow(stack.popNumber(), 2));
    }
  }

  public static class Cube implements Unary
  {
    public void exec(JCalculator calculator)
    {
      CalculatorStack stack = calculator.getStack();
      stack.pushNumber(Math.pow(stack.popNumber(), 3));
    }
  }
    
// ----------------------------------------------------------------
// COMMANDS
// ----------------------------------------------------------------
  
  public static class Clear implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.clearField();
    }
  }

  public static class ClearAll implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      CalculatorStack stack = calculator.getStack();
      field.clearField();
      stack.clear();
    }
  }

  public static class Backspace implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.backspace();
    }
  }

  public static class Zero implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(0);
    }
  }

  public static class One implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(1);
    }
  }

  public static class Two implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(2);
    }
  }

  public static class Three implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(3);
    }
  }

  public static class Four implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(4);
    }
  }

  public static class Five implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(5);
    }
  }

  public static class Six implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(6);
    }
  }

  public static class Seven implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(7);
    }
  }

  public static class Eight implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(8);
    }
  }

  public static class Nine implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDigit(9);
    }
  }

  public static class Decimal implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.addDecimal();
    }
  }

  public static class Sign implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.invertSign();
    }
  }

  public static class MemClear implements Command
  {
    public void exec(JCalculator calculator)
    {
      calculator.setMemory(0);
      calculator.getLabel().setText("C");
    }
  }

  public static class MemRecall implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      field.setNumber(calculator.getMemory());
    }
  }

  public static class MemStore implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      calculator.setMemory(field.getNumber());
      calculator.getLabel().setText("M");
    }
  }

  public static class MemPlus implements Command
  {
    public void exec(JCalculator calculator)
    {
      CalculatorField field = calculator.getField();
      double mem = calculator.getMemory();
      calculator.setMemory(mem + field.getNumber());
      calculator.getLabel().setText("M");
    }
  }
}

