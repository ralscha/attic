package desktop;

/*
=====================================================================
  
  ExampleCalculator.java

  Created by Claude Duguay
  Copyright (c) 2001

=====================================================================
*/

import java.awt.*;
import javax.swing.*;

public class ExampleCalculator extends JPanel
{
  public ExampleCalculator()
  {
    setLayout(new BorderLayout(2, 2));
    setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    
    JTextField field = new JTextField("1999.01");
    field.setHorizontalAlignment(JTextField.RIGHT);
    add(BorderLayout.NORTH, field);
    
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4, 4, 1, 1));
    panel.add(makeButton("1"));
    panel.add(makeButton("2"));
    panel.add(makeButton("3"));
    panel.add(makeButton("+"));
    panel.add(makeButton("4"));
    panel.add(makeButton("5"));
    panel.add(makeButton("6"));
    panel.add(makeButton("-"));
    panel.add(makeButton("7"));
    panel.add(makeButton("8"));
    panel.add(makeButton("9"));
    panel.add(makeButton("*"));
    panel.add(makeButton("."));
    panel.add(makeButton("0"));
    panel.add(makeButton("="));
    panel.add(makeButton("/"));
    add(BorderLayout.CENTER, panel);
  }
  
  protected JButton makeButton(String text)
  {
    JButton button = new JButton(text);
    button.setMargin(new Insets(0, 0, 0, 0));
    return button;
  }
}

