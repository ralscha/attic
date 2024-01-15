package meter;
/*
=====================================================================

  JMeterTest.java
  
  Created by Claude Duguay
  Copyright (c) 2001
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;
import common.ui.meter.*;

public class JMeterTest extends JPanel
{
  public JMeterTest()
  {
    int radius = 100;
    JPanel quad = new JPanel(new GridLayout(2, 2));
    quad.setOpaque(false);
    quad.add(newMeter("[ NW ]", 270, 90, radius));
    quad.add(newMeter("[ NE ]", 0, 90, radius));
    quad.add(newMeter("[ SW ]", 180, 90, radius));
    quad.add(newMeter("[ SE ]", 90, 90, radius));
    
    JPanel vert = new JPanel(new GridLayout(1, 2));
    vert.setOpaque(false);
    vert.add(newMeter("[ West ]", 180, 180, radius));
    vert.add(newMeter("[ East ]", 0, 180, radius));

    JPanel horz = new JPanel(new GridLayout(2, 1));
    horz.setOpaque(false);
    horz.add(newMeter("[ North ]", 270, 180, radius));
    horz.add(newMeter("[ South ]", 90, 180, radius));

    setLayout(new GridLayout(2, 2));
    add(quad);
    add(horz);
    add(newMeter("[ Full ]", 180, 360, radius));
    add(vert);
  }

  protected JComponent newMeter(String name,
    int start, int extent, int radius)
  {
    JMeter meter = new JMeter(start, extent, radius);
    JPanel panel = makeTitledPanel(name, meter);
    Thread thread = new MeterDemoThread(meter);
    thread.start();
    return panel;
  }
  
  public JPanel makeTitledPanel(String name, Component child)
  {
    JPanel panel = new JPanel(new GridLayout());
    panel.setBorder(BorderFactory.createTitledBorder(name));
    panel.add(child);
    return panel;
  }
  
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JMeter Test");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(
      BorderLayout.CENTER, new JMeterTest());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.pack();
    frame.setVisible(true);
  }
}

