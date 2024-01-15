package slide;

import java.awt.*;
import javax.swing.*;
import common.ui.slide.*;

public class JSlideTest extends JPanel
{
  public JSlideTest()
  {
    setLayout(new BorderLayout(4, 4));
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    GradientSlide gradient = new GradientSlide(Color.blue, Color.red);
    gradient.setBorder(BorderFactory.createEtchedBorder());
    add(BorderLayout.NORTH, gradient);
    
    JPanel panel = new JPanel(new GridLayout(1, 2, 4, 4));
    panel.add(new JSlide());
    panel.add(new JSlide(new ColorRangeView(), 
      new GradientSlide(Color.black, Color.white)));
    add(BorderLayout.SOUTH, panel);
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JSlide Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JSlideTest());
    frame.pack();
    frame.show();
  }
}

