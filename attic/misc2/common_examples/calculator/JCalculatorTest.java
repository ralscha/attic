package calculator;

import java.awt.*;
import javax.swing.*;
import common.ui.calculator.*;

public class JCalculatorTest
{
  public static void main(String[] args)
  {
    common.swing.PlafPanel.setNativeLookAndFeel(true);
    
    JFrame frame = new JFrame("JCalculator Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JCalculator(false));
    frame.pack();
    frame.setVisible(true);
  }
}

