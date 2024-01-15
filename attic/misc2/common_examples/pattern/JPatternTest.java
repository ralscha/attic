package pattern;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import common.ui.pattern.*;

public class JPatternTest extends JPanel
  implements ActionListener
{
  protected JPattern pattern;
  
  public JPatternTest()
  {
    pattern = new JPattern(JPattern.COMBO);
    pattern.setPattern(new PatternPaint(
      PatternConstants.SLASH, Color.blue, Color.red));
    pattern.addActionListener(this);
    setLayout(new GridLayout(1, 1));
    add(pattern);
  }
  
  public void actionPerformed(ActionEvent event)
  {
    System.out.println(pattern.getPattern());
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JPattern Test");
    frame.getContentPane().setLayout(new GridLayout(1, 1));
    frame.getContentPane().add(new JPatternTest());
    frame.pack();
    frame.show();
  }
}

