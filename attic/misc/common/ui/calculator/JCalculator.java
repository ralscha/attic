package common.ui.calculator;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class JCalculator extends JPanel
  implements SwingConstants
{
  protected CalculatorField field = new CalculatorField();
  protected CalculatorStack stack = new CalculatorStack();
  protected CalculatorField label = new CalculatorField("C");
  protected JPanel math, trig;
  protected boolean expand;
  protected double memory;

  public JCalculator()
  {
    this(false);
  }
  
  public JCalculator(boolean expand)
  {
    label.setHorizontalAlignment(CENTER);
    
    JPanel exp = new JPanel(new BorderLayout(4, 4));
    exp.add(BorderLayout.CENTER, createFunctionPanel());
    exp.add(BorderLayout.WEST, math = createMathPanel());
    exp.add(BorderLayout.EAST, trig = createTrigPanel());
    setExpand(expand);
    
    JPanel main = new JPanel(new BorderLayout(4, 4));
    main.add(BorderLayout.NORTH, createClearPanel());
    main.add(BorderLayout.CENTER, createDigitPanel());
    main.add(BorderLayout.EAST, exp);

    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    setLayout(new BorderLayout(4, 4));
    add(BorderLayout.NORTH, field);
    add(BorderLayout.CENTER, main);
    add(BorderLayout.WEST, createMemoryPanel());
  }

  protected JPanel createClearPanel()
  {
    JPanel panel = new JPanel(new GridLayout(1, 4, 4, 4));
    panel.setForeground(Color.red);
    panel.add(new CalculatorButton("Backspace",
      this, new CalculatorCommands.Backspace()));
    panel.add(new CalculatorButton("CE",
      this, new CalculatorCommands.Clear()));
    panel.add(new CalculatorButton("C",
      this, new CalculatorCommands.ClearAll()));
    JPanel east = new JPanel(new BorderLayout());
    east.add(BorderLayout.EAST, panel);
    return east;	
  }

  protected JPanel createDigitPanel()
  {
    JPanel panel = new JPanel(new GridLayout(4, 3, 4, 4));
    panel.setForeground(Color.blue);
    panel.add(new CalculatorButton("7",
      this, new CalculatorCommands.Seven()));
    panel.add(new CalculatorButton("8",
      this, new CalculatorCommands.Eight()));
    panel.add(new CalculatorButton("9",
      this, new CalculatorCommands.Nine()));
    panel.add(new CalculatorButton("4",
      this, new CalculatorCommands.Four()));
    panel.add(new CalculatorButton("5",
      this, new CalculatorCommands.Five()));
    panel.add(new CalculatorButton("6",
      this, new CalculatorCommands.Six()));
    panel.add(new CalculatorButton("1",
      this, new CalculatorCommands.One()));
    panel.add(new CalculatorButton("2",
      this, new CalculatorCommands.Two()));
    panel.add(new CalculatorButton("3",
      this, new CalculatorCommands.Three()));
    panel.add(new CalculatorButton("+/-",
      this, new CalculatorCommands.Sign()));
    panel.add(new CalculatorButton("0",
      this, new CalculatorCommands.Zero()));
    panel.add(new CalculatorButton(".",
      this, new CalculatorCommands.Decimal()));
    return panel;		
  }

  protected JPanel createFunctionPanel()
  {
    JPanel panel = new JPanel(new GridLayout(4, 2, 4, 4));
    panel.setForeground(Color.magenta);
    panel.add(new CalculatorButton("/",
      this, new CalculatorCommands.Div()));
    panel.add(new CalculatorButton("sqrt",
      this, new CalculatorCommands.Sqrt()));
    panel.add(new CalculatorButton("*",
      this, new CalculatorCommands.Mult()));
    panel.add(new CalculatorButton("%",
      this, new CalculatorCommands.Percent()));
    panel.add(new CalculatorButton("-",
      this, new CalculatorCommands.Minus()));
    panel.add(new CalculatorButton("1/x",
      this, new CalculatorCommands.Reciprocal()));
    panel.add(new CalculatorButton("+",
      this, new CalculatorCommands.Plus()));
    panel.add(new CalculatorButton("=", this, null));
    return panel;
  }
  
  protected JPanel createMathPanel()
  {
    JPanel panel = new JPanel(new GridLayout(4, 3, 4, 4));
    panel.setForeground(Color.magenta);
    panel.add(new CalculatorButton("abs",
      this, new CalculatorCommands.Abs()));
    panel.add(new CalculatorButton("and",
      this, new CalculatorCommands.And()));
    panel.add(new CalculatorButton("lsh",
      this, new CalculatorCommands.Left()));
    
    panel.add(new CalculatorButton("mod",
      this, new CalculatorCommands.Mod()));
    panel.add(new CalculatorButton("or",
      this, new CalculatorCommands.Or()));
    panel.add(new CalculatorButton("rsh",
      this, new CalculatorCommands.Right()));
    
    panel.add(new CalculatorButton("int",
      this, new CalculatorCommands.Int()));
    panel.add(new CalculatorButton("xor",
      this, new CalculatorCommands.Xor()));
    panel.add(new CalculatorButton("n!",
      this, new CalculatorCommands.Factorial()));
    
    panel.add(new CalculatorButton("rnd",
      this, new CalculatorCommands.Round()));
    panel.add(new CalculatorButton("not",
      this, new CalculatorCommands.Not()));
    panel.add(new CalculatorButton("pi",
      this, new CalculatorCommands.PI()));
    
    return panel;
  }
  
  protected JPanel createTrigPanel()
  {
    JPanel panel = new JPanel(new GridLayout(4, 3, 4, 4));
    panel.setForeground(Color.magenta);
    panel.add(new CalculatorButton("exp",
      this, new CalculatorCommands.Exp()));
    panel.add(new CalculatorButton("log",
      this, new CalculatorCommands.Log()));
    panel.add(new CalculatorButton("ln",
      this, new CalculatorCommands.Ln()));
    
    panel.add(new CalculatorButton("pow",
      this, new CalculatorCommands.Pow()));
    panel.add(new CalculatorButton("x^2",
      this, new CalculatorCommands.Square()));
    panel.add(new CalculatorButton("x^3",
      this, new CalculatorCommands.Cube()));
    
    panel.add(new CalculatorButton("sin",
      this, new CalculatorCommands.Sin()));
    panel.add(new CalculatorButton("cos",
      this, new CalculatorCommands.Cos()));
    panel.add(new CalculatorButton("tan",
      this, new CalculatorCommands.Tan()));
    
    panel.add(new CalculatorButton("asin",
      this, new CalculatorCommands.Asin()));
    panel.add(new CalculatorButton("acos",
      this, new CalculatorCommands.Acos()));
    panel.add(new CalculatorButton("atan",
      this, new CalculatorCommands.Atan()));
    return panel;
  }
  
  protected JPanel createMemoryPanel()
  {
    JPanel panel = new JPanel(new GridLayout(5, 1, 4, 4));
    panel.setForeground(Color.red);
    panel.add(label);
    panel.add(new CalculatorButton("MC",
      this, new CalculatorCommands.MemClear()));
    panel.add(new CalculatorButton("MR",
      this, new CalculatorCommands.MemRecall()));
    panel.add(new CalculatorButton("MS",
      this, new CalculatorCommands.MemStore()));
    panel.add(new CalculatorButton("M+",
      this, new CalculatorCommands.MemPlus()));
    return panel;		
  }

  public CalculatorField getField()
  {
    return field;
  }
  
  public CalculatorStack getStack()
  {
    return stack;
  }
  
  public CalculatorField getLabel()
  {
    return label;
  }
  
  public double getMemory()
  {
    return memory;
  }
  
  public void setMemory(double memory)
  {
    this.memory = memory;
  }
  
  public boolean isExpand()
  {
    return expand;
  }
  
  public void setExpand(boolean expand)
  {
    this.expand = expand;
    math.setVisible(expand);
    trig.setVisible(expand);
    invalidate();
  }
}

