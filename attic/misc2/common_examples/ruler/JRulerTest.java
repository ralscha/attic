package ruler;

import java.awt.*;
import javax.swing.*;
import common.ui.ruler.*;

public class JRulerTest extends JPanel
  implements RulerConstants
{
  public JRulerTest()
  {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    
    JPanel inner = newPanel(
      JRuler.getFixedInstance(NORTH, FORWARD,
        0, 0.0, 0.0, 0.0, 12.0, 1.0, 40, 10, 2),
      JRuler.getFixedInstance(SOUTH, BACKWARD,
        0, 0.0, 0.0, 0.0, 12.0, 1.0, 40, 10, 2),
      JRuler.getFixedInstance(WEST, FORWARD,
        0, 0.0, 0.0, 0.0, 7.0, 1.0, 30, 4, 2),
      JRuler.getFixedInstance(EAST, BACKWARD,
        0, 0.0, 0.0, 0.0, 7.0, 1.0, 30, 4, 2),
      new JPanel());

    JPanel inside = newPanel(
      JRuler.getVariableInstance(NORTH, BACKWARD,
        0, 0.0, 0.0, 0.0, 10.0, 1.0, 10, 2),
      JRuler.getVariableInstance(SOUTH, BACKWARD,
        2, 5.0, 0.25, 0.75, 10.0, 0.25, 10, 2),
      JRuler.getVariableInstance(WEST, BACKWARD,
        0, 0.0, 0.0, 0.0, 10.0, 1.0, 8, 2),
      JRuler.getVariableInstance(EAST, BACKWARD,
        1, 4.5, 0.5, 0.5, 10.0, 0.5, 8, 2),
      inner);
    
    JPanel middle = newPanel(
      JRuler.getVariableInstance(NORTH, FORWARD,
        0, 0.0, 0.0, 0.0, 10.0, 1.0, 4, 2),
      JRuler.getVariableInstance(SOUTH, FORWARD,
        2, 5.0, 0.25, 0.75, 10.0, 0.25, 4, 2),
      JRuler.getVariableInstance(WEST, FORWARD,
        0, 0.0, 0.0, 0.0, 10.0, 1.0, 6, 2),
      JRuler.getVariableInstance(EAST, FORWARD,
        1, 4.5, 0.5, 0.5, 10.0, 0.5, 6, 2),
      inside);
    
    JPanel outside = newPanel(
      JRuler.getEnglishInstance(NORTH, FORWARD, 10),
      JRuler.getEnglishInstance(SOUTH, BACKWARD, 10),
      JRuler.getMetricInstance(WEST, FORWARD, 15),
      JRuler.getMetricInstance(EAST, BACKWARD, 15),
      middle);

    add(BorderLayout.CENTER, outside);
  }
  
  protected static JPanel newPanel(
    JComponent north, JComponent south,
    JComponent west, JComponent east,
    JComponent center)
  {
    JPanel panel = new JPanel(new BorderLayout(4, 4));
    panel.add(BorderLayout.NORTH, north);
    panel.add(BorderLayout.SOUTH, south);
    panel.add(BorderLayout.WEST, west);
    panel.add(BorderLayout.EAST, east);
    panel.add(BorderLayout.CENTER, center);
    panel.setOpaque(false);
    return panel;
  }
    
  
  public static void main(String[] args)
  {
    common.swing.PlafPanel.setNativeLookAndFeel(true);
    JFrame frame = new JFrame("JRuler Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JRulerTest());
    frame.setSize(800, 600);
    frame.setVisible(true);
  }
}

