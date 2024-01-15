package digital;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import common.ui.digital.*;
public class JDigitalClockTest extends JPanel
  implements ActionListener
{
  protected JDigital digital;
  protected ClockImage model;
  
  public JDigitalClockTest()
  {
    digital = new JDigital();
    digital.setModel(model = new ClockImage());
    digital.setRenderer(new LEDRenderer(LEDRenderer.ROUND));
    
    setLayout(new GridLayout());
    add(digital);
    
    Timer timer = new Timer(1000, this);
    timer.start();
  }
  
  public void actionPerformed(ActionEvent event)
  {
    model.setTime(System.currentTimeMillis());
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JDigital Clock Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JDigitalClockTest());
    frame.pack();
    frame.show();
  }
}

